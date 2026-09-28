package ai;

import chessboard.Bitboard;
import chessboard.Chess;
import chessboard.ChessLogic;
import chessboard.Chessboard;
import common.Coordinate;
import common.MoveValue;

import java.util.ArrayList;
import java.util.List;

public interface MoveChooser {
    MoveValue chooseMove(Chessboard board, ChessLogic logic);



    default List<MoveValue> getPossibleMoves(Chessboard board, ChessLogic logic) {
        List<MoveValue> moves = new ArrayList<>();

        Bitboard pieces = board.getAllColourPositions(board.getTurn());
        for(Coordinate piece : pieces) {
            for(Coordinate move : logic.getPossibleMoves(piece)) {
                moves.add(new MoveValue(piece, move));
            }
        }

        return moves;
    }
}
