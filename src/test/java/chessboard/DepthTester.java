package chessboard;

import common.Coordinate;
import exception.InvalidMoveException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;


public class DepthTester {
    private final ChessGame game;
    private final int topDepth;
    private final ForkJoinPool pool = new ForkJoinPool();

    public DepthTester(ChessGame game, int topDepth) {
        this.game = game;
        this.topDepth = topDepth;
        System.out.println("Testing depth " + topDepth);
    }

    public int testDepthCopying(ChessGame chessGame, int currentDepth) {
        return pool.invoke(new DepthTask(chessGame, currentDepth, topDepth));
    }

    private static class DepthTask extends RecursiveTask<Integer> {
        private final ChessGame chessGame;
        private final int currentDepth;
        private final int topDepth;

        public DepthTask(ChessGame chessGame, int currentDepth, int topDepth) {
            this.chessGame = chessGame;
            this.currentDepth = currentDepth;
            this.topDepth = topDepth;
        }

        @Override
        protected Integer compute() {
            int positions = 0;
            Collection<Coordinate> pieces = chessGame.getAllColourPieces(chessGame.getTurn());
            List<DepthTask> tasks = new ArrayList<>();
            for (Coordinate piece : pieces) {
                Collection<Coordinate> positionCoordinates = chessGame.getPossibleMoves(piece);
                if (currentDepth == 1) {
                    positions += positionCoordinates.size();
                    continue;
                }
                for (Coordinate newMove : positionCoordinates) {
                    ChessGame copy = chessGame.copy();
                    try {
                        copy.makeMove(piece, newMove);
                    } catch (InvalidMoveException e) {
                        throw new RuntimeException(e);
                    }
                    DepthTask task = new DepthTask(copy, currentDepth - 1, topDepth);
                    task.fork();
                    tasks.add(task);
                }
            }
            for (DepthTask task : tasks) {
                positions += task.join();
            }
            return positions;
        }
    }
}

