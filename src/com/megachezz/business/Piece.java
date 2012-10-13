package com.megachezz.business;

import java.util.Collection;

import com.megachezz.model.Image;
import com.megachezz.model.Position;

/**
 * It's a simple piece class
 * z
 * @author lab11
 * 
 */
public abstract class Piece {

	public enum Color {
		BLACK("BLACK"), WHITE("WHITE");

		private final String color;

		Color(String color) {
			this.color = color;
		}

		public Color oponentColor() {
			return this.equals(BLACK) ? WHITE : BLACK;
		}

		private String getStringColor() {
			return this.color;
		}

	}

	private Position actualPosition;
	private Chessboard board;
	private Color color;
	private Image image;

	public Piece(Chessboard board, Position position, Color color) {
		this.actualPosition = position;
		this.board = board;
		this.color = color;
	}

	public final Collection<Position> getAvailableMoves() {
		return this.getPosibleMoves();
	}

	/**
	 * Should return all posible movements that this piece can do
	 * 
	 * @return
	 */
	protected abstract Collection<Position> getPosibleMoves();

	/**
	 * Should return true if this piece can move to a point
	 * 
	 * @param p
	 * @return
	 */
	public Boolean isEnableToMoveToPoint(Position p) {
		return getPosibleMoves().contains(p);
	}

	/**
	 * Move the piece to a defined position
	 * 
	 * @param p
	 * @throws MoveProduceCheckException
	 */
	public void moveToPoint(Position p) throws MoveProduceCheckException {
		if (this.isEnableToMoveToPoint(p)) {
			board.saveActualState();
			board.setPieceOnPoint(this, p);
			if (board.validateCheck(this.getColor())) {
				board.rollBack();
				throw new MoveProduceCheckException();
			}
			this.setActualPoint(p);
		}
	}

	protected void setActualPoint(Position p) {
		this.actualPosition = p;
	}

	public void destroyPiece() {
		board.removePiece(this);
	}

	public Position getActualPosition() {
		return this.actualPosition;
	}

	public Chessboard getBoard() {
		return this.board;
	}

	// By default is not king
	public Boolean isKing() {
		return false;
	}

	public Color getColor() {
		return color;
	}
}
