package pieces;

import java.awt.image.BufferedImage;
import main.Board;

public class Queen extends Piece{
    public Queen(Board board, int col, int row, boolean isWhite){
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.titleSize;
        this.yPos = row * board.titleSize;

        this.isWhite = isWhite;
        this.name = "Queen";
        this.sprite = sheet.getSubimage(1*sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale)
        .getScaledInstance(board.titleSize, board.titleSize, BufferedImage.SCALE_SMOOTH);
    }

    public boolean isValidMovement(int col, int row){
        return Math.abs(this.col - col) == Math.abs(this.row - row) || this.col == col || this.row == row;
    }

    public boolean moveCollidesWithPiece(int col, int row){
        if (this.col == col || this.row == row){
            //left
            if (this.col > col){
                for (int i = this.col - 1; i > col; i--){
                    if (board.getPiece(i, this.row) != null){
                        return true;
                    }
                }
            }

            //right
            if (this.col < col){
                for (int i = this.col + 1; i < col; i++){
                    if (board.getPiece(i, this.row) != null){
                        return true;
                    }
                }
            }

            //up
            if (this.row > row){
                for (int i = this.row - 1; i > row; i--){
                    if (board.getPiece(this.col, i) != null){
                        return true;
                    }
                }
            }

            //down
            if (this.row < row){
                for (int i = this.row + 1; i < row; i++){
                    if (board.getPiece(this.col, i) != null){
                        return true;
                    }
                }
            }
        } else {
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
        }
        return false;
    }
}