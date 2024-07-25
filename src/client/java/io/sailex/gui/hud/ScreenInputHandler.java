package io.sailex.gui.hud;

import io.sailex.gui.screens.MoveHudElementsScreen;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.minecraft.client.gui.screen.Screen;

import java.util.List;

public class ScreenInputHandler {

    private final List<IHudElement> elements;

    public ScreenInputHandler(List<IHudElement> elements) {
        this.elements = elements;
        register();
    }

    public void register() {
        ScreenEvents.AFTER_INIT.register((client, currentScreen, scaledWidth, scaledHeight) -> {
            if (!(currentScreen instanceof MoveHudElementsScreen)) {
                return;
            }
            handleMouseClick(currentScreen);
            handleMouseRelease(currentScreen);
        });
    }

    private void handleMouseClick(Screen currentScreen) {
        ScreenMouseEvents.afterMouseClick(currentScreen).register((screen, mouseX, mouseY, button) -> {
            for (IHudElement element : elements) {
                element.onClick(button, (int) mouseX, (int) mouseY);
            }
        });
    }

    private void handleMouseRelease(Screen currentScreen) {
        ScreenMouseEvents.afterMouseRelease(currentScreen).register((screen, mouseX, mouseY, button) -> {
            for (IHudElement element : elements) {
                element.onRelease(button, (int) mouseX, (int) mouseY);
            }
        });
    }
}
