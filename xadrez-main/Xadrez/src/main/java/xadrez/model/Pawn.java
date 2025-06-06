package xadrez.model;

public class Pawn extends Piece {
    public Pawn(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public char getSymbol() {
        return isWhite() ? 'P' : 'p';
    }

    @Override
    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        Piece destinationPiece = board.getPiece(toRow, toCol);
        int direction = isWhite() ? -1 : 1;

        // Movimento de 1 casa para frente
        if (fromCol == toCol && destinationPiece == null && toRow == fromRow + direction) {
            return true;
        }

        // Movimento inicial de 2 casas
        boolean isStartingRow = isWhite() ? (fromRow == 6) : (fromRow == 1);
        if (isStartingRow && fromCol == toCol && destinationPiece == null && toRow == fromRow + 2 * direction) {
            if (board.getPiece(fromRow + direction, fromCol) == null) {
                return true; // Caminho livre
            }
        }

        // Captura
        if (Math.abs(fromCol - toCol) == 1 && toRow == fromRow + direction && destinationPiece != null) {
            return true;
        }

        return false;
    }
}