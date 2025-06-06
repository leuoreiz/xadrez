package xadrez.model;

public class Board {
    private final Piece[][] board;

    public Piece[][] getBoard() {
        return board;
    }

    public Board() {
        board = new Piece[8][8];
        initializeBoard();
    }

    private void initializeBoard() {
        // Peças pretas
        board[0][0] = new Rook(false);
        board[0][1] = new Knight(false);
        board[0][2] = new Bishop(false);
        board[0][3] = new Queen(false);
        board[0][4] = new King(false);
        board[0][5] = new Bishop(false);
        board[0][6] = new Knight(false);
        board[0][7] = new Rook(false);
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(false);
        }

        // Peças brancas
        board[7][0] = new Rook(true);
        board[7][1] = new Knight(true);
        board[7][2] = new Bishop(true);
        board[7][3] = new Queen(true);
        board[7][4] = new King(true);
        board[7][5] = new Bishop(true);
        board[7][6] = new Knight(true);
        board[7][7] = new Rook(true);
        for (int i = 0; i < 8; i++) {
            board[6][i] = new Pawn(true);
        }
    }

    public void printBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null) {
                    System.out.print(piece.getSymbol() + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    public Piece getPiece(int row, int col) {
        if (row < 0 || row >= 8 || col < 0 || col >= 8) return null;
        return board[row][col];
    }

    public void setPiece(int row, int col, Piece piece) {
        if (row >= 0 && row < 8 && col >= 0 && col < 8) {
            board[row][col] = piece;
        }
    }
}
