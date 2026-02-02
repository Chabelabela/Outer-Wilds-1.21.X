package net.chabelabela.outerwildsmod.mixin;

import net.chabelabela.outerwildsmod.entities.SunEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

/**
 * Mixin to force celestial bodies (Sun, planets) to always be tracked by all players
 * in the same dimension, regardless of distance.
 * The vanilla tracking check has THREE conditions:
 * 1. Distance check (e <= f)
 * 2. canBeSpectated check
 * 3. isTracked chunk check <-- This is what blocks us!
 * We bypass all of this for celestial bodies by injecting at HEAD and handling them specially.
 */
@Mixin(targets = "net.minecraft.server.world.ServerChunkLoadingManager$EntityTracker")
public abstract class CelestialTrackingMixin {

    @Shadow
    @Final
    Entity entity;

    @Shadow
    @Final
    private Set<net.minecraft.server.network.PlayerAssociatedNetworkHandler> listeners;

    @Shadow
    @Final
    EntityTrackerEntry entry;

    @Shadow
    @Final
    private int maxDistance;

    /**
     * For celestial bodies, completely bypass the vanilla tracking logic.
     * We force tracking for any player in the same dimension.
     */
    @Inject(
            method = "updateTrackedStatus(Lnet/minecraft/server/network/ServerPlayerEntity;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void forceCelestialTracking(ServerPlayerEntity player, CallbackInfo ci) {
        if (!(this.entity instanceof SunEntity)) {
            return; // Let vanilla handle non-celestial entities
        }

        // Don't track self
        if (player == this.entity) {
            ci.cancel();
            return;
        }

        // Check same dimension
        boolean sameDimension = player.getWorld().getRegistryKey().equals(this.entity.getWorld().getRegistryKey());
        boolean canBeSpectated = this.entity.canBeSpectated(player);

        // For celestial bodies: always track if in same dimension and can be spectated
        boolean shouldTrack = sameDimension && canBeSpectated;

        if (shouldTrack) {
            // Add listener if not already tracking
            if (this.listeners.add(player.networkHandler)) {
                this.entry.startTracking(player);
                System.out.println("[OuterWildsMod] Started tracking sun for player: " + player.getName().getString());
            }
        } else {
            // Remove listener if we shouldn't track
            if (this.listeners.remove(player.networkHandler)) {
                this.entry.stopTracking(player);
                System.out.println("[OuterWildsMod] Stopped tracking sun for player: " + player.getName().getString());
            }
        }

        // Cancel vanilla logic - we've handled it
        ci.cancel();
    }
}