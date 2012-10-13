package com.megachezz.model.pieces;

import java.util.Collection;
import java.util.HashSet;

import com.megachezz.business.Chessboard;
import com.megachezz.business.Piece;
import com.megachezz.model.Position;

public class Queen extends Piece {

	public Queen(Chessboard board, Position position, Color color) {
		super(board, position, color);
	}

	@Override
	protected Collection<Position> getPosibleMoves() {
		Collection<Position> positions = new HashSet<Position>();
		positions.addAll(getTowerMoves());
		positions.addAll(getBishopMoves());
		
		return positions;
	}

	protected Collection<Position> getBishopMoves() {
		Collection<Position> positions = new HashSet<Position>();
		Position tryPos = new Position(this.getActualPosition());
		
		//Diag Rigth Top
		tryPos.setCol(tryPos.getCol()+1);
		tryPos.setRow(tryPos.getRow()+1);
		while(this.getBoard().isValidPosition(tryPos)){
			if(this.getBoard().isAnotherPieceOn(tryPos)){
				if(!this.getBoard().getPieceOn(tryPos).getColor().equals(this.getColor()))
					positions.add(new Position(tryPos));
				break;
			}
			positions.add(new Position(tryPos));
			tryPos.setCol(tryPos.getCol()+1);
			tryPos.setRow(tryPos.getRow()+1);
		}
		
		//Diag Left Top
		tryPos = new Position(this.getActualPosition());
		tryPos.setCol(tryPos.getCol()-1);
		tryPos.setRow(tryPos.getRow()+1);
		while(this.getBoard().isValidPosition(tryPos)){
			if(this.getBoard().isAnotherPieceOn(tryPos)){
				if(!this.getBoard().getPieceOn(tryPos).getColor().equals(this.getColor()))
					positions.add(new Position(tryPos));
				break;
			}
			positions.add(new Position(tryPos));
			tryPos.setCol(tryPos.getCol()-1);
			tryPos.setRow(tryPos.getRow()+1);
		}
		
		//Diag Left Bottom
		tryPos = new Position(this.getActualPosition());
		tryPos.setCol(tryPos.getCol()-1);
		tryPos.setRow(tryPos.getRow()-1);
		while(this.getBoard().isValidPosition(tryPos)){
			if(this.getBoard().isAnotherPieceOn(tryPos)){
				if(!this.getBoard().getPieceOn(tryPos).getColor().equals(this.getColor()))
					positions.add(new Position(tryPos));
				break;
			}
			positions.add(new Position(tryPos));
			tryPos.setCol(tryPos.getCol()-1);
			tryPos.setRow(tryPos.getRow()-1);
		}
		
		//Diag Rigth Bottom
		tryPos = new Position(this.getActualPosition());
		tryPos.setCol(tryPos.getCol()+1);
		tryPos.setRow(tryPos.getRow()-1);
		while(this.getBoard().isValidPosition(tryPos)){
			if(this.getBoard().isAnotherPieceOn(tryPos)){
				if(!this.getBoard().getPieceOn(tryPos).getColor().equals(this.getColor()))
					positions.add(new Position(tryPos));
				break;
			}
			positions.add(new Position(tryPos));
			tryPos.setCol(tryPos.getCol()+1);
			tryPos.setRow(tryPos.getRow()-1);
		}
		return positions;
	}

	protected Collection<Position> getTowerMoves() {
		Collection<Position> positions = new HashSet<Position>();
		Position tryPos = new Position(this.getActualPosition());
		
		//Try rigth
		tryPos.setCol(tryPos.getCol()+1);
		while(this.getBoard().isValidPosition(tryPos)){
			if(this.getBoard().isAnotherPieceOn(tryPos)){
				if(!this.getBoard().getPieceOn(tryPos).getColor().equals(this.getColor()))
					positions.add(tryPos);
				break;
			}
			positions.add(new Position(tryPos));
			tryPos.setCol(tryPos.getCol()+1);
		}
		
		//Try Left
		tryPos = new Position(this.getActualPosition());
		tryPos.setCol(tryPos.getCol()-1);
		while(this.getBoard().isValidPosition(tryPos)){
			if(this.getBoard().isAnotherPieceOn(tryPos)){
				if(!this.getBoard().getPieceOn(tryPos).getColor().equals(this.getColor()))
					positions.add(new Position(tryPos));
				break;
			}
			positions.add(new Position(tryPos));
			tryPos.setCol(tryPos.getCol()-1);
		}
		
		//Try top
		tryPos = new Position(this.getActualPosition());
		tryPos.setRow(tryPos.getRow()+1);
		while(this.getBoard().isValidPosition(tryPos)){
			if(this.getBoard().isAnotherPieceOn(tryPos)){
				if(!this.getBoard().getPieceOn(tryPos).getColor().equals(this.getColor()))
					positions.add(new Position(tryPos));
				break;
			}
			positions.add(new Position(tryPos));
			tryPos.setRow(tryPos.getRow()+1);
		}
		
		//Try bottom
		tryPos = new Position(this.getActualPosition());
		tryPos.setRow(tryPos.getRow()-1);
		while(this.getBoard().isValidPosition(tryPos)){
			if(this.getBoard().isAnotherPieceOn(tryPos)){
				if(!this.getBoard().getPieceOn(tryPos).getColor().equals(this.getColor()))
					positions.add(new Position(tryPos));
				break;
			}
			positions.add(new Position(tryPos));
			tryPos.setRow(tryPos.getRow()-1);
		}
		
		return positions;
	}

}
