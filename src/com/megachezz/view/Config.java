package com.megachezz.view;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class Config {
	final static Properties config;
	static {
		config = new Properties();
		try {
			config.load(Config.class.getResourceAsStream("/resources/config/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int getPieceWidth() {
		return Integer.parseInt(config.getProperty("window.pieceWidth"));
	}

	public static int getPieceHeight() {
		return Integer.parseInt(config.getProperty("window.pieceHeight"));
	}

	public static URL getChesBoardImg() {
		return Config.class.getResource(config.getProperty("window.chessBoardImg"));
	}
	
	public static int getWindowWidth(){
		return Integer.parseInt(config.getProperty("window.width"));
	}
	
	public static int getWindowHeigth(){
		return Integer.parseInt(config.getProperty("window.heigth"));
	}

	public static int getChessBoardWidth() {
		return Integer.parseInt(config.getProperty("window.chessboard.width"));
	}

	public static int getChessBoardHeight() {
		return Integer.parseInt(config.getProperty("window.chessboard.height"));
	}
	 
	

}
