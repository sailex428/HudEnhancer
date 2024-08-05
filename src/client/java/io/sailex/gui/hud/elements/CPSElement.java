package io.sailex.gui.hud.elements;

import io.sailex.config.ConfigElement;
import io.sailex.config.DefaultConfig;
import io.sailex.gui.hud.AHudElement;
import io.sailex.util.CPSCalculator;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;

/**
 * HUD element that shows current CPS.
 *
 * @author sailex
 */
public class CPSElement extends AHudElement {

    /**
     * Constructs a CPSElement with the given config element.
     *
     * @param key name of the element
     * @param cps config element
     */
    public CPSElement(String key, ConfigElement cps) {
        super(key, cps);
    }


    /**
     * Draws the CPS in the HUD.
     */
    @Override
    public void drawElement(DrawContext context, ClientPlayerEntity player) {
        context.fill(elementX, elementY,
                elementX + elementWidth, elementY + elementHeight,
                background ? BACKGROUND_GRAY : BACKGROUND_TRANSPARENT
        );
        context.drawText(client.textRenderer,  CPSCalculator.getInstance().getCPS() + " " + DefaultConfig.CPS,
                elementX + 5, elementY + 5,
                color, shadow
        );
    }

}
