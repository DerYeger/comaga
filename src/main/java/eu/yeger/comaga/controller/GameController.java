package eu.yeger.comaga.controller;

import eu.yeger.comaga.model.Field;
import eu.yeger.comaga.model.Game;
import eu.yeger.comaga.model.Grid;
import eu.yeger.comaga.model.Model;

import java.util.Random;

public class GameController {

    private static final String DEFAULT_COLOR = "#444444";

    //blue, green, yellow, orange, red
    private static final String[] COLORS = {"#0000ff", "#008000", "#ffff00", "#ffa500", "#ff0000"};

    private Random random;

    public void initGame(final int width, final int height) {
        Game game = Model.getInstance().getGame();

        Grid grid = new Grid();

        grid.setGame(game)
                .setWidth(width)
                .setHeight(height);

        random = new Random(System.currentTimeMillis());

        generateFields();

        generateStartSetup();
    }

    private void generateFields() {
        Grid grid = Model.getInstance().getGame().getGrid();
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                new Field().setColor(DEFAULT_COLOR)
                        .setGrid(grid)
                        .setXPos(x)
                        .setYPos(y);
            }
        }
    }

    private void generateStartSetup() {
        Grid grid = Model.getInstance().getGame().getGrid();

        int halfHeight = (grid.getHeight() + 1)/ 2;

        //fills bottom half of grid with random colors
        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < halfHeight; y++) {
                Field field = grid.getFields().get(x + y * grid.getWidth());
                field.setOccupied(true)
                        .setColor(getRandomColor());
            }
        }
    }

    private String getRandomColor() {
        return COLORS[random.nextInt(COLORS.length)];
    }

}
