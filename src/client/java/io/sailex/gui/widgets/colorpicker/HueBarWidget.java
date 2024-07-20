package io.sailex.gui.widgets.colorpicker;

import io.sailex.util.Textures;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.awt.Color;

public class HueBarWidget extends ClickableWidget {

    private final OnHueChanged onHueChanged;

    private int selectedHue;
    private int draggedMousePosY;

    public HueBarWidget(int x, int y, int width, int height, OnHueChanged onHueChanged, int selectedHue) {
        super(x, y, width, height, Text.literal("ColorHueBar"));
        this.onHueChanged = onHueChanged;
        this.selectedHue = selectedHue;
        this.draggedMousePosY = (int) (y + (selectedHue / 360.0) * height);
    }

    @FunctionalInterface
    public interface OnHueChanged {
        void onHueChanged(int newHue);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        drawHueBar(context);
        drawControlButton(context);
    }

    private void drawHueBar(DrawContext context) {
        for (int i = 0; i < getHeight(); i++) {
            int color = Color.HSBtoRGB(i / (float) getHeight(), 1.0f, 1.0f);
            context.fill(getX(), getY() + i, getX() + getWidth(), getY() + i + 1, color);
        }
    }

    private void drawControlButton(DrawContext context) {
        context.drawTexture(Textures.HUE_BAR_CONTROL, getX() - 1, draggedMousePosY - 2, 0, 0,
                getWidth() + 2, 8, getWidth() + 2, 8);
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
        if (!isMouseYOver((int) mouseY)) {
            return;
        }
        updateHue((int) mouseY);
        this.draggedMousePosY = (int) mouseY;
    }

    private boolean isMouseYOver(int mouseY) {
        return mouseY >= getY() && mouseY <= getY() + getHeight() - getHeight() / 10;
    }

    private void updateHue(int mouseY) {
        this.selectedHue = (int) ((mouseY - getY()) * 360.0f / getHeight());
        this.onHueChanged.onHueChanged(this.selectedHue);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}

}

