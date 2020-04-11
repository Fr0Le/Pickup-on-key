package ru.fr0le.pickup.client;

import org.lwjgl.opengl.Display;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import ru.fr0le.pickup.Info;
import ru.fr0le.pickup.client.handlers.KeybindsRegister;
import ru.fr0le.pickup.common.CommonProxy;
import ru.fr0le.pickup.network.PacketSender;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit() {
		super.preInit();  
		Display.setTitle(Info.name + " demo");
		keyBind();
		utils();
	}

	@Override
	public void Init() {
		super.Init();

	}

	@Override
	public void postInit() {
		super.postInit(); 

	}

	public void keyBind() {
		KeybindsRegister.register();
	}

	public void utils() {
		FMLCommonHandler.instance().bus().register(new PacketSender(Minecraft.getMinecraft()));
		MinecraftForge.EVENT_BUS.register(new PacketSender(Minecraft.getMinecraft()));
	}

}