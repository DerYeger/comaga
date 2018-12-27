package eu.yeger.comaga.controller;

import eu.yeger.comaga.model.Field;

import javafx.fxml.FXML;
import javafx.scene.paint.Paint;
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
    }

    private void addListeners() {
        field.addPropertyChangeListener(Field.PROPERTY_color, evt -> updateColor());
        field.addPropertyChangeListener(Field.PROPERTY_selected, evt -> updateSelectedIndicator());
        field.addPropertyChangeListener(Field.PROPERTY_highlighted, evt -> updateHighlightIndicator());
    }

    private void updateColor() {
        //TODO Fix original implementation
        //colorCircle.setFill(Paint.valueOf(field.getColor()));
        colorCircle.setStyle("-fx-fill: " + field.getColor());
    }

    private void updateSelectedIndicator() {
        selectionRectangle.setVisible(field.getSelected());
    }

    private void updateHighlightIndicator() {
        highlightRectangle.setVisible(field.getHighlighted());
    }
}
