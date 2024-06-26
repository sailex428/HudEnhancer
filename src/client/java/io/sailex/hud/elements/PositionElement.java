package io.sailex.hud.elements;

import io.sailex.util.AHudElement;
import io.sailex.util.Direction;
import io.sailex.util.PositionElementRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.Optional;

public class PositionElement extends AHudElement {

    private final PositionElementRenderer positionElementRenderer;

    public PositionElement(int elementX, int elementY, int elementWidth, int elementHeight) {
        this.elementX = elementX;
        this.elementY = elementY;
        this.elementWidth = elementWidth;
        this.elementHeight = elementHeight;
        this.positionElementRenderer = new PositionElementRenderer();
    }

    @Override
    public void drawElement(DrawContext drawContext, ClientPlayerEntity player) {
        positionElementRenderer.render(drawContext, player, elementX, elementY, elementWidth, elementHeight);
    }

}