package pacman;

public abstract class Agent {
    private PositionAgent xy;
    private Boolean alive;
    private Strategy strategy;

    Agent(PositionAgent _xy){
        this.xy = _xy;
        this.alive = true;
        this.strategy = new RandomStrategy(this);
    }

    public PositionAgent getXy() {
        return xy;
    }

    public void setXy(PositionAgent _xy) {
        this.xy = _xy;
    }

    public Strategy getStrategy() {
        return this.strategy;
    }

    public void setStrategy(Strategy _newStrategy){
        this.strategy = _newStrategy;
    }

    public Boolean isAlive() {
        return alive;
    }

    public void setAlive(Boolean _alive) {
        this.alive = _alive;
    }

    public void Die() {
        this.setAlive(false);
    }
}
