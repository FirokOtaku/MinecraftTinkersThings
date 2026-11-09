package firok.tiths.client.render.tile;

import firok.tiths.TinkersThings;
import firok.tiths.common.Configs;
import firok.tiths.tile.TEPalantir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * 真知晶球 TESR:
 * <ul>
 *   <li>深邃深蓝外壳 + 缓慢涌动核心, 上下浮动</li>
 *   <li>触摸后外壳渐透, 内部景象渐入渐出</li>
 *   <li>方块按视角深度排序; 跨维度 / 未加载显示混沌迷雾</li>
 * </ul>
 */
@SideOnly(Side.CLIENT)
public class TESRPalantir extends TileEntitySpecialRenderer<TEPalantir>
{
    private static final ResourceLocation TEX_SHELL = new ResourceLocation(TinkersThings.MOD_ID, "textures/blocks/block_palantir.png");
    private static final ResourceLocation TEX_CORE = new ResourceLocation(TinkersThings.MOD_ID, "textures/blocks/block_palantir_core.png");
    /** 与贴图帧数一致 (16 帧竖条) */
    private static final int CORE_FRAMES = 16;
    /** 每帧持续 tick, 整圈约 16*10 = 160 tick */
    private static final int CORE_FRAME_TICKS = 10;

    private final Random chaosRand = new Random();

    @Override
    public void render(TEPalantir te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        if (te == null || te.getWorld() == null) return;

        World world = te.getWorld();
        float bob = MathHelper.sin((world.getTotalWorldTime() + partialTicks) * 0.08f) * 0.04f;
        float visionAlpha = te.getDisplayAlpha(partialTicks);
        float shellOpacity = te.getShellOpacity(partialTicks);

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D, y + 0.5D + bob, z + 0.5D);

        // 先画内部景象, 再画半透明外壳, 这样内容在球心可见
        if (visionAlpha > 0.01f && te.hasTarget())
        {
            renderVision(te, world, visionAlpha, partialTicks);
        }

        renderOrb(shellOpacity, visionAlpha, world.getTotalWorldTime() + partialTicks);

