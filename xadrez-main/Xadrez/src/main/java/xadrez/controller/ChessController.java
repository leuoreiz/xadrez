// Arquivo: Xadrez/src/main/java/xadrez/controller/ChessController.java
package xadrez.controller;

import xadrez.model.Board;
import xadrez.model.King;
import xadrez.model.Piece;
import xadrez.view.ChessView;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessController {
    private final Board board;
    private final ChessView view;
    private boolean whiteTurn = true;
    private int[] selectedSquare = null;

    public ChessController(Board board, ChessView view) {
        this.board = board;
        this.view = view;
        this.view.setButtonListener(new BoardClickListener());
        this.view.updateBoard(boardMatrix());
        this.view.setStatusMessage("Turno do jogador Branco");
    }

    private class BoardClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            int row = (int) button.getClientProperty("row");
            int col = (int) button.getClientProperty("col");

            if (selectedSquare == null) {
                Piece piece = board.getPiece(row, col);
                if (piece == null || piece.isWhite() != whiteTurn) {
                    view.setStatusMessage("Selecione uma peça válida.");
                    return;
                }

                selectedSquare = new int[]{row, col};
                view.highlightSquare(row, col, Color.YELLOW);
            } else {
                int fromRow = selectedSquare[0];
                int fromCol = selectedSquare[1];

                

                if (tryMove(fromRow, fromCol, row, col)) {
                    whiteTurn = !whiteTurn;
                    view.setStatusMessage("Turno do jogador " + (whiteTurn ? "Branco" : "Preto"));
                    view.updateBoard(boardMatrix());
                    // PASSO 2.1: Chame a verificação de fim de jogo aqui
                    checkGameOver();
                } else {
                    view.setStatusMessage("Movimento inválido.");
                }
                selectedSquare = null;
            }
        }
    }
    
    // PASSO 2.2: Adicione este novo método
    private void checkGameOver() {
        boolean whiteKingFound = false;
        boolean blackKingFound = false;

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece piece = board.getPiece(r, c);
                if (piece instanceof King) {
                    if (piece.isWhite()) {
                        whiteKingFound = true;
                    } else {
                        blackKingFound = true;
                    }
                }
            }
        }

        if (!whiteKingFound) {
            endGame("Preto"); // Jogador Preto venceu
        } else if (!blackKingFound) {
            endGame("Branco"); // Jogador Branco venceu
        }
    }

    // PASSO 2.3: Adicione este novo método para finalizar o jogo
    private void endGame(String winner) {
        view.setStatusMessage("Fim de jogo! O jogador " + winner + " venceu.");
        view.disableBoard();
    }

    private boolean tryMove(int fromRow, int fromCol, int toRow, int toCol) {
        Piece piece = board.getPiece(fromRow, fromCol);
        Piece destination = board.getPiece(toRow, toCol);

        if (destination != null && destination.isWhite() == piece.isWhite()) {
            return false;
        }

        if (!piece.isValidMove(board, fromRow, fromCol, toRow, toCol)) {
            return false;
        }

        board.setPiece(toRow, toCol, piece);
        board.setPiece(fromRow, fromCol, null);

        return true;
    }

    private Piece[][] boardMatrix() {
        Piece[][] matrix = new Piece[8][8];
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
                matrix[r][c] = board.getPiece(r, c);
        return matrix;
    }
}