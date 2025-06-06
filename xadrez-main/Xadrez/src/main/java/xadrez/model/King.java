package xadrez.model;

public class King extends Piece {
    public King(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public char getSymbol() {
        return isWhite() ? 'K' : 'k';
    }

    @Override
    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        int rowDiff = Math.abs(fromRow - toRow);
        int colDiff = Math.abs(fromCol - toCol);
        // Não implementa roque ou validação de xeque por simplicidade
        return rowDiff <= 1 && colDiff <= 1;
    }
}