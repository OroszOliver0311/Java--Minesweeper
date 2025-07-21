package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MinesweeperMenuSwing extends JFrame {

    private boolean savedGameExists = false;
    private boolean customMapExists = false;

    public MinesweeperMenuSwing() {
        setTitle("Aknakereső Menü");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1));

        // Gombok létrehozása
        JButton continueButton = new JButton("Folytatás");
        JButton newGameButton = new JButton("Új játék");
        JButton minesweeperPlusButton = new JButton("Aknakereső+");
        JButton customButton = new JButton("Saját");
        JButton mapEditorButton = new JButton("Pályaszerkesztő");
        JButton exitButton = new JButton("Kilépés");

        // Gombok hozzáadása a frame-hez
        add(continueButton);
        add(newGameButton);
        add(minesweeperPlusButton);
        add(customButton);
        add(mapEditorButton);
        add(exitButton);

        // Gombokhoz tartozó eseménykezelők
        continueButton.addActionListener(e -> continueGame());
        newGameButton.addActionListener(e -> newGame());
        minesweeperPlusButton.addActionListener(e -> minesweeperPlus());
        customButton.addActionListener(e -> loadCustomMap());
        mapEditorButton.addActionListener(e -> mapEditor());
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void continueGame() {
        MinesweeperGame game = Loader.loadGame();
        game.loadBoard(game.board, game.mineList);
     }
    

    private void newGame() {

        this.setVisible(false);
        SwingUtilities.invokeLater(() -> {
            MinesweeperGame game = new MinesweeperGame(16,16,10);
            game.map();
        });
        
    }

    private void minesweeperPlus() {
    	this.setVisible(false);
        SwingUtilities.invokeLater(() -> {
            Gameplus gamep = new Gameplus(16,16,10);
            gamep.map();
        });
    }

    private void loadCustomMap() {
        if (customMapExists) {
            JOptionPane.showMessageDialog(this, "Saját pálya betöltése...");
            // További logika a saját pálya betöltéséhez
        } else {
            JOptionPane.showMessageDialog(this, "Nincs elérhető saját pálya.");
        }
    }

    private void mapEditor() {
        String size = JOptionPane.showInputDialog(this, "Add meg a pálya méretét (pl. 16x16):");
        if (size != null && !size.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Üres pálya generálva. Bombákat helyezhetsz le.");
            // Logika az üres pályára és bombák lehelyezésére
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MinesweeperMenuSwing menu = new MinesweeperMenuSwing();
            menu.setVisible(true);
        });
    	 
    }
}
