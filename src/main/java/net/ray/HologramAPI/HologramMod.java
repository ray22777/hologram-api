package net.ray.HologramAPI;

import net.ray.HologramAPI.platform.Platform;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//? fabric {
/*import net.ray.HologramAPI.platform.fabric.FabricPlatform;
*///?} neoforge {
/*import net.ray.HologramAPI.platform.neoforge.NeoforgePlatform;
*///?} forge {
import net.ray.HologramAPI.platform.forge.ForgePlatform;
//?}

@SuppressWarnings("LoggingSimilarMessage")
public class HologramMod {

	public static final String MOD_ID = /*$ mod_id*/ "hologram_api";
	public static final String MOD_VERSION = /*$ mod_version*/ "2.0.1";
	public static final String MOD_FRIENDLY_NAME = /*$ mod_name*/ "Hologram API";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final Platform PLATFORM = createPlatformInstance();

	public static void onInitialize() {
//		LOGGER.info("Initializing {} on {}", MOD_ID, HologramMod.xplat().loader());
//		LOGGER.debug("{}: { version: {}; friendly_name: {} }", MOD_ID, MOD_VERSION, MOD_FRIENDLY_NAME);
	}

	public static void onInitializeClient() {
//		LOGGER.info("Initializing {} Client on {}", MOD_ID, HologramMod.xplat().loader());
//		LOGGER.debug("{}: { version: {}; friendly_name: {} }", MOD_ID, MOD_VERSION, MOD_FRIENDLY_NAME);
	}

	static Platform xplat() {
		return PLATFORM;
	}

	private static Platform createPlatformInstance() {
		//? fabric {
		/*return new FabricPlatform();
		*///?} neoforge {
		/*return new NeoforgePlatform();
		 *///?} forge {
		return new ForgePlatform();
		//?}
	}
}
