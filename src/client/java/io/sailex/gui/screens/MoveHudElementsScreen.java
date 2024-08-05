package io.sailex.gui.screens;

import io.sailex.gui.widgets.AddWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;

public class MoveHudElementsScreen extends AScreen {

    public MoveHudElementsScreen() {
        super(Text.of("Move HUD Elements"));
    }

    @Override
    protected void init() {
        super.init();

        this.clearChildren();
        this.addDrawableChild(createAddWidget());
    }

    private ClickableWidget createAddWidget() {
        Window window = client.getWindow();
        int widgetSize = 40;
        return new AddWidget(window.getScaledWidth() / 2 - (widgetSize / 2),  window.getScaledHeight() / 2 - (widgetSize / 2),
                widgetSize, widgetSize);
    }

    @Override
    public void close() {
        this.client.setScreen(null);
    }

}
