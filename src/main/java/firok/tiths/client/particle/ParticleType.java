package firok.tiths.client.particle;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static firok.tiths.TinkersThings.resource;

/**
 * 所有粒子类型
 */
@SideOnly(Side.CLIENT)
public final class ParticleType
{
	static List<ParticleType> types = new ArrayList<>();

	public final ResourceLocation rlMain; // 用于召唤粒子用的
	public final ResourceLocation rlTexture; // 用于注册材质用的 // 暂时没用
	public final IParticleFactory factory;

	private ParticleType(String name,IParticleFactory factory)
	{
		Objects.requireNonNull(name);
		Objects.requireNonNull(factory);

		this.rlMain = resource(name);
		this.rlTexture = resource("particles/"+name);
		this.factory = factory;

		ParticleFactory.addParticleFactory(this,this.factory);
		types.add(this);
	}

	void onTextureRegister(TextureStitchEvent.Pre event)
	{
		event.getMap().registerSprite(this.rlTexture);
	}

	public static ParticleType STAR = new ParticleType("star",ParticleStar::new);
	public static ParticleType SMOKE_BALL = new ParticleType("smoke_ball",ParticleSmokeBall::new);

	public static void onTextureRegisterEvent(TextureStitchEvent.Pre event)
	{
		for(ParticleType type : types)
		{
			type.onTextureRegister(event);
		}
	}
}
