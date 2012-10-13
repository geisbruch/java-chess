package com.megachezz.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.megachezz.business.Chessboard;
import com.megachezz.business.Game;
import com.megachezz.business.MoveProduceCheckException;
import com.megachezz.business.Piece;
import com.megachezz.dao.GameDao;
import com.megachezz.exceptions.KingInCheckExeption;
import com.megachezz.exceptions.LoadGameException;
import com.megachezz.model.Position;

public class MainWindow extends JFrame implements ActionListener{

	ButtonBar buttonBar;
	PlayersStatus playersStatus;
	private Chessboard board;
	ChessBoardComponent chessBoardComp;
	JPanel showChess;
	Position selectedPosition;
	Game game = new Game();
	private boolean isPieceSelected = false;
	private Piece selectedPiece;
	private MovesTable movesList;
	
	public MainWindow() throws IOException {
		setSize(Config.getWindowWidth(),Config.getWindowHeigth());	
		JPanel topBar = new JPanel();
		
		buttonBar = new ButtonBar();
		buttonBar.addActionListener(this);
		
		playersStatus = new PlayersStatus(game);
		playersStatus.setVisible(false);
		
		showChess = new JPanel();
		showChess.setLayout(new BoxLayout(showChess, BoxLayout.X_AXIS));
		
		JPanel rightWindow = new JPanel();
		rightWindow.setLayout(new BoxLayout(rightWindow, BoxLayout.Y_AXIS));
		
		chessBoardComp = new ChessBoardComponent(null);
		chessBoardComp.addActionListener(this);
		
		showChess.add(chessBoardComp);
		
		movesList = new MovesTable(game.getMoves());
		rightWindow.add(playersStatus);
		rightWindow.add(movesList);
		
		showChess.add(rightWindow);
		
		topBar.add(buttonBar);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(topBar);
		mainPanel.add(showChess);
		mainPanel.setOpaque(true);
	    this.getContentPane().add(mainPanel);
	}
	
	public void display() {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            	pack();
                setVisible(true);
            }
        });
	}

	private void loadGame(Game game){
		this.game = game;
		this.board = game.getChessBoard();
		this.chessBoardComp.setBoard(this.board);
		this.playersStatus.setGame(this.game);
		this.playersStatus.setVisible(true);
		this.playersStatus.refresh();
		movesList.setMoves(this.game.getMoves());
		chessBoardComp.redrawBoxes();
		chessBoardComp.redrawBoard();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			if(e.getActionCommand().equals("newGame")){
				loadGame(new Game());
			}else if(e.getActionCommand().equals("saveGame")){
				if(this.game == null){
					JOptionPane.showMessageDialog(this, "Error: No se ha empezado ningun juego",
							"Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(this.game.getMoves().size() == 0){
					JOptionPane.showMessageDialog(this, "No se ha realizado ningun movimiento",
							"Error",JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				JFileChooser chooser = new JFileChooser();
				int option = chooser.showSaveDialog(this);
				if(option == JFileChooser.APPROVE_OPTION){
					File gameFile = chooser.getSelectedFile();
					if(gameFile.exists()){
						int choose = JOptionPane.showConfirmDialog(this, "Este archivo ya existe!!!\n" +
								"Si salva ahora los datos se perderan, " +
								"quiere salvar?","Guardando...",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
						if(choose == JOptionPane.NO_OPTION){
							return;
						}
					}
					try{
						new GameDao().saveGame(this.game,gameFile);
					}catch(Exception ex){
						JOptionPane.showMessageDialog(this, "Ocurrio un error guardando.\n" +
								"["+ex.getMessage()+"]");
						ex.printStackTrace();
					}
				}
			}else if(e.getActionCommand().equals("loadGame")){
				if(this.game != null && this.game.getMoves().size() > 0){
					int choose = JOptionPane.showConfirmDialog(this,
									"Ya ha comenzado a jugar una partida.\n" +
									"Si carga una partida perdera la actual, desea cargar una partida?",
									"Cargando partida...",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if(choose == JOptionPane.NO_OPTION){
						return;
					}
				}
				JFileChooser chooser = new JFileChooser();
				int option = chooser.showOpenDialog(this);
				if(option == JFileChooser.APPROVE_OPTION){
					File gameFile = chooser.getSelectedFile();
					if(!gameFile.exists()){
						JOptionPane.showMessageDialog(this, "El archivo no existe, seleccion uno");
						return;
					}
					try {
						loadGame(new GameDao().loadGame(gameFile));
					} catch (LoadGameException e1) {
						JOptionPane.showConfirmDialog(this, "Error cargando juego, verifique que el archivo seleccionado sea un juego valido.", "Error", JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}
			}else if(e.getActionCommand().equals("boxClicked")){
				if(game.isCheckMate()){
					JOptionPane.showMessageDialog(this, "El Juego ya termino");
					return;
				}
				Box box = (Box)e.getSource();
				System.out.println("Clicked: "+box.getPosition());
				if(isPieceSelected){
					try {
						if(selectedPiece.getAvailableMoves().contains(box.getPosition())){
							try{
								this.game.movePiece(selectedPiece, box.getPosition());
								this.chessBoardComp.redrawBoard();
								this.game.playerEndMove();
								this.movesList.refresh();
								this.playersStatus.refresh();
								if(this.game.isCheckMate()){
									JOptionPane.showMessageDialog(this, "Jaque mate, Ganan las "+game.getActualPlayer().getColor());
								}else if(this.game.isCheck()){
									JOptionPane.showMessageDialog(this, "Jaque !!!");
								}
							}catch (MoveProduceCheckException ex) {
								JOptionPane.showMessageDialog(this, "Este movimiento no puede ser realizado ya que deja a tu propio rey en jaque");
							}
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				if(box.getPiece() != null){
					if(!isPieceSelected ){
						if(!this.game.getActualPlayer().getColor().equals(box.getPiece().getColor()))
							return;
						try {
							Collection<Position> movements = box.getPiece().getAvailableMoves();
							movements.add(box.getPosition());
							this.chessBoardComp.showPosibleMoves(movements);
							isPieceSelected = true;
							selectedPiece = box.getPiece();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}else{
						if(selectedPiece.getActualPosition().equals(box.getPosition())){
							this.chessBoardComp.clearSelections();
							this.isPieceSelected = false;
						}
					}
				}
			}
		}catch (Throwable ex) {
			System.err.println("Error incrontrolable");
			ex.printStackTrace();
			JOptionPane.showConfirmDialog(this, "Ocurrio un error grave en la aplicaicion\n" +
					"Se recomienda cerrarla y volverla a abrir", "Error", JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
		}
	}
		
}
