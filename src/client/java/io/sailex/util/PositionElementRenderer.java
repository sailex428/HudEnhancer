package io.sailex.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.Optional;

public class PositionElementRenderer {

    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static void render(DrawContext drawContext, ClientPlayerEntity player,
                       int elementX, int elementY, int elementWidth, int elementHeight,
                       int color, boolean background, boolean shadow, boolean isActive) {
        drawContext.fill(elementX, elementY, elementX + elementWidth, elementY + elementHeight, background ? 0x80000000 : 0x00FFFFFF);
        String[] textContent = createTextContent(player);

        for (int i = 0; i < textContent.length; i++) {
            drawContext.drawText(client.textRenderer, textContent[i], elementX + 5, elementY + 5 + (i * 10), color, shadow);
        }
        drawContext.drawText(client.textRenderer, getDirection(player), elementX + 105, elementY + 5, color, shadow);
    }

    private static String[] createTextContent(ClientPlayerEntity player) {
        return new String[] {
                "X " + Math.round(player.getX() * 10.0) / 10.0,
                "Y " + Math.round(player.getY() * 10.0) / 10.0,
                "Z " + Math.round(player.getZ() * 10.0) / 10.0,
                "Biome: " + getBiome(player)
        };
    }

    private static String getBiome(ClientPlayerEntity player) {
        Optional<RegistryKey<Biome>> biomeRegistry = player.getWorld().getBiome(player.getBlockPos()).getKey();
        if (biomeRegistry.isPresent()) {
            String biome = biomeRegistry.get().getValue().getPath();
            if (biome.length() > 16) {
                biome = biome.substring(0, 16);
            }
            return biome.replaceAll("_", " ");
        }
        return "";
    }

    private static String getDirection(ClientPlayerEntity player) {
        int yaw = Math.round(player.getYaw());
        int directionLength = Direction.values().length;
        int part = 360 / directionLength;

        yaw = (yaw % 360 + 360) % 360;

        int index = (yaw + part / 2) / part;
        if (index == directionLength) index = 0;

        return Direction.values()[index].getAbbreviation();
    }

}
