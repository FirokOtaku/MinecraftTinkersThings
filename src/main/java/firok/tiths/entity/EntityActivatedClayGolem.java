package firok.tiths.entity;

import firok.tiths.TinkersThings;
import firok.tiths.common.Items;
import firok.tiths.common.SoundEvents;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityActivatedClayGolem extends EntityIronGolem
{
    public static final ResourceLocation LOOT_TABLE = LootTableList.register(
            TinkersThings.resource("entities/activated_clay_golem"));

    public EntityActivatedClayGolem(World world)
    {
        super(world);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60);
        getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2);
        getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(6);
        IAttributeInstance speed = getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        speed.setBaseValue(speed.getBaseValue() * 1.25);
    }

    @Override
    public boolean attackEntityAsMob(Entity target)
    {
        if (isPlayerCreated() && target instanceof EntityPlayer) return false;

        float damage;
        switch (world.getDifficulty())
        {
            case EASY: damage = 2; break;
            case NORMAL: damage = 3; break;
            case HARD: damage = 4; break;
            default: return false;
        }

        world.setEntityState(this, (byte) 4);
        playSound(SoundEvents.activatedClayGolemAttack, 0.75F, 1.0F);
        // Vanilla scales mob damage only for player targets. The hit already accounts for difficulty.
        EntityDamageSource source = new EntityDamageSource("mob", this)
        {
            @Override
            public boolean isDifficultyScaled()
            {
                return false;
            }
        };
        boolean hit = target.attackEntityFrom(source, damage);
        if (hit)
        {
            target.motionY += (double) 0.2F;
            applyEnchantments(this, target);
        }
        return hit;
    }

    @Override
    public void playSound(SoundEvent soundIn, float volume, float pitch)
    {
        // The inherited attack animation plays this sound on the client; the server sends our own.
        if (soundIn != net.minecraft.init.SoundEvents.ENTITY_IRONGOLEM_ATTACK)
        {
            super.playSound(soundIn, volume, pitch);
        }
    }

    @Override
    protected void playStepSound(BlockPos pos, Block block)
    {
        playSound(SoundEvents.activatedClayGolemStep, 0.65F, 1.0F);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return SoundEvents.activatedClayGolemHurt;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.activatedClayGolemDeath;
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() != Items.activatedClay || !isEntityAlive() || getHealth() >= getMaxHealth())
        {
            return super.processInteract(player, hand);
        }
        if (!world.isRemote)
        {
            float health = getHealth();
            heal(10);
            if (getHealth() > health && !player.capabilities.isCreativeMode) stack.shrink(1);
        }
        return true;
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (world.isRemote || ticksExisted % 50 != 0 || !isEntityAlive() || getHealth() >= getMaxHealth()) return;

        int hpRegen = 0;
        int light = world.getLight(new BlockPos(posX, posY, posZ), false);
        if (light > 10) hpRegen = 4;
        else if (light > 5) hpRegen = 2;

        if(hpRegen > 0 && isWet()) // 在水里额外回血
            hpRegen += 2;

        heal(hpRegen);
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return LOOT_TABLE;
    }
}
