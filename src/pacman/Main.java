package pacman;

public class Main {

    public static void main(String[] args) {
        String mazePath = "./layouts/smallClassic.lay";
        PacmanGame pmg = new PacmanGame(300, mazePath);
        ControleurPacmanGame cpmg = new ControleurPacmanGame(pmg);
    }

}
