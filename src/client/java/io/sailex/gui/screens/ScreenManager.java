package io.sailex.gui.screens;

import io.sailex.gui.hud.IHudElement;

import java.util.List;

/**
 * Manages and register multiple screens.
 *
 * @author sailex
 */
public class ScreenManager {

    private final List<IHudElement> hudElementList;
    private static ToggleHudElementsScreen toggleHudElementsScreen;
    private static MoveHudElementsScreen moveHudElementsScreen;

    /**
     * Constructs a {@code ScreenManager}.
     *
     * @param hudElementList the list of hud elements that must be rendered
     */
    public ScreenManager(List<IHudElement> hudElementList) {
        this.hudElementList = hudElementList;
    }

    /**
     * Register the move and toggle hud elements screens.
     */
    public void registerScreens() {
        moveHudElementsScreen = new MoveHudElementsScreen();
        toggleHudElementsScreen = new ToggleHudElementsScreen(hudElementList);
    }

    /**
     * Gets the ToggleHudElements screen.
     *
     * @return the ToggleHudElements screen
     */
    public ToggleHudElementsScreen getToggleHudElementsScreen() {
        return toggleHudElementsScreen;
    }

    /**
     * Gets the MoveHudElements screen.
     *
     * @return the MoveHudElements screen
     */
    public MoveHudElementsScreen getMoveHudElementsScreen() {
        return moveHudElementsScreen;
    }

}
