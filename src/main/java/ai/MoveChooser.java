package ai;

import chessboard.ChessInterface;
import common.MoveValue;

public interface MoveChooser {
    MoveValue chooseMove(ChessInterface chessboard);
}
