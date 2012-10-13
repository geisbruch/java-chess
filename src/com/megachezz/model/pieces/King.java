package com.megachezz.model.pieces;

import java.util.Collection;
import java.util.HashSet;

import com.megachezz.business.Chessboard;
import com.megachezz.business.HasMove;
import com.megachezz.business.MoveProduceCheckException;
import com.megachezz.business.Piece;
import com.megachezz.model.Position;

public class King extends Piece implements HasMove{

	private boolean hasMove;

	public King(Chessboard board, Position position, Color color) {
		super(board, position, color);
	}
	
	private boolean validateRook(Position pos){
		Piece r = this.getBoard().getPieceOn(pos);
		if(r != null 
				&& r.getColor().equals(this.getColor()) 
				&& r instanceof Rook 
				&& !((Rook)r).hasMove()
				&& !this.getBoard().isKingCheck()){
			
			int min = Math.min(pos.getCol(), this.getActualPosition().getCol());
			int max = Math.max(pos.getCol(), this.getActualPosition().getCol());
			for(int i = min+1; i < max; i++){
				if(this.getBoard().getPieceOn(new Position(i,this.getActualPosition().getRow())) != null)
					return false;
			}
			return true;
		}
		return false;
	}

	@Override
	protected Collection<Position> getPosibleMoves() {
		Collection<Position> posibles = new HashSet<Position>();
		Position corner = new Position(this.getActualPosition());
		corner.setCol(corner.getCol()-1);
		corner.setRow(corner.getRow()-1);
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				Position toCheck = new Position(corner.getCol()+i,corner.getRow()+j);
				if(this.getBoard().isValidPosition(toCheck) && 
						!this.getActualPosition().equals(toCheck)){
					if(this.getBoard().isAnotherPieceOn(toCheck) && 
							this.getBoard().getPieceOn(toCheck).getColor().equals(this.getColor()))
							continue;
					posibles.add(toCheck);
				}
			}
		}

		//Verificamos enroque
		if(!this.hasMove()){
			Position pos = new Position(1,this.getActualPosition().getRow());
			if(validateRook(pos))
				posibles.add(new Position(3,this.getActualPosition().getRow()));
			pos = new Position(8,this.getActualPosition().getRow());
			if(validateRook(pos))
				posibles.add(new Position(7,this.getActualPosition().getRow()));
		}
		return posibles;
	}
	
	@Override
	public void moveToPoint(Position p) throws MoveProduceCheckException {
		Piece piece = null;
		if(Math.abs(p.getCol() - this.getActualPosition().getCol()) > 1){
			if(this.getActualPosition().getCol() - p.getCol() < 0)
				piece = this.getBoard().getPieceOn(new Position(8,this.getActualPosition().getRow()));
			else
				piece = this.getBoard().getPieceOn(new Position(1,this.getActualPosition().getRow()));
		}
		if(piece != null && piece.getColor().equals(this.getColor()) && piece instanceof Rook){
			Position king;
			Position rook;
			if(piece.getActualPosition().getCol() == 8){
				king = new Position(7,this.getActualPosition().getRow());
				rook = new Position(6,this.getActualPosition().getRow());
			}else{
				king = new Position(3,this.getActualPosition().getRow());
				rook = new Position(4,this.getActualPosition().getRow());
			}
			super.moveToPoint(king);
			this.getBoard().setPieceOnPoint(piece, rook);
			piece.getActualPosition().setPostion(rook);
		}else{
			super.moveToPoint(p);
		}
		this.hasMove = true;
	}

	@Override
	public Boolean isKing() {
		return true;
	}

	@Override
	public boolean hasMove() {
		return this.hasMove;
	}

}
