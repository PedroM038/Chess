package pieces;

import java.awt.image.BufferedImage;
import main.Board;

public class Bishop extends Piece{
    public Bishop(Board board, int col, int row, boolean isWhite){
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.titleSize;
        this.yPos = row * board.titleSize;

        this.isWhite = isWhite;
        this.name = "Bishop";
        this.sprite = sheet.getSubimage(2*sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale)
        .getScaledInstance(board.titleSize, board.titleSize, BufferedImage.SCALE_SMOOTH);
    }

    public boolean isValidMovement(int col, int row){
        return Math.abs(this.col - col) == Math.abs(this.row - row);
    }

    public boolean moveCollidesWithPiece(int col, int row){
        int colDir = col > this.col ? 1 : -1;
        int rowDir = row > this.row ? 1 : -1;

        int i = this.col + colDir;
        int j = this.row + rowDir;

        while (i != col && j != row){
            if (board.getPiece(i, j) != null){
                return true;
            }
            i += colDir;
            j += rowDir;
        }
        return false;
    }
}