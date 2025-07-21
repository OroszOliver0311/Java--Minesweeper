package minesweeper;

import javax.swing.*;
import javax.xml.stream.*;
import java.io.FileWriter;

public class Saver {

    public static void saveGame(MinesweeperGame game, JFrame frame) {
        try {
            // XML mentés
            FileWriter fileWriter = new FileWriter("minesweeper_save.xml");
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter xmlWriter = outputFactory.createXMLStreamWriter(fileWriter);

            xmlWriter.writeStartDocument("1.0");
            xmlWriter.writeStartElement("MinesweeperGame");

            xmlWriter.writeStartElement("Settings");
            xmlWriter.writeStartElement("Rows");
            xmlWriter.writeCharacters(String.valueOf(game.numRows));
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("Columns");
            xmlWriter.writeCharacters(String.valueOf(game.numCols));
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("MineCount");
            xmlWriter.writeCharacters(String.valueOf(game.mineCount));
            xmlWriter.writeEndElement();
            xmlWriter.writeEndElement(); // Settings vége

            xmlWriter.writeStartElement("Board");
            for (int r = 0; r < game.numRows; r++) {
                for (int c = 0; c < game.numCols; c++) {
                    xmlWriter.writeStartElement("Tile");
                    xmlWriter.writeAttribute("row", String.valueOf(r));
                    xmlWriter.writeAttribute("col", String.valueOf(c));
                    xmlWriter.writeAttribute("enabled", String.valueOf(game.board[r][c].isEnabled()));
                    xmlWriter.writeAttribute("text", game.board[r][c].getText());
                    xmlWriter.writeEndElement(); // Tile vége
                }
            }
            xmlWriter.writeEndElement(); // Board vége

            xmlWriter.writeEndElement(); // MinesweeperGame vége
            xmlWriter.writeEndDocument();
            xmlWriter.close();

            JOptionPane.showMessageDialog(frame, "Állapot mentve! Kilépés...");
            frame.dispose(); // Ablak bezárása
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Hiba történt a mentés során: " + ex.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
        }
    }
}
