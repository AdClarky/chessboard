package ai;

import chessboard.Chess;
import common.MoveValue;
import common.Pieces;

import java.util.List;
import java.util.Random;

public class RandomMoves implements MoveChooser {
    @Override
    public MoveValue chooseMove(Chess chessboard) {
        List<MoveValue> moves = getPossibleMoves(chessboard);

        Random rand = new Random();
        MoveValue move = moves.get(rand.nextInt(moves.size()));
        if(chessboard.isPromotion(move.oldPos(), move.newPos())) {
            Pieces promotionPiece = List.of(Pieces.QUEEN, Pieces.ROOK, Pieces.BISHOP, Pieces.KNIGHT).get(rand.nextInt(4));
            move = new MoveValue(move, promotionPiece);
        }
        return move;
    }
}
