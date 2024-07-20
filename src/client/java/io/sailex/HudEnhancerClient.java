package io.sailex;

import io.sailex.config.ConfigManager;
import io.sailex.config.PositionDisplayConfig;
import io.sailex.gui.hud.HudElementsManager;
import io.sailex.gui.screens.ScreenManager;
import io.sailex.keybinds.MoveHudElementsKeybind;
import net.fabricmc.api.ClientModInitializer;

public class HudEnhancerClient implements ClientModInitializer {

	public static String MOD_ID = "hud-enhancer";
	public static ScreenManager screenManager;

    @Override
	public void onInitializeClient() {

		PositionDisplayConfig positionDisplayConfig = new PositionDisplayConfig();
		positionDisplayConfig.register();

		ConfigManager configManager = new ConfigManager(positionDisplayConfig);
		configManager.initialize();

		HudElementsManager hudManager = new HudElementsManager(positionDisplayConfig.getPositionMap());
		hudManager.register();

        ScreenManager screenManager = new ScreenManager(hudManager.getHudWidgets());
		screenManager.registerScreens();

		MoveHudElementsKeybind keybind = new MoveHudElementsKeybind(screenManager.getMoveHudElementsScreen());
		keybind.register();

	}

	public static ScreenManager getScreenManager() {
		return screenManager;
	}

}