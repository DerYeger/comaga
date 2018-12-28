package eu.yeger.comaga.model;

import org.fulib.Fulib;
import org.fulib.builder.ClassBuilder;
import org.fulib.builder.ClassModelBuilder;
import org.fulib.classmodel.ClassModel;

public class GenModel {

    public static void main(String[] args) {
        //preparation
        ClassModelBuilder mb = Fulib.classModelBuilder("eu.yeger.comaga.model", "src/main/java");


        //classes
        ClassBuilder game = mb.buildClass("Game");

        ClassBuilder field = mb.buildClass("Field");


        //attributes
        game.buildAttribute("score", ClassModelBuilder.INT, "0");
        game.buildAttribute("width", ClassModelBuilder.INT);
        game.buildAttribute("height", ClassModelBuilder.INT);

        field.buildAttribute("occupied", ClassModelBuilder.BOOLEAN, "false");
        field.buildAttribute("highlighted", ClassModelBuilder.BOOLEAN, "false");
        field.buildAttribute("color", ClassModelBuilder.STRING, "\"#333333\"");
        field.buildAttribute("xPos", ClassModelBuilder.INT);
        field.buildAttribute("yPos", ClassModelBuilder.INT);


        //associations
        game.buildAssociation(field, "fields", ClassModelBuilder.MANY, "game", ClassModelBuilder.ONE);
        game.buildAssociation(field, "selectedField", ClassModelBuilder.ONE, "selectedBy", ClassModelBuilder.ONE);

        field.buildAssociation(field, "neighbors", ClassModelBuilder.MANY, "neighbors", ClassModelBuilder.MANY);


        //generator
        ClassModel model = mb.getClassModel();
        Fulib.generator().generate(model);
    }

}
