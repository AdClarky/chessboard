package chessboard;

import common.Coordinate;
import common.Pieces;
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

    public long testDepthCopying(ChessGame chessGame, int currentDepth) {
        return pool.invoke(new DepthTask(chessGame, currentDepth, topDepth));
    }

    private static class DepthTask extends RecursiveTask<Long> {
        private static final Pieces[] PROMOTION_PIECES = {Pieces.QUEEN, Pieces.ROOK, Pieces.KNIGHT, Pieces.BISHOP};
        private final ChessGame chessGame;
        private final int currentDepth;
        private final int topDepth;

        public DepthTask(ChessGame chessGame, int currentDepth, int topDepth) {
            this.chessGame = chessGame;
            this.currentDepth = currentDepth;
            this.topDepth = topDepth;
        }

        @Override
        protected Long compute() {
            long positions = 0;
            Collection<Coordinate> pieces = chessGame.getAllColourPieces(chessGame.getTurn());
            List<DepthTask> tasks = new ArrayList<>();
            List<String> moves = new ArrayList<>();
            for (Coordinate piece : pieces) {
                Collection<Coordinate> positionCoordinates = chessGame.getPossibleMoves(piece);
                if (currentDepth == 1) {
                    positions += positionCoordinates.size();
                    for(Coordinate move : positionCoordinates) {
                        if(chessGame.isMovePromotion(piece, move)){
                            positions += 3;
//                            if(topDepth == 1) {
//                                for(Pieces promoPiece : PROMOTION_PIECES) {
//                                    System.out.println("" + piece + move + Character.toLowerCase(promoPiece.toCharacter()) + ": 1");
//                                }
//                                positionCoordinates.remove(move);
//                            }
                        }
                    }
//                    if(topDepth == 1) for (Coordinate move : positionCoordinates) System.out.println("" + piece + move + ": 1");
                    continue;
                }
                for (Coordinate newMove : positionCoordinates) {
                    if(chessGame.isMovePromotion(piece, newMove)){
                        for(Pieces currentPiece : PROMOTION_PIECES) {
                            DepthTask task = testNewDepth(piece, newMove, currentPiece);
                            task.fork();
                            tasks.add(task);
                            if(topDepth == currentDepth) moves.add("" + piece + newMove + currentPiece.toCharacter() + ": ");
                        }
                    }else{
                        DepthTask task = testNewDepth(piece, newMove, Pieces.BLANK);
                        task.fork();
                        tasks.add(task);
                        if(topDepth == currentDepth) moves.add("" + piece + newMove + ": ");
                    }
                }
            }
            for (DepthTask task : tasks) {
                positions += task.join();
            }
//            if(topDepth == currentDepth){
//                for(int i = 0; i < moves.size(); i++){
//                    System.out.println(moves.get(i) + tasks.get(i).join());
//                }
//            }
            return positions;
        }

        private DepthTask testNewDepth(Coordinate piece, Coordinate newMove, Pieces promotionPiece){
            ChessGame copy = chessGame.copy();
            try {
                copy.makeMove(piece, newMove, promotionPiece);
            } catch (InvalidMoveException e) {
                throw new RuntimeException(e);
            }
            return new DepthTask(copy, currentDepth - 1, topDepth);
        }
    }
}

