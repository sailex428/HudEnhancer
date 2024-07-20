package io.sailex.gui.screens;

import io.sailex.gui.widgets.AWidget;

import java.util.List;

public class ScreenManager {

    private final List<AWidget> widgetList;
    private static ToggleHudElementsScreen toggleHudElementsScreen;
    private static MoveHudElementsScreen moveHudElementsScreen;

    public ScreenManager(List<AWidget> widgetList) {
        this.widgetList = widgetList;
    }

    public void registerScreens() {
        moveHudElementsScreen = new MoveHudElementsScreen(widgetList);
        toggleHudElementsScreen = new ToggleHudElementsScreen(widgetList);
    }

    public ToggleHudElementsScreen getAddHudElementsScreen() {
        return toggleHudElementsScreen;
    }

    public MoveHudElementsScreen getMoveHudElementsScreen() {
        return moveHudElementsScreen;
    }

}
