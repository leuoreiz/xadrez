package xadrez.model;

public class Queen extends Piece {
    public Queen(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public char getSymbol() {
        return isWhite() ? 'Q' : 'q';
    }

    @Override
    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        boolean isStraight = fromRow == toRow || fromCol == toCol;
        boolean isDiagonal = Math.abs(fromRow - toRow) == Math.abs(fromCol - toCol);

        if (!isStraight && !isDiagonal) {
            return false;
        }

        if (isStraight) {
            if (fromRow == toRow) { // Movimento Horizontal
                int start = Math.min(fromCol, toCol) + 1;
                int end = Math.max(fromCol, toCol);
                for (int c = start; c < end; c++) {
                    if (board.getPiece(fromRow, c) != null) return false;
                }
            } else { // Movimento Vertical
                int start = Math.min(fromRow, toRow) + 1;
                int end = Math.max(fromRow, toRow);
                for (int r = start; r < end; r++) {
                    if (board.getPiece(r, fromCol) != null) return false;
                }
            }
        } else { // Movimento Diagonal
            int rowStep = (toRow > fromRow) ? 1 : -1;
            int colStep = (toCol > fromCol) ? 1 : -1;
            int r = fromRow + rowStep;
            int c = fromCol + colStep;
            while (r != toRow) {
                if (board.getPiece(r, c) != null) return false;
                r += rowStep;
                c += colStep;
            }
        }
        return true;
    }
}