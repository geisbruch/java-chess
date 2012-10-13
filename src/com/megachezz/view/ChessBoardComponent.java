package com.megachezz.view;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Map;

import javax.swing.JPanel;

import com.megachezz.business.Chessboard;
import com.megachezz.business.Piece;
import com.megachezz.business.Piece.Color;
import com.megachezz.model.Position;

public class ChessBoardComponent extends JPanel implements Actionable{
	
	private Image img;
	private Chessboard board;
	
	public Chessboard getBoard() {
		return board;
	}

	public void setBoard(Chessboard board) {
		this.board = board;
	}

	private Box[][] positionContainers = new Box[8][8];
	public ChessBoardComponent(Chessboard board) {
		super();
		this.setSize(Config.getPieceWidth()*8, Config.getPieceHeight()*8);
		GridLayout layout = new GridLayout(8,8);
		this.setLayout(layout);
		this.board = board;
		initBoxes();
	}

	public void initBoxes(){
		boolean colorBlack = true;
			for(int col = 0; col < 8; col++ ){
				for(int row = 0; row < 8; row++){
				Box box = new Box(row,col, colorBlack ? Color.BLACK : Color.WHITE);
				positionContainers[row][col] = box;
				this.add(box);
				colorBlack = !colorBlack;
			}
			colorBlack = !colorBlack;
		}	
	}
	
	public void redrawBoxes(){
		for(int i = 0; i < 8; i++ ){
			for(int j = 0; j < 8; j++){
				positionContainers[i][j].clearBox();
			}
		}
	}
	
	public void redrawBoard(){
		if(this.board == null || this.board.getBoardPositions() == null)
			return;
		for(Map.Entry<Position, Piece> entry : this.board.getBoardPositions().entrySet()){
			int col = entry.getKey().getCol()-1;
			int row = entry.getKey().getRow()-1;
			positionContainers[col][row].clearBox();
			if(entry.getValue()!=null)
				positionContainers[col][row].setPiece(entry.getValue());
		}
	}

	@Override
	public void addActionListener(ActionListener actionListener) {
		for(int i = 0; i < 8; i++ ){
			for(int j = 0; j < 8; j++){
				positionContainers[i][j].addActionListener(actionListener);
			}
		}
	}

	public void showPosibleMoves(Collection<Position> movements) {
		for(Position position : movements){
			this.positionContainers[position.getCol()-1][position.getRow()-1].setAsPosibleMove();
		}
	}

	public void clearSelections() {
		for(int col = 0; col < 8; col++ )
			for(int row = 0; row < 8; row++)
				this.positionContainers[col][row].unsetAsPosibleMove();
	}

}