        GlStateManager.popMatrix();
    }

    private void renderOrb(float shellOpacity, float visionAlpha, float time)
    {
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
        );
        // 外壳需要写深度, 避免内部内容穿出球体表面过多
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);

        // 核心: 平时暗沉, 打开时略亮且更透
        bindTexture(TEX_CORE);
        float coreAlpha = 0.9f - visionAlpha * 0.45f;
        float coreTone = 0.55f + visionAlpha * 0.35f;
        GlStateManager.color(0.35f * coreTone, 0.45f * coreTone, 0.85f * coreTone, coreAlpha);
        float framePos = (time / (float) CORE_FRAME_TICKS) % CORE_FRAMES;
        drawAnimatedCore(0.11f, framePos);

        // 外壳: 深蓝色, opacity 由 TE 控制
        bindTexture(TEX_SHELL);
        float shade = 0.55f + (1f - visionAlpha) * 0.35f; // 关闭时更深
        GlStateManager.color(0.25f * shade, 0.4f * shade, 0.85f * shade, shellOpacity);
        // 半透明外壳后写深度会挡住后面物体; 对球体外壳用 depthMask false 更合适,
        // 但关闭时希望遮住内部核心的乱闪 — 按打开程度切换
        GlStateManager.depthMask(shellOpacity > 0.7f);
        drawTexturedCube(0.24f);
        GlStateManager.depthMask(true);

        GlStateManager.color(1f, 1f, 1f, 1f);
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
    }

    /**
     * framePos 可为小数, 用相邻两帧 UV 做简易混合观感 (实际仍画当前帧, 插值靠帧间连续性).
     */
    private void drawAnimatedCore(float half, float framePos)
    {
        int frame = MathHelper.clamp(MathHelper.floor(framePos) % CORE_FRAMES, 0, CORE_FRAMES - 1);
        double v0 = frame / (double) CORE_FRAMES;
        double v1 = (frame + 1) / (double) CORE_FRAMES;

        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buf = tess.getBuffer();
        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        // +Z
        buf.pos(-half, -half, half).tex(0, v1).endVertex();
        buf.pos(half, -half, half).tex(1, v1).endVertex();
        buf.pos(half, half, half).tex(1, v0).endVertex();
        buf.pos(-half, half, half).tex(0, v0).endVertex();
        // -Z
        buf.pos(half, -half, -half).tex(0, v1).endVertex();
        buf.pos(-half, -half, -half).tex(1, v1).endVertex();
        buf.pos(-half, half, -half).tex(1, v0).endVertex();
        buf.pos(half, half, -half).tex(0, v0).endVertex();
        // +X
        buf.pos(half, -half, half).tex(0, v1).endVertex();
        buf.pos(half, -half, -half).tex(1, v1).endVertex();
        buf.pos(half, half, -half).tex(1, v0).endVertex();
        buf.pos(half, half, half).tex(0, v0).endVertex();
        // -X
        buf.pos(-half, -half, -half).tex(0, v1).endVertex();
        buf.pos(-half, -half, half).tex(1, v1).endVertex();
        buf.pos(-half, half, half).tex(1, v0).endVertex();
        buf.pos(-half, half, -half).tex(0, v0).endVertex();
        // +Y
        buf.pos(-half, half, half).tex(0, v1).endVertex();
        buf.pos(half, half, half).tex(1, v1).endVertex();
        buf.pos(half, half, -half).tex(1, v0).endVertex();
        buf.pos(-half, half, -half).tex(0, v0).endVertex();
        // -Y
        buf.pos(-half, -half, -half).tex(0, v1).endVertex();
        buf.pos(half, -half, -half).tex(1, v1).endVertex();
        buf.pos(half, -half, half).tex(1, v0).endVertex();
        buf.pos(-half, -half, half).tex(0, v0).endVertex();

        tess.draw();
    }

    private void drawTexturedCube(float half)
    {
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buf = tess.getBuffer();
        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        buf.pos(-half, -half, half).tex(0, 1).endVertex();
        buf.pos(half, -half, half).tex(1, 1).endVertex();
        buf.pos(half, half, half).tex(1, 0).endVertex();
        buf.pos(-half, half, half).tex(0, 0).endVertex();

        buf.pos(half, -half, -half).tex(0, 1).endVertex();
        buf.pos(-half, -half, -half).tex(1, 1).endVertex();
        buf.pos(-half, half, -half).tex(1, 0).endVertex();
        buf.pos(half, half, -half).tex(0, 0).endVertex();

        buf.pos(half, -half, half).tex(0, 1).endVertex();
        buf.pos(half, -half, -half).tex(1, 1).endVertex();
        buf.pos(half, half, -half).tex(1, 0).endVertex();
        buf.pos(half, half, half).tex(0, 0).endVertex();

        buf.pos(-half, -half, -half).tex(0, 1).endVertex();
        buf.pos(-half, -half, half).tex(1, 1).endVertex();
        buf.pos(-half, half, half).tex(1, 0).endVertex();
        buf.pos(-half, half, -half).tex(0, 0).endVertex();

        buf.pos(-half, half, half).tex(0, 1).endVertex();
        buf.pos(half, half, half).tex(1, 1).endVertex();
        buf.pos(half, half, -half).tex(1, 0).endVertex();
        buf.pos(-half, half, -half).tex(0, 0).endVertex();

        buf.pos(-half, -half, -half).tex(0, 1).endVertex();
        buf.pos(half, -half, -half).tex(1, 1).endVertex();
        buf.pos(half, -half, half).tex(1, 0).endVertex();
        buf.pos(-half, -half, half).tex(0, 0).endVertex();

        tess.draw();
    }

    private void renderVision(TEPalantir te, World world, float visionAlpha, float partialTicks)
    {
        BlockPos target = te.getTargetPos();
        if (target == null) return;

        int horiz = Math.max(0, Configs.Gameplay.palantir_range_horizontal);
        int vert = Math.max(0, Configs.Gameplay.palantir_range_vertical);

        boolean sameDim = world.provider.getDimension() == te.getTargetDim();
        boolean loaded = sameDim && world.isBlockLoaded(target)
                && world.isBlockLoaded(target.add(horiz, 0, horiz))
                && world.isBlockLoaded(target.add(-horiz, 0, -horiz));

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.color(1f, 1f, 1f, 1f);

        if (!loaded)
        {
            // 迷雾本身带顶点 alpha
            GlStateManager.tryBlendFuncSeparate(
                    GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                    GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
            );
            GlStateManager.depthMask(false);
            renderChaosFog(world.getTotalWorldTime() + partialTicks, visionAlpha);
            GlStateManager.depthMask(true);
        }
        else
        {
            float diameter = Math.max(1, horiz * 2 + 1);
            float scale = 0.38f / diameter;
            GlStateManager.scale(scale, scale, scale);

            // 方块绘制会把顶点 alpha 写成 1, 用 CONSTANT_ALPHA 做整团渐隐
            org.lwjgl.opengl.GL14.glBlendColor(1f, 1f, 1f, MathHelper.clamp(visionAlpha, 0f, 1f));
            GlStateManager.tryBlendFuncSeparate(
                    GlStateManager.SourceFactor.CONSTANT_ALPHA,
                    GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA,
                    GlStateManager.SourceFactor.ONE,
                    GlStateManager.DestFactor.ZERO
            );

            renderBlocks(world, target, horiz, vert, partialTicks);
            renderEntities(world, target, horiz, vert, visionAlpha, partialTicks);

            // 恢复常规混合
            GlStateManager.tryBlendFuncSeparate(
                    GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                    GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
            );
        }

        GlStateManager.color(1f, 1f, 1f, 1f);
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    private void renderBlocks(World world, BlockPos center, int horiz, int vert, float partialTicks)
    {
        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        RenderHelper.disableStandardItemLighting();

        // 按玩家视线做远->近排序, 避免后面的方块盖住前面
        Vec3d look = getViewLook(partialTicks);

        List<int[]> cells = new ArrayList<>();
        for (int dy = -vert; dy <= vert; dy++)
        {
            for (int dx = -horiz; dx <= horiz; dx++)
            {
                for (int dz = -horiz; dz <= horiz; dz++)
                {
                    BlockPos bp = center.add(dx, dy, dz);
                    if (!world.isBlockLoaded(bp)) continue;
                    IBlockState state = world.getBlockState(bp);
                    if (state.getBlock() == Blocks.AIR) continue;
                    if (state.getRenderType() == net.minecraft.util.EnumBlockRenderType.INVISIBLE) continue;
                    cells.add(new int[]{dx, dy, dz});
                }
            }
        }

        // 写深度时由近到远绘制, 远处片元会被挡住
        cells.sort(Comparator.comparingDouble((int[] c) ->
                c[0] * look.x + c[1] * look.y + c[2] * look.z
        ));

        for (int[] c : cells)
        {
            int dx = c[0], dy = c[1], dz = c[2];
            BlockPos bp = center.add(dx, dy, dz);
            IBlockState state = world.getBlockState(bp);

            GlStateManager.pushMatrix();
            // 模型占 [0,1]^3 → 平移后中心落在本地 (dx,dy,dz)
            GlStateManager.translate(dx - 0.5D, dy - 0.5D, dz - 0.5D);
            try
            {
                // renderBlockBrightness 内部会 rotate(+90,Y) 并把染色做成物品栏同款效果;
                // 先转 -90 抵消, 既居中又保留草方块等正确颜色.
                GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
                dispatcher.renderBlockBrightness(state, 1.0F);
            }
            catch (Exception ignored)
            {
            }
            GlStateManager.popMatrix();
        }

        RenderHelper.enableStandardItemLighting();
    }

    /**
     * 取得玩家视线方向; 用于本地偏移的深度排序.
     */
    private Vec3d getViewLook(float partialTicks)
    {
        EntityPlayer player = Minecraft.getMinecraft().player;
        if (player == null)
        {
            return new Vec3d(0, 0, 1);
        }
        return player.getLook(partialTicks);
    }

    private void renderEntities(World world, BlockPos center, int horiz, int vert, float visionAlpha, float partialTicks)
    {
        AxisAlignedBB box = new AxisAlignedBB(
                center.getX() - horiz, center.getY() - vert, center.getZ() - horiz,
                center.getX() + horiz + 1, center.getY() + vert + 1, center.getZ() + horiz + 1
        );
        List<Entity> list = world.getEntitiesWithinAABB(Entity.class, box);
        RenderManager rm = Minecraft.getMinecraft().getRenderManager();
        boolean oldShadow = rm.isRenderShadow();
        rm.setRenderShadow(false);

        Vec3d look = getViewLook(partialTicks);
        list.sort(Comparator.comparingDouble((Entity e) ->
        {
            double ox = e.posX - center.getX() - 0.5D;
            double oy = e.posY - center.getY() - 0.5D;
            double oz = e.posZ - center.getZ() - 0.5D;
            return ox * look.x + oy * look.y + oz * look.z;
        }));

        for (Entity entity : list)
        {
            if (entity instanceof EntityPlayer) continue;
            // 与方块同一套原点: 目标方块中心 = (center+0.5), 实体相对中心的偏移
            double ox = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - center.getX() - 0.5D;
            double oy = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - center.getY() - 0.5D;
            double oz = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - center.getZ() - 0.5D;

            GlStateManager.pushMatrix();
            GlStateManager.color(1f, 1f, 1f, visionAlpha);
            try
            {
                rm.renderEntity(entity, ox, oy, oz, entity.rotationYaw, partialTicks, false);
            }
            catch (Exception ignored)
            {
            }
            GlStateManager.popMatrix();
        }

        rm.setRenderShadow(oldShadow);
    }

    private void renderChaosFog(float time, float visionAlpha)
    {
        GlStateManager.disableTexture2D();
        chaosRand.setSeed(((long) time) * 31L ^ 0x5A17L);

        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buf = tess.getBuffer();
        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

        for (int i = 0; i < 28; i++)
        {
            float px = (chaosRand.nextFloat() - 0.5f) * 0.4f;
            float py = (chaosRand.nextFloat() - 0.5f) * 0.4f;
            float pz = (chaosRand.nextFloat() - 0.5f) * 0.4f;
            float s = 0.03f + chaosRand.nextFloat() * 0.07f;
            float wobble = MathHelper.sin(time * 0.12f + i) * 0.015f;
            float r = 0.12f + chaosRand.nextFloat() * 0.2f;
            float g = 0.04f + chaosRand.nextFloat() * 0.12f;
            float b = 0.22f + chaosRand.nextFloat() * 0.3f;
            float a = visionAlpha * (0.3f + chaosRand.nextFloat() * 0.4f);
            putChaosBox(buf, px, py + wobble, pz, s, r, g, b, a);
        }

        tess.draw();
        GlStateManager.enableTexture2D();
    }

    private static void putChaosBox(BufferBuilder buf, float cx, float cy, float cz, float half,
                                    float r, float g, float b, float a)
    {
        float x0 = cx - half, x1 = cx + half;
        float y0 = cy - half, y1 = cy + half;
        float z0 = cz - half, z1 = cz + half;
        int R = (int) (r * 255), G = (int) (g * 255), B = (int) (b * 255), A = (int) (a * 255);

        buf.pos(x0, y0, z1).color(R, G, B, A).endVertex();
        buf.pos(x1, y0, z1).color(R, G, B, A).endVertex();
        buf.pos(x1, y1, z1).color(R, G, B, A).endVertex();
        buf.pos(x0, y1, z1).color(R, G, B, A).endVertex();

        buf.pos(x1, y0, z0).color(R, G, B, A).endVertex();
        buf.pos(x0, y0, z0).color(R, G, B, A).endVertex();
        buf.pos(x0, y1, z0).color(R, G, B, A).endVertex();
        buf.pos(x1, y1, z0).color(R, G, B, A).endVertex();

        buf.pos(x0, y1, z0).color(R, G, B, A).endVertex();
        buf.pos(x0, y1, z1).color(R, G, B, A).endVertex();
        buf.pos(x1, y1, z1).color(R, G, B, A).endVertex();
        buf.pos(x1, y1, z0).color(R, G, B, A).endVertex();

        buf.pos(x0, y0, z0).color(R, G, B, A).endVertex();
        buf.pos(x1, y0, z0).color(R, G, B, A).endVertex();
        buf.pos(x1, y0, z1).color(R, G, B, A).endVertex();
        buf.pos(x0, y0, z1).color(R, G, B, A).endVertex();
    }

    @Override
    public boolean isGlobalRenderer(TEPalantir te)
    {
        return false;
    }
}
