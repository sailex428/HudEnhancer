package io.sailex.gui.hud.elements;

import io.sailex.config.HudElement;
import io.sailex.util.AHudElement;
import io.sailex.util.CPSCalculator;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;

public class CPSElement extends AHudElement {

    private final CPSCalculator cpsCalculator;

    public CPSElement(HudElement cps) {
        this.elementX = cps.x();
        this.elementY = cps.y();
        this.elementWidth = cps.width();
        this.elementHeight = cps.height();
        this.color = cps.color();
        this.backgroundColor = cps.backgroundColor();
        this.shadow = cps.shadow();
        cpsCalculator = new CPSCalculator();
        cpsCalculator.register();
    }

    @Override
    public void drawElement(DrawContext drawContext, ClientPlayerEntity player) {
        drawContext.fill(elementX, elementY, elementX + elementWidth, elementY + elementHeight, backgroundColor);
        drawContext.drawText(client.textRenderer,  cpsCalculator.getCPS() + " CPS", elementX + 5, elementY + 5, color, shadow);
    }

}
