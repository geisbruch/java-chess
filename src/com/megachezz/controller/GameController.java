package com.megachezz.controller;

import java.io.IOException;
import java.util.Properties;

import com.megachezz.view.MainWindow;

public class GameController {

	private MainWindow mainWindow;
	private Properties config;

	public GameController() throws IOException {
		this.mainWindow = new MainWindow();
		
	}
	
	public static void main(String[] args) throws IOException {
		new GameController().startUp();
	}

	private void startUp() {
		mainWindow.display();
	}
}
