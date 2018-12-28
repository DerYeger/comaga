package eu.yeger.comaga.model;

public class Model {

    private static Model model;

    private Game game;

    private Model() {
        game = new Game();
    }

    public static Model getInstance() {
        if (model == null) model = new Model();
        return model;
    }

    public Game getGame() {
        return game;
    }

    public static void reset() {
        model = null;
    }
}
