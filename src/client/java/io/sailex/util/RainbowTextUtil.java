package io.sailex.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;

public class RainbowTextUtil {

    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final float ANIMATION_SPEED = 0.003F;
    private float hueOffset = 0.0F;

    public int getRainbowColor(float hue) {
        return Color.HSBtoRGB(hue % 1.0F, 1.0F, 1.0F);
    }

    public void drawAnimatedRainbowText(DrawContext context, String text, int x, int y, boolean shadow) {
        int length = text.length();
        int totalWidth = 0;

        for (int i = 0; i < length; i++) {
            totalWidth += client.textRenderer.getWidth(String.valueOf(text.charAt(i)));
        }

        hueOffset += ANIMATION_SPEED;
        if (hueOffset > 1.0F) {
            hueOffset -= 1.0F;
        }

        int currentX = x;

        for (int i = 0; i < length; i++) {
            float relativePosition = (float) (currentX - x) / totalWidth;

            float hue = (hueOffset + relativePosition) % 1.0F;
            int color = getRainbowColor(hue);

            context.drawText(client.textRenderer, String.valueOf(text.charAt(i)), x, y, color, shadow);
            x += client.textRenderer.getWidth(String.valueOf(text.charAt(i)));
        }
    }

}
