package simple_game;

public class Main {
	
	public static void main(String[] args) {
		SimpleGame sg = new SimpleGame(10);
		ControleurSimpleGame gc = new ControleurSimpleGame(sg);
	}
}
