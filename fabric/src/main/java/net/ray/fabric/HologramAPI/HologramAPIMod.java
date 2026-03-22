package net.ray.fabric.HologramAPI;

import net.fabricmc.api.ModInitializer;
import net.ray.HologramAPI.HologramAPI;
import net.ray.HologramAPI.HologramManager;
import net.ray.HologramAPI.HologramModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HologramAPIMod implements ModInitializer {
    public static final String MOD_ID = "hologram_api";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        HologramModInitializer.onInit(LOGGER);

    }
}
