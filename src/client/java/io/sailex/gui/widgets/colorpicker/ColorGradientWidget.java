package io.sailex.gui.widgets.colorpicker;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.awt.Color;

public class ColorGradientWidget extends ClickableWidget {
    private int selectedHue;
    private int selectedColor;
    private final OnColorChanged onColorChanged;
    private final int widgetX;
    private final int widgetY;

    public interface OnColorChanged {
        void onColorChanged(int newColor);
    }

    public ColorGradientWidget(int x, int y, int width, int height, OnColorChanged onColorChanged) {
        super(x, y, width, height, Text.literal(""));
        this.widgetX = x;
        this.widgetY = y;
        this.onColorChanged = onColorChanged;
        this.selectedHue = 0;
        this.selectedColor = 0xFFFFFF;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                float saturation = i / (float) this.width;
                float brightness = 1.0f - j / (float) this.height;
                int color = Color.HSBtoRGB(selectedHue / 360.0f, saturation, brightness);
                context.fill(widgetX + i, widgetY + j, widgetX + i + 1, widgetY + j + 1, color);
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (clicked(mouseX, mouseY)) {
            float saturation = (float) (mouseX - widgetX) / this.width;
            float brightness = 1.0f - (float) (mouseY - widgetY) / this.height;
            this.selectedColor = Color.HSBtoRGB(this.selectedHue / 360.0f, saturation, brightness);
            this.onColorChanged.onColorChanged(this.selectedColor);
            return true;
        }
        return false;
    }

    public void setSelectedHue(int selectedHue) {
        this.selectedHue = selectedHue;
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}

