package xadrez.model;

public abstract class Piece {
    private final boolean isWhite;

    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public abstract char getSymbol();

    public abstract boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol);
}