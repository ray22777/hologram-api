package net.ray.HologramAPI.forge;


import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("hologram_api")
public class HologramAPIForge {
    public static final String MOD_ID = "hologram_api";
    public HologramAPIForge() {
        MinecraftForge.EVENT_BUS.register(this);
    }
}