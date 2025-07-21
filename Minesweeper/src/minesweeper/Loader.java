package minesweeper;

import javax.xml.stream.*;
import java.io.FileReader;
import java.util.ArrayList;

public class Loader {

    public static MinesweeperGame loadGame() {
        MinesweeperGame game = null;
        try {
            FileReader fileReader = new FileReader("minesweeper_save.xml");
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLStreamReader xmlReader = inputFactory.createXMLStreamReader(fileReader);

            int numRows = 0, numCols = 0, mineCount = 0;
            MineTile[][] board = null;
            ArrayList<MineTile> mineList = new ArrayList<>();

            while (xmlReader.hasNext()) {
                int eventType = xmlReader.next();
                if (eventType == XMLStreamConstants.START_ELEMENT) {
                    String elementName = xmlReader.getLocalName();

                    // Olvassuk be a beállításokat
                    if (elementName.equals("Rows")) {
                        numRows = Integer.parseInt(xmlReader.getElementText());
                    } else if (elementName.equals("Columns")) {
                        numCols = Integer.parseInt(xmlReader.getElementText());
                    } else if (elementName.equals("MineCount")) {
                        mineCount = Integer.parseInt(xmlReader.getElementText());
                    } else if (elementName.equals("Tile")) {
                        if (board == null) {
                            board = new MineTile[numRows][numCols];
                        }
                        int row = Integer.parseInt(xmlReader.getAttributeValue(null, "row"));
                        int col = Integer.parseInt(xmlReader.getAttributeValue(null, "col"));
                        boolean enabled = Boolean.parseBoolean(xmlReader.getAttributeValue(null, "enabled"));
                        String text = xmlReader.getAttributeValue(null, "text");

                        MineTile tile = new MineTile(row, col);
                        tile.setEnabled(enabled);
                        tile.setText(text);
                        board[row][col] = tile;

                        // Ha bomba, hozzáadjuk a bombák listájához
                        if ("B".equals(text)) {
                            mineList.add(tile);
                        }
                    }
                }
            }

            xmlReader.close();

            // Hozzuk létre a játékot az olvasott adatok alapján
            if (numRows > 0 && numCols > 0 && mineCount > 0 && board != null) {
                game = new MinesweeperGame(numRows, numCols, mineCount);
                game.board = board;
                game.mineCount = mineCount;
                game.mineList = mineList; // Beállítjuk a bombák listáját

                // Frissítjük a játék tábla méreteit
                game.boardWidth = numCols * 60;
                game.boardHeight = numRows * 60;

                // Betöltjük a mentett állapotot
                game.loadBoard(board, mineList);
            } else {
                throw new RuntimeException("Az XML fájl hibás vagy hiányos!");
            }
        } catch (Exception ex) {
            System.err.println("Hiba történt a betöltés során: " + ex.getMessage());
        }
        return game;
    }
}
