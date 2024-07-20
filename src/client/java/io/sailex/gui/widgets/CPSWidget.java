package io.sailex.gui.widgets;

import io.sailex.config.HudElement;
import io.sailex.config.HudEnhancerConfig;
import io.sailex.util.CPSElementRenderer;
import io.sailex.gui.hud.IHudElement;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.util.Map;


public class CPSWidget extends AWidget {

    public CPSWidget(
            HudElement cps, Map<AWidget,
            IHudElement> widgetToHudElement,
            Map<String, HudElement> positionMap) {
        super(cps, Text.literal(HudEnhancerConfig.CPS));
        this.widgetToHudElement = widgetToHudElement;
        this.positionMap = positionMap;
    }

    @Override
    protected void renderWidget(DrawContext drawContext, int mouseX, int mouseY, float delta) {
        CPSElementRenderer.render(
                drawContext,
                this.getX(), this.getY(), this.getWidth(), this.getHeight(),
                getColor(), isBackground(), isShadow(), isActive()
        );
    }
}
