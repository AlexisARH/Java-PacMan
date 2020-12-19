package pacman;

public class Pacman extends Agent{
    Pacman(PositionAgent _xy) {
        super(_xy);
        this.setStrategy(new KeyboardStrategy(this));
        // this.setStrategy(new AStrategy(this));
        // this.setStrategy(new RandomStrategy(this));
    }
}
