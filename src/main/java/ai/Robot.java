package ai;

import chessboard.ChessInterface;
import common.BoardListener;
import common.Coordinate;
import common.MoveValue;
import common.PieceColour;
import exception.InvalidMoveException;

public class Robot implements BoardListener {
    private final PieceColour colour;
    private final ChessInterface board;
    private final MoveChooser moveChooser;

    public Robot(ChessInterface board, PieceColour colour, MoveChooser moveChooser){
        this.board = board;
        this.colour = colour;
        this.moveChooser = moveChooser;
    }

    @Override
    public void moveMade(Coordinate oldPos, Coordinate newPos) {
        if(board.getCurrentTurn().equals(colour)){
            MoveValue move = moveChooser.chooseMove(board);
            try{
                board.makeMove(move.oldPos(), move.newPos());
            } catch (InvalidMoveException e){
                throw new RuntimeException("ERROR WHEN PICKING NEW MOVE");
            }
        }
    }

    @Override
    public void checkmate(Coordinate kingPos) {

    }

    @Override
    public void draw(Coordinate whitePos, Coordinate blackPos) {

    }

    @Override
    public void boardChanged(Coordinate oldPos, Coordinate newPos) {

    }

    @Override
    public void promotion() {

    }
}
