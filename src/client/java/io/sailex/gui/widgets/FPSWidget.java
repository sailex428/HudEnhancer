package io.sailex.gui.widgets;

import io.sailex.config.HudElement;
import io.sailex.config.PositionDisplayConfig;
import io.sailex.util.AWidget;
import io.sailex.util.FPSElementRenderer;
import io.sailex.util.IHudElement;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.Map;

public class FPSWidget extends AWidget {

    public FPSWidget(
            HudElement fps, Map<ClickableWidget,
            IHudElement> widgetToHudElement, Map<String,
            HudElement> positionMap) {
        super(fps, Text.literal(PositionDisplayConfig.FPS));
        this.widgetToHudElement = widgetToHudElement;
        this.positionMap = positionMap;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        FPSElementRenderer.render(
                context,
                this.getX(), this.getY(), this.getWidth(), this.getHeight(),
                color, background, shadow
        );
    }

}
