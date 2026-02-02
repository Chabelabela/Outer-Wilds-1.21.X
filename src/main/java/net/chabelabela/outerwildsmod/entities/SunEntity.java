package net.chabelabela.outerwildsmod.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

/**
 * The Sun entity - a massive celestial body that will eventually go supernova.
 * Players near it take heat damage. In the future, this will expand over time.
 *
 * The sun forces its chunk to stay loaded so it's always tracked by clients.
 */
public class SunEntity extends Entity {

    // Tracked data for syncing sun radius to clients (for future supernova expansion)
    private static final TrackedData<Float> RADIUS = DataTracker.registerData(
            SunEntity.class, TrackedDataHandlerRegistry.FLOAT
    );

    // How close before the player starts taking damage
    private static final double DAMAGE_RADIUS = 50.0;
    private static final float DAMAGE_AMOUNT = 4.0f; // 2 hearts

    // Track if we've set up the force-loaded chunk
    private boolean chunkForceLoaded = false;

    public SunEntity(EntityType<? extends SunEntity> type, World world) {
        super(type, world);
        this.noClip = true;           // Sun passes through blocks
        this.setNoGravity(true);      // Sun doesn't fall
        this.ignoreCameraFrustum = true; // Always render even if "off screen"
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(RADIUS, 10.0f); // Default radius
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("Radius")) {
            this.dataTracker.set(RADIUS, nbt.getFloat("Radius"));
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putFloat("Radius", this.dataTracker.get(RADIUS));
    }

    public float getRadius() {
        return this.dataTracker.get(RADIUS);
    }

    public void setRadius(float radius) {
        this.dataTracker.set(RADIUS, radius);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.getWorld().isClient) {
            ServerWorld serverWorld = (ServerWorld) this.getWorld();

            // Force-load the chunk the sun is in (keeps it always active)
            if (!chunkForceLoaded) {
                ChunkPos chunkPos = new ChunkPos(this.getBlockPos());
                serverWorld.setChunkForced(chunkPos.x, chunkPos.z, true);
                chunkForceLoaded = true;
                System.out.println("[OuterWildsMod] Sun chunk force-loaded at " + chunkPos);
            }

            // Damage nearby players
            this.getWorld().getPlayers().forEach(player -> {
                double distance = this.distanceTo(player);
                if (distance < DAMAGE_RADIUS && distance > 0) {
                    // Damage increases as you get closer
                    float damage = (float) (DAMAGE_AMOUNT * (1.0 - (distance / DAMAGE_RADIUS)));
                    player.damage(getWorld().getDamageSources().inFire(), damage);
                    player.setOnFireFor(2);
                }
            });
        }
    }

    @Override
    public void remove(RemovalReason reason) {
        // Unforce the chunk when the sun is removed
        if (!this.getWorld().isClient && chunkForceLoaded) {
            ServerWorld serverWorld = (ServerWorld) this.getWorld();
            ChunkPos chunkPos = new ChunkPos(this.getBlockPos());
            serverWorld.setChunkForced(chunkPos.x, chunkPos.z, false);
            System.out.println("[OuterWildsMod] Sun chunk unforced at " + chunkPos);
        }
        super.remove(reason);
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (!this.getWorld().isClient) {
            player.damage(getWorld().getDamageSources().inFire(), 20.0f);
            player.setOnFireFor(10);
        }
    }

    @Override
    public boolean isFireImmune() {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false; // Sun is invulnerable
    }

    @Override
    public boolean canHit() {
        return false;
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean shouldRender(double distance) {
        // Always render regardless of distance
        return true;
    }
}