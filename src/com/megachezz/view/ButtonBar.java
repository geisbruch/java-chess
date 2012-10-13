package com.megachezz.view;

import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class ButtonBar extends JPanel{

	JButton newGame;
	private JButton save;
	
	Collection<JButton> buttons = new HashSet<JButton>();
	private JButton loadGame;
	public ButtonBar() {
		newGame = new JButton("New Game");
		newGame.setActionCommand("newGame");
		
		save = new JButton("Save Game");
		save.setActionCommand("saveGame");

		loadGame = new JButton("Cargar Juego");
		loadGame.setActionCommand("loadGame");
		
		this.add(newGame);
		this.add(save);
		this.add(loadGame);
		
		buttons.add(newGame);
		buttons.add(save);
		buttons.add(loadGame);
	}
	
	public void addActionListener(ActionListener l) {
		for(JButton button : buttons){
			button.addActionListener(l);
		}
	}
}
