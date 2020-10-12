import java.util.Observable;

public abstract class Game extends Observable implements Runnable {
	private int turn;
	private int maxturn;
	private boolean isRunning;
	private boolean gameOver;
	private Thread thread;
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
		this.gameOver = false;
		this.initializeGame();
	};
	
	public void step() {
		this.turn++;
		if(this.gameContinue() && this.turn <= this.maxturn) {
			this.takeTurn();
		} else {
			this.isRunning = false;
			this.gameOver();
		}
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
		if(this.isRunning){
			notifyObservers("Reprise de la partie");
		} else {
			notifyObservers("Partie en pause");
		}
	}
	
	public void launch() {
		this.isRunning = true;
		this.thread = new Thread(this);
		this.thread.start();
	}

	public void setTime(long _time){
		this.time = 1000 / _time;
	}
	
	public abstract void initializeGame();
	
	public abstract boolean gameContinue();
	
	public abstract void takeTurn();
	
	public abstract void gameOver();
	
}
