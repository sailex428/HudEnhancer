package io.sailex.config;

public record HudElement(
        int x,
        int y,
        int width,
        int height,
        int color,
        int backgroundColor,
        boolean shadow
) {}
