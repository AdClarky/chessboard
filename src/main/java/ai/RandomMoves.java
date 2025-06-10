package ai;

import chessboard.Chess;
import common.Coordinate;
import common.MoveValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class RandomMoves implements MoveChooser {
    @Override
    public MoveValue chooseMove(Chess chessboard) {
        List<MoveValue> moves = getPossibleMoves(chessboard);

        Random rand = new Random();
        return moves.get(rand.nextInt(moves.size()));
    }

    private List<MoveValue> getPossibleMoves(Chess chessboard) {
        List<MoveValue> moves = new ArrayList<>();

        Collection<Coordinate> pieces = chessboard.getAllColourPieces(chessboard.getCurrentTurn());
        for(Coordinate piece : pieces) {
            for(Coordinate move : chessboard.getPossibleMoves(piece)) {
                moves.add(new MoveValue(piece, move));
            }
        }

        return moves;
    }
}
