package pacman;

public class Main {

    //static String choixLayout = "./layouts/smallClassic.lay"; // Vous pouvez tester ici en choisissant parmis
    static String choixLayout = "./layouts/mediumClassic.lay"; // Vous pouvez tester ici en choisissant parmis

    public static void main(String[] args) {
        String mazePath = choixLayout;
        // Cette partie sera gérée depuis les UI Swing
        // Keyboard ou Random
        PacmanGame pmg = new PacmanGame(300, mazePath);
        ControleurPacmanGame cpmg = new ControleurPacmanGame(pmg);
    }

}
