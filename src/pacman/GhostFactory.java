package pacman;

public class GhostFactory implements AgentFactory{
    @Override
    public Agent createAgent(PositionAgent _pos) {
        return new Ghost(_pos);
    }
}
