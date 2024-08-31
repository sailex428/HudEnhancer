package io.sailex.gui.hud.elements;

import io.sailex.config.ConfigElement;
import io.sailex.gui.hud.AHudElement;
import io.sailex.util.Direction;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.world.biome.Biome;

import java.util.Optional;

/**
 * HUD element that shows current position, biome and direction of the player.
 *
 * @author sailex
 */
public class PositionElement extends AHudElement {

    /**
     * Constructs a PositionElement with the given config element.
     *
     * @param key name of the element
     * @param posDisplay config element
     */
    public PositionElement(String key, ConfigElement posDisplay) {
        super(key, posDisplay);
    }

    /**
     * Draws the position element on the screen.
     */
    @Override
    public void drawElement(DrawContext context, ClientPlayerEntity player) {
        drawElementBackground(context);
        String[] textContent = createTextContent(player);

        for (int i = 0; i < textContent.length; i++) {
            this.drawText(context, textContent[i], elementX + 5, elementY + 5 + (i * 10));
        }
        this.drawText(context, getDirection(player), elementX + 105, elementY + 5);
    }

    /**
     * Creates the text content to be displayed in the position element.
     *
     * @param player the client player entity
     * @return an array of strings representing the text content
     */
    private String[] createTextContent(ClientPlayerEntity player) {
        return new String[] {
                "X " + Math.round(player.getX() * 10.0) / 10.0,
                "Y " + Math.round(player.getY() * 10.0) / 10.0,
                "Z " + Math.round(player.getZ() * 10.0) / 10.0,
                "Biome: " + getBiome(player)
        };
    }

    /**
     * Retrieves the name of the biome the player is currently in.
     *
     * @param player the client player entity
     * @return the formattedBiome biome name
     */
    private String getBiome(ClientPlayerEntity player) {
        Optional<RegistryKey<Biome>> biomeRegistry = player.getWorld().getBiome(player.getBlockPos()).getKey();
        if (biomeRegistry.isPresent()) {
            String biome = biomeRegistry.get().getValue().getPath();
            return formatBiomeName(biome);
        }
        return "";
    }

    /**
     * Formats the biome name to fit the HUD element.
     * @param biome the biome name
     * @return the formattedBiome biome name
     */
    private String formatBiomeName(String biome) {
        int lengthSum = 0;
        String[] biomeParts = biome.split("_");
        StringBuilder formattedBiome = new StringBuilder();

        for (int i = 0; i < biomeParts.length; i++) {
            lengthSum += biomeParts[i].length();

            if (lengthSum > 14) {
                if (i == 1) {
                    return biomeParts[1];
                } else if (i >= 2) {
                    return biomeParts[i - 1] + " " + biomeParts[i];
                }
            }

            formattedBiome.append(biomeParts[i]);
            if (i < biomeParts.length - 1) {
                formattedBiome.append(" ");
            }
        }

        return formattedBiome.toString();
        }

    /**
     * Determines the direction the player is facing.
     *
     * @param player the client player entity
     * @return the abbreviation of the direction
     */
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