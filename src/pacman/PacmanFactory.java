package pacman;

public class PacmanFactory implements AgentFactory{
    @Override
    public Agent createAgent(PositionAgent _pos) {
        return new Pacman(_pos);
    }
}
