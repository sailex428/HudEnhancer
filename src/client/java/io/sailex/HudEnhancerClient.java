package io.sailex;

import io.sailex.config.ConfigManager;
import io.sailex.config.DefaultConfig;
import io.sailex.gui.hud.HudElementsManager;
import io.sailex.gui.hud.ScreenInputHandler;
import io.sailex.gui.screens.ScreenManager;
import io.sailex.keybinds.MoveHudElementsKeybind;
import net.fabricmc.api.ClientModInitializer;

/**
 * Entry point for the HudEnhancer mod's client-side.
 * Initializes and manages HUD elements, configurations, and input handlers.
 *
 * @author sailex
 */
public class HudEnhancerClient implements ClientModInitializer {

	public static String MOD_ID = "hud-enhancer";
	private static ScreenManager screenManager;
	private static HudElementsManager hudElementsManager;

	/**
	 * Initializes the client-side components of the HudEnhancer mod.
	 */
	@Override
	public void onInitializeClient() {

		DefaultConfig defaultConfig = new DefaultConfig();
		defaultConfig.register();

		ConfigManager configManager = new ConfigManager(defaultConfig);
		configManager.initialize();

		hudElementsManager = new HudElementsManager(defaultConfig.getConfigElementMap());
		hudElementsManager.register();
		configManager.setHudElementsManager(hudElementsManager);

		ScreenInputHandler screenInputHandler = new ScreenInputHandler(hudElementsManager.getHudElements());
		screenInputHandler.register();

		screenManager = new ScreenManager(hudElementsManager.getHudElements());
		screenManager.registerScreens();

		MoveHudElementsKeybind keybind = new MoveHudElementsKeybind(screenManager.getMoveHudElementsScreen());
		keybind.register();

	}

	/**
	 * Gets the ScreenManager instance.
	 *
	 * @return the ScreenManager instance
	 */
	public static ScreenManager getScreenManager() {
		return screenManager;
	}

	public static HudElementsManager getHudElementsManager() {
		return hudElementsManager;
	}

}