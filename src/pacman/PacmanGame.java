package pacman;
import engine.Game;

import java.util.ArrayList;

public class PacmanGame extends Game{

    private String mazePath;
    private static Maze maze;
    private ArrayList<Agent> agentList;
    
    int itemMangeable;
    int nbMange;
    int capsuleChrono;
    int capsuleTempsActivation;
    boolean capsuleActivated;

    public PacmanGame(int _maxturn, String _mazePath) {
        super(_maxturn);
        this.mazePath = _mazePath;
        initializeGame();
    }

    @Override
    public void initializeGame() {
        try {
            // Initialisations de variables
            this.itemMangeable = 0;
            this.nbMange = 0;
            this.capsuleChrono = 0;
            this.capsuleTempsActivation = 20;
            this.capsuleActivated = false;

            // Création du labyrinthe
            maze = new Maze(this.mazePath);

            // Ajout des agents : PacMan et les Fantômes
            // Design Pattern : Abstract Factory
            this.agentList = new ArrayList<>();

            AgentFactory agentFactoryPM = FactoryProvider.getFactory(TypeAgent.PACMAN);
            for(PositionAgent pos : maze.getPacman_start()) {
                this.agentList.add(agentFactoryPM.createAgent(pos));
            }

            AgentFactory agentFactoryG = FactoryProvider.getFactory(TypeAgent.GHOST);
            for(PositionAgent pos : maze.getGhosts_start()) {
                this.agentList.add(agentFactoryG.createAgent(pos));
            }
            this.capsuleToggle(false);
            System.out.println("Capsule désactivée");

            // Compteur de comestibles
            int mazeSizeX = maze.getSizeX();
            int mazeSizeY = maze.getSizeY();
            for(int i=0; i<mazeSizeX; i++){
                for(int j=0; j<mazeSizeY; j++){
                    if (maze.isNourriture(i, j) || maze.isCapsule(i, j)){
                        this.itemMangeable++;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean gameContinue() {
        for (Agent a : agentList){
            if (a instanceof Pacman){
                return (a.isLiving() && this.nbMange < this.itemMangeable);
            }
        }
        System.out.println("Tous les pacmans sont mort");
        return false;
    }

    @Override
    public void takeTurn() {
        this.capsuleChrono--;
        if (capsuleChrono==0) {
            this.capsuleActivated = false;
            this.capsuleToggle(false);
            System.out.println("Capsule désactivée");
        }

        for(Agent agent : this.agentList){
            AgentAction action = agent.getStrategy().action(this);
            try{
                if(detectionIncoherenceDeplacement(agent, action)) {
                    moveAgent(agent, action);
                }
            } catch (Exception e){
                System.out.println(e.toString());
            }

            int x = agent.getCoord().getX();
            int y = agent.getCoord().getY();

            if(agent instanceof Pacman){
                if(maze.isNourriture(x, y)){
                    maze.setFood(x, y, false);
                    nbMange++;
                }
                if(maze.isCapsule(x, y)){
                    maze.setCapsule(x, y, false);
                    this.capsuleChrono += this.capsuleTempsActivation;
                    this.capsuleActivated = true;
                    this.capsuleToggle(true);
                    System.out.println("Capsule activée");
                }
                for(Agent ghost : this.agentList){
                    if(ghost instanceof Ghost){
                        if(agent.getCoord().equals(ghost.getCoord())){
                            if(this.capsuleActivated){
                                ghost.Die();
                                System.out.println("-1 Pac-gum");
                            } else {
                                agent.Die();
                                System.out.println("-1 Pac-man");
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void gameOver() {
        System.out.println("Game Over !");
    }

    public static boolean detectionIncoherenceDeplacement(Agent agent, AgentAction action){
        int x = agent.getCoord().getX() + action.get_vx();
        int y = agent.getCoord().getY() + action.get_vy();
        return !maze.detecteWall(x, y);
    }
	static Boolean detectionIncoherenceDeplacement(PositionAgent agent, AgentAction action) {
		int x = agent.getX() + action.get_vx();
		int y = agent.getY() + action.get_vy();
		if(maze.detecteWall(x, y)) return false;
		return true;
    }
    private void moveAgent(Agent agent, AgentAction action){
        int x = agent.getCoord().getX() + action.get_vx();
        int y = agent.getCoord().getY() + action.get_vy();
        agent.getCoord().setX(x);
        agent.getCoord().setY(y);
        agent.getCoord().setDir(action.get_direction());
    }

    private void capsuleToggle(boolean state){
        for (Agent agent : this.agentList){
            if (agent instanceof Ghost){
                ((Ghost) agent).setScared(true);
            }
        }
    }

    public Maze getMaze() {
        return maze;
    }

    public void setMaze(Maze _maze) {
        maze = _maze;
    }

    public ArrayList<Agent> getAgents() {
        for(Agent a : agentList){
            System.out.println(a.getClass());
            System.out.println(a.getCoord());
            System.out.println(a.getStrategy());
            System.out.println("\n");
        }
        return agentList;
    }
}
