package net.ray.HologramAPI;

import org.slf4j.Logger;

public class HologramModInitializer {
    private static Logger log;
    protected static void setLogger(Logger l){
        log = l;
    }
    protected static Logger getLogger(){
        return log;
    }
    public static void onInit(Logger log){
        setLogger(log);
        HologramModInitializer.getLogger().warn("Hologram API initialized.");
    }
}
