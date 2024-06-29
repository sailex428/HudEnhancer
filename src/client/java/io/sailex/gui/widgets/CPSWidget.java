package io.sailex.gui.widgets;

import io.sailex.config.HudElement;
import io.sailex.config.PositionDisplayConfig;
import io.sailex.util.AWidget;
import io.sailex.util.CPSCalculator;
import io.sailex.util.IHudElement;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.Map;


public class CPSWidget extends AWidget {

    private final CPSCalculator cpsCalculator;

    public CPSWidget(
            HudElement cps, Map<ClickableWidget,
            IHudElement> widgetToHudElement,
            Map<String, HudElement> positionMap) {
        super(cps, Text.literal(PositionDisplayConfig.CPS));
        this.widgetToHudElement = widgetToHudElement;
        this.positionMap = positionMap;
        this.cpsCalculator = new CPSCalculator();
    }

    @Override
    protected void renderWidget(DrawContext drawContext, int mouseX, int mouseY, float delta) {
        drawContext.fill(getX(), getY(), getX() + getWidth(), getY() + getHeight(), backgroundColor);
        drawContext.drawText(client.textRenderer,  cpsCalculator.getCPS() + " CPS", getX() + 5, getY() + 5, color, shadow);
    }
}
