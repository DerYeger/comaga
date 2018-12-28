package eu.yeger.comaga.controller;

import eu.yeger.comaga.model.Field;
import eu.yeger.comaga.model.Game;
import eu.yeger.comaga.model.Model;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class FieldController {

    @FXML
    private Circle colorCircle;

    @FXML
    private Rectangle selectionRectangle;

    @FXML
    private Rectangle highlightRectangle;

    private Field field;

    public void setField(final Field field) {
        this.field = field;

        updateColor();
        updateSelectedIndicator();
        updateHighlightIndicator();

        addListeners();

        addHandlers();
    }

    private void updateColor() {
        //TODO Fix original implementation
        //colorCircle.setFill(Paint.valueOf(field.getColor()));
        colorCircle.setStyle("-fx-fill: " + field.getColor());
    }

    private void updateSelectedIndicator() {
        selectionRectangle.setVisible(field.getSelectedBy() != null);
        unhighlightNeighbors();
    }

    private void updateHighlightIndicator() {
        //highlightRectangle.setVisible(field.getHighlightedBy() != null);
        highlightRectangle.setVisible(field.getHighlighted());
    }

    private void addListeners() {
        field.addPropertyChangeListener(Field.PROPERTY_color, evt -> updateColor());
        field.addPropertyChangeListener(Field.PROPERTY_selectedBy, evt -> updateSelectedIndicator());
        field.addPropertyChangeListener(Field.PROPERTY_highlighted, evt -> updateHighlightIndicator());
    }

    private void addHandlers() {
        final Game game = Model.getInstance().getGame();
        colorCircle.setOnMouseClicked(event -> {
            if (field.getOccupied()) {
                if (game.getSelectedField() == null || !game.getSelectedField().equals(field) && !field.getHighlighted()) {
                    //no field or different field selected
                    //new field is not highlighted
                    game.setSelectedField(field);
                    highlightNeighbors();
                } else if (game.getSelectedField().equals(field)){
                    //same field previously selected
                    //unselect field
                    game.setSelectedField(null);
                    unhighlightNeighbors();
                } else {
                    //user clicked on highlighted field, initiate swap
                    Field otherField = game.getSelectedField();
                    String otherColor = otherField.getColor();

                    otherField.setColor(field.getColor());
                    field.setColor(otherColor);

                    game.setSelectedField(null);

                    new GameController().turn();
                }
            }
        });
    }

    //TODO Decide if swapping two fields with same color should be allowed
    private void highlightNeighbors() {
        field.getNeighbors().stream().filter(f -> f.getOccupied() && !f.getColor().equals(field.getColor())).forEach(f -> f.setHighlighted(true));
    }

    private void unhighlightNeighbors() {
        field.getNeighbors().stream().forEach(f -> f.setHighlighted(false));
    }
}
