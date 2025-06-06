package xadrez.model;

public class Bishop extends Piece {
    public Bishop(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public char getSymbol() {
        return isWhite() ? 'B' : 'b';
    }

    @Override
    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        if (Math.abs(fromRow - toRow) != Math.abs(fromCol - toCol)) {
            return false; // Não é um movimento diagonal
        }

        int rowStep = (toRow > fromRow) ? 1 : -1;
        int colStep = (toCol > fromCol) ? 1 : -1;
        int r = fromRow + rowStep;
        int c = fromCol + colStep;

        while (r != toRow) {
            if (board.getPiece(r, c) != null) {
                return false; // Caminho bloqueado
            }
            r += rowStep;
            c += colStep;
        }
        return true;
    }
}