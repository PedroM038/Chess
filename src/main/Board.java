package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.*;
import pieces.*;

public class Board extends JPanel {
    
    public int titleSize = 85;
    private final int cols = 8;
    private final int rows = 8;

    ArrayList<Piece> pieceList = new ArrayList<>();

    public Piece selectedPiece;

    Input input = new Input(this);

    public int enPassantTile = -1;

    public Board() {
        this.setPreferredSize(new Dimension(cols * titleSize, rows * titleSize));
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
        initialize();
    }

    public Piece getPiece(int col, int row){
        for (Piece piece : pieceList){
            if (piece.col == col && piece.row == row){
                return piece;
            }
        }
        return null;
    }

    public void makeMove(Move move){

        if(move.piece.name.equals("Pawn")) {
            movePawn(move);
        } else {
            move.piece.col = move.newCol;
            move.piece.row = move.newRow;
            move.piece.xPos = move.newCol * titleSize;
            move.piece.yPos = move.newRow * titleSize;
            move.piece.isFirstMove = false;
            capture(move);
        }
    
    }

    public int getTileNum(int col, int row) {
        return row * cols + col;
    }

    private void movePawn(Move move){

        //en passant
        int colorIndex = move.piece.isWhite ? 1 : -1;

        if(getTileNum(move.newCol, move.newRow) == enPassantTile){
            move.capture = getPiece(move.newCol, move.newRow + colorIndex);
        } 
        if (Math.abs(move.piece.row - move.newRow) == 2){
            enPassantTile = getTileNum(move.newCol, move.newRow + colorIndex);
        } else {
            enPassantTile = -1;
        }

        //promotions
        colorIndex = move.piece.isWhite ? 0 : 7;
        if (move.newRow == colorIndex){
            promotePawn(move);
        }

        move.piece.col = move.newCol;
        move.piece.row = move.newRow;
        move.piece.xPos = move.newCol * titleSize;
        move.piece.yPos = move.newRow * titleSize;
        move.piece.isFirstMove = false;
        capture(move);
    }

    private void promotePawn(Move move){
        pieceList.remove(move.piece);
        pieceList.add(new Queen(this, move.newCol, move.newRow, move.piece.isWhite));
        capture(move);
    }

    public void capture(Move move){
        pieceList.remove(move.capture);
    }

    public boolean isValidMove(Move move){ 
        if(sameTeam(move.piece, move.capture)){
            return false;
        }

        if(!move.piece.isValidMovement(move.newCol, move.newRow)){
            return false;
        }

        if(move.piece.moveCollidesWithPiece(move.newCol, move.newRow)){
            return false;
        }
        return true;
    }

    public boolean sameTeam(Piece piece, Piece target){
        if(piece == null || target == null){
            return false;
        }
        return piece.isWhite == target.isWhite;
    }

    Piece findKing(boolean isWhite){
        for (Piece piece : pieceList){
            if (piece.name.equals("King") && piece.isWhite == isWhite){
                return piece;
            }
        }
        return null;
    }

    private void initialize() {
        addPieces();
    }

    private void addPieces(){
        
        //black pieces
        pieceList.add(new Rook(this, 0, 0, false));
        pieceList.add(new Knight(this, 1, 0, false));
        pieceList.add(new pieces.Bishop(this, 2, 0, false));
        pieceList.add(new pieces.Queen(this, 3, 0, false));
        pieceList.add(new pieces.King(this, 4, 0, false));
        pieceList.add(new pieces.Bishop(this, 5, 0, false));
        pieceList.add(new pieces.Knight(this, 6, 0, false));
        pieceList.add(new Rook(this, 7, 0, false));
        for (int i = 0; i < 8; i++){
            pieceList.add(new pieces.Pawn(this, i, 1, false));
        }

        //white pieces
        pieceList.add(new Rook(this, 0, 7, true));
        pieceList.add(new Knight(this, 1, 7, true));
        pieceList.add(new pieces.Bishop(this, 2, 7, true));
        pieceList.add(new pieces.Queen(this, 3, 7, true));
        pieceList.add(new pieces.King(this, 4, 7, true));
        pieceList.add(new pieces.Bishop(this, 5, 7, true));
        pieceList.add(new pieces.Knight(this, 6, 7, true));
        pieceList.add(new Rook(this, 7, 7, true));
        for (int i = 0; i < 8; i++){
            pieceList.add(new pieces.Pawn(this, i, 6, true));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        //paint board
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                g2d.setColor((c+r) % 2 == 0 ? new Color(227,198,181) : new Color(157,105,53));
                g2d.fillRect(c * titleSize, r * titleSize, titleSize, titleSize);
            }
        }

        //paint highlighted
        if(selectedPiece != null){
            for (int r = 0; r < rows; r++){
                for (int c = 0; c < cols; c++){
                    if(isValidMove(new Move(this, selectedPiece, c, r))){
                        g2d.setColor(new Color(68, 180, 57, 190));
                        g2d.fillRect(c * titleSize, r * titleSize, titleSize, titleSize);
                    }
                }
            }
        }

        //paint pieces

        for (Piece piece : pieceList){
            piece.paint(g2d);
        }
    }
}
