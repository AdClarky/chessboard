package chessboard;

import common.Coordinate;
import common.MoveValue;
import common.PieceColour;
import common.Pieces;

import java.util.HashMap;
import java.util.Map;

public class PossibleMoves {
    private final Chessboard board;
    private final MaskGenerator maskGenerator;
    private final Map<Coordinate, Bitboard> possibleMoves;
    private Bitboard enemyPossible;

    public PossibleMoves(Chessboard board) {
        this.board = board;
        this.maskGenerator = new MaskGenerator(board);
        this.possibleMoves = new HashMap<>();
    }

    public void calculatePossibleMoves() {
        enemyPossible = calculatePieces(board.getTurn().invert());
        calculateFriendlyPieces();
    }

    public Bitboard calculatePieces(PieceColour colour) {
        Bitboard pieces = this.board.getAllColourPositions(colour);
        long possible = 0;
        for (Coordinate piecePos : pieces) {
            possible |= maskGenerator.getMaskForPiece(piecePos);
        }
        return new Bitboard(possible);
    }

    private void calculateFriendlyPieces() {
        Bitboard pieces = board.getAllColourPositions(board.getTurn());
        for (Coordinate piecePos : pieces) {
            Bitboard possible = new Bitboard(maskGenerator.getMaskForPiece(piecePos));
            possible = removeMovesInCheck(piecePos, possible);
            possibleMoves.put(piecePos, possible);
        }
    }

    private Bitboard removeMovesInCheck(Coordinate pos, Bitboard possible) {
        Bitboard newPossible = possible;
        for (Coordinate move : possible) {
            if (isMoveUnsafe(pos, move))
                newPossible = newPossible.remove(move);
        }
        if (board.getPiece(pos) == Pieces.KING)
            newPossible = removeCastlingThroughCheck(possible, pos);
        return newPossible;
    }

    private boolean isMoveUnsafe(Coordinate position, Coordinate movePos) {
        if (!isKingInCheck() && !doesMoveExposeKing(position, movePos))
            return false;
        PieceColour currentTurn = board.getTurn();
        Move move = new Move(board, position, movePos);
        PossibleMoves possibleMoves = new PossibleMoves(move.getNewBoard());
        Bitboard possible = possibleMoves.calculatePieces(board.getTurn());
        return isKingInCheck(currentTurn, possible);
    }

    private boolean doesMoveExposeKing(Coordinate position, Coordinate movePosition) {
        if (board.getPiece(position) != Pieces.KING && !enemyPossible.contains(position))
            return false;
        Coordinate kingPos = board.getKingPos(board.getTurn());
        if (position.x() == kingPos.x() && movePosition.x() != position.x()) {
            return canEnemyPieceSeeKing(kingPos, position);
        } else if (position.y() == kingPos.y() && movePosition.y() != position.y()) {
            return canEnemyPieceSeeKing(kingPos, position);
        } else if ((Math.abs(position.x() - kingPos.x()) == Math.abs(position.y() - kingPos.y())) &&
                ((position.x() - kingPos.x()) * (movePosition.y() - kingPos.y()) - (position.y() - kingPos.y()) * (movePosition.x() - kingPos.x())) != 0) {
            return canEnemyPieceSeeKing(kingPos, position);
        }
        return false;
    }

    private boolean canEnemyPieceSeeKing(Coordinate kingPos, Coordinate position) {
        int xDifference = Integer.signum(position.x() - kingPos.x());
        int yDifference = Integer.signum(position.y() - kingPos.y());
        if (xDifference == 0 && yDifference == 0)
            return true;
        PieceColour colour = board.getColour(kingPos);
        for (int x = kingPos.x() + xDifference, y = kingPos.y() + yDifference; ; x += xDifference, y += yDifference) {
            Coordinate square = new Coordinate(x, y);
            if (square.isNotInRange())
                return false;
            if (square.x() == position.x() && square.y() == position.y())
                continue;
            if (board.isSquareColour(square, colour))
                return false;
            if (board.isSquareColour(square, colour.invert()))
                return canPieceSeeSquare(board.getPiece(square), square, kingPos, xDifference, yDifference);
        }
    }

    private boolean canPieceSeeSquare(Pieces piece, Coordinate piecePos, Coordinate kingPos, int xDiff, int yDiff) {
        int absDiff = Math.abs(yDiff) + Math.abs(xDiff);
        if (absDiff == 2) { // diagonal, bishop, pawn, queen
            if (piece == Pieces.BISHOP || piece == Pieces.QUEEN)
                return true;
            return piece == Pieces.PAWN && kingPos.x() + xDiff == piecePos.x() && kingPos.y() + yDiff == piecePos.y();
        } else { // horizontal, rook, queen
            return piece == Pieces.ROOK || piece == Pieces.QUEEN;
        }
    }

    private Bitboard removeCastlingThroughCheck(Bitboard possible, Coordinate position) {
        Coordinate left = new Coordinate(position.x() - 1, position.y());
        Coordinate right = new Coordinate(position.x() + 1, position.y());
        if (isKingInCheck()) {
            possible = possible.remove(new Coordinate(position.x() - 2, position.y()))
                    .remove(new Coordinate(position.x() + 2, position.y()));
        }
        if (!possible.contains(left))
            possible = possible.remove(new Coordinate(position.x() - 2, position.y()));
        if (!possible.contains(right))
            possible = possible.remove(new Coordinate(position.x() + 2, position.y()));
        return possible;
    }


    public boolean isKingInCheck() {
        Coordinate kingPos = board.getKingPos(board.getTurn());
        return enemyPossible.contains(kingPos);
    }

    public boolean isKingInCheck(PieceColour colour, Bitboard possible) {
        return possible.contains(board.getKingPos(colour));
    }

    public boolean isEnemyPiece(int x, int y, PieceColour colour) {
        return board.isSquareColour(new Coordinate(x, y), colour.invert());
    }

    public MoveValue getMoveForOtherPiece(int x, int y, int newX, int newY) {
        return new MoveValue(new Coordinate(x, y), new Coordinate(newX, newY));
    }

    public Map<Coordinate, Bitboard> getPossibleMoves() {
        return possibleMoves;
    }

    public Bitboard get(Coordinate position) {
        return possibleMoves.get(position);
    }

    public boolean hasMoves(){
        return !possibleMoves.isEmpty();
    }
}