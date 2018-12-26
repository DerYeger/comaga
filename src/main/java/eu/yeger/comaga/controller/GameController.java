package eu.yeger.comaga.controller;

import eu.yeger.comaga.model.Field;
import eu.yeger.comaga.model.Game;
import eu.yeger.comaga.model.Grid;
import eu.yeger.comaga.model.Model;

import java.util.Random;

public class GameController {

    private static final String DEFAULT_COLOR = "#333333";

    private static final String[] COLORS = {"Green", "Red", "Blue", "Orange", "Yellow"};

    public void initGame(final int width, final int height) {
        Game game = Model.getInstance().getGame();

        Grid grid = new Grid();

        grid.setGame(game)
                .setWidth(width)
                .setHeight(height);

        generateFields();

        generateStartSetup();
    }

    private void generateFields() {
        Grid grid = Model.getInstance().getGame().getGrid();
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                new Field().setColor("PLACEHOLDER")
                        .setGrid(grid)
                        .setXPos(x)
                        .setYPos(y);
            }
        }
    }

    private void generateStartSetup() {
        Grid grid = Model.getInstance().getGame().getGrid();

        int halfHeight = grid.getHeight() / 2;

        //fills bottom half of grid with random colors
        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < halfHeight - 1; y++) {
                Field field = grid.getFields().get(x + y * grid.getWidth());
                field.setOccupied(true)
                        .setColor(getRandomColor());
            }
        }

        Random random = new Random(System.currentTimeMillis());
        for (int x = 0; x < grid.getWidth(); x++) {
            if (random.nextBoolean()) {
                Field field = grid.getFields().get(x + (halfHeight - 1) * grid.getWidth());
                field.setOccupied(true).setColor(getRandomColor());
            }
        }
    }

    private String getRandomColor() {
        Random random = new Random(System.currentTimeMillis());
        return COLORS[random.nextInt(COLORS.length - 1)];
    }

}
