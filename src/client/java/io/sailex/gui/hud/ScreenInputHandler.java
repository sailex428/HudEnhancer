package io.sailex.gui.hud;

import io.sailex.gui.screens.EditHudElementsScreen;
import io.sailex.gui.screens.MoveHudElementsScreen;
import io.sailex.gui.screens.ToggleHudElementsScreen;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.minecraft.client.gui.screen.Screen;

import java.util.List;

/**
 * Handles input events for HUD elements on specific screens.
 *
 * @author sailex
 */
public class ScreenInputHandler {

    private final List<IHudElement> elements;

    /**
     * Constructs a new {@code ScreenInputHandler} and registers event handlers.
     *
     * @param elements the list of HUD elements to handle input for
     */
    public ScreenInputHandler(List<IHudElement> elements) {
        this.elements = elements;
        register();
    }

    /**
     * Registers event handlers for mouse input on relevant screens.
     */
    public void register() {
        ScreenEvents.AFTER_INIT.register((client, currentScreen, scaledWidth, scaledHeight) -> {
            if (!isMoveElementScreen(currentScreen)) {
                return;
            }
            handleMouseClick(currentScreen);
            handleMouseRelease(currentScreen);
        });
    }

    /**
     * Checks if the given screen is one of the screens related to HUD elements.
     *
     * @param currentScreen the screen currently shown on the client
     * @return {@code true} if the screen is {@link MoveHudElementsScreen},
     *         {@link EditHudElementsScreen}, or {@link ToggleHudElementsScreen};
     *         {@code false} otherwise
     */
    private boolean isMoveElementScreen(Screen currentScreen) {
        return currentScreen instanceof MoveHudElementsScreen ||
                currentScreen instanceof EditHudElementsScreen ||
                currentScreen instanceof ToggleHudElementsScreen;
    }

    /**
     * Registers a handler for mouse clicks on the given screen.
     *
     * @param currentScreen the screen currently shown on the client
     */
    private void handleMouseClick(Screen currentScreen) {
        ScreenMouseEvents.afterMouseClick(currentScreen).register((screen, mouseX, mouseY, button) -> {
            for (IHudElement element : elements) {
                element.onClick(button, (int) mouseX, (int) mouseY);
            }
        });
    }

    /**
     * Registers a handler for mouse releases on the given screen.
     *
     * @param currentScreen the screen currently shown on the client
     */
    private void handleMouseRelease(Screen currentScreen) {
        ScreenMouseEvents.afterMouseRelease(currentScreen).register((screen, mouseX, mouseY, button) -> {
            for (IHudElement element : elements) {
                element.onRelease(button, (int) mouseX, (int) mouseY);
            }
        });
    }
}
