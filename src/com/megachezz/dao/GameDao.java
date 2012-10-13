package com.megachezz.dao;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.megachezz.business.Game;
import com.megachezz.business.Move;
import com.megachezz.exceptions.LoadGameException;
import com.megachezz.model.Position;

public class GameDao {

	public void saveGame(Game game, File file) throws Exception{
		try{
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file, false));
			for(Move m : game.getMoves()){
				out.write(m.getFrom().getAritmeticProsition().getBytes());
				out.write("-".getBytes());
				out.write(m.getTo().getAritmeticProsition().getBytes());
				out.write("\n".getBytes());
			}
			out.close();
		}catch(Exception e){
			throw e;
		}
	}
	
	public Game loadGame(File fileGame) throws LoadGameException{
		try{
			BufferedReader reader = new BufferedReader(new FileReader(fileGame));
			String line;
			List<Move> moves = new ArrayList<Move>();
			while((line = reader.readLine())!=null){
				String[] fromTo = line.split("-");
				if(fromTo.length != 2){
					//TODO: throw exception
				}
				moves.add(new Move(new Position(fromTo[0]), new Position(fromTo[1])));
			}
			return new Game(moves);
		}catch(Exception e){
			throw new LoadGameException("Error cargando juego",e);
		}
	}
}
