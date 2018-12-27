package eu.yeger.comaga.controller;

import eu.yeger.comaga.model.Field;
import eu.yeger.comaga.model.Game;
import eu.yeger.comaga.model.Grid;
import eu.yeger.comaga.model.Model;

import java.util.Random;

public class GameController {

    private static final int TURN_DURATION = 3;

    private static final String DEFAULT_COLOR = "#444444";

    //purple, blue, green, yellow, orange, red
    private static final String[] COLORS = {"#800080", "#0000ff", "#008000", "#ffff00", "#ffa500", "#ff0000"};

    private static GameController gameController;

    private int turnCounter = 0;

    private GameController() {

    }

    public static GameController getGameControllerInstance() {
        if (gameController == null) gameController = new GameController();
        return gameController;
    }

    private Random random;

    public void initGame(final int width, final int height) {
        Game game = Model.getInstance().getGame();

        Grid grid = new Grid();

        grid.setGame(game)
                .setWidth(width)
                .setHeight(height);

        random = new Random(System.currentTimeMillis());

        generateFields();

        setNeighbors();

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

    //TODO Optimize?
    private void setNeighbors() {
        Grid grid = Model.getInstance().getGame().getGrid();
        for (Field field : grid.getFields()) {
            int xPos = field.getXPos();
            int yPos = field.getYPos();
            grid.getFields().stream().filter(f -> f.getXPos() == xPos && f.getYPos() == yPos + 1
                    || f.getXPos() == xPos + 1 && f.getYPos() == yPos
                    || f.getXPos() == xPos && f.getYPos() == yPos - 1
                    || f.getXPos() == xPos - 1 && f.getYPos() == yPos).forEach(f -> f.withNeighbors(field));
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

    public void turn() {
        pushDown();
        if (turnCounter == 2) spawnNewRowAtBottom();
        turnCounter = (turnCounter + 1) % TURN_DURATION;
    }

    private void pushDown() {
        Grid grid = Model.getInstance().getGame().getGrid();
        for (int i = 0; i < grid.getHeight() - 1; i++) {
            for (Field field : grid.getFields()) {
                if (field.getOccupied()) continue;
                final int xPos = field.getXPos();
                final int yPos = field.getYPos();
                grid.getFields().stream().filter(f -> f.getXPos() == xPos && f.getYPos() > yPos && f.getOccupied()).limit(1).forEach(f -> {
                    field.setOccupied(true).setColor(f.getColor());
                    f.setOccupied(false).setColor(DEFAULT_COLOR);
                });
            }
        }
    }

    private void liftUp() {
        Grid grid = Model.getInstance().getGame().getGrid();
        for (int i = grid.getFields().size() - 1; i >= grid.getWidth(); i--) {
            Field field = grid.getFields().get(i);
            final int xPos = field.getXPos();
            final int yPos = field.getYPos();
            //set color and occupancy of field to that of the field below it
            grid.getFields().stream().filter(f -> f.getXPos() == xPos && f.getYPos() == yPos - 1).limit(1).forEach(f -> {
                field.setOccupied(f.getOccupied()).setColor(f.getColor());
            });
        }
    }

    private void spawnNewRowAtBottom() {
        liftUp();
        Grid grid = Model.getInstance().getGame().getGrid();
        for (int i = 0; i < grid.getWidth(); i++) {
            grid.getFields().get(i).setOccupied(true).setColor(getRandomColor());
        }
    }
}
