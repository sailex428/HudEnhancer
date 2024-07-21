package io.sailex.gui.screens;

import net.minecraft.client.gui.widget.ClickableWidget;

import java.util.List;

public class ScreenManager {

    private final List<ClickableWidget> widgetList;
    private static MoveHudElementsScreen moveHudElementsScreen;

    public ScreenManager(List<ClickableWidget> widgetList) {
        this.widgetList = widgetList;
    }

    public void registerScreens() {
        moveHudElementsScreen = new MoveHudElementsScreen(widgetList);
    }

    public MoveHudElementsScreen getMoveHudElementsScreen() {
        return moveHudElementsScreen;
    }

}
