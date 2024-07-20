package io.sailex;

import io.sailex.config.ConfigManager;
import io.sailex.config.HudEnhancerConfig;
import io.sailex.gui.hud.HudElementsManager;
import io.sailex.gui.screens.ScreenManager;
import io.sailex.keybinds.MoveHudElementsKeybind;
import net.fabricmc.api.ClientModInitializer;

public class HudEnhancerClient implements ClientModInitializer {

	public static String MOD_ID = "hud-enhancer";
	private static ScreenManager screenManager;

	@Override
	public void onInitializeClient() {

		HudEnhancerConfig hudEnhancerConfig = new HudEnhancerConfig();
		hudEnhancerConfig.register();

		ConfigManager configManager = new ConfigManager(hudEnhancerConfig);
		configManager.initialize();

		HudElementsManager hudManager = new HudElementsManager(hudEnhancerConfig.getPositionMap());
		hudManager.register();

		screenManager = new ScreenManager(hudManager.getHudWidgets());
		screenManager.registerScreens();

		MoveHudElementsKeybind keybind = new MoveHudElementsKeybind(screenManager.getMoveHudElementsScreen());
		keybind.register();

	}

	public static ScreenManager getScreenManager() {
		return screenManager;
	}

}