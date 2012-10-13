package com.megachezz.model.pieces;

import java.util.Collection;

import com.megachezz.business.Chessboard;
import com.megachezz.business.HasMove;
import com.megachezz.business.MoveProduceCheckException;
import com.megachezz.model.Position;
 
public class Rook extends Queen implements HasMove{

	private boolean hasMove = false;

	public Rook(Chessboard board, Position position, Color color) {
		super(board, position, color);
	}
	
	/**
	 * Ver tema de enroque
	 */
	@Override
	protected Collection<Position> getPosibleMoves() {
		return getTowerMoves();
	}

	@Override
	public void moveToPoint(Position p) throws MoveProduceCheckException {
		super.moveToPoint(p);
		this.hasMove = true;
	}
	
	@Override
	public boolean hasMove() {
		return this.hasMove ;
	}

}
