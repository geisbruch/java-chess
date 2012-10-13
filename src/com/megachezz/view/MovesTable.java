package com.megachezz.view;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.megachezz.business.Move;

public class MovesTable extends JPanel { 

	JScrollPane panel;
	JTable table;
	private MovesTableModel model;
	public MovesTable(List<Move> moves) {
		table = new JTable();
		panel = new JScrollPane(table);
		this.setMoves(moves);
		this.add(panel);
	}
	
	public void setMoves(List<Move> moves){
		this.model = new MovesTableModel(moves);
		table.setModel(model);
	}

	public void refresh() {
		this.model.fireTableDataChanged();
	}
}
