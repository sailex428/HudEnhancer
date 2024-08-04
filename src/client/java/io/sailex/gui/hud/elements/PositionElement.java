package io.sailex.gui.hud.elements;

import io.sailex.config.ConfigElement;
import io.sailex.gui.hud.AHudElement;
import io.sailex.util.Direction;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.Optional;

public class PositionElement extends AHudElement {

    public PositionElement(String key, ConfigElement posDisplay) {
        super(key, posDisplay);
    }

    @Override
    public void drawElement(DrawContext context, ClientPlayerEntity player) {
        context.fill(elementX, elementY, elementX + elementWidth, elementY + elementHeight, background ? BACKGROUND_GRAY : BACKGROUND_TRANSPARENT);
        String[] textContent = createTextContent(player);

        for (int i = 0; i < textContent.length; i++) {
            context.drawText(client.textRenderer, textContent[i], elementX + 5, elementY + 5 + (i * 10), color, shadow);
        }
        context.drawText(client.textRenderer, getDirection(player), elementX + 105, elementY + 5, color, shadow);
    }

    private String[] createTextContent(ClientPlayerEntity player) {
        return new String[] {
                "X " + Math.round(player.getX() * 10.0) / 10.0,
                "Y " + Math.round(player.getY() * 10.0) / 10.0,
                "Z " + Math.round(player.getZ() * 10.0) / 10.0,
                "Biome: " + getBiome(player)
        };
    }

    private String getBiome(ClientPlayerEntity player) {
        Optional<RegistryKey<Biome>> biomeRegistry = player.getWorld().getBiome(player.getBlockPos()).getKey();
        if (biomeRegistry.isPresent()) {
            String biome = biomeRegistry.get().getValue().getPath();
            return formatBiomeName(biome);
        }
        return "";
    }

    private String formatBiomeName(String biome) {
        if (biome.length() > 16) {
            biome = biome.substring(0, 16);
        }
        return biome.replaceAll("_", " ");
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