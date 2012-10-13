package com.megachezz.model.pieces;

import java.util.Collection;
import java.util.HashSet;

import com.megachezz.business.Chessboard;
import com.megachezz.business.MoveProduceCheckException;
import com.megachezz.business.Piece;
import com.megachezz.model.Position;

public class Pawn extends Piece {

	Integer multiplier;
	private boolean wasMoved = false;
	public Pawn(Chessboard board, Position position, Color color) {
		super(board, position, color);
		if(color.equals(Color.BLACK)){
			multiplier = -1;
		}else{
			multiplier = 1;
		}
	}

	@Override
	protected Collection<Position> getPosibleMoves() {
		//Only 3 points posible
		Collection<Position> pos = new HashSet<Position>();
		Position up = new Position(this.getActualPosition());
		up.setRow(up.getRow()+this.multiplier);
		if(this.getBoard().isValidPosition(up) && !this.getBoard().isAnotherPieceOn(up))
			pos.add(up);
		
		if(!wasMoved){
			Position up2 = new Position(this.getActualPosition()); 
			up2.setRow(up2.getRow()+(2*this.multiplier));
			if(this.getBoard().isValidPosition(up2) && !this.getBoard().isAnotherPieceOn(up2))
				pos.add(up2);
		}
		
		Position diagRigth = new Position(this.getActualPosition());
		diagRigth.setCol(diagRigth.getCol()+1);
		diagRigth.setRow(diagRigth.getRow()+this.multiplier);
		if(this.getBoard().isValidPosition(diagRigth) && 
				this.getBoard().isAnotherPieceOn(diagRigth) && 
				!this.getBoard().getPieceOn(diagRigth).getColor().equals(this.getColor()))
			pos.add(diagRigth);
		
		Position diagLeft = new Position(this.getActualPosition());
		diagLeft.setCol(diagLeft.getCol()-1);
		diagLeft.setRow(diagLeft.getRow()+this.multiplier);
		if(this.getBoard().isValidPosition(diagLeft) && 
				this.getBoard().isAnotherPieceOn(diagLeft) &&
				!this.getBoard().getPieceOn(diagLeft).getColor().equals(this.getColor()))
			pos.add(diagLeft);
		
		return pos;
	}

	@Override
	public void moveToPoint(Position p) throws MoveProduceCheckException {
		super.moveToPoint(p);
		wasMoved  = true;
	}
}
