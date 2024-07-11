package io.sailex.gui.widgets.colorpicker;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.awt.Color;


public class HueBarWidget extends ClickableWidget {
    private int selectedHue;
    private final OnHueChanged onHueChanged;

    public interface OnHueChanged {
        void onHueChanged(int newHue);
    }

    public HueBarWidget(
            int x, int y,
            int width, int height,
            OnHueChanged onHueChanged,
            int selectedHue) {
        super(x, y, width, height, Text.literal("ColorHueBar"));
        this.onHueChanged = onHueChanged;
        this.selectedHue = selectedHue;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        for (int i = 0; i < this.height; i++) {
            int color = Color.HSBtoRGB(i / (float) this.height, 1.0f, 1.0f);
            context.fill(getX(), getY() + i, getX() + this.width, getY() + i + 1, color);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (clicked(mouseX, mouseY)) {
            this.selectedHue = (int) ((mouseY - getY()) * 360.0f / this.height);
            this.onHueChanged.onHueChanged(this.selectedHue);
            return true;
        }
        return false;
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}

