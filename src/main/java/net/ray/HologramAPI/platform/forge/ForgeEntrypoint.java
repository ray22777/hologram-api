package net.ray.HologramAPI.platform.forge;

//? forge {

import net.minecraftforge.fml.common.Mod;
import net.ray.HologramAPI.HologramMod;

@Mod(HologramMod.MOD_ID)
public class ForgeEntrypoint {

	public ForgeEntrypoint() {
		HologramMod.onInitialize();
	}
}
//?}
