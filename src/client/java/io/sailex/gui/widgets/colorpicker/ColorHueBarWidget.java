package io.sailex.gui.widgets.colorpicker;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.awt.Color;


public class ColorHueBarWidget extends ClickableWidget {
    private int selectedHue;
    private final OnHueChanged onHueChanged;
    private final int widgetX;
    private final int widgetY;

    public interface OnHueChanged {
        void onHueChanged(int newHue);
    }

    public ColorHueBarWidget(int x, int y, int width, int height, OnHueChanged onHueChanged) {
        super(x, y, width, height, Text.literal(""));
        this.onHueChanged = onHueChanged;
        this.selectedHue = 0;
        this.widgetX = x;
        this.widgetY = y;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        for (int i = 0; i < this.height; i++) {
            int color = Color.HSBtoRGB(i / (float) this.height, 1.0f, 1.0f);
            context.fill(widgetX, widgetY + i, widgetX + this.width, widgetY + i + 1, color);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (clicked(mouseX, mouseY)) {
            this.selectedHue = (int) ((mouseY - widgetY) * 360.0f / this.height);
            this.onHueChanged.onHueChanged(this.selectedHue);
            return true;
        }
        return false;
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}

