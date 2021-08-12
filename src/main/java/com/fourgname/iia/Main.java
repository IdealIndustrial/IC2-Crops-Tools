package com.fourgname.iia;

import com.fourgname.iia.loader.MainLoader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = Main.MODID,
        name = Main.NAME,
        version = Main.VERSION
)
public class Main {
    public static final String MODID = "ic2cropstools";
    public static final String VERSION = "0.0.1";
    public static final String NAME = "IC2 Crops Tools";

    public static final String CLIENTSIDE = "com.fourgname.iia.ClientProxy";
    public static final String SERVERSIDE = "com.fourgname.iia.CommonProxy";

//    @SidedProxy(clientSide = Main.CLIENTSIDE, serverSide = Main.SERVERSIDE)
//    public static CommonProxy proxy;

    @Mod.Instance(MODID)
    public static Main instance;

    @EventHandler
    public void init(FMLInitializationEvent e) {
        MainLoader.init(e);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        MainLoader.postInit(e);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        MainLoader.preInit(e);
    }
}
