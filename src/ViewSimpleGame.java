import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ViewSimpleGame implements Observer {

    private Game game;
    private JPanel panel;

    public ViewSimpleGame(Game _game){
        this.game = _game;
        this.game.addObserver(this);
        this.createView();
    }

    public void createView(){
        JFrame jFrame = new JFrame();
        jFrame.setTitle("Jeu");
        jFrame.setSize(new Dimension(350, 175));
        Dimension windowSize = jFrame.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2 ;
        int dy = centerPoint.y - windowSize.height / 2 - 700;
        jFrame.setLocation(dx, dy);

        this.panel = new JPanel();
        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.PAGE_AXIS));
        JScrollPane scrollPane = new JScrollPane(this.panel);

        jFrame.setContentPane(scrollPane);
        jFrame.setVisible(true);
    }

    public void addMessage(String _message){
        JLabel label = new JLabel(_message);
        this.panel.add(label);
        this.panel.revalidate();
        this.panel.repaint();
    }

    @Override
    public void update(Observable o, Object arg) {
        this.addMessage((String) arg);
    }
}
