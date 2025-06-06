package xadrez;

import xadrez.controller.ChessController;
import xadrez.model.Board;
import xadrez.view.ChessView;

public class Main {
    public static void main(String[] args) {
        // Aplicações Swing devem ser executadas na Event Dispatch Thread (EDT)
        javax.swing.SwingUtilities.invokeLater(() -> {
            Board board = new Board();
            ChessView view = new ChessView();
            // Instancia o Controller para conectar o Model e a View
            new ChessController(board, view);
        });
    }
}