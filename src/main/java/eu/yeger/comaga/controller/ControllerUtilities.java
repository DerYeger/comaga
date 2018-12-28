package eu.yeger.comaga.controller;

import java.util.Random;

class ControllerUtilities {

    static final int SCORE_MULTIPLIER = 10;

    static final int TURN_DURATION = 3;

    //dark grey
    static final String DEFAULT_COLOR = "#444444";

    //purple, blue, green, yellow, orange, red
    private static final String[] COLORS = {"#800080", "#0000ff", "#008000", "#ffff00", "#ffa500", "#ff0000"};

    private static final Random random = new Random(System.currentTimeMillis());

    private ControllerUtilities() {

    }

    static String getRandomColor() {
        return COLORS[random.nextInt(COLORS.length)];
    }
}
