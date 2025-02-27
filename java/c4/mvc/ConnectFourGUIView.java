package c4.mvc;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import c4.players.ConnectFourPlayer;

public class ConnectFourGUIView implements ActionListener, GridObserver, ConnectFourViewInterface{
	ConnectFourModelInterface model;
	ConnectFourController controller;
	
	JFrame frame;
	JPanel mainPanel;
	
	JPanel grid;
	JPanel gridLocations[][];
	JLabel gridLabels[][];
	JButton columnButtons[];
	
	JPanel northPanel;
	JLabel currentTurnLabel;
	
	JPanel southPanel;
	JButton quitGameButton;
	boolean gameOver;
	
	public ConnectFourGUIView(ConnectFourModelInterface model, ConnectFourController controller){
		this.model = model;
		this.controller = controller;
		this.gameOver = false;
	}
	
	public void createView(){
		frame = new JFrame();
		mainPanel = new JPanel(new BorderLayout());
		
		northPanel = new JPanel();
		southPanel = new JPanel();
		
		createGrid();
		
		currentTurnLabel = new JLabel("Current turn: PLAYER 1");
		northPanel.add(currentTurnLabel);
		
		quitGameButton = new JButton("Quit");
		quitGameButton.addActionListener(this);
		southPanel.add(quitGameButton);
		
		mainPanel.add(northPanel, BorderLayout.NORTH);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		frame.add(mainPanel);
		
		frame.setTitle("Connect Four");
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		model.registerObserver((GridObserver) this);
	}
	
	public void playGame(){
		gameOver = false;
		while(!gameOver){
			int playerNum = model.getTurn();
			if(playerNum == ConnectFourModelInterface.PLAYER1)
				currentTurnLabel.setText("Current Turn: PLAYER 1");
			else{
				currentTurnLabel.setText("Current Turn: PLAYER 2");
			}
			ConnectFourPlayer player = controller.getPlayer(playerNum);
			if(player.isAutomated()){
				int move = player.getMove();
				controller.placeToken(move);
			}
			else{
				try{
					Thread.sleep(200);
				}
				catch(InterruptedException ex){
					Thread.currentThread().interrupt();
				}
			}
		}
	}
	
	public void createGrid(){
		grid = new JPanel(new GridLayout(7,7));
		gridLocations = new JPanel[7][6];
		gridLabels = new JLabel[7][6];
		columnButtons = new JButton[7];
		for(int i=0; i<7; i++){
			columnButtons[i] = new JButton("V");
			grid.add(columnButtons[i]);
			columnButtons[i].addActionListener(this);
		}
		for(int j = 0; j<6; j++){
			for(int i=0; i<7; i++){
				gridLocations[i][j] = new JPanel();
				gridLocations[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				gridLocations[i][j].setSize(50, 50);
				gridLabels[i][j] = new JLabel();
				gridLabels[i][j].setFont(new Font(gridLabels[i][j].getFont().getFontName(), Font.BOLD, 32));
				gridLocations[i][j].add(gridLabels[i][j]);
				grid.add(gridLocations[i][j]);
			}
		}
		
		mainPanel.add(grid, BorderLayout.CENTER);
	}
	
	@Override
	public void updateGrid() {
		int[][] grid = model.getGrid();
		for(int i=0; i<7; i++){
			for(int j=0; j<6; j++){
				if(grid[i][j] == ConnectFourModelInterface.PLAYER1){
					gridLabels[i][j].setText("O");
					gridLabels[i][j].setForeground(Color.RED);
				}
				else if(grid[i][j] == ConnectFourModelInterface.PLAYER2){
					gridLabels[i][j].setText("O");
					gridLabels[i][j].setForeground(Color.YELLOW);
				}
				else{
					gridLabels[i][j].setText("");
				}
			}
		}
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == quitGameButton){
			controller.quit();
		}
		else{
			for(int i=0; i<columnButtons.length; i++){
				if(e.getSource() == columnButtons[i]){
					controller.placeToken(i);
				}
			}
		}
	}
	
	public void enableColumn(int c){
		columnButtons[c].setEnabled(true);
	}
	
	public void disableColumn(int c){
		columnButtons[c].setEnabled(false);
	}

	public void announceWinner(int winner){
		JOptionPane.showMessageDialog(frame, "Player "+winner+" wins!");
		gameOver = true;
	}
	
	public void announceDraw(){
		JOptionPane.showMessageDialog(frame, "It's a draw!");
		gameOver = true;
	}
}
