package ru.fr0le.pickup.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import ru.fr0le.pickup.Info;

public class PickupNetwork {

	public static SimpleNetworkWrapper netHandler = NetworkRegistry.INSTANCE.newSimpleChannel(Info.modid);

	public static void init() {
		netHandler.registerMessage(PickupPacket.Handler.class, PickupPacket.class, 0, Side.SERVER);		
	}

}