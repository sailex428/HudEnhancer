package io.sailex;

import io.sailex.config.ConfigManager;
import io.sailex.config.DefaultConfig;
import io.sailex.gui.hud.HudElementsManager;
import io.sailex.gui.hud.ScreenInputHandler;
import io.sailex.gui.screens.ScreenManager;
import io.sailex.keybinds.MoveHudElementsKeybind;
import net.fabricmc.api.ClientModInitializer;

public class HudEnhancerClient implements ClientModInitializer {

	public static String MOD_ID = "hud-enhancer";
	private static ScreenManager screenManager;

	@Override
	public void onInitializeClient() {

		DefaultConfig defaultConfig = new DefaultConfig();
		defaultConfig.register();

		ConfigManager configManager = new ConfigManager(defaultConfig);
		configManager.initialize();

		HudElementsManager hudManager = new HudElementsManager(defaultConfig.getConfigElementMap());
		hudManager.register();
		configManager.setHudElementsManager(hudManager);

		ScreenInputHandler screenInputHandler = new ScreenInputHandler(hudManager.getHudElements());
		screenInputHandler.register();

		screenManager = new ScreenManager(hudManager.getHudElements());
		screenManager.registerScreens();

		MoveHudElementsKeybind keybind = new MoveHudElementsKeybind(screenManager.getMoveHudElementsScreen());
		keybind.register();

	}

	public static ScreenManager getScreenManager() {
		return screenManager;
	}

}