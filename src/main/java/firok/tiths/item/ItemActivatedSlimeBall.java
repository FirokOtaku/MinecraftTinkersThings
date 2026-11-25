package firok.tiths.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;

public class ItemActivatedSlimeBall extends ItemCustom
{
    public enum Variant
    {
        GREEN("minecraft:slime"),
        BLUE("tconstruct:blueslime"),
        PURPLE("tconstruct:blueslime"), // TConstruct 1.12.2 has no purple slime entity.
        BLOOD("minecraft:magma_cube"); // No blood slime entity; use the red slime-family mob.

        private final ResourceLocation entityId;

        Variant(String entityId)
        {
            this.entityId = new ResourceLocation(entityId);
        }
    }

    private final Variant variant;

    public ItemActivatedSlimeBall(Variant variant)
    {
        this.variant = variant;
    }

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
                                      EnumFacing face, float hitX, float hitY, float hitZ)
    {
        if (world.isRemote) return EnumActionResult.SUCCESS;

        ItemStack stack = player.getHeldItem(hand);
        BlockPos target = pos.offset(face);
        boolean spawned = trySpawn(player, world, target, face, stack);
        if (spawned && !player.capabilities.isCreativeMode) stack.shrink(1);

        return spawned ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
    }

    private boolean trySpawn(EntityPlayer player, World world, BlockPos target, EnumFacing face, ItemStack stack)
    {
        if (stack.isEmpty()) return false;
        if (world.getDifficulty() == EnumDifficulty.PEACEFUL) return false;
        if (target.getY() < 0 || target.getY() >= world.getHeight()) return false;
        if (!world.isBlockLoaded(target)) return false;
        if (!world.isBlockModifiable(player, target) || !player.canPlayerEdit(target, face, stack)) return false;

        Entity entity = EntityList.createEntityByIDFromName(variant.entityId, world);
        if (!(entity instanceof EntitySlime)) return false;
        EntitySlime slime = (EntitySlime) entity;
        // One ball yields one small slime; do not use natural-spawn rules or random sizes.
        slime.setSlimeSize(1, true);
        double y = target.getY();
        if (face == EnumFacing.DOWN) y += 1.0D - slime.height;
        slime.setLocationAndAngles(target.getX() + 0.5D, y, target.getZ() + 0.5D,
                world.rand.nextFloat() * 360.0F, 0.0F);

        AxisAlignedBB bounds = slime.getEntityBoundingBox();
        WorldBorder border = world.getWorldBorder();
        if (bounds.minX < border.minX() || bounds.maxX > border.maxX()
                || bounds.minZ < border.minZ() || bounds.maxZ > border.maxZ()) return false;
        if (!world.getEntitiesWithinAABBExcludingEntity(slime, bounds).isEmpty()) return false;
        if (!world.getCollisionBoxes(slime, bounds).isEmpty()) return false;
        if (!world.spawnEntity(slime)) return false;

        slime.playLivingSound();
        return true;
    }
}
