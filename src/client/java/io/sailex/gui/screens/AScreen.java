package io.sailex.gui.screens;

import io.sailex.HudEnhancerClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.text.Text;

/**
 * Abstract base class for custom screens in the HUD Enhancer mod.
 *
 * @author sailex
 */
public abstract class AScreen extends Screen {

    protected static final int CONTENT_PADDING = 5;
    protected static final int DEFAULT_LINE_PADDING = 20;
    protected final MinecraftClient client;

    protected int screenX;
    protected int screenY;

    /**
     * Constructs a screen with the given title.
     *
     * @param title The title of the screen.
     */
    protected AScreen(Text title) {
        super(title);
        this.client = MinecraftClient.getInstance();
    }

    /**
     * Renders the background of the screen.
     */
    protected void renderScreenBackground(DrawContext context, int screenX, int screenY) {
        context.fill(screenX, screenY, width - screenX, height - screenY, 0xFF232323);
    }

    /**
     * Renders the title of the screen.
     *
     * @param translationKey The translation key for the title text.
     */
    protected void renderScreenTitle(DrawContext context, int windowX, int windowY, String translationKey) {
        context.drawText(client.textRenderer,
                Text.translatable(translationKey),
                windowX + CONTENT_PADDING, windowY + 7,
                0xFFFFFFFF, true);
    }

    /**
     * Renders a horizontal line on the screen.
     *
     * @param linePadding The padding for the line.
     */
    protected void renderLine(DrawContext context, int windowX, int windowY, int linePadding) {
        context.drawHorizontalLine(windowX + CONTENT_PADDING, width - windowX - CONTENT_PADDING,
                windowY + linePadding, 0xFF565656);
    }

    /**
     * Renders the in-game background, filling the entire screen with a transparent color.
     *
     * @param context The rendering context.
     */
    @Override
    public void renderInGameBackground(DrawContext context) {
        context.fill(0, 0, this.width, this.height, 0);
    }

    /**
     * Builds a basic CheckboxWidget.
     *
     * @param currentLinePadding Y coordinate of screen
     * @param checked value of checkbox on initialize
     * @param callback function that gets called on checked value change
     * @return the created CheckboxWidget
     */
    protected CheckboxWidget createCheckBoxWidget(int currentLinePadding, boolean checked, CheckboxWidget.Callback callback) {
        return CheckboxWidget.builder(Text.of(""), textRenderer)
                .pos(this.width - screenX - 21, screenY + currentLinePadding)
                .checked(checked).callback(callback).build();
    }

    /**
     * Closes the current screen and opens the Move HUD Elements screen.
     */
    @Override
    public void close() {
        client.setScreen(HudEnhancerClient.getScreenManager().getMoveHudElementsScreen());
    }
}
