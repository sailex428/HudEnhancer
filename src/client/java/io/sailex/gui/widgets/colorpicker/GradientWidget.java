package io.sailex.gui.widgets.colorpicker;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.awt.Color;

import io.sailex.gui.widgets.Textures;


public class GradientWidget extends ClickableWidget {

    private final OnColorChanged onColorChanged;

    private int selectedHue;
    private int selectedColor;
    private int draggedMouseX;
    private int draggedMouseY;

    public GradientWidget(int x, int y, int width, int height, OnColorChanged onColorChanged, int selectedHue, int selectedColor) {
        super(x, y, width, height, Text.literal("ColorGradient"));
        this.onColorChanged = onColorChanged;
        this.selectedHue = selectedHue;
        this.selectedColor = selectedColor;
        setDraggedMousePos();
    }

    public interface OnColorChanged {
        void onColorChanged(int newColor);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        renderGradient(context);
        renderGradientControl(context);
        updateGradient(draggedMouseX, draggedMouseY); //TODO update gradient only on hueChange
    }

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

    private void renderGradientControl(DrawContext context) {
        int size = 12;
        context.drawTexture(Textures.BUTTON_IDENTIFIER, draggedMouseX, draggedMouseY, 0, 0,
                size, size, size, size);
    }

    private void updateGradient(int mouseX, int mouseY) {
        float saturation = (float) (mouseX - getX()) / getWidth();
        float brightness = 1.0f - (float) (mouseY - getY()) / getHeight();

        this.selectedColor = Color.HSBtoRGB(this.selectedHue / 360.0f, saturation, brightness);
        this.onColorChanged.onColorChanged(this.selectedColor);
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
        if (!isMouseOver((int) mouseX, (int) mouseY)) {
            return;
        }
        this.draggedMouseX = (int) mouseX - 6;
        this.draggedMouseY = (int) mouseY - 6;
    }

    private boolean isMouseOver(int mouseX, int mouseY) {
        int offset = 6;
        return mouseX >= getX() - offset && mouseY >= getY() - offset &&
                mouseX <= getX() + getWidth() - offset && mouseY <= getY() + getHeight() - offset;
    }

    private void setDraggedMousePos() {
        float[] hsbValues = new float[3];
        Color.RGBtoHSB((selectedColor >> 16) & 0xFF, (selectedColor >> 8) & 0xFF, selectedColor & 0xFF, hsbValues);
        float saturation = hsbValues[1];
        float brightness = hsbValues[2];

        this.draggedMouseX = (int) (getX() + saturation * getWidth());
        this.draggedMouseY = (int) (getY() + (1.0f - brightness) * getHeight());
    }

    public void setSelectedHue(int selectedHue) {
        this.selectedHue = selectedHue;
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}

