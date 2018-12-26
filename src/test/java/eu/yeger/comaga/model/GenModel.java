package eu.yeger.comaga.model;

import org.fulib.Fulib;
import org.fulib.builder.ClassBuilder;
import org.fulib.builder.ClassModelBuilder;
import org.fulib.classmodel.ClassModel;

public class GenModel {

    public static void main(String[] args) {
        ClassModelBuilder mb = Fulib.classModelBuilder("eu.yeger.comaga.model", "src/main/java");

        //classes
        ClassBuilder game = mb.buildClass("Game");
        ClassBuilder grid = mb.buildClass("Grid");
        ClassBuilder field = mb.buildClass("Field");


        //attributes
        game.buildAttribute("score", ClassModelBuilder.INT, "0");

        grid.buildAttribute("width", ClassModelBuilder.INT, "8");
        grid.buildAttribute("height", ClassModelBuilder.INT, "20");

        field.buildAttribute("occupied", ClassModelBuilder.BOOLEAN, "false");
        field.buildAttribute("selected", ClassModelBuilder.BOOLEAN, "false");
        field.buildAttribute("highlighted", ClassModelBuilder.BOOLEAN, "false");
        field.buildAttribute("color", ClassModelBuilder.STRING, "#333333");
        field.buildAttribute("xPos", ClassModelBuilder.INT);
        field.buildAttribute("yPos", ClassModelBuilder.INT);


        //associations
        game.buildAssociation(grid, "grid", ClassModelBuilder.ONE, "game", ClassModelBuilder.ONE);

        grid.buildAssociation(field, "fields", ClassModelBuilder.MANY, "grid", ClassModelBuilder.ONE);

        field.buildAssociation(field, "neighbors", ClassModelBuilder.MANY, "neighbors", ClassModelBuilder.MANY);


        //generator
        ClassModel model = mb.getClassModel();
        Fulib.generator().generate(model);
    }

}
