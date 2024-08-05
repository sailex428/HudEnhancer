package io.sailex.config;

/**
 * Configuration data for a HUD element.
 *
 * @author sailex
 */
public record ConfigElement(
        int x,
        int y,
        int width,
        int height,
        int color,
        int hue,
        boolean background,
        boolean shadow,
        boolean isActive
) {}
