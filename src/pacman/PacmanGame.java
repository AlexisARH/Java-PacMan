package pacman;
import engine.Game;

import java.util.ArrayList;

public class PacmanGame extends Game{

    private String mazePath;
    private static Maze maze;
    private ArrayList<Agent> agentList;
    
    int edible;
    int eaten;
    int capsuleTimer;
    int capsuleTime;
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
            this.edible = 0;
            this.eaten = 0;
            this.capsuleTimer = 0;
            this.capsuleTime = 20;
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
                    if (maze.isFood(i, j) || maze.isCapsule(i, j)){
                        this.edible++;
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
                return (a.isAlive() && this.eaten < this.edible);
            }
        }
        System.out.println("Tous les pacmans sont mort");
        return false;
    }

    @Override
    public void takeTurn() {
        this.capsuleTimer--;
        if (capsuleTimer==0) {
            this.capsuleActivated = false;
            this.capsuleToggle(false);
            System.out.println("Capsule désactivée");
        }

        for(Agent agent : this.agentList){
            AgentAction action = agent.getStrategy().action(this);
            try{
                if(isLegalMove(agent, action)) {
                    moveAgent(agent, action);
                }
            } catch (Exception e){
                System.out.println(e.toString());
            }

            int x = agent.getXy().getX();
            int y = agent.getXy().getY();

            if(agent instanceof Pacman){
                if(maze.isFood(x, y)){
                    maze.setFood(x, y, false);
                    // TODO: Food mechanics
                    eaten++;
                }
                if(maze.isCapsule(x, y)){
                    maze.setCapsule(x, y, false);
                    this.capsuleTimer += this.capsuleTime;
                    this.capsuleActivated = true;
                    this.capsuleToggle(true);
                    System.out.println("Capsule activée");
                }
                for(Agent ghost : this.agentList){
                    if(ghost instanceof Ghost){
                        if(agent.getXy().equals(ghost.getXy())){
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

    public static boolean isLegalMove(Agent agent, AgentAction action){
        int x = agent.getXy().getX() + action.get_vx();
        int y = agent.getXy().getY() + action.get_vy();
        return !maze.isWall(x, y);
    }

    private void moveAgent(Agent agent, AgentAction action){
        int x = agent.getXy().getX() + action.get_vx();
        int y = agent.getXy().getY() + action.get_vy();
        agent.getXy().setX(x);
        agent.getXy().setY(y);
        agent.getXy().setDir(action.get_direction());
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
            System.out.println(a.getXy());
            System.out.println(a.getStrategy());
            System.out.println("\n");
        }
        return agentList;
    }
}
