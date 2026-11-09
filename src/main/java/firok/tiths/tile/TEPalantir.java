package firok.tiths.tile;

import firok.tiths.common.Configs;
import firok.tiths.util.PalantirData;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * 真知晶球 TE: 存储观测坐标, 以及当前显示剩余时间.
 */
public class TEPalantir extends TileEntity implements ITickable
{
    public static final int FADE_TICKS = 20;

    private boolean hasTarget = false;
    private int targetX;
    private int targetY;
    private int targetZ;
    private int targetDim;

    /** 剩余显示 tick, 0 表示未激活 */
    private int displayTicksRemaining = 0;
    /** 已完成的渐入 tick, 上限 {@link #FADE_TICKS}; 与 remaining 独立, 避免续时重跑渐入 */
    private int fadeInTicks = 0;

    public boolean hasTarget()
    {
        return hasTarget;
    }

    @Nullable
    public BlockPos getTargetPos()
    {
        return hasTarget ? new BlockPos(targetX, targetY, targetZ) : null;
    }

    public int getTargetDim()
    {
        return targetDim;
    }

    public void setTarget(BlockPos pos, int dim)
    {
        this.hasTarget = true;
        this.targetX = pos.getX();
        this.targetY = pos.getY();
        this.targetZ = pos.getZ();
        this.targetDim = dim;
        markDirtyAndSync();
    }

    public void clearTarget()
    {
        this.hasTarget = false;
        this.targetX = this.targetY = this.targetZ = 0;
        this.targetDim = 0;
        this.displayTicksRemaining = 0;
        this.fadeInTicks = 0;
        markDirtyAndSync();
    }

    public void readTargetFromNbt(NBTTagCompound nbt)
    {
        if (PalantirData.hasTarget(nbt))
        {
            BlockPos pos = PalantirData.getPos(nbt);
            this.hasTarget = true;
            this.targetX = pos.getX();
            this.targetY = pos.getY();
            this.targetZ = pos.getZ();
            this.targetDim = PalantirData.getDim(nbt);
        }
        else
        {
            this.hasTarget = false;
            this.targetX = this.targetY = this.targetZ = 0;
            this.targetDim = 0;
        }
    }

    public void writeTargetToNbt(NBTTagCompound nbt)
    {
        if (hasTarget)
        {
            PalantirData.setTarget(nbt, new BlockPos(targetX, targetY, targetZ), targetDim);
        }
        else
        {
            PalantirData.clearTarget(nbt);
        }
    }

    public int getDisplayTicksRemaining()
    {
        return displayTicksRemaining;
    }

    public boolean isDisplaying()
    {
        return displayTicksRemaining > 0 && hasTarget;
    }

    /**
     * 触摸激活 / 续时.
     * <ul>
     *   <li>未显示: 从头渐入</li>
     *   <li>已在显示 (含满亮): 静默把剩余时间拉回满时长, 不重跑渐入</li>
     *   <li>正在渐出: 从当前透明度平滑淡回满亮, 再按满时长计时</li>
     * </ul>
     */
    public void activateDisplay()
    {
        if (!hasTarget) return;
        int duration = Math.max(1, Configs.Gameplay.palantir_display_duration);

        if (displayTicksRemaining <= 0)
        {
            displayTicksRemaining = duration;
            fadeInTicks = 0;
        }
        else
        {
            // 渐出阶段: alpha ≈ remaining/FADE; 把 fadeInTicks 对齐到当前 alpha, 再续时即可平滑淡回
            if (fadeInTicks >= FADE_TICKS && displayTicksRemaining < FADE_TICKS)
            {
                fadeInTicks = displayTicksRemaining;
            }
            displayTicksRemaining = Math.max(displayTicksRemaining, duration);
        }
        markDirtyAndSync();
    }

    /**
     * 窥视内容透明度: 0~1. 渐入 / 渐出各约 {@link #FADE_TICKS} tick.
     */
    public float getDisplayAlpha(float partialTicks)
    {
        if (!isDisplaying()) return 0f;

        float fadeIn;
        if (fadeInTicks >= FADE_TICKS)
        {
            fadeIn = 1f;
        }
        else
        {
            fadeIn = Math.min(1f, (fadeInTicks + partialTicks) / (float) FADE_TICKS);
        }

        float fadeOut = Math.min(1f, (displayTicksRemaining - partialTicks) / (float) FADE_TICKS);
        return Math.max(0f, Math.min(fadeIn, fadeOut));
    }

    /**
     * 外壳不透明度: 平时深邃几乎不透明; 窥视时随 displayAlpha 变透明.
     */
    public float getShellOpacity(float partialTicks)
    {
        float open = getDisplayAlpha(partialTicks);
        return 0.94f - open * 0.62f;
    }

    private void markDirtyAndSync()
    {
        markDirty();
        if (world != null && !world.isRemote)
        {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
        }
    }

    @Override
    public void update()
    {
        if (displayTicksRemaining > 0)
        {
            displayTicksRemaining--;
            if (fadeInTicks < FADE_TICKS)
            {
                fadeInTicks++;
            }
            if (displayTicksRemaining <= 0)
            {
                displayTicksRemaining = 0;
                fadeInTicks = 0;
                if (!world.isRemote)
                {
                    markDirtyAndSync();
                }
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        writeTargetToNbt(compound);
        compound.setInteger("DisplayTicks", displayTicksRemaining);
        compound.setInteger("FadeInTicks", fadeInTicks);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        readTargetFromNbt(compound);
        displayTicksRemaining = compound.getInteger("DisplayTicks");
        fadeInTicks = compound.getInteger("FadeInTicks");
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return writeToNBT(new NBTTagCompound());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 0, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }
}
