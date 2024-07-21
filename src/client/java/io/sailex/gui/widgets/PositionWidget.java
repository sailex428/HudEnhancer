package io.sailex.gui.widgets;

import io.sailex.config.HudElement;
import io.sailex.config.HudEnhancerConfig;
import io.sailex.gui.hud.IHudElement;
import io.sailex.util.PositionElementRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.Map;


public class PositionWidget extends AWidget {

    public PositionWidget(
            HudElement posDisplay,
            Map<ClickableWidget, IHudElement> widgetToHudElement,
            Map<String, HudElement> positionMap) {
        super(posDisplay, Text.literal(HudEnhancerConfig.POSITION));
        this.widgetToHudElement = widgetToHudElement;
        this.positionMap = positionMap;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.client.player == null) {
            return;
        }
        PositionElementRenderer.render(
                context,
                this.client.player,
                this.getX(), this.getY(), this.getWidth(), this.getHeight(),
                color, background, shadow
        );
    }

}

