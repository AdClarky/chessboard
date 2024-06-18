package chessboard;

import java.util.ArrayList;
import java.util.List;

public class TempMove {
    private Board board;
    private final List<Move> tempMoves = new ArrayList<>(3);
    private final ArrayList<Piece> tempPieces = new ArrayList<>(2);


    public TempMove(int x, int y, Piece piece, Board board){
        this.board = board;
        Iterable<Move> moves = piece.getMoves(x, y, board);
        for(Move move : moves){
            if(!board.isSquareBlank(move.newX(), move.newY())){ // if taking
                tempMoves.add(new Move(move.newX(), move.newY(), move.newX(), move.newY()));
                tempPieces.add(board.getPiece(move.newX(), move.newY()));
            }
            if(move.newX() == move.oldX() && move.newY() == move.oldY()){ // promotion
                tempPieces.add(board.getPiece(move.newX(), move.newY()));
                if(move.newY() == 0)
                    board.setSquare(move.newX(), 0, new Queen(move.newX(), 0, Piece.BLACK_PIECE));
                else
                    board.setSquare(move.newX(), 0, new Queen(move.newX(), move.newY(), Piece.WHITE_PIECE));
            }
            tempMoves.add(new Move(move.oldX(), move.oldY(), move.newX(), move.newY()));
            Piece temp = board.getPiece(move.oldX(), move.oldY());
            board.setSquare(move.oldX(), move.oldY(), new Blank(move.oldX(), move.oldY()));
            temp.setX(move.newX());
            temp.setY(move.newY());
            board.setSquare(move.newX(), move.newY(), temp);
        }
    }

    public void undo(){
        for(Move move : tempMoves.reversed()){
            if(move.oldY() == move.newY() && move.newX() == move.oldX()) {
                board.setSquare(move.newX(), move.newY(), tempPieces.getLast());
                tempPieces.removeLast();
            }
            else {
                board.setSquare(move.oldX(), move.oldY(), board.getPiece(move.newX(), move.newY()));
                board.setSquare(move.newX(), move.newY(), new Blank(move.newX(), move.newY()));
            }

            board.getPiece(move.oldX(), move.oldY()).setX(move.oldX());
            board.getPiece(move.oldX(), move.oldY()).setY(move.oldY());
        }
        tempMoves.clear();
    }
}
