package eu.yeger.comaga.controller;

import eu.yeger.comaga.model.Field;
import eu.yeger.comaga.model.Game;
import eu.yeger.comaga.model.Model;
import eu.yeger.comaga.view.ViewBuilder;
import javafx.stage.Stage;

public class GameController {

    private Stage stage;

    public void initGame(Stage stage, final int width, final int height) {
        this.stage = stage;

        Model.getInstance().reset();

        Game game = Model.getInstance().getGame();

        game.setWidth(width).setHeight(height)
                .addPropertyChangeListener(Game.PROPERTY_turnCount, evt -> {
                    if (game.getTurnCount() % ControllerUtilities.TURN_DURATION == ControllerUtilities.TURN_DURATION - 1) {
                        executeTurnCycle();
                    }
                });

        generateFields();
        setNeighbors();
        generateStartSetup();

        removeAndScore();

        game.setScore(0);
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

    private void executeTurnCycle() {
        if (isGameOver()) {
            stage.setScene(ViewBuilder.buildGameOverScreenScene(stage));
        }
        liftUp();
        spawnNewRowAtBottom();
        removeAndScore();
    }

    public void turn() {
        Model.getInstance().getGame().setTurnCount((Model.getInstance().getGame().getTurnCount() + 1) % ControllerUtilities.TURN_DURATION);
        removeAndScore();
    }

    private void removeAndScore() {
        boolean removed = true;
        while (removed) {
            removed = removeFieldsAndScore();
            pushDown();
        }
    }

    private boolean isGameOver() {
        removeAndScore();
        Game game = Model.getInstance().getGame();
        return game.getFields().stream().anyMatch(field -> field.getOccupied() && field.getYPos() == game.getHeight() - 1);
    }

    //pushes all bubbles as far down as possible
    private boolean pushDown() {
        boolean pushed = false;
        Game game = Model.getInstance().getGame();
        for (int i = 0; i < game.getHeight() - 1; i++) {
            for (Field field : game.getFields()) {
                if (field.getOccupied()) continue;
                final int xPos = field.getXPos();
                final int yPos = field.getYPos();
                pushed = game.getFields().stream().anyMatch(f -> f.getXPos() == xPos && f.getYPos() > yPos && f.getOccupied());
                game.getFields().stream().filter(f -> f.getXPos() == xPos && f.getYPos() > yPos && f.getOccupied()).limit(1).forEach(f -> {
                    field.setOccupied(true).setColor(f.getColor());
                    f.setOccupied(false).setColor(ControllerUtilities.DEFAULT_COLOR);
                });
            }
        }
        return pushed;
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
        if (xPos >= game.getWidth() || yPos >= game.getHeight() || xPos < 0 || yPos < 0) return null;
        int index = xPos + game.getWidth() * yPos;
        return game.getFields().get(index);
    }

    //mark all fields that are part of a chain with a minimum length of 3 for removal
    private void markFieldsForRemoval() {
        for (Field field : Model.getInstance().getGame().getFields()) {
            if (!field.getOccupied() || field.getMarked()) continue;
            int xPos = field.getXPos();
            int yPos = field.getYPos();
            String color = field.getColor();

            //get adjacent fields
            Field topField = getFieldByCoordinates(xPos, yPos + 1);
            Field rightField = getFieldByCoordinates(xPos + 1, yPos);
            Field bottomField = getFieldByCoordinates(xPos, yPos - 1);
            Field leftField = getFieldByCoordinates(xPos - 1, yPos);

            Field secondTopField = getFieldByCoordinates(xPos, yPos + 2);
            Field secondRightField = getFieldByCoordinates(xPos + 2, yPos);
            Field secondBottomField = getFieldByCoordinates(xPos, yPos - 2);
            Field secondLeftField = getFieldByCoordinates(xPos - 2, yPos);

            if (topField != null && bottomField != null && topField.getColor().equals(color) && bottomField.getColor().equals(color)) { //field is inner part of vertical chain
                field.setMarked(true);
                topField.setMarked(true);
                bottomField.setMarked(true);
            } else if (rightField != null && leftField != null && rightField.getColor().equals(color) && leftField.getColor().equals(color)) { //field is inner part of horizontal chain
                field.setMarked(true);
                rightField.setMarked(true);
                leftField.setMarked(true);
            } else if (bottomField != null && secondBottomField != null && bottomField.getColor().equals(color) && secondBottomField.getColor().equals(color)) { //field is top of vertical chain
                field.setMarked(true);
                bottomField.setMarked(true);
                secondBottomField.setMarked(true);
            } else if (topField != null && secondTopField != null && topField.getColor().equals(color) && secondTopField.getColor().equals(color)) { //field is bottom of vertical chain
                field.setMarked(true);
                topField.setMarked(true);
                secondTopField.setMarked(true);
            } else if (leftField != null && secondLeftField != null && leftField.getColor().equals(color) && secondLeftField.getColor().equals(color)) { //field is right end of horizontal chain
                field.setMarked(true);
                leftField.setMarked(true);
                secondLeftField.setMarked(true);
            } else if (rightField != null && secondRightField != null && rightField.getColor().equals(color) && secondRightField.getColor().equals(color)) { //field is left end of horizontal chain
                field.setMarked(true);
                rightField.setMarked(true);
                secondRightField.setMarked(true);
            }
        }
    }

    private boolean removeFieldsAndScore() {
        markFieldsForRemoval();
        Game game = Model.getInstance().getGame();
        int score = (int) Math.pow(game.getFields().stream().filter(Field::getMarked).count(), 2) * ControllerUtilities.SCORE_MULTIPLIER;
        game.setScore(game.getScore() + score);
        game.getFields().stream().filter(Field::getMarked).forEach(field -> {
            field.setOccupied(false);
            field.setColor(ControllerUtilities.DEFAULT_COLOR);
            field.setMarked(false);
        });
        return score > 0;
    }

}
