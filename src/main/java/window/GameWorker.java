package window;

import ai.MoveChooser;
import chessboard.Chess;
import common.MoveValue;
import common.PieceColour;

import javax.swing.SwingWorker;
import java.util.concurrent.TimeUnit;

public class GameWorker extends SwingWorker<Void, String> {

        private final Chess chessGame;
        private final MoveChooser whiteChooser;
        private final MoveChooser blackChooser;

        public GameWorker(Chess chessGame, MoveChooser whiteChooser, MoveChooser blackChooser, GameWindow gameWindow) {
            this.chessGame = chessGame;
            this.whiteChooser = whiteChooser;
            this.blackChooser = blackChooser;
        }

        @Override
        protected Void doInBackground() throws Exception {
            while (!chessGame.isCheckmate() && !chessGame.isDraw()) {
                PieceColour currentTurn = chessGame.getCurrentTurn();

                MoveChooser currentChooser = null;
                if (currentTurn == PieceColour.WHITE) {
                    currentChooser = whiteChooser;
                } else if (currentTurn == PieceColour.BLACK) {
                    currentChooser = blackChooser;
                }

                TimeUnit.MILLISECONDS.sleep(100);

                MoveValue chosenMove = currentChooser.chooseMove(chessGame);

                if (chosenMove != null) {
                    chessGame.makeMove(chosenMove);
                }
            }
            return null;
        }
    }
