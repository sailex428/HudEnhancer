package io.sailex.hud.elements;

import io.sailex.hud.util.AHudElement;
import io.sailex.hud.util.Direction;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.Optional;

public class PositionDisplay extends AHudElement {

    public PositionDisplay(int elementX, int elementY, int elementWidth, int elementHeight) {
        this.elementX = elementX;
        this.elementY = elementY;
        this.elementWidth = elementWidth;
        this.elementHeight = elementHeight;
    }

    @Override
    public void drawElement(DrawContext drawContext, ClientPlayerEntity player) {
        int textColor = 0xFFFFFF;

        drawContext.fill(elementX, elementY, elementX + elementWidth, elementY + elementHeight, 0x80000000);
        String[] texts = {
                "X " + Math.round(player.getX() * 10.0) / 10.0,
                "Y " + Math.round(player.getY() * 10.0) / 10.0,
                "Z " + Math.round(player.getZ() * 10.0) / 10.0,
                "Biome: " + getBiome(player)
        };
        for (int i = 0; i < texts.length; i++) {
            drawContext.drawText(client.textRenderer, texts[i], elementX + 5, elementY + 5 + (i * 10), textColor, true);
        }
        drawContext.drawText(client.textRenderer, getDirection(player), elementX + 5 + 100, elementY + 5, textColor, true);
    }

    private String getBiome(ClientPlayerEntity player) {
        Optional<RegistryKey<Biome>> biomeRegistry = player.getWorld().getBiome(player.getBlockPos()).getKey();
        if (biomeRegistry.isPresent()) {
            String biome = biomeRegistry.get().getValue().getPath();
            return biome.replaceAll("_", " ");
        }
        return "";
    }

    private String getDirection(ClientPlayerEntity player) {
        int yaw = Math.round(player.getYaw());
        int directionLength = Direction.values().length;
        int part = 360 / directionLength;

        yaw = (yaw % 360 + 360) % 360;

        int index = (yaw + part / 2) / part;
        if (index == directionLength) index = 0;

        return Direction.values()[index].getAbbreviation();
    }

}