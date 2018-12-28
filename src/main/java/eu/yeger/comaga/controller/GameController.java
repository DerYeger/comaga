package eu.yeger.comaga.controller;

import eu.yeger.comaga.model.Field;
import eu.yeger.comaga.model.Game;
import eu.yeger.comaga.model.Model;

public class GameController {

    public void initGame(final int width, final int height) {
        Game game = Model.getInstance().getGame();

        game.setWidth(width).setHeight(height)
                .addPropertyChangeListener(Game.PROPERTY_turnCount, evt -> {
                    if (game.getTurnCount() % ControllerUtilities.TURN_DURATION == ControllerUtilities.TURN_DURATION - 1) {
                        cycle();
                    }
                });

        generateFields();
        setNeighbors();
        generateStartSetup();
    }

    private void generateFields() {
        Game game = Model.getInstance().getGame();
        for (int y = 0; y < game.getHeight(); y++) {
            for (int x = 0; x < game.getWidth(); x++) {
                new Field().setColor(ControllerUtilities.DEFAULT_COLOR)
                        .setGame(game)
                        .setXPos(x)
                        .setYPos(y);
            }
        }
    }

    //TODO Optimize?
    private void setNeighbors() {
        Game game = Model.getInstance().getGame();
        for (Field field : game.getFields()) {
            int xPos = field.getXPos();
            int yPos = field.getYPos();
            game.getFields().stream().filter(f -> f.getXPos() == xPos && f.getYPos() == yPos + 1
                    || f.getXPos() == xPos + 1 && f.getYPos() == yPos
                    || f.getXPos() == xPos && f.getYPos() == yPos - 1
                    || f.getXPos() == xPos - 1 && f.getYPos() == yPos).forEach(f -> f.withNeighbors(field));
        }
    }

    private void generateStartSetup() {
        Game game = Model.getInstance().getGame();

        int halfHeight = (game.getHeight() + 1)/ 2;

        //fills bottom half of grid with random colors
        for (int x = 0; x < game.getWidth(); x++) {
            for (int y = 0; y < halfHeight; y++) {
                Field field = game.getFields().get(x + y * game.getWidth());
                field.setOccupied(true)
                        .setColor(ControllerUtilities.getRandomColor());
            }
        }
    }

    private void cycle() {
        //TODO end game if lift up would push fields out of bounds
        liftUp();
        spawnNewRowAtBottom();
    }

    public void turn() {
        Game game = Model.getInstance().getGame();
        game.setTurnCount(game.getTurnCount() + 1);
        //TODO implement field marking, marked field removal and call pushDown()
    }


    //pushes all bubbles as far down as possible
    private void pushDown() {
        Game game = Model.getInstance().getGame();
        for (int i = 0; i < game.getHeight() - 1; i++) {
            for (Field field : game.getFields()) {
                if (field.getOccupied()) continue;
                final int xPos = field.getXPos();
                final int yPos = field.getYPos();
                game.getFields().stream().filter(f -> f.getXPos() == xPos && f.getYPos() > yPos && f.getOccupied()).limit(1).forEach(f -> {
                    field.setOccupied(true).setColor(f.getColor());
                    f.setOccupied(false).setColor(ControllerUtilities.DEFAULT_COLOR);
                });
            }
        }
    }

    //lifts all bubbles up by one row
    //lowest row is unaffected, thus duplicated
    private void liftUp() {
        Game game = Model.getInstance().getGame();
        for (int i = game.getFields().size() - 1; i >= game.getWidth(); i--) {
            Field field = game.getFields().get(i);
            Field fieldBelow = getFieldByCoordinates(field.getXPos(), field.getYPos() - 1);
            if (fieldBelow != null) {
                field.setOccupied(fieldBelow.getOccupied())
                        .setColor(fieldBelow.getColor());
            }
        }
    }

    //spawn a new row of randomly colored bubbles at the bottom of the grid
    private void spawnNewRowAtBottom() {
        Game game = Model.getInstance().getGame();
        for (int i = 0; i < game.getWidth(); i++) {
            game.getFields().get(i).setOccupied(true).setColor(ControllerUtilities.getRandomColor());
        }
    }

    //returns the field with given coordinates or null if coordinates are out of bounds
    private Field getFieldByCoordinates(final int xPos, final int yPos) {
        Game game = Model.getInstance().getGame();
        int index = xPos + game.getWidth() * yPos;
        if (index >= game.getFields().size() || index < 0) return null;
        return game.getFields().get(index);
    }
}
