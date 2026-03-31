package net.ray.HologramAPI.platform.fabric;

//? fabric {

import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ModInitializer;
import net.ray.HologramAPI.HologramModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entrypoint("main")
public final class FabricEntrypoint implements ModInitializer {
	public static final String MOD_ID = "hologram_api";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		HologramModInitializer.onInit(LOGGER);

	}
}
//?}
