package io.sailex.screens.widgets;

import io.sailex.util.PositionElementRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

public class PositionWidget extends ClickableWidget {

    private final MinecraftClient client = MinecraftClient.getInstance();
    private final PositionElementRenderer positionElementRenderer;

    public PositionWidget(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
        this.positionElementRenderer = new PositionElementRenderer();
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        if (client.player == null) {
            return;
        }
        positionElementRenderer.render(context, client.player, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return true;
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}

