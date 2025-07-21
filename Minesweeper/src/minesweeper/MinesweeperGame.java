package minesweeper;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class MinesweeperGame {

  protected int numRows;
  protected int numCols;
  protected  int boardWidth;
  protected int boardHeight;
  protected int mineCount; 
	
    JFrame frame = new JFrame("Minesweeper");
	JLabel textLabel = new JLabel();
	JPanel textPanel = new JPanel();
	JPanel boardPanel = new JPanel();
	MineTile[][] board;

	
	ArrayList<MineTile> mineList;
	Random random = new Random();
    int win;
	boolean gameOver = false;     

	public MinesweeperGame(int nr, int nc, int nm) {
		this.numCols = nc;
		this.numRows = nr;
		this.mineCount = nm;
		this.boardWidth = nc * 60;
		this.boardHeight = nr * 60;
		this.board = new MineTile[numRows][numCols];
        win = nm;	
	}

  

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
        JButton saveExitButton = new JButton("Mentés és Kilépés");
        saveExitButton.setFont(new Font("Arial", Font.BOLD, 20));       
        saveExitButton.addActionListener(e -> Saver.saveGame(this, frame));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveExitButton);

        frame.add(buttonPanel, BorderLayout.SOUTH); // Gomb hozzáadása az ablak aljára
        frame.setVisible(true);
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
    }
public void loadBoard(MineTile[][] loadedBoard, ArrayList<MineTile> loadedMines) {
    for (int r = 0; r < numRows; r++) {
        for (int c = 0; c < numCols; c++) {
            MineTile tile = loadedBoard[r][c];
            board[r][c] = tile;

            // Frissítjük a mező szövegét és engedélyezett állapotát
            board[r][c].setEnabled(tile.isEnabled());
            board[r][c].setText(tile.getText());

            // Ha a mező bomba, akkor hozzáadjuk a bombák listájához
            if ("B".equals(tile.getText())) {
                mineList.add(board[r][c]);
            }
        }
    }

    // Frissítjük a bombák számát és a nyerési állapotot
    win = mineCount - mineList.size();
    textLabel.setText("Minesweeper: " + Integer.toString(win));
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
    JButton saveExitButton = new JButton("Mentés és Kilépés");
    saveExitButton.setFont(new Font("Arial", Font.BOLD, 20));       
    saveExitButton.addActionListener(e -> Saver.saveGame(this, frame));
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(saveExitButton);

    frame.add(buttonPanel, BorderLayout.SOUTH); // Gomb hozzáadása az ablak aljára
    frame.setVisible(true);
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

}






   public void setMines() {
        mineList = new ArrayList<MineTile>();

        // mineList.add(board[2][2]);
        // mineList.add(board[2][3]);
        // mineList.add(board[5][6]);
        // mineList.add(board[3][4]);
        // mineList.add(board[1][1]);
        int mineLeft = mineCount;
        while (mineLeft > 0) {
            int r = random.nextInt(numRows); //0-7
            int c = random.nextInt(numCols);

            MineTile tile = board[r][c]; 
            if (!mineList.contains(tile)) {
                mineList.add(tile);
                mineLeft -= 1;
            }
        }
    }

    public void revealMines() {
        for (int i = 0; i < mineList.size(); i++) {
            MineTile tile = mineList.get(i);
            tile.setText("B");
        }

        gameOver = true;
        textLabel.setText("Game Over!");
    }

    public void checkMine(int r, int c) {
        if (r < 0 || r >= numRows || c < 0 || c >= numCols) {
            return;
        }

        MineTile tile = board[r][c];
        if (!tile.isEnabled()) {
            return;
        }
        tile.setEnabled(false);

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

    public int countMine(int r, int c) {
        if (r < 0 || r >= numRows || c < 0 || c >= numCols) {
            return 0;
        }
        if (mineList.contains(board[r][c])) {
            return 1;
        }
        return 0;
    }
  public void win() {if (win == 0) {
        gameOver = true;
        textLabel.setText("Mines Cleared!");}
    }
}
