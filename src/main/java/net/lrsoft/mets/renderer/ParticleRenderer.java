package net.lrsoft.mets.renderer;
import java.util.Vector;

import net.lrsoft.mets.entity.EntityParticleGroup;
import net.lrsoft.mets.renderer.particle.XCustomizedParticle;
import net.lrsoft.mets.util.MathUtils;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
public class ParticleRenderer  extends Render<EntityParticleGroup>{
	
	public ParticleRenderer(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityParticleGroup entity, double x, double y, double z, float entityYaw,
			float partialTicks) {
		for(int i=0; i < particleVector.size(); i++)
		{
			XCustomizedParticle particle = particleVector.get(i);
			particle.onRender(partialTicks, 0.5f);
			if(particle.getIsFinish())
			{
				particleVector.remove(i);
			}
		}

		if(entity.ticksExisted % entity.getParticleSpawnPerTick() == 0)
			for(int count = 0; count < entity.getParticleSpawnCount(); count ++)
			{
				
				XCustomizedParticle particle = new XCustomizedParticle(new Vec3d(0.8f, 1.0f, 1.0f),
						new Vec3d(x, y, z), 
						new Vec3d((MathUtils.getRandomFromRange(1, -1)) * Math.random() / 10.0f,
								  (MathUtils.getRandomFromRange(1, -1)) * Math.random()/ 10.0f,
								  (MathUtils.getRandomFromRange(1, -1)) * Math.random()/ 10.0f),
						new Vec3d(0.5f, 0.5f, 0.5f), new Vec3d(0.0f, 0.0f, 1.0f), 0.0f, MathUtils.getRandomFromRange(10, 5));
				particleVector.add(particle);
			}
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityParticleGroup entity) {
		return null;
	}
	
	private Vector<XCustomizedParticle> particleVector = new Vector();
}
