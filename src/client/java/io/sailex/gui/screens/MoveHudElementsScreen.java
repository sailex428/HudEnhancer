package io.sailex.gui.screens;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.List;

public class MoveHudElementsScreen extends Screen {

    private final MinecraftClient client = MinecraftClient.getInstance();
    private final List<ClickableWidget> widgetList;

    public MoveHudElementsScreen(List<ClickableWidget> widgetList) {
        super(Text.empty());
        this.widgetList = widgetList;
    }

    @Override
    protected void init() {
        super.init();

        this.clearChildren();

        for (ClickableWidget widget : widgetList) {
            this.addDrawableChild(widget);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawScreenTitle(context);
    }

    @Override
    public void renderInGameBackground(DrawContext context) {
        context.fill(0, 0, this.width, this.height, 0);
    }


    private void drawScreenTitle(DrawContext context) {
        int screenHeight = client.getWindow().getScaledHeight();
        context.fill(width / 2 - 40, screenHeight - 65, width / 2 + 40, screenHeight - 48, 0x80000000);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Move Elements"), width / 2, screenHeight - 60, 0xFFFFFFFF);
    }

}
