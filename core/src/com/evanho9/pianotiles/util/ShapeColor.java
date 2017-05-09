package com.evanho9.pianotiles.util;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by evanh on 5/6/2017.
 */

public enum ShapeColor {
    RED(new Color(1.0f, 0.549f, 0.765f, 1f)),
    GREEN(new Color(0.549f, 1.0f, 0.565f, 1f)),
    BLUE(new Color(0.549f, 0.901f, 1.0f, 1f)),
    YELLOW(new Color(0.961f, 1.0f, 0.569f, 1f));

    public Color color;

    ShapeColor(Color color) {
        this.color = color;
    }
}
