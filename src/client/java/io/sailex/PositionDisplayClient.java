package io.sailex;

import io.sailex.config.ConfigManager;
import io.sailex.config.PositionDisplayConfig;
import io.sailex.hud.HudElementsManager;
import io.sailex.keybinds.EditHudElementsKeybind;
import io.sailex.screens.HudElementScreen;
import io.sailex.screens.WidgetManager;
import io.sailex.screens.widgets.FPSWidget;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class PositionDisplayClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		PositionDisplayConfig positionDisplayConfig = new PositionDisplayConfig();
		positionDisplayConfig.register();

		ConfigManager configManager = new ConfigManager(positionDisplayConfig);
		configManager.initialize();

		HudElementsManager hudManager = new HudElementsManager(positionDisplayConfig.getPositionMap());
		hudManager.register();

		WidgetManager widgetManager = new WidgetManager(positionDisplayConfig.getPositionMap());
		widgetManager.registerWidgets();

		HudElementScreen screen = new HudElementScreen(Text.empty(), widgetManager.getWidgets());

		EditHudElementsKeybind keybind = new EditHudElementsKeybind(screen);
		keybind.register();

	}
}