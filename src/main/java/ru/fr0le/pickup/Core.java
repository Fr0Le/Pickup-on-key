package ru.fr0le.pickup;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import ru.fr0le.pickup.common.CommonProxy;
import ru.fr0le.pickup.network.PickupNetwork;

@Mod(modid = Info.modid, name = Info.name, version = Info.version)

public class Core {

	@Mod.Instance(Info.modid)
	public static Core mod;

	@SidedProxy(clientSide = "ru.fr0le.pickup.client.ClientProxy", serverSide = "ru.fr0le.pickup.common.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit();
		PickupNetwork.init();
	}

	@EventHandler
	public void Init(FMLInitializationEvent e) {   
		proxy.Init();
		System.out.println(Info.name + " has been successfully initialized");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();
	}

}