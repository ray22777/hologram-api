package net.ray.neoforge;

import net.neoforged.fml.common.Mod;

import net.ray.HologramAPI;

@Mod(HologramAPI.MOD_ID)
public final class ExampleModNeoForge {
    public ExampleModNeoForge() {
        // Run our common setup.
        HologramAPI.init();
    }
}
