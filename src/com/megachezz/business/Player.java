package com.megachezz.business;

import java.util.Collection;

import com.megachezz.business.Piece.Color;

public class Player {

	boolean isPlaying = false;
	
	Long startTurnTms = 0l;
	
	Long playTime = 0l;
	
	Collection<Piece> pieces;
	
	Color color;
	
	String name;
	
	boolean isPlayTime;
	
	public Player(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	public void startTurn(){
		this.isPlaying = true;
		this.startTurnTms = System.currentTimeMillis();
	}
	
	public void endTurn(){
		this.isPlaying = false;
		this.playTime += System.currentTimeMillis() - this.startTurnTms;
	}
	
	public long getPlayTime(){
		Long time = new Long(this.playTime);
		if(this.isPlaying)
			time += System.currentTimeMillis() - this.startTurnTms;
		return time;
	}
}
