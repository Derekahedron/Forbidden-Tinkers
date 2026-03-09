package derekahedron.forbiddentinkers.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChampiumSmokeParticle extends TextureSheetParticle {
    public static final float PEAK_PROGRESS = 0.2F;
    public static final float PEAK_ALPHA = 0.9F;
    public final SpriteSet sprites;
    public final float originalQuadSize;

    protected ChampiumSmokeParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, SpriteSet sprites) {
        super(level, x, y, z, 0.0F, 0.0F, 0.0F);
        this.friction = 0.9F;
        this.speedUpWhenYMotionIsBlocked = true;
        this.sprites = sprites;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.gravity = -0.15F;
        this.lifetime = Mth.lerpInt(level.random.nextFloat(), 80, 100);
        this.pickSprite(sprites);
        this.scale(2);
        this.originalQuadSize = this.quadSize;
        this.updateScaleAndAlpha();
    }

    public void tick() {
        updateScaleAndAlpha();
        super.tick();
    }

    public void updateScaleAndAlpha() {
        float progress = (float) age / lifetime;

        if (progress < PEAK_PROGRESS) {
            progress /= PEAK_PROGRESS;
        } else {
            progress = 1 - (progress - PEAK_PROGRESS) / (1 - PEAK_PROGRESS);
        }

        float intensity = (float) (1 - Math.pow(progress - 1, 2));
        quadSize = originalQuadSize * intensity;
        setAlpha(PEAK_ALPHA * intensity);
    }


    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {

        public Particle createParticle(
                SimpleParticleType type, ClientLevel level,
                double x, double y, double z,
                double xd, double yd, double zd) {
            return new ChampiumSmokeParticle(level, x, y, z, xd, yd, zd, sprites);
        }
    }
}
