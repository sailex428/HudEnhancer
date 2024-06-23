package io.sailex;

import io.sailex.hud.HudElementsManager;
import io.sailex.hud.util.IHudElement;
import io.sailex.hud.util.MouseInputHandler;
import io.sailex.hud.elements.PositionDisplay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.ChatScreen;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PositionDisplayClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		HudElementsManager manager = new HudElementsManager();
		manager.register();

	}
}