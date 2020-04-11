package ru.fr0le.pickup.client.render;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import ru.fr0le.pickup.client.handlers.KeybindsRegister;
import ru.fr0le.pickup.client.handlers.Type;

public class ItemRenderOnFloor {

	static RenderItem itemRenderer = new RenderItem();

	public static void drawItems(RenderGameOverlayEvent e) {
		Minecraft mc = Minecraft.getMinecraft();
		if(e.type == ElementType.ALL) {
			if (Minecraft.getMinecraft().objectMouseOver != null && Minecraft.getMinecraft().objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				Vec3 hitVec = mc.objectMouseOver.hitVec;
				EntityClientPlayerMP player = mc.thePlayer;
				double playerX = player.posX;
				double playerY = player.posY;
				double playerZ = player.posZ;
				WorldClient world = mc.theWorld;
				double segLen = 0.25;
				double segLend2 = 0.125;
				double dx = hitVec.xCoord - playerX;
				double dy = hitVec.yCoord - playerY;
				double dz = hitVec.zCoord - playerZ;
				double lineLen = Math.sqrt(Math.pow(dx, 2.0) + Math.pow(dy, 2.0) + Math.pow(dz, 2.0));
				double segNumDouble = lineLen / segLen;
				int segNum = (int)segNumDouble;
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

					String keyName = closeItem.getEntityItem().getDisplayName();
					String itemName = Keyboard.getKeyName(KeybindsRegister.PICKUP_KEY.getKeyCode());
					Minecraft.getMinecraft().fontRenderer.drawString("[" + itemName + "] " + Type.getTranslate("event.pickup"), e.resolution.getScaledWidth() / 2 - 2, e.resolution.getScaledHeight() / 2 + 22, 0xFFFFFF);
					Minecraft.getMinecraft().fontRenderer.drawString(keyName, (e.resolution.getScaledWidth()) / 2 - Minecraft.getMinecraft().fontRenderer.getStringWidth(keyName) - 6, e.resolution.getScaledHeight() / 2 + 22, 0xFFFFFF);
					renderInventorySlot(closeItem.getEntityItem(), e.resolution.getScaledWidth() / 2 - 7, e.resolution.getScaledHeight() / 2 + 6, 0f);					
				}
			}
		}
	}

	protected static void renderInventorySlot(ItemStack itemStack, double d, int i, float f) {
		Minecraft mc = Minecraft.getMinecraft();

		if(itemStack != null) {
			float f1 = (float)itemStack.animationsToGo - f;
			if(f1 > 0.0F) {
				GL11.glPushMatrix();
				float f2 = 1.0F + f1 / 5.0F;
				GL11.glTranslatef((float)(d + 8), (float)(i + 12), 0.0F);
				GL11.glScalef(1.0F / f2, (f2 + 1.0F) / 2.0F, 1.0F);
				GL11.glTranslatef((float)(-(d + 8)), (float)(-(i + 12)), 0.0F);
			}

			itemRenderer.renderItemIntoGUI(mc.fontRenderer, mc.getTextureManager(), itemStack, (int) d, i);
			if(f1 > 0.0F) {
				GL11.glPopMatrix();
			}
			itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.getTextureManager(), itemStack, (int) d, i);
		}
	} 
}