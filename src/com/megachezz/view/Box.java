package com.megachezz.view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.megachezz.business.Piece;
import com.megachezz.business.Piece.Color;
import com.megachezz.model.Position;

public class Box extends JPanel implements Actionable, MouseListener{
	private Color color;
	JLayeredPane layers = new JLayeredPane();
	JLabel pieceLayer = new JLabel();
	Piece piece;
	TransparentColorBox colorBox;

	int i;
	int j;
	int width;
	int height;
	private Boolean selected = false;
	private List<ActionListener> listeners;
	
	public Box(int i, int j, Color color) {
		super();
		listeners = new ArrayList<ActionListener>();
		this.i = i;
		this.j = j;
		this.width = Config.getPieceWidth();
		this.height = Config.getPieceHeight();
		Dimension d = new Dimension(width, height);
		this.color = color;
		this.setMaximumSize(d);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.layers.setMaximumSize(d);
		this.layers.setMinimumSize(d);
		this.layers.setPreferredSize(d);

		colorBox = new TransparentColorBox(width, height, java.awt.Color.GREEN);
		colorBox.setVisible(false);
		
		this.pieceLayer.setBounds(0, 0, width, height);
		
		this.layers.add(this.pieceLayer, 2);
		this.layers.add(colorBox, 10);
		this.add(layers);
		this.clearBox();
		this.addMouseListener(this);
		
	}

	public void setPiece(Piece piece){
		Image img = new ImageIcon(getImageFileName(piece)).getImage();
		img = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		this.pieceLayer.setIcon(new ImageIcon(img));
		this.piece = piece;
	}
	
	public URL getImageFileName(Piece piece){
		return this.getClass().getResource("/resources/img/pieces/"+
			piece.getColor().toString().toLowerCase()+"/"+
			piece.getClass().getSimpleName()+".png");
	}
	
	public void clearBox(){
		if(color.equals(Color.WHITE)){
			this.setBackground(java.awt.Color.LIGHT_GRAY);
		}else{
			this.setBackground(java.awt.Color.GRAY);
		}
		this.colorBox.setVisible(false);
		this.piece = null;
		this.pieceLayer.setIcon(null);
	}

	@Override
	public void addActionListener(ActionListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.selected  = !this.selected;
		ActionEvent event = new ActionEvent(this, 0, "boxClicked");
		for(ActionListener listener : this.listeners){
			listener.actionPerformed(event);
		}
	}

	public Boolean isSelected() {
		return selected;
	}

	public Piece getPiece() {
		return piece;
	}

	public Position getPosition(){
		return new Position(i+1, j+1);
	}
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

	public void setAsPosibleMove() {
		colorBox.setVisible(true);
	}

	public void unsetAsPosibleMove(){
		this.colorBox.setVisible(false);
	}
}
