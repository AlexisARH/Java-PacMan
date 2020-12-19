package pacman;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ViewPacmanGame implements Observer {

    PanelPacmanGame ppmg;

    public ViewPacmanGame(PacmanGame pmg){
        JFrame jFrame = new JFrame();
        jFrame.setTitle("PacMan");
        jFrame.setSize(new Dimension(1000, 600));
        Dimension windowSize = jFrame.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        java.awt.Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width /2;
        int dy = centerPoint.y - windowSize.height /2 +150;
        jFrame.setLocation(dx,dy);

        ppmg = new PanelPacmanGame(pmg.getMaze());
        jFrame.add(ppmg);
        jFrame.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        PacmanGame game = (PacmanGame)o;

        ArrayList<PositionAgent> pacmanPositions = new ArrayList<PositionAgent>();
        ArrayList<PositionAgent> ghostsPositions = new ArrayList<PositionAgent>();

        for (Agent a : game.getAgents()) {
            if(a instanceof Pacman) {
                pacmanPositions.add(a.getCoord());
            }
            if(a instanceof Ghost) {
                ghostsPositions.add(a.getCoord());
            }
        }

        ppmg.setPacmans_pos(pacmanPositions);
        ppmg.setGhosts_pos(ghostsPositions);
        ppmg.setMaze(game.getMaze());
        ppmg.repaint();
    }

}
