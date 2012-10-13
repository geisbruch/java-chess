package com.megachezz.view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class TransparentColorBox extends JPanel{
	int width;
	int height;
	Color color;
	
	public TransparentColorBox(int width, int height, Color color) {
		super();
		this.width = width;
		this.height = height;
		this.color = color;
		Dimension d = new Dimension(width, height);
		this.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100));
		this.setSize(d);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
		this.setMaximumSize(d);
	}

}
