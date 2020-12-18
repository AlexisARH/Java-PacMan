package pacman;

import engine.InterfaceControleur;
import engine.ViewCommand;

public class ControleurPacmanGame implements InterfaceControleur{

    private PacmanGame game;
    private ViewCommand vc;
    private ViewPacmanGame vpcg;


    public ControleurPacmanGame(PacmanGame _game){
        this.game = _game;
        this.vc = new ViewCommand(this.game, this);
        this.vpcg = new ViewPacmanGame(this.game);
        this.game.addObserver(vc);
        this.game.addObserver(vpcg);
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
