package ru.fr0le.pickup.network;

import java.util.ArrayList;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import ru.fr0le.pickup.client.handlers.KeybindsRegister;
import ru.fr0le.pickup.client.render.ItemRenderOnFloor;

public class PacketSender {

	public Minecraft mc;
	RenderItem itemRenderer = new RenderItem();

	public PacketSender(Minecraft mc) {
		this.mc = mc;
	}    

	@SubscribeEvent(priority = EventPriority.HIGHEST,receiveCanceled = true)
	public void onKeyInput(KeyInputEvent e) {
		if(KeybindsRegister.PICKUP_KEY.isPressed()) {
			if (mc.theWorld.isRemote && mc.objectMouseOver != null && mc.objectMouseOver.hitVec != null) {
				Vec3 hitVec = mc.objectMouseOver.hitVec;
				EntityClientPlayerMP player = mc.thePlayer;
				double playerX = player.posX;
				double playerY = player.posY;
				double segLen = 0.25;
				double segLend2 = 0.125;
				double playerZ = player.posZ;
				double dx = hitVec.xCoord - playerX;
				double dy = hitVec.yCoord - playerY;
				double dz = hitVec.zCoord - playerZ;
				double lineLen = Math.sqrt(Math.pow(dx, 2.0) + Math.pow(dy, 2.0) + Math.pow(dz, 2.0));
				double segNumDouble = lineLen / segLen;
				int segNum = (int)segNumDouble;
				World world = player.getEntityWorld();
				ArrayList<EntityItem> items = null;
				int index = 0;

				while (++index <= segNum) {
					double cenX = playerX + dx / segNumDouble * (double)index;
					double cenY = playerY + dy / segNumDouble * (double)index;
					double cenZ = playerZ + dz / segNumDouble * (double)index;
					AxisAlignedBB curAABB = AxisAlignedBB.getBoundingBox((double)(cenX - segLend2), (double)(cenY - segLend2), (double)(cenZ - segLend2), (double)(cenX + segLend2), (double)(cenY + segLend2), (double)(cenZ + segLend2));
					items = (ArrayList)world.getEntitiesWithinAABB(EntityItem.class, curAABB);
					if (items == null || items.isEmpty()) continue;
					index = segNum + 1;
				}

				if (items != null && !items.isEmpty()) {
					EntityItem closeItem = (EntityItem)items.get(0);
					double closeDist = 100.0;
					for (EntityItem item : items) {
						double itemDist = Math.pow(item.posX - playerX, 2.0) + Math.pow(item.posY - playerY, 2.0) + Math.pow(item.posZ - playerZ, 2.0);
						if (itemDist >= closeDist) continue;
						closeDist = itemDist;
						closeItem = item;
					}
					PickupNetwork.netHandler.sendToServer(new PickupPacket(closeItem.getEntityId(), player.getEntityId()));
				}
			}
		}
	}

	@SubscribeEvent(receiveCanceled = true,	priority = EventPriority.HIGHEST)
	public void onInGameUI(RenderGameOverlayEvent e) {
		if(e.type == ElementType.ALL) {	  
			RenderHelper.disableStandardItemLighting();
			ItemRenderOnFloor.drawItems(e);
			RenderHelper.disableStandardItemLighting();
		}
	}

}