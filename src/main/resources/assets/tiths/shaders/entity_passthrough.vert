#version 120

// Compatibility-profile passthrough vertex stage for entity tint programs.
//
// Entity models in 1.12.2 submit fixed-function vertex arrays / display lists.
// A core-style attribute-based vertex shader would have to re-bind every
// attribute and would conflict with other rendering mods. This file instead
// mirrors what the fixed pipeline already does, using the built-in GLSL 120
// inputs so we only need to attach it for a complete (linkable / drawable)
// program. Fragment-only programs may link on some drivers but then produce
// GL_INVALID_OPERATION on draw or leave the pipeline in a bad state.
//
// Intended to stay shared when entity tints are later merged into one program
// (see EntityTintShaderGuard JavaDoc: single program + TintState, not one
// program per effect). New whole-model tint fragments should pair with this
// vertex stage unless they truly need a different vertex path.
//
// NOTE: keep this file ASCII-only.

void main()
{
    gl_Position = ftransform();
    gl_FrontColor = gl_Color;
    gl_BackColor = gl_Color;

    // Texture unit 0: entity skin. Texture unit 1: lightmap, whose texture
    // matrix is set up by EntityRenderer#enableLightmap.
    gl_TexCoord[0] = gl_TextureMatrix[0] * gl_MultiTexCoord0;
    gl_TexCoord[1] = gl_TextureMatrix[1] * gl_MultiTexCoord1;

    // Default fog source is fragment depth (eye-space |z|).
    vec4 eye = gl_ModelViewMatrix * gl_Vertex;
    gl_FogFragCoord = abs(eye.z);
}
