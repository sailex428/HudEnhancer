package io.sailex.util;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.awt.Color;

/**
 * Utility class for rendering animated rainbow text.
 * @author sailex
 */
public class RainbowTextRenderer {

    public static final RainbowTextRenderer INSTANCE = new RainbowTextRenderer();
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final float ANIMATION_SPEED = 0.008F;
    private float hueOffset = 0.0F;

    /**
     * Constructs a new {@code RainbowTextRenderer}.
     */
    private RainbowTextRenderer() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            hueOffset += ANIMATION_SPEED;
            if (hueOffset > 1.0F) {
                hueOffset -= 1.0F;
            }
        });
    }

    /**
     * Gets the color of the rainbow at the specified hue.
     *
     * @param hue the hue of the color
     * @return the color of the rainbow at the specified hue
     */
    public int getRainbowColor(float hue) {
        return Color.HSBtoRGB(hue % 1.0F, 1.0F, 1.0F);
    }

    /**
     * Draws animated rainbow text on the screen.
     *
     * @param context the draw context
     * @param text the text to draw
     * @param x the x-coordinate of the text
     * @param y the y-coordinate of the text
     * @param shadow whether to draw a shadow
     */
    public void drawAnimatedRainbowText(DrawContext context, String text, int x, int y, boolean shadow) {
        int length = text.length();
        float hueRange = 0.075F / length;

        for (int i = 0; i < length; i++) {
            float relativePosition = i * hueRange;

            float hue = (hueOffset + relativePosition) % 1.0F;
            int color = getRainbowColor(hue);

            context.drawText(client.textRenderer, String.valueOf(text.charAt(i)), x, y, color, shadow);
            x += client.textRenderer.getWidth(String.valueOf(text.charAt(i)));
        }
    }

}
