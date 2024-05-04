package io.sailex;

import io.sailex.hud.PositionDisplay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class PositionDisplayClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		HudRenderCallback.EVENT.register(new PositionDisplay());

	}
}