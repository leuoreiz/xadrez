package xadrez.controller;

import xadrez.model.Board;
import xadrez.model.King;
import xadrez.model.Piece;
import xadrez.view.ChessView;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ChessController {
    private Board board;
    private final ChessView view;
    private boolean whiteTurn = true;
    private int[] selectedSquare = null;
    private String whitePlayer = "Branco";
    private String blackPlayer = "Preto";

    private static final String GAME_STATE_FILE = "game.ser";

    public ChessController(Board board, ChessView view) {
        this.board = board;
        this.view = view;

        // Adiciona listeners para os botões do tabuleiro e de controle
        this.view.setButtonListener(new BoardClickListener());
        this.view.setNewGameListener(e -> startNewGame());
        this.view.setSaveListener(e -> saveGame());
        this.view.setLoadListener(e -> loadGame());

        startNewGame(); // Inicia um novo jogo ao abrir
    }

    private void startNewGame() {
        this.board = new Board(); // Cria um novo tabuleiro
        view.enableBoard();
        this.whiteTurn = true;
        this.selectedSquare = null;
        this.view.updateBoard(boardMatrix());
        this.view.setStatusMessage("Turno do jogador Branco");
    }

    // Salva o estado do jogo
    private void saveGame() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(GAME_STATE_FILE))) {
            oos.writeObject(board);
            oos.writeBoolean(whiteTurn);
            //view.setStatusMessage("Jogo salvo com sucesso!");
        } catch (IOException e) {
            view.setStatusMessage("Erro ao salvar o jogo: " + e.getMessage());
        }
    }

    // Carrega o estado do jogo
    private void loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(GAME_STATE_FILE))) {
            board = (Board) ois.readObject();
            whiteTurn = ois.readBoolean();
            view.updateBoard(boardMatrix());
            view.setStatusMessage("Jogo carregado. Turno do jogador " + (whiteTurn ? "Branco" : "Preto"));
        } catch (IOException | ClassNotFoundException e) {
            view.setStatusMessage("Erro ao carregar o jogo: " + e.getMessage());
        }
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
                    saveGame(); // Salvamento automático
                    checkGameOver();
                } else {
                    view.setStatusMessage("Movimento inválido.");
                }
                // Limpa o destaque da casa
                view.updateBoard(boardMatrix());
                selectedSquare = null;
            }
        }
    }

    private void checkGameOver() {
        boolean whiteKingFound = false;
        boolean blackKingFound = false;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece piece = board.getPiece(r, c);
                if (piece instanceof King) {
                    if (piece.isWhite()) whiteKingFound = true;
                    else blackKingFound = true;
                }
            }
        }

        if (!whiteKingFound) {
            endGame("Preto");
        } else if (!blackKingFound) {
            endGame("Branco");
        }
    }
    
    private void endGame(String winner) {
        view.setStatusMessage("Fim de jogo! O jogador " + winner + " venceu.");
        view.disableBoard();
        RankingManager.updateRanking(winner); // Atualiza o ranking
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