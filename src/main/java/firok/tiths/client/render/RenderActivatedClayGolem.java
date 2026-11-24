package firok.tiths.client.render;

import firok.tiths.TinkersThings;
import net.minecraft.client.renderer.entity.RenderIronGolem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.util.ResourceLocation;

public class RenderActivatedClayGolem extends RenderIronGolem
{
    private static final String TEXTURE_PATH = "textures/entity/activated_clay_golem";
    private static final ResourceLocation TEXTURE = TinkersThings.resource(TEXTURE_PATH + ".png");
    private static final ResourceLocation TEXTURE_CRACKED_LOW = TinkersThings.resource(TEXTURE_PATH + "_cracked_low.png");
    private static final ResourceLocation TEXTURE_CRACKED_MEDIUM = TinkersThings.resource(TEXTURE_PATH + "_cracked_medium.png");
    private static final ResourceLocation TEXTURE_CRACKED_HIGH = TinkersThings.resource(TEXTURE_PATH + "_cracked_high.png");

    public RenderActivatedClayGolem(RenderManager manager)
    {
        super(manager);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityIronGolem entity)
    {
        float healthRatio = entity.getHealth() / entity.getMaxHealth();
        if (healthRatio <= 0.25F) return TEXTURE_CRACKED_HIGH;
        if (healthRatio <= 0.50F) return TEXTURE_CRACKED_MEDIUM;
        if (healthRatio <= 0.75F) return TEXTURE_CRACKED_LOW;
        return TEXTURE;
    }
}
