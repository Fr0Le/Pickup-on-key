package ru.fr0le.pickup.client.handlers;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;

public class KeybindsRegister {	

	public static final KeyBinding
	PICKUP_KEY = new KeyBinding(Type.getTranslate("key.pickup"), Keyboard.KEY_F, Type.getTranslate("key.categories.pickup")); 

	public static void register() {
		setRegister(PICKUP_KEY);
	}

	private static void setRegister(KeyBinding binding) {
		ClientRegistry.registerKeyBinding(binding);
	}

}