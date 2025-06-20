package ai;

import chessboard.ChessLogic;
import chessboard.Chessboard;
import common.MoveValue;
import common.Pieces;

import java.util.List;

public class TakingMove implements MoveChooser{

    @Override
    public MoveValue chooseMove(Chessboard board, ChessLogic logic) {
        List<MoveValue> moves = getPossibleMoves(board);
        for (MoveValue move : moves){
            if(logic.isPromotion(move.oldPos(), move.newPos()))
                return move.withPromotionPiece(Pieces.QUEEN);
        }
    }
}
