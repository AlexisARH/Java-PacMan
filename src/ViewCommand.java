import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ViewCommand implements Observer{

	private Game game;
	private InterfaceControleur controleurSimpleGame;

	private JButton restartButton;
	private JButton runButton;
	private JButton stepButton;
	private JButton pauseButton;

	private JLabel currentTurnLabel;

	public ViewCommand(Game _game, InterfaceControleur _controleurSimpleGame) {
		this.game = _game;
		this.controleurSimpleGame = _controleurSimpleGame;
		this.createView();
		this.game.addObserver(this);
	}

	private void createView() {
		JFrame jFrame = new JFrame();
		jFrame.setTitle("Commandes");
		jFrame.setSize(new Dimension(700, 350));
		Dimension windowSize = jFrame.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x - windowSize.width / 2 ;
		int dy = centerPoint.y - windowSize.height / 2 - 350;
		jFrame.setLocation(dx, dy);

		JPanel container = new JPanel(new GridLayout(2, 1));

		// Boutons
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 4));

		Icon restartIcon = new ImageIcon("./icons/icon_restart.png");
		this.restartButton = new JButton(restartIcon);
		this.restartButton.setToolTipText("Start");
		this.restartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				controleurSimpleGame.start();
				restartButton.setEnabled(false);
				runButton.setEnabled(true);
				stepButton.setEnabled(true);
				pauseButton.setEnabled(true);
			}
		});

		Icon runIcon = new ImageIcon("./icons/icon_run.png");
		this.runButton = new JButton(runIcon);
		this.runButton.setToolTipText("Run");
		this.runButton.setEnabled(false);
		this.runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				controleurSimpleGame.run();
			}
		});

		Icon stepIcon = new ImageIcon("./icons/icon_step.png");
		this.stepButton = new JButton(stepIcon);
		this.stepButton.setToolTipText("Step");
		this.stepButton.setEnabled(false);
		this.stepButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				controleurSimpleGame.step();
			}
		});

		Icon pauseIcon = new ImageIcon("./icons/icon_pause.png");
		this.pauseButton = new JButton(pauseIcon);
		this.pauseButton.setToolTipText("Pause");
		this.pauseButton.setEnabled(false);
		this.pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				controleurSimpleGame.pause();
				if (runButton.isEnabled() && stepButton.isEnabled()){
					runButton.setEnabled(false);
					stepButton.setEnabled(false);
				} else {
					runButton.setEnabled(true);
					stepButton.setEnabled(true);
				}
			}
		});

		buttonsPanel.add(this.restartButton);
		buttonsPanel.add(this.runButton);
		buttonsPanel.add(this.stepButton);
		buttonsPanel.add(this.pauseButton);
		container.add(buttonsPanel);

		// Turns
		JPanel turnsPanel = new JPanel(new GridLayout(1, 2));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.weightx = 1;

		JPanel sliderPanel = new JPanel(new GridBagLayout());
		JLabel sliderLabel = new JLabel("Number of turns per second");
		sliderLabel.setHorizontalAlignment(JLabel.CENTER);
		gbc.insets = new Insets(25,0,0,0);
		sliderPanel.add(sliderLabel, gbc);
		JSlider slider = new JSlider(1, 10);
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setValue(2);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				int value = slider.getValue();
				controleurSimpleGame.setTime(value);
			}
		});
		gbc.insets = new Insets(50,0,0,0);
		sliderPanel.add(slider, gbc);
		turnsPanel.add(sliderPanel);

		JPanel currentTurnPanel = new JPanel(new GridBagLayout());
		this.currentTurnLabel = new JLabel("Turn : 0");
		currentTurnPanel.add(currentTurnLabel);
		turnsPanel.add(currentTurnPanel);

		container.add(turnsPanel);

		jFrame.setContentPane(container);
		jFrame.setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(this.game.getTurn() <= this.game.getMaxTurn()){
			this.currentTurnLabel.setText("Turn : " + this.game.getTurn());
			this.currentTurnLabel.repaint();
		}
		if(this.game.getTurn() >= this.game.getMaxTurn()){
			this.restartButton.setEnabled(true);
			this.runButton.setEnabled(false);
			this.stepButton.setEnabled(false);
			this.pauseButton.setEnabled(false);
		}
	}
}