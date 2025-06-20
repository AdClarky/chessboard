package ai;

import chessboard.ChessLogic;
import chessboard.Chessboard;
import common.MoveValue;

import java.util.List;
import java.util.Random;

public class TakingMove implements MoveChooser{

    @Override
    public MoveValue chooseMove(Chessboard board, ChessLogic logic) {
        List<MoveValue> moves = getPossibleMoves(board, logic);
        List<MoveValue> promotionMoves = moves.stream().filter(logic::isPromotion).toList();
        List<MoveValue> takingMoves = moves.stream().filter(logic::isTakingMove).toList();

        if(!promotionMoves.isEmpty()){
            return promotionMoves.get(0);
        }
        if(!takingMoves.isEmpty()){
            return promotionMoves.get(0);
        }
        Random rand = new Random();
        return moves.get(rand.nextInt(moves.size()));
    }
}
