package ru.fr0le.pickup.common;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;
import ru.fr0le.pickup.common.handlers.PickupEvent;

public class CommonProxy {	

	public void preInit() {
		noPickup();
	}

	public void Init() {	

	}

	public void postInit() {

	}	

	public void noPickup() {
		FMLCommonHandler.instance().bus().register(new PickupEvent());
		MinecraftForge.EVENT_BUS.register(new PickupEvent());	
	}

}