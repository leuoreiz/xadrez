package xadrez.view;

import xadrez.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChessView extends JFrame {
    private final JButton[][] boardButtons = new JButton[8][8];
    private final JLabel statusLabel = new JLabel("Bem-vindo ao Xadrez!");

    public ChessView() {
        setTitle("Xadrez");
        setSize(600, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        Font pieceFont = new Font("Serif", Font.PLAIN, 36);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = new JButton();
                button.setFont(pieceFont);
                button.setFocusPainted(false);
                button.putClientProperty("row", row);
                button.putClientProperty("col", col);
                boardButtons[row][col] = button;
                boardPanel.add(button);
            }
        }

        add(boardPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
        setVisible(true);
    }

    public void updateBoard(Piece[][] board) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                JButton button = boardButtons[row][col];
                if (piece != null) {
                    button.setText(getUnicodeSymbol(piece));
                } else {
                    button.setText("");
                }
                button.setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.GRAY);
            }
        }
    }
    public void disableBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boardButtons[row][col].setEnabled(false);
            }
        }
    }

    public void highlightSquare(int row, int col, Color color) {
        boardButtons[row][col].setBackground(color);
    }

    public void setStatusMessage(String message) {
        statusLabel.setText(message);
    }

    public void setButtonListener(ActionListener listener) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boardButtons[row][col].addActionListener(listener);
            }
        }
    }

    public int getRowFromButton(JButton button) {
        return (int) button.getClientProperty("row");
    }

    public int getColFromButton(JButton button) {
        return (int) button.getClientProperty("col");
    }

    private String getUnicodeSymbol(Piece piece) {
        boolean isWhite = piece.isWhite();
        if (piece instanceof King) return isWhite ? "♔" : "♚";
        if (piece instanceof Queen) return isWhite ? "♕" : "♛";
        if (piece instanceof Rook) return isWhite ? "♖" : "♜";
        if (piece instanceof Bishop) return isWhite ? "♗" : "♝";
        if (piece instanceof Knight) return isWhite ? "♘" : "♞";
        if (piece instanceof Pawn) return isWhite ? "♙" : "♟";
        return "?";
    }
}
