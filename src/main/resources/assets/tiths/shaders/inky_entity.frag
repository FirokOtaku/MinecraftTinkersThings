#version 120

// Inky - entity darkening shader.
//
// Fragment-stage-only program: the vertex stage stays on the fixed-function
// pipeline. Vanilla and Tinkers' entity models submit raw vertex arrays /
// display lists, so a full vertex+fragment program would have to re-bind all
// vertex attributes itself and would be much more likely to conflict with
// other rendering mods. With only a fragment stage, the fixed pipeline still
// fills in gl_Color / gl_TexCoord[] / gl_FogFragCoord; the lightmap
// coordinates are already normalized by the texture matrix set up in
// EntityRenderer#enableLightmap.
//
// NOTE: keep this file ASCII-only. Some drivers silently mis-parse shader
// sources containing non-ASCII bytes (even inside comments), producing a
// program that links successfully but has no active uniforms.
//
// What this shader does: sample texture * vertex color * lightmap like the
// fixed pipeline would, then blend towards an ink color and darken overall,
// so the mob looks soaked in ink. Since the shader takes over the fragment
// stage, vanilla fog and the hurt red flash (both fixed-function features)
// have to be re-implemented here manually.

uniform sampler2D uTexture;   // texture unit 0: entity texture
uniform sampler2D uLightmap;  // texture unit 1: lightmap
uniform float uInkAmount;     // 0 - 1, ink coverage
uniform vec3 uInkColor;       // ink color
uniform float uHurt;          // 0 - 1, hurt flash amount

// fog (re-implemented because the fixed-function fog is bypassed)
uniform float uFogEnabled;    // 0 / 1
uniform float uFogMode;       // 0 - linear, 1 - exp, 2 - exp2
uniform vec3 uFogColor;
uniform float uFogStart;
uniform float uFogEnd;
uniform float uFogDensity;

float fogFactor()
{
    float coord = gl_FogFragCoord;

    if (uFogMode < 0.5) // GL_LINEAR
    {
        float range = max(uFogEnd - uFogStart, 0.0001);
        return clamp((coord - uFogStart) / range, 0.0, 1.0);
    }
    else if (uFogMode < 1.5) // GL_EXP
    {
        return clamp(1.0 - exp(-uFogDensity * coord), 0.0, 1.0);
    }
    else // GL_EXP2
    {
        float d = uFogDensity * coord;
        return clamp(1.0 - exp(-d * d), 0.0, 1.0);
    }
}

void main()
{
    // replicate the fixed pipeline: texture * vertex color * lightmap
    vec4 tex = texture2D(uTexture, gl_TexCoord[0].st);
    vec4 light = texture2D(uLightmap, gl_TexCoord[1].st);
    vec4 color = tex * gl_Color * light;

    // luma keeps some body detail so the mob does not become a flat black blob
    float luma = dot(color.rgb, vec3(0.299, 0.587, 0.114));
    vec3 inked = uInkColor * (0.30 + 1.10 * luma);

    // blend towards ink, then darken overall
    color.rgb = mix(color.rgb, inked, uInkAmount);
    color.rgb *= 1.0 - 0.35 * uInkAmount;

    // keep the vanilla hurt red flash
    color.rgb = mix(color.rgb, vec3(1.0, 0.05, 0.05), uHurt * 0.6);

    if (uFogEnabled > 0.5)
    {
        color.rgb = mix(color.rgb, uFogColor, fogFactor());
    }

    gl_FragColor = color;
}
