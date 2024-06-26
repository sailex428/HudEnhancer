package io.sailex.screens.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

public class FPSWidget extends ClickableWidget {

    private final MinecraftClient client = MinecraftClient.getInstance();

    public FPSWidget(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        int textColor = 0xFFFFFF;

        context.fill(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0x80000000);
        context.drawText(client.textRenderer, client.getCurrentFps() + " FPS", getX() + 5, getY() + 5, textColor, true);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}
