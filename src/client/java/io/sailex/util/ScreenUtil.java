package io.sailex.util;

import net.minecraft.client.MinecraftClient;

public class ScreenUtil {

    private static final MinecraftClient client = MinecraftClient.getInstance();

    private ScreenUtil() {}

    private static int getScreenY(int windowHeight, int scaleFactor) {
        return windowHeight / scaleFactor;
    }

    public static int calculateScreenY(int windowHeight) {
        int guiScale = client.options.getGuiScale().getValue();
        guiScale = switch (guiScale) {
            case 1, 2 -> 3;
            case 0 -> 5;
            default -> guiScale;
        };
        return getScreenY(windowHeight, guiScale);
    }

}
