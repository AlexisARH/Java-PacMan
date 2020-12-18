package pacman;

public class Ghost extends Agent{

    Strategy flee;
    Strategy hunt;

    boolean isScared;

    Ghost(PositionAgent _xy) {

        super(_xy);
        this.isScared = false;
//        this.flee = new Strat_Random();
//        this.hunt = new Strat_AStar(this);
//        updateStrategy();
    }

    public void setScared(boolean b){
        this.isScared = b;
//        updateStrategy();
    }

    private void updateStrategy(){
        if(this.isScared){
            this.setStrategy(flee);
            System.out.println("Ghosts are now scared !");
        } else {
            this.setStrategy(hunt);
            System.out.println("Be careful, the ghosts are hunting !");
        }
    }
}
