package com.megachezz.business;

import java.util.HashMap;
import java.util.Map;

import com.megachezz.business.Piece.Color;
import com.megachezz.model.Position;

public class Chessboard {
	Map<Position, Piece> mapPoints;
	
	private Piece blackKing;
	private Piece whiteKing;
	
	private Boolean isKingCheck = false;
	private Color kingCheckColor;

	private boolean checkMate = false;

	private HashMap<Position, Piece> lastSavedState;

	private Position lastSavedWhitePosition;

	private Position lastSavedBlackPosition;
	
	public Chessboard() {
		mapPoints = new HashMap<Position, Piece>();
		for(int i = 1; i <= 8; i++){
			for(int j = 1; j <= 8; j++){
				mapPoints.put(new Position(i,j), null);
			}
		}
	}
	
	public Chessboard(Chessboard board) {
		this.isKingCheck = board.isKingCheck;
		this.checkMate = board.checkMate;
		this.blackKing = board.blackKing;
		this.whiteKing = board.whiteKing;
		this.mapPoints = new HashMap<Position, Piece>(board.mapPoints);
	}

	public void addPiece(Piece piece){
		if(piece.isKing())
			if(piece.getColor().equals(Color.BLACK))
				blackKing = piece;
			else
				whiteKing = piece;
		mapPoints.put(piece.getActualPosition(), piece);
	}
	
	public void removePiece(Piece piece) {
		mapPoints.put(piece.getActualPosition(),null);
	}

	public boolean isAnotherPieceOn(Position p) {
		Piece pos = mapPoints.get(p);
		return  pos != null;
	}

	public Piece getPieceOn(Position p) {
		return mapPoints.get(p);
	}

	public void checkMate() {
		this.checkMate  = true;
	}

	public Boolean isKingCheck(){
		return this.isKingCheck;
	}

	public boolean validateCheck(Color color) {
		Color oponentColor = color.oponentColor();
		Position kingPos = this.getKingPosition(color);
		boolean intersect = false;
		for(Map.Entry<Position, Piece> map : mapPoints.entrySet()){
			Piece piece = map.getValue();
			if(piece != null && piece.getColor().equals(oponentColor)){
				for(Position pos : piece.getPosibleMoves()){
					if(pos.equals(kingPos)){
						intersect = true;
						break;
					}
				}
			}
			if(intersect)
				break;
		}
//		if(intersect){
//			this.isKingCheck = true;
//			this.kingCheckColor = oponentColor;
//		}else{
//			this.isKingCheck = false;
//			this.kingCheckColor = null;
//		}
		this.isKingCheck = intersect;
		if(intersect)
			this.kingCheckColor = color;
		else
			this.kingCheckColor = null;
		return intersect;
	}

	private Position getKingPosition(Color oponentColor) {
		return oponentColor.equals(Color.BLACK) ? blackKing.getActualPosition() : whiteKing.getActualPosition();
	}

	public boolean isValidPosition(Position toCheck) {
		return this.mapPoints.containsKey(toCheck);
	}
	
	public Map<Position, Piece> getBoardPositions(){
		return mapPoints;
	}

	public void saveActualState() {
		this.lastSavedState = new HashMap<Position, Piece>(this.mapPoints);
		this.lastSavedWhitePosition = new Position(this.whiteKing.getActualPosition());
		this.lastSavedBlackPosition = new Position(this.blackKing.getActualPosition());
	}

	public void rollBack() {
		if(this.lastSavedState != null)
			this.mapPoints = lastSavedState;
		if(this.lastSavedBlackPosition != null)
			this.blackKing.setActualPoint(this.lastSavedBlackPosition);
		if(this.lastSavedWhitePosition != null)
			this.whiteKing.setActualPoint(this.lastSavedWhitePosition);
	}

	public void setPieceOnPoint(Piece piece, Position p) {
		mapPoints.put(p, piece);
		mapPoints.put(piece.getActualPosition(), null);
		if(piece.isKing())
			this.getKingPosition(piece.getColor()).setPostion(p);		
	}
}
