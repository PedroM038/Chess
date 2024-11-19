package main;

import pieces.Piece;

public class Move {
    int oldCol, oldRow, newCol, newRow;
    
    Piece piece;
    Piece capture;

    public Move(Board board, Piece piece, int newCol, int newRow){
        this.piece = piece;
        this.oldCol = piece.col;
        this.oldRow = piece.row;
        this.newCol = newCol;
        this.newRow = newRow;
        this.capture = board.getPiece(newCol, newRow);
    }
}
