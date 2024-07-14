package io.sailex.gui.screens;

import net.minecraft.client.gui.widget.ClickableWidget;

import java.util.List;

public class ScreenManager {

    private final List<ClickableWidget> widgetList;
    private AddHudElementsScreen addHudElementsScreen;
    private static MoveHudElementsScreen moveHudElementsScreen;

    public ScreenManager(List<ClickableWidget> widgetList) {
        this.widgetList = widgetList;
    }

    public void registerScreens() {
        moveHudElementsScreen = new MoveHudElementsScreen(widgetList);
        addHudElementsScreen = new AddHudElementsScreen();
    }

    public AddHudElementsScreen getAddHudElementsScreen() {
        return addHudElementsScreen;
    }

    public MoveHudElementsScreen getMoveHudElementsScreen() {
        return moveHudElementsScreen;
    }

}
