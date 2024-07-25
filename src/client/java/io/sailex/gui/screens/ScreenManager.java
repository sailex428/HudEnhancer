package io.sailex.gui.screens;

import io.sailex.gui.hud.IHudElement;

import java.util.List;

public class ScreenManager {

    private final List<IHudElement> hudElementList;
    private static ToggleHudElementsScreen toggleHudElementsScreen;
    private static MoveHudElementsScreen moveHudElementsScreen;

    public ScreenManager(List<IHudElement> hudElementList) {
        this.hudElementList = hudElementList;
    }

    public void registerScreens() {
        moveHudElementsScreen = new MoveHudElementsScreen();
        toggleHudElementsScreen = new ToggleHudElementsScreen(hudElementList);
    }

    public ToggleHudElementsScreen getAddHudElementsScreen() {
        return toggleHudElementsScreen;
    }

    public MoveHudElementsScreen getMoveHudElementsScreen() {
        return moveHudElementsScreen;
    }

}
