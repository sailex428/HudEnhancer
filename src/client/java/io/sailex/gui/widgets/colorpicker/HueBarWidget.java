package io.sailex.gui.widgets.colorpicker;

import io.sailex.util.Textures;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.awt.Color;

/**
 * A widget that represents a hue selection bar for picking colors.
 *
 * @author sailex
 */
public class HueBarWidget extends ClickableWidget {

    private final OnHueChanged onHueChanged;

    private int selectedHue;
    private int draggedMousePosY;

    /**
     * Constructs a {@code HueBarWidget}.
     *
     * @param x              the x position of the widget
     * @param y              the y position of the widget
     * @param width          the width of the widget
     * @param height         the height of the widget
     * @param onHueChanged   the callback interface to be called when the hue changes
     * @param selectedHue    the initial selected hue
     */
    public HueBarWidget(int x, int y, int width, int height, OnHueChanged onHueChanged, int selectedHue) {
        super(x, y, width, height, Text.literal("ColorHueBar"));
        this.onHueChanged = onHueChanged;
        this.selectedHue = selectedHue;
        this.draggedMousePosY = (int) (y + (selectedHue / 360.0) * height);
    }

    /**
     * Functional interface for handling hue change events.
     */
    @FunctionalInterface
    public interface OnHueChanged {
        /**
         * Called when the hue changes.
         *
         * @param newHue the new hue value
         */
        void onHueChanged(int newHue);
    }

    /**
     * Renders the hue bar and the control button.
     */
    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        drawHueBar(context);
        drawControlButton(context);
    }

    /**
     * Draws the hue bar, displaying a gradient of hues from top to bottom.
     *
     * @param context the draw context
     */
    private void drawHueBar(DrawContext context) {
        for (int i = 0; i < getHeight(); i++) {
            int color = Color.HSBtoRGB(i / (float) getHeight(), 1.0f, 1.0f);
            context.fill(getX(), getY() + i, getX() + getWidth(), getY() + i + 1, color);
        }
    }

    /**
     * Draws the control button that indicates the current hue selection position.
     *
     * @param context the draw context
     */
    private void drawControlButton(DrawContext context) {
        context.drawTexture(Textures.HUE_BAR_CONTROL, getX() - 1, draggedMousePosY - 2, 0, 0,
                getWidth() + 2, 8, getWidth() + 2, 8);
    }

    /**
     * Gets called on drag of the widget and updates the hue based on the Y mouse position.
     */
    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
        if (!isMouseYOver((int) mouseY)) {
            return;
        }
        updateHue((int) mouseY);
        this.draggedMousePosY = (int) mouseY;
    }

    /**
     * Checks if the mouse Y position is within the bounds of the hue bar.
     *
     * @param mouseY the Y position of the mouse
     * @return {@code true} if the mouse Y position is within bounds, {@code false} otherwise
     */
    private boolean isMouseYOver(int mouseY) {
        return mouseY >= getY() && mouseY <= getY() + getHeight() - getHeight() / 10;
    }

    /**
     * Updates the selected hue based on the Y position of the mouse and notifies the callback.
     *
     * @param mouseY the Y position of the mouse
     */
    private void updateHue(int mouseY) {
        this.selectedHue = (int) ((mouseY - getY()) * 360.0f / getHeight());
        this.onHueChanged.onHueChanged(this.selectedHue);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}

}

