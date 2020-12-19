package engine;

import java.util.Observable;

public abstract class Game extends Observable implements Runnable {
	private int turn;
	private int maxturn;
	private boolean isRunning;
	private long time = 500;
	
	public Game(int _maxturn) {
		this.maxturn = _maxturn;
	}
	
	public int getTurn() {
		return this.turn;
	}

	public int getMaxTurn(){ return this.maxturn; }
	
	public void init() {
		this.turn = 0;
		this.isRunning = true;
		this.initializeGame();
	};
	
	public void step() {
		this.turn++;
		if(this.gameContinue() && this.turn <= this.maxturn) {
			this.takeTurn();
		} else {
			this.isRunning = false;
			this.gameOver();
			System.out.println("Game over ! " + this.turn);
		}
		setChanged();
		notifyObservers();
		System.out.println("---------");
		System.out.println("Tour " + this.turn);
		System.out.println("---------");
	}
	
	public void run() {
		while(this.isRunning) {
			this.step();
			try {
				Thread.sleep(this.time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void pause() {
		this.isRunning = !this.isRunning;
		setChanged();
		notifyObservers("Partie en pause");
	}
	
	public void launch() {
		if(!this.isRunning && this.turn > 0){
			setChanged();
			notifyObservers("Reprise de la partie");
		}
		this.isRunning = true;
		Thread thread = new Thread(this);
		thread.start();
	}

	public void setTime(long _time){
		this.time = 1000 / _time;
	}
	
	public abstract void initializeGame();
	
	public abstract boolean gameContinue();
	
	public abstract void takeTurn();
	
	public abstract void gameOver();
	
}
