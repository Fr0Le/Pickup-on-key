package ru.fr0le.pickup.client.handlers;

import net.minecraft.util.StatCollector;

public class Type {

	public static String getTranslate(String s) {
		return StatCollector.translateToLocal(s);
	}

}