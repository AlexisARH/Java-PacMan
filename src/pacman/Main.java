package pacman;

public class Main {
    static String choixStrategie = "Keyboard"; // Vous pouvez tester ici en choisissant parmis
    // Keyboard ou Random
    static String choixLayout = "./layouts/smallClassic.lay"; // Vous pouvez tester ici en choisissant parmis

    public static void main(String[] args) {
        String mazePath = choixLayout;
        PacmanGame pmg = new PacmanGame(300, mazePath, choixStrategie);
        ControleurPacmanGame cpmg = new ControleurPacmanGame(pmg);
    }

}
