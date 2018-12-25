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
        game.buildAttribute("score", ClassModelBuilder.INT);

        grid.buildAttribute("width", ClassModelBuilder.INT);
        grid.buildAttribute("height", ClassModelBuilder.INT);

        field.buildAttribute("occupied", ClassModelBuilder.BOOLEAN);
        field.buildAttribute("color", ClassModelBuilder.STRING);


        //associations
        game.buildAssociation(grid, "grid", ClassModelBuilder.ONE, "game", ClassModelBuilder.ONE);

        grid.buildAssociation(field, "fields", ClassModelBuilder.MANY, "grid", ClassModelBuilder.ONE);

        field.buildAssociation(field, "neighbors", ClassModelBuilder.MANY, "neighbors", ClassModelBuilder.MANY);


        //generator
        ClassModel model = mb.getClassModel();
        Fulib.generator().generate(model);
    }

}
