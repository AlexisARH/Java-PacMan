package simple_game;

import engine.Game;
import engine.InterfaceControleur;
import engine.ViewCommand;

public class ControleurSimpleGame implements InterfaceControleur{

    private Game game;

    public ControleurSimpleGame(Game _game){
        this.game = _game;
        ViewCommand vc = new ViewCommand(this.game, this);
        ViewSimpleGame vsg = new ViewSimpleGame(this.game);
    }

    @Override
    public void start() {
        this.game.init();
    }

    @Override
    public void step() {
        this.game.step();
    }

    @Override
    public void run() {
        this.game.launch();
    }

    @Override
    public void pause() {
        this.game.pause();
    }

    @Override
    public void setTime(long _time) {
        this.game.setTime(_time);
    }
}
