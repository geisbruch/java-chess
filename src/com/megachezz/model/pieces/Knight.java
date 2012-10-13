package com.megachezz.model.pieces;

import java.util.Collection;
import java.util.HashSet;

import com.megachezz.business.Chessboard;
import com.megachezz.business.HasMove;
import com.megachezz.business.Piece;
import com.megachezz.model.Image;
import com.megachezz.model.Position;

public class Knight extends Piece {

	private boolean hasMove;

	public Knight(Chessboard board, Position position, Color color) {
		super(board, position, color);
	}

	@Override
	protected Collection<Position> getPosibleMoves() {
		//Eigth possible moves
		Collection<Position> positions = new HashSet<Position>();
		Collection<Position> positionsTent = new HashSet<Position>();
		//Pos 1
		Position pos1 = new Position(this.getActualPosition());
		pos1.setCol(pos1.getCol()+1);
		pos1.setRow(pos1.getRow()+2);
		//Pos 2
		Position pos2 = new Position(this.getActualPosition());
		pos2.setCol(pos2.getCol()-1);
		pos2.setRow(pos2.getRow()+2);
		//Pos 3
		Position pos3 = new Position(this.getActualPosition());
		pos3.setCol(pos3.getCol()-1);
		pos3.setRow(pos3.getRow()-2);
		//Pos 4
		Position pos4 = new Position(this.getActualPosition());
		pos4.setCol(pos4.getCol()+1);
		pos4.setRow(pos4.getRow()-2);
		//Pos 5
		Position pos5 = new Position(this.getActualPosition());
		pos5.setCol(pos5.getCol()+2);
		pos5.setRow(pos5.getRow()+1);
		//Pos 6
		Position pos6 = new Position(this.getActualPosition());
		pos6.setCol(pos6.getCol()+2);
		pos6.setRow(pos6.getRow()-1);
		//Pos 7
		Position pos7 = new Position(this.getActualPosition());
		pos7.setCol(pos7.getCol()-2);
		pos7.setRow(pos7.getRow()+1);
		//Pos 8
		Position pos8 = new Position(this.getActualPosition());
		pos8.setCol(pos8.getCol()-2);
		pos8.setRow(pos8.getRow()-1);
		
		
		positionsTent.add(pos1);
		positionsTent.add(pos2);
		positionsTent.add(pos3);
		positionsTent.add(pos4);
		positionsTent.add(pos5);
		positionsTent.add(pos6);
		positionsTent.add(pos7);
		positionsTent.add(pos8);
		
		Piece pieceOnBoard;
		for(Position pos : positionsTent){
			pieceOnBoard = this.getBoard().getPieceOn(pos);
			if(this.getBoard().isValidPosition(pos) &&
					( pieceOnBoard == null ||
							( pieceOnBoard != null && !pieceOnBoard.getColor().equals(this.getColor()))
					)
				)
				positions.add(pos);
		}
		return positions;
	}

	@Override
	public void moveToPoint(Position p) throws com.megachezz.business.MoveProduceCheckException {
		super.moveToPoint(p);
	};
	
}
