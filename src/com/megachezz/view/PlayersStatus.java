package com.megachezz.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.megachezz.business.Game;
import com.megachezz.business.Player;

public class PlayersStatus extends JPanel implements ActionListener {

	private Game game;

	JLabel turnLabel = new JLabel("Turno De: ");
	JLabel turnLabelText = new JLabel();
	
	JLabel blackTimeLabel = new JLabel("Tiempo de Juego Negras: ");
	JLabel blackTimeLabelText = new JLabel();
	
	JLabel whiteTimeLabel = new JLabel("Tiempo de Juego Blancas: ");
	JLabel whiteTimeLabelText = new JLabel();
	
	public PlayersStatus(Game game) {
		this.game = game;
		this.setLayout(new GridLayout(3,3));
		this.add(turnLabel);
		this.add(turnLabelText);

		this.add(whiteTimeLabel);
		this.add(whiteTimeLabelText);
		
		this.add(blackTimeLabel);
		this.add(blackTimeLabelText);
		Timer timer = new Timer(1000, this);
		timer.setActionCommand("refresh");
		timer.start();
	}
	
	public void refresh(){
		turnLabelText.setText(game.getActualPlayer().getColor().toString());
		blackTimeLabelText.setText(getElapsedTime(game.getPlayerBlack()));
		whiteTimeLabelText.setText(getElapsedTime(game.getPlayerWhite()));
	}

	private String getElapsedTime(Player player) {
		Long secconds = new Double(Math.floor(player.getPlayTime() / 1000)).longValue();
		Long minutes = new Double(Math.floor(secconds/60)).longValue();
		secconds-= minutes*60;
		return minutes+":"+secconds;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("refresh"))
			this.refresh();
	}
	
}
