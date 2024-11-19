package pieces;

import java.awt.image.BufferedImage;
import main.Board;

public class King extends Piece{
    public King(Board board, int col, int row, boolean isWhite){
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.titleSize;
        this.yPos = row * board.titleSize;

        this.isWhite = isWhite;
        this.name = "King";
        this.sprite = sheet.getSubimage(0*sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale)
        .getScaledInstance(board.titleSize, board.titleSize, BufferedImage.SCALE_SMOOTH);
    }

    public boolean isValidMovement(int col, int row){
        return Math.abs(this.col - col) <= 1 && Math.abs(this.row - row) <= 1;
    }

    public boolean moveCollidesWithPiece(int col, int row){
        return false;
    }
}
