package ai;

import chessboard.Bitboard;
import chessboard.Chess;
import common.Coordinate;
import common.MoveValue;

import java.util.ArrayList;
import java.util.List;

public interface MoveChooser {
    MoveValue chooseMove(Chess chessboard);



    default List<MoveValue> getPossibleMoves(Chess chessboard) {
        List<MoveValue> moves = new ArrayList<>();

        Bitboard pieces = chessboard.getAllColourPieces(chessboard.getCurrentTurn());
        for(Coordinate piece : pieces) {
            for(Coordinate move : chessboard.getPossibleMoves(piece)) {
                moves.add(new MoveValue(piece, move));
            }
        }

        return moves;
    }
}
