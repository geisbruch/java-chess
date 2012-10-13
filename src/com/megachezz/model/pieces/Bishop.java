package com.megachezz.model.pieces;

import java.util.Collection;

import com.megachezz.business.Chessboard;
import com.megachezz.model.Position;

public class Bishop extends Queen {

	public Bishop(Chessboard board, Position position, Color color) {
		super(board, position, color);
	}
	
	@Override
	protected Collection<Position> getPosibleMoves() {
		return getBishopMoves();
	}


}
