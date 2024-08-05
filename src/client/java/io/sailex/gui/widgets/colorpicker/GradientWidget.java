package io.sailex.gui.widgets.colorpicker;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.awt.Color;

import io.sailex.util.Textures;

/**
 * A widget that represents a color gradient picker, allowing users to select colors based on hue, saturation, and brightness.
 *
 * @author sailex
 */
public class GradientWidget extends ClickableWidget {

    private final OnColorChanged onColorChanged;

    private int selectedHue;
    private int selectedColor;
    private int draggedMouseX;
    private int draggedMouseY;
    private final int offset = 4;

    /**
     * Constructs a {@code GradientWidget}.
     *
     * @param x              the x position of the widget
     * @param y              the y position of the widget
     * @param width          the width of the widget
     * @param height         the height of the widget
     * @param onColorChanged the callback interface to be called when the color changes
     * @param selectedHue    the initial selected hue
     * @param selectedColor  the initial selected color
     */
    public GradientWidget(int x, int y, int width, int height, OnColorChanged onColorChanged, int selectedHue, int selectedColor) {
        super(x, y, width, height, Text.literal("ColorGradient"));
        this.onColorChanged = onColorChanged;
        this.selectedHue = selectedHue;
        this.selectedColor = selectedColor;
        setDraggedMousePos();
    }

    /**
     * Functional interface for handling color change events.
     */
    @FunctionalInterface
    public interface OnColorChanged {
        /**
         * Called when the color changes.
         *
         * @param newColor the new color value
         */
        void onColorChanged(int newColor);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        renderGradient(context);
        renderGradientControl(context);
        updateGradient(draggedMouseX, draggedMouseY); //TODO update gradient only on hueChange
    }

    /**
     * Renders the color gradient based on the current hue.
     */
    private void renderGradient(DrawContext context) {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                float saturation = i / (float) getWidth();
                float brightness = 1.0f - j / (float) getHeight();
                int color = Color.HSBtoRGB(selectedHue / 360.0f, saturation, brightness);
                context.fill(getX() + i, getY() + j, getX() + i + 1, getY() + j + 1, color);
            }
        }
    }

    /**
     * Renders the control button that indicates the current color selection position.
     */
    private void renderGradientControl(DrawContext context) {
        int size = 8;
        context.drawTexture(Textures.GRADIENT_CONTROL, draggedMouseX, draggedMouseY,
                0, 0, size, size, size, size);
    }

    /**
     * Updates the selected color based on the current position of the mouse and notifies the callback.
     *
     * @param mouseX the X position of the mouse
     * @param mouseY the Y position of the mouse
     */
    private void updateGradient(int mouseX, int mouseY) {
        float saturation = (float) (mouseX + offset - getX()) / getWidth();
        float brightness = 1.0f - (float) (mouseY + offset - getY()) / getHeight();

        this.selectedColor = Color.HSBtoRGB(this.selectedHue / 360.0f, saturation, brightness);
        this.onColorChanged.onColorChanged(this.selectedColor);
    }

    /**
     * Gets called on drag of the widget and sets new dragged position of the mouse.
     */
    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
        if (!isMouseOver((int) mouseX, (int) mouseY)) {
            return;
        }
        this.draggedMouseX = (int) mouseX - offset;
        this.draggedMouseY = (int) mouseY - offset;
    }

    /**
     * Sets the position of the control button based on the currently selected color.
     */
    private void setDraggedMousePos() {
        float[] hsbValues = new float[3];
        Color.RGBtoHSB((selectedColor >> 16) & 0xFF, (selectedColor >> 8) & 0xFF, selectedColor & 0xFF, hsbValues);
        float saturation = hsbValues[1];
        float brightness = hsbValues[2];

        this.draggedMouseX = (int) (getX() + saturation * getWidth()) - offset;
        this.draggedMouseY = (int) (getY() + (1.0f - brightness) * getHeight()) -  offset;
    }

    /**
     * Sets a new hue for the gradient and updates the control button position accordingly.
     *
     * @param selectedHue the new hue to be set
     */
    public void setSelectedHue(int selectedHue) {
        this.selectedHue = selectedHue;
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}

