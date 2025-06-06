package xadrez.model;

public class Rook extends Piece {
    public Rook(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public char getSymbol() {
        return isWhite() ? 'R' : 'r';
    }

    @Override
    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        if (fromRow != toRow && fromCol != toCol) {
            return false; // Não é um movimento vertical ou horizontal
        }

        if (fromRow == toRow) { // Movimento Horizontal
            int start = Math.min(fromCol, toCol) + 1;
            int end = Math.max(fromCol, toCol);
            for (int c = start; c < end; c++) {
                if (board.getPiece(fromRow, c) != null) {
                    return false; // Caminho bloqueado
                }
            }
        } else { // Movimento Vertical
            int start = Math.min(fromRow, toRow) + 1;
            int end = Math.max(fromRow, toRow);
            for (int r = start; r < end; r++) {
                if (board.getPiece(r, fromCol) != null) {
                    return false; // Caminho bloqueado
                }
            }
        }
        return true;
    }
}