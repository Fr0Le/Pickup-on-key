package ru.fr0le.pickup.common.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

public class PickupEvent {

	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event) {
		if(event.entity.worldObj.isRemote || !(event.entityLiving instanceof EntityPlayer)) {
			return;
		}		
		event.setCanceled(true);		
	}

}