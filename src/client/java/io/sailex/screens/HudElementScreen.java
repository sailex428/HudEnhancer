package io.sailex.screens;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.List;

public class HudElementScreen extends Screen {

    private final List<ClickableWidget> widgetList;

    public HudElementScreen(Text title, List<ClickableWidget> widgetList) {
        super(title);
        this.widgetList = widgetList;
    }

    @Override
    protected void init() {
        super.init();

        for (ClickableWidget widget : widgetList) {
            this.addDrawableChild(widget);
        }
    }

    @Override
    public void renderInGameBackground(DrawContext context) {
        context.fillGradient(0, 0, this.width, this.height, 0, 0);
    }

    @Override
    protected void setInitialFocus(Element element) {

    }

}
