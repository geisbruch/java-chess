package com.megachezz.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.megachezz.business.Move;
import com.megachezz.model.Position;

public class MovesTableModel extends AbstractTableModel{

	String[] names = {"Jugada","Jugador","Desde","Hasta"};
	private List<Move> moves;
	
	public MovesTableModel(List<Move> moves) {
		this.moves = moves;
	}
	
	@Override
	public String getColumnName(int column) {
		return names[column];
	}	
	
	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return moves.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		int id = this.moves.size() - rowIndex;
		Move move = this.moves.get(id - 1);
		if(columnIndex == 0){
			//Jugada
			return id;
		}else if(columnIndex == 1){
			//Jugador
			return move.getColor();
		}else if(columnIndex == 2){
			//Desde
			return positionToChessValue(move.getFrom());
		}else if(columnIndex == 3){
			//Hasta
			return positionToChessValue(move.getTo());
		}
		return "No Data";
	}

	private String positionToChessValue(Position pos){
		Character col = new Character((char) (64+pos.getCol()));
		return new String(col.toString()+pos.getRow());
	}
}
