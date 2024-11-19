package pieces;

import java.awt.image.BufferedImage;
import main.Board;

public class Pawn extends Piece{
    private int colorIndex;

    public Pawn(Board board, int col, int row, boolean isWhite){
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.titleSize;
        this.yPos = row * board.titleSize;

        this.isWhite = isWhite;
        this.name = "Pawn";
        this.sprite = sheet.getSubimage(5*sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale)
        .getScaledInstance(board.titleSize, board.titleSize, BufferedImage.SCALE_SMOOTH);
        this.colorIndex = isWhite ? -1 : 1;
    }

    public boolean isValidMovement(int col, int row){
        if (this.isWhite){
            if (this.row - row == 1 && this.col == col){
                return true;
            }
            if (this.row == 6 && this.row - row == 2 && this.col == col){
                return true;
            }
            if (this.row - row == 1 && Math.abs(this.col - col) == 1){
                return true;
            }
        } else {
            if (row - this.row == 1 && this.col == col){
                return true;
            }
            if (this.row == 1 && row - this.row == 2 && this.col == col){
                return true;
            }
            if (row - this.row == 1 && Math.abs(this.col - col) == 1){
                return true;
            }
        }

        //en passant left
        if (board.getTileNum(col,row) == board.enPassantTile && col == this.col - 1 && row == this.row - colorIndex && board.getPiece(col, row + colorIndex) != null){ 
            return true;
        }

        return false;
    }

    public boolean moveCollidesWithPiece(int col, int row){
        if (this.isWhite){
            if (this.row - row == 1 && this.col == col){
                return board.getPiece(col, row) != null;
            }
            if (this.row == 6 && this.row - row == 2 && this.col == col){
                return board.getPiece(col, row) != null || board.getPiece(col, row + 1) != null;
            }
            if (this.row - row == 1 && Math.abs(this.col - col) == 1){
                return board.getPiece(col, row) == null;
            }
        } else {
            if (row - this.row == 1 && this.col == col){
                return board.getPiece(col, row) != null;
            }
            if (this.row == 1 && row - this.row == 2 && this.col == col){
                return board.getPiece(col, row) != null || board.getPiece(col, row - 1) != null;
            }
            if (row - this.row == 1 && Math.abs(this.col - col) == 1){
                return board.getPiece(col, row) == null;
            }
        }
        return true;
    }
}