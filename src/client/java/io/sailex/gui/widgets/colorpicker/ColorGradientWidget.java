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

    public interface OnColorChanged {
        void onColorChanged(int newColor);
    }

    public ColorGradientWidget(
            int x, int y, int width, int height,
            OnColorChanged onColorChanged,
            int selectedHue, int selectedColor) {
        super(x, y, width, height, Text.literal("ColorGradient"));
        this.onColorChanged = onColorChanged;
        this.selectedHue = selectedHue;
        this.selectedColor = selectedColor;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                float saturation = i / (float) this.width;
                float brightness = 1.0f - j / (float) this.height;
                int color = Color.HSBtoRGB(selectedHue / 360.0f, saturation, brightness);
                context.fill(getX() + i, getY() + j, getX() + i + 1, getY() + j + 1, color);
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.clicked(mouseX, mouseY)) {
            float saturation = (float) (mouseX - getX()) / this.width;
            float brightness = 1.0f - (float) (mouseY - getY()) / this.height;
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

