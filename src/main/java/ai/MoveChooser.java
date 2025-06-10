package ai;

import chessboard.Chess;
import common.MoveValue;

public interface MoveChooser {
    MoveValue chooseMove(Chess chessboard);
}
