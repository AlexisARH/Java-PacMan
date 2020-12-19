package pacman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardStrategy extends JFrame implements Strategy, KeyListener {

    private Agent agent;
    private AgentAction action;
    private JLabel label;

    public KeyboardStrategy(Agent _agent) {
        this.agent = _agent;
        addKeyListener(this);
        setVisible(false);
        setSize(500, 70);

        label = new JLabel(" Choisissez votre direction grâce Z, Q, S, D");
        add(label);

        Dimension windowSize = getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2 ;
        int dy = centerPoint.y - windowSize.height / 2 + 100;
        setLocation(dx, dy);
    }

    public AgentAction action(PacmanGame _pmg) {
        requestFocusInWindow(true);
        setVisible(true);

        synchronized (this) {
            while (action != null)
            {
                try {
                    wait();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if(PacmanGame.detectionIncoherenceDeplacement(this.agent, action)){
                    return action;
                }
            }
        }
        return null;
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        switch (arg0.getKeyCode()) {
            /* Les touches de pacman */
            case KeyEvent.VK_NUMPAD8:
            case KeyEvent.VK_Z:
                action = new AgentAction(AgentAction.NORTH);
                break;
            case KeyEvent.VK_NUMPAD5:
            case KeyEvent.VK_S:
                action = new AgentAction(AgentAction.SOUTH);
                break;
            case KeyEvent.VK_NUMPAD4:
            case KeyEvent.VK_Q:
                action = new AgentAction(AgentAction.WEST);
                break;
            case KeyEvent.VK_NUMPAD6:
            case KeyEvent.VK_D:
                action = new AgentAction(AgentAction.EAST);
                break;
            default:
                action = new AgentAction(AgentAction.STOP);
                break;
        }

        synchronized (this) {
            notifyAll();
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }
}