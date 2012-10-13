package com.megachezz.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.megachezz.business.Piece.Color;
import com.megachezz.model.Position;
import com.megachezz.model.pieces.Bishop;
import com.megachezz.model.pieces.King;
import com.megachezz.model.pieces.Knight;
import com.megachezz.model.pieces.Pawn;
import com.megachezz.model.pieces.Queen;
import com.megachezz.model.pieces.Rook;

public class Game {

	Chessboard board;
	Player playerWhite;
	Player playerBlack;
	Player actualPlayer;

	List<Move> moves;
	private Color check;
	private boolean checkMate;
	
	public Game() {
		this.checkMate = false;
		board = new Chessboard();
		playerWhite = new Player(Color.WHITE);
		playerBlack = new Player(Color.BLACK);
		actualPlayer = playerWhite;
		moves = new ArrayList<Move>();
		loadPieces();
		this.actualPlayer.startTurn();
	}
	
	public Game(List<Move> moves){
		this();
		this.actualPlayer.endTurn();
		for(Move move : moves){
			Piece p = this.board.getPieceOn(move.getFrom());
			try {
				this.movePiece(p, move.getTo());
			} catch (MoveProduceCheckException e) {
				System.out.println("Ese movimiento puso en jaque a si mismo, " +
						"se asume que el juego esta bien armado y es un estado temporal de la carga");
			}
			move.setBoardState(board);
		}
		if(moves.size() > 0)
			this.actualPlayer = moves.get(moves.size()-1).getColor().
									oponentColor().equals(Color.WHITE) ? playerWhite : playerBlack;
		verifyGameStatus();
		this.actualPlayer.startTurn();
	}

	private void loadPieces() {
		int row = 1;
		int col = 0;
		Color color = Color.WHITE;
		board.addPiece(new Rook(board, new Position(++col,row), color));
		board.addPiece(new Knight(board, new Position(++col,row), color));
		board.addPiece(new Bishop(board, new Position(++col,row), color));
		board.addPiece(new Queen(board, new Position(++col,row), color));
		board.addPiece(new King(board, new Position(++col,row), color));
		board.addPiece(new Bishop(board, new Position(++col,row), color));
		board.addPiece(new Knight(board, new Position(++col,row), color));
		board.addPiece(new Rook(board, new Position(++col,row), color));
		row=2;
		for(col=1;col<=8;col++)
			board.addPiece(new Pawn(board, new Position(col,row), color));
		
		row=8;
		col=0;
		color = Color.BLACK;
		board.addPiece(new Rook(board, new Position(++col,row), color));
		board.addPiece(new Knight(board, new Position(++col,row), color));
		board.addPiece(new Bishop(board, new Position(++col,row), color));
		board.addPiece(new Queen(board, new Position(++col,row), color));
		board.addPiece(new King(board, new Position(++col,row), color));
		board.addPiece(new Bishop(board, new Position(++col,row), color));
		board.addPiece(new Knight(board, new Position(++col,row), color));
		board.addPiece(new Rook(board, new Position(++col,row), color));
		
		row=7;
		for(col=1;col<=8;col++)
			board.addPiece(new Pawn(board, new Position(col,row), color));
	}

	public void playerEndMove(){
		verifyGameStatus();
		actualPlayer.endTurn();
		if(checkMate){
			playerBlack.endTurn();
			playerWhite.endTurn();
			return;
		}
		if(actualPlayer == playerWhite){
			actualPlayer = playerBlack;
		}else{
			actualPlayer = playerWhite;
		}
		actualPlayer.startTurn();
	}
	
	private void verifyGameStatus() {
		Color nextPlayerColor = this.actualPlayer.getColor().oponentColor();
		if(board.validateCheck(nextPlayerColor)){
			this.check = nextPlayerColor;
			this.checkMate = verifyCheckMate(nextPlayerColor);
		}else{
			this.check = null;
		}		
	}

	private boolean verifyCheckMate(Color nextPlayerColor) {
		for(Map.Entry<Position, Piece> map : this.board.getBoardPositions().entrySet()){
			Piece piece = map.getValue();
			if(piece != null && piece.getColor().equals(nextPlayerColor)){
				Collection<Position> pieceMoves = piece.getAvailableMoves();
				for(Position pos : pieceMoves){
					this.board.saveActualState();
					try{
						Position before = new Position(piece.getActualPosition());
						piece.moveToPoint(pos);
						/*
						 * Si pasa quiere decir que hay al menos un movimiento donde
						 * el rey sale de jaque
						 */
						piece.setActualPoint(before);
						this.board.rollBack();
						return false;
					}catch (MoveProduceCheckException e) {
						//Do nothing
					}
				}
				
			}
		}
		return true;
	}

	private void verifyCheckMate() {
		
	}

	public Chessboard getChessBoard() {
		return board;
	}

	public Player getPlayerWhite() {
		return playerWhite;
	}

	public Player getPlayerBlack() {
		return playerBlack;
	}

	public Player getActualPlayer() {
		return actualPlayer;
	}
	
	public void movePiece(Piece piece, Position to) throws MoveProduceCheckException{
		Position from = new Position(piece.getActualPosition());
		piece.moveToPoint(to);
		this.moves.add(new Move(from, new Position(piece.getActualPosition()),this.board));
	}

	public List<Move> getMoves() {
		return this.moves;
	}
	
	public boolean isCheck(){
		return this.check != null;
	}

	public boolean isCheckMate() {
		return checkMate;
	}
	
	
}
