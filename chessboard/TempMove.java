package chessboard;

import java.util.ArrayList;
import java.util.List;

public class TempMove {
    private final Board board;
    private final Piece piece;
    private final int x;
    private final int y;
    private final List<Move> moves = new ArrayList<>(3);
    private final ArrayList<Piece> tempPieces = new ArrayList<>(2);


    public TempMove(int x, int y, Piece piece, Board board){
        this.x = x;
        this.y = y;
        this.piece = piece;
        this.board = board;
        makeMove();
    }

    public void makeMove(){
        Iterable<Move> moves = piece.getMoves(x, y, board);
        for(Move move : moves){
            if(!board.isSquareBlank(move.newX(), move.newY())){ // if taking
                this.moves.add(new Move(move.newX(), move.newY(), move.newX(), move.newY()));
                tempPieces.add(board.getPiece(move.newX(), move.newY()));
                board.getColourPieces(tempPieces.getLast()).remove(tempPieces.getLast());
            }
            if(move.newX() == move.oldX() && move.newY() == move.oldY()){ // promotion
                tempPieces.add(board.getPiece(move.oldX(), move.oldY()));
                board.getColourPieces(tempPieces.getLast()).remove(tempPieces.getLast());
                if(move.oldY() == 0)
                    board.setSquare(move.oldX(), 0, new Queen(move.oldX(), 0, Piece.BLACK_PIECE));
                else
                    board.setSquare(move.oldX(), move.oldY(), new Queen(move.oldX(), move.oldY(), Piece.WHITE_PIECE));
                board.getColourPieces(board.getPiece(move.oldX(), move.oldY())).add(board.getPiece(move.oldX(), move.oldY()));
            }else{
                this.moves.add(new Move(move.oldX(), move.oldY(), move.newX(), move.newY()));
                board.setSquare(move.newX(), move.newY(), board.getPiece(move.oldX(), move.oldY()));
                board.setSquare(move.oldX(), move.oldY(), new Blank(move.oldX(), move.oldY()));
            }
            board.getPiece(move.newX(), move.newY()).setX(move.newX());
            board.getPiece(move.newX(), move.newY()).setY(move.newY());
        }
    }

    public void undo(){
        for(Move move : moves.reversed()){
            if(move.oldY() == move.newY() && move.newX() == move.oldX()) {
                if(tempPieces.getLast() instanceof Pawn pawn && (pawn.getY() == 7 || pawn.getY() == 0)) // if it was a promotion
                    board.getColourPieces(tempPieces.getLast()).remove(board.getPiece(move.oldX(), move.oldY()));
                board.setSquare(move.newX(), move.newY(), tempPieces.getLast());
                board.getColourPieces(tempPieces.getLast()).add(tempPieces.getLast());
                tempPieces.removeLast();
            }
            else {
                board.setSquare(move.oldX(), move.oldY(), board.getPiece(move.newX(), move.newY()));
                board.setSquare(move.newX(), move.newY(), new Blank(move.newX(), move.newY()));
            }
            board.getPiece(move.oldX(), move.oldY()).setX(move.oldX());
            board.getPiece(move.oldX(), move.oldY()).setY(move.oldY());
        }
    }

    public int getX() {return x;}
    public int getY() {return y;}
    public Piece getPiece() {return piece;}
    public List<Move> getMoves() {return moves;}
}
