package com.megachezz.business;

import com.megachezz.business.Piece.Color;
import com.megachezz.model.Position;

public class Move {

	private Position from;
	private Position to;
	private Chessboard boardState; 
	private Color color;
	
	public Move(Position from, Position to) {
		super();
		this.from = from;
		this.to = to;
	}
	
	public Move(Position from, Position to, Chessboard chessboard) {
		this(from,to);
		this.setBoardState(chessboard);
	}

	public Position getFrom() {
		return from;
	}
	public Position getTo() {
		return to;
	}

	public Chessboard getBoardState() {
		return boardState;
	}

	public void setBoardState(Chessboard boardState) {
		this.boardState = new Chessboard(boardState);
		this.color = this.boardState.getPieceOn(this.to).getColor();
	}

	public Color getColor() {
		return color;
	}
	
}
