package ai;

import chessboard.ChessLogic;
import chessboard.Chessboard;
import common.MoveValue;
import common.Pieces;

import java.util.List;
import java.util.Random;

public class RandomMoves implements MoveChooser {
    @Override
    public MoveValue chooseMove(Chessboard board, ChessLogic logic) {
        List<MoveValue> moves = getPossibleMoves(board, logic);

        Random rand = new Random();
        MoveValue move = moves.get(rand.nextInt(moves.size()));
        if(logic.isPromotion(move)) {
            Pieces promotionPiece = List.of(Pieces.QUEEN, Pieces.ROOK, Pieces.BISHOP, Pieces.KNIGHT).get(rand.nextInt(4));
            move = move.withPromotionPiece(promotionPiece);
        }
        return move;
    }
}
