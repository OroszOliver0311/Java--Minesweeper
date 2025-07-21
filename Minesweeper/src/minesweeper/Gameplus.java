package minesweeper;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Gameplus extends MinesweeperGame {
	ArrayList<MineTile> mole;
    boolean molefound = false;
	
	public Gameplus(int nr, int nc, int nm) {
		super(nr, nc, nm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void map() {

		// frame.setVisible(true);
	        frame.setSize(this.boardWidth, this.boardHeight);
	        frame.setLocationRelativeTo(null);
	        frame.setResizable(false);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setLayout(new BorderLayout());

	        textLabel.setFont(new Font("Arial", Font.BOLD, 25));
	        textLabel.setHorizontalAlignment(JLabel.CENTER);
	        textLabel.setText("Minesweeper: " + Integer.toString(this.mineCount));
	        textLabel.setOpaque(true);

	        textPanel.setLayout(new BorderLayout());
	        textPanel.add(textLabel);
	        frame.add(textPanel, BorderLayout.NORTH);

	        boardPanel.setLayout(new GridLayout(this.numRows, this.numCols)); //8x8
	        // boardPanel.setBackground(Color.green);
	        frame.add(boardPanel);
	 
	      

		 for (int r = 0; r < numRows; r++) {
	            for (int c = 0; c < numCols; c++) {
	                MineTile tile = new MineTile(r, c);
	                board[r][c] = tile;

	                tile.setFocusable(false);
	                tile.setMargin(new Insets(0, 0, 0, 0));
	                tile.setFont(new Font("Arial Unicode MS", Font.PLAIN, 45));
	                // tile.setText("💣");
	                tile.addMouseListener(new MouseAdapter() {
	                    @Override
	                    public void mousePressed(MouseEvent e) {
	                        if (gameOver) {
	                            return;
	                        }
	                        MineTile tile = (MineTile) e.getSource();

	                        //left click
	                      
	                        if (e.getButton() == MouseEvent.BUTTON1) {
	                            if (tile.getText() == "") {

	                            	if (mineList.contains(tile)) {
	                                    revealMines();
	                                }
	                            	else if(mole.contains(tile)) {
	                            		tile.setText("M"); 
	                            		molefound = true; 
	                            		molerun(tile.getR(), tile.getC()); 
	                            	}
	                                
	                            	else {
	                                    checkMine(tile.getR(), tile.getC());
	                                }
	                            }
	                        }
	                        //right click
	                        else if (e.getButton() == MouseEvent.BUTTON3) {
	                            if (tile.getText() == "" && tile.isEnabled()) {
	                                
	                            	textLabel.setText("Minesweeper: " + Integer.toString(--win));
	                            	tile.setText("F");
	                            	win();	
	                            }
	                            else if (tile.getText() == "F") {
	                            	textLabel.setText("Minesweeper: " + Integer.toString(++win));
	                            	tile.setText("");
	                            	win();
	                            }
	                        }
	                    } 
	                });

	                boardPanel.add(tile);
	                
	            }
	        }

	        frame.setVisible(true);

	        setMines();
	        setMole();
	}
	 public void setMole() {
		 mole = new ArrayList<MineTile>();
		 int molecnt = 1;
	 while(molecnt >0) {
         int r = random.nextInt(numRows-1); //0-7
         int c = random.nextInt(numCols-1);

         MineTile tile = board[r][c]; 
         if (!mineList.contains(tile)) {mole.add(tile); molecnt--; };
         
	 }
	 } 	
 
	 public void molerun(int r, int c) {
		 for(int i = c+1; i < numRows; i++ ) {
			 checktile(r,i);
		 }
		 for(int i = c+1; i >= 0; i-- ) {
			 checktile(r,i);
		 }
		 for(int i = r+1; i < numRows; i++ ) {
			 checktile(i,c);
		 }
		 for(int i = r+1; i >= 0; i-- ) {
			 checktile(i,c);
		 }
	 } 
	 public void checktile(int r, int c) {
	 MineTile tile = board[r][c];
	 if(mineList.contains(tile)) { tile.setEnabled(false); tile.setText("B"); win-- ;textLabel.setText("Minesweeper: " + Integer.toString(win));win();  } 
     else { tile.setEnabled(false); tile.setText("");  if(mole.contains(tile)) {tile.setText("M"); win();}}
 }

@Override
public void checkMine(int r, int c) {
    if (r < 0 || r >= numRows || c < 0 || c >= numCols) {
        return;
    }

    MineTile tile = board[r][c];
    if (!tile.isEnabled()) {
        return;
    }

    tile.setEnabled(false);
    if(mole.contains(tile))tile.setEnabled(true);


    int minesFound = 0;

    //top 3
    minesFound += countMine(r-1, c-1);  //top left
    minesFound += countMine(r-1, c);    //top
    minesFound += countMine(r-1, c+1);  //top right

    //left and right
    minesFound += countMine(r, c-1);    //left
    minesFound += countMine(r, c+1);    //right

    //bottom 3
    minesFound += countMine(r+1, c-1);  //bottom left
    minesFound += countMine(r+1, c);    //bottom
    minesFound += countMine(r+1, c+1);  //bottom right

    if (minesFound > 0) {
        tile.setText(Integer.toString(minesFound));
        if(mole.contains(tile)) {tile.setText(""); if(molefound)tile.setText("M");}
    }
    else {
        tile.setText("");
        
        //top 3
        checkMine(r-1, c-1);    //top left
        checkMine(r-1, c);      //top
        checkMine(r-1, c+1);    //top right

        //left and right
        checkMine(r, c-1);      //left
        checkMine(r, c+1);      //right

        //bottom 3
        checkMine(r+1, c-1);    //bottom left
        checkMine(r+1, c);      //bottom
        checkMine(r+1, c+1);    //bottom right
    }
    win();
}

}
