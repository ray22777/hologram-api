package net.ray.neoforge.HologramAPI;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.ray.HologramAPI.HologramModInitializer;
import org.slf4j.LoggerFactory;

@EventBusSubscriber(modid = "hologram_api", value = Dist.CLIENT)
public final class HologramAPIModSetup {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        HologramModInitializer.onInit(LoggerFactory.getLogger("hologram_api"));
    }
}