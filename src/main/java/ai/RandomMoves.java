package ai;

import chessboard.Bitboard;
import chessboard.Chess;
import common.Coordinate;
import common.MoveValue;
import common.Pieces;

import java.util.ArrayList;
import java.util.Collection;
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

    private List<MoveValue> getPossibleMoves(Chess chessboard) {
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
