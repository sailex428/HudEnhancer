package io.sailex.config;

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
