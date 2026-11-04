#version 120

// 墨染 - 生物实体染色着色器
//
// 这是一个只包含片元阶段的程序, 顶点阶段依然交给固定管线处理.
// 原版和匠魂的实体模型都是直接提交顶点数组 / 显示列表的,
// 如果写成完整的 vertex + fragment 程序, 就得自己负责所有顶点属性的绑定,
// 与其它渲染类模组 (以及匠魂自己的模型系统) 冲突的风险会大得多.
// 只写着色器程序里的片元阶段时, 固定管线依旧会填好
// gl_Color (当前顶点色), gl_TexCoord[] (各纹理单元的坐标) 和 gl_FogFragCoord,
// 其中光照贴图的坐标已经由 EntityRenderer#enableLightmap 的纹理矩阵归一化好了.

uniform sampler2D uTexture;   // 纹理单元 0: 实体贴图
uniform sampler2D uLightmap;  // 纹理单元 1: 光照贴图
uniform float uInkAmount;     // 0 - 1, 墨水覆盖程度
uniform vec3 uInkColor;       // 墨水颜色
uniform float uHurt;          // 0 - 1, 受击红闪程度

// 雾 (着色器接管片元阶段以后, 原版通过固定管线做的雾就失效了, 需要手动补上)
uniform float uFogEnabled;    // 0 / 1
uniform float uFogMode;       // 0 - 线性, 1 - 指数, 2 - 指数平方
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
    // 手动复刻固定管线: 贴图 * 顶点色 * 光照贴图
    vec4 tex = texture2D(uTexture, gl_TexCoord[0].st);
    vec4 light = texture2D(uLightmap, gl_TexCoord[1].st);
    vec4 color = tex * gl_Color * light;

    // 亮度用来保留一点形体细节, 同时避免整只生物糊成一块纯黑色
    float luma = dot(color.rgb, vec3(0.299, 0.587, 0.114));
    vec3 inked = uInkColor * (0.30 + 1.10 * luma);

    // 向墨色靠拢, 再整体压暗
    color.rgb = mix(color.rgb, inked, uInkAmount);
    color.rgb *= 1.0 - 0.35 * uInkAmount;

    // 保留原版的受击红闪手感
    color.rgb = mix(color.rgb, vec3(1.0, 0.05, 0.05), uHurt * 0.6);

    if (uFogEnabled > 0.5)
    {
        color.rgb = mix(color.rgb, uFogColor, fogFactor());
    }

    gl_FragColor = color;
}
