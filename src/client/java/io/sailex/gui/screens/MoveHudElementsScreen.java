package io.sailex.gui.screens;

import io.sailex.gui.widgets.AddWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;

import java.util.List;

public class MoveHudElementsScreen extends AScreen {

    private final List<ClickableWidget> widgetList;

    public MoveHudElementsScreen(List<ClickableWidget> widgetList) {
        super(Text.of("Move HUD Elements"));
        this.widgetList = widgetList;
    }

    @Override
    protected void init() {
        super.init();

        this.clearChildren();
        this.addDrawableChild(createAddWidget());

        addDrawables(widgetList);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawScreenTitle(context);
    }

    private void drawScreenTitle(DrawContext context) {
        int screenHeight = client.getWindow().getScaledHeight();
        context.fill(width / 2 - 40, screenHeight - 65, width / 2 + 40, screenHeight - 48, 0x80000000);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Move Elements"), width / 2, screenHeight - 60, 0xFFFFFFFF);
    }

    private ClickableWidget createAddWidget() {
        Window window = client.getWindow();
        return new AddWidget(window.getScaledWidth() - 40, window.getScaledHeight(), 25, 25);
    }

}
