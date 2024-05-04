package io.sailex.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.Optional;

public class PositionDisplay implements HudRenderCallback {

    private final MinecraftClient client = MinecraftClient.getInstance();

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        ClientPlayerEntity player = client.player;
        if (player == null || client.getDebugHud().shouldShowDebugHud()) {
            return;
        }
        int hudBegin = 7;
        int textBegin = hudBegin + 5;
        int textColor = 0xFFFFFF;

        MatrixStack matrices = drawContext.getMatrices();
        matrices.push();
        matrices.scale(0.8F, 0.8F, 0.8F);

        drawContext.fill(hudBegin, hudBegin, 130, 55, 0x80000000);
        String[] texts = {
                "X " + Math.round(player.getX() * 10.0) / 10.0,
                "Z " + Math.round(player.getZ() * 10.0) / 10.0,
                "Y " + Math.round(player.getY() * 10.0) / 10.0,
                "Biome: " + getBiome(player)
        };
        for (int i = 0; i < texts.length; i++) {
            drawContext.drawText(client.textRenderer, texts[i], textBegin, textBegin + (i * 10), textColor, true);
        }
        drawContext.drawText(client.textRenderer, getDirection(player), textBegin + 100, textBegin, textColor, true);
        matrices.pop();
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