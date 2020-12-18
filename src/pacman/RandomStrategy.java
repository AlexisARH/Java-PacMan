package pacman;

import java.util.concurrent.ThreadLocalRandom;

public class RandomStrategy implements Strategy{

    Agent agent;

    RandomStrategy(Agent _agent){
        this.agent = _agent;
    }

    @Override
    public AgentAction action(PacmanGame _pmg){
        int i = ThreadLocalRandom.current().nextInt(0,4);

        int j = (i+1)%4;
        AgentAction action = new AgentAction(j);
        while(!PacmanGame.isLegalMove(this.agent, action) && j != i){
            action = new AgentAction(j);
            j = (j+1)%4;
        }

        return action;
    }
}
