package pacman;

public abstract class FactoryProvider {

    public static AgentFactory getFactory(TypeAgent typeAgent){
        AgentFactory agentFactory = null;
        switch (typeAgent){
            case PACMAN:
                agentFactory = new PacmanFactory();
                break;
            case GHOST:
                agentFactory = new GhostFactory();
                break;
            default:
                break;
        }
        return agentFactory;
    }

}
