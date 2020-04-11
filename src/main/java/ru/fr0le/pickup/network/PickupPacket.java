package ru.fr0le.pickup.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class PickupPacket implements IMessage {

	public int entityID;
	public int playerID;

	public PickupPacket() {

	}

	public PickupPacket(int entityID, int playerID) {
		this.entityID = entityID;
		this.playerID = playerID;
	}

	public void toBytes(ByteBuf out) {
		out.writeInt(this.entityID);
		out.writeInt(this.playerID);
	}

	public void fromBytes(ByteBuf in) {
		this.entityID = in.readInt();
		this.playerID = in.readInt();
	}

	public static class Handler	implements IMessageHandler<PickupPacket, IMessage> {
		public IMessage onMessage(PickupPacket message, MessageContext ctx) {
			MinecraftServer server = MinecraftServer.getServer();
			if (server.getEntityWorld().getEntityByID(message.playerID) != null && server.getEntityWorld().getEntityByID(message.entityID) != null && server.getEntityWorld().getEntityByID(message.playerID) instanceof EntityPlayer) {
				EntityPlayer thePlayer = (EntityPlayer)server.getEntityWorld().getEntityByID(message.playerID);
				EntityItem theItem = (EntityItem)server.getEntityWorld().getEntityByID(message.entityID);
				theItem.setDead();
				thePlayer.worldObj.playSoundEffect(thePlayer.posX, thePlayer.posY, thePlayer.posZ, "random.pop", 1.0f, 1.0f);
				thePlayer.inventory.addItemStackToInventory(theItem.getEntityItem());
				thePlayer.inventoryContainer.detectAndSendChanges();
			}
			return null;
		}
	}

}