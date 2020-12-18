package simple_game;

import engine.Game;

public class SimpleGame extends Game {
	public SimpleGame(int _maxturn) {
		super(_maxturn);
	}
	
	@Override
	public void initializeGame() {
		setChanged();
		notifyObservers("Initialisation de la partie");
	}
	
	@Override
	public boolean gameContinue() {
		return true;
	}
	
	@Override
	public void takeTurn() {
		setChanged();
		notifyObservers("Tour " + this.getTurn() + " du jeu en cours");
	}
	
	@Override
	public void gameOver() {
		setChanged();
		notifyObservers("Game Over...");
	}	
}
