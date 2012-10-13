package com.megachezz.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Position {
	static Pattern validPosition = Pattern.compile("^(\\w)(\\d)$");
	private int col;
	private int row;

	public Position(int i, int j) {
		this.col = i;
		this.row = j;
	}

	public Position(Position p) {
		this.col = p.getCol();
		this.row = p.getRow();
	}
	
	public Position(String aritmeticPostion) {
		Matcher m = validPosition.matcher(aritmeticPostion);
		if(m.find()){
			this.col = m.group(1).toUpperCase().charAt(0)-64;
			this.row = new Integer(m.group(2));
		}else{
			//TODO: throw exception
		}
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getAritmeticProsition(){
		return (char)(64 + this.col)+""+this.row;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[x:"+col+", y:"+row+"]";
	}

	public void setPostion(Position p) {
		this.col = p.getCol();
		this.row = p.getRow();		
	}
}
