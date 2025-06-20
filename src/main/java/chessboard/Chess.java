package chessboard;

import common.Coordinate;
import common.MoveValue;
import common.PieceColour;
import exception.InvalidMoveException;
import org.jetbrains.annotations.Nullable;

/**
 * Represents the core functionality and state of a standard chess game.
 * This interface defines the contract for any class that wishes to implement
 * a chess engine, handling game progression, move validation, board state queries,
 * and win/draw conditions.
 *
 * <p>Implementations of this interface are responsible for:
 * <ul>
 * <li>Managing the current turn (White or Black).</li>
 * <li>Validating and executing legal chess moves, including special moves like
 * castling, en passant, and pawn promotion.</li>
 * <li>Maintaining the board state and allowing queries about pieces and their positions.</li>
 * <li>Determining game-ending conditions such as checkmate and draws.</li>
 * <li>Providing a FEN (Forsyth-Edwards Notation) string representation of the current board state.</li>
 * </ul>
 * <p>This interface acts as the central API for interacting with and controlling a chess game.</p>
 */
public interface Chess {
    /**
     * The current turn of the board, i.e. black or white.
     *
     * @return the current turn
     */
    PieceColour getCurrentTurn();

    /**
     * Calculates if the current position is checkmate.
     *
     * @return true if the current position is checkmate
     */
    boolean isCheckmate();

    /**
     * Calculates if the current position is a draw.
     *
     * @return true if the current position is a draw
     */
    boolean isDraw();

    /**
     * Moves a promotionPiece to a new location while validating it is a valid move.
     * Assumes the provided old coordinates are valid coordinates for a promotionPiece.
     * Checks for checkmate and draws.
     *
     * @throws InvalidMoveException when the move given is not a valid move.
     */
    void makeMove(MoveValue moveValue) throws InvalidMoveException;

    /**
     * {@link Chess#makeMove(MoveValue)}
     *
     * @param move a chess move in algebraic notation
     * @throws InvalidMoveException when the move given is not a valid move
     */
    void makeMove(String move) throws InvalidMoveException;

    /**
     * Calculates a FEN string based on the current position
     *
     * @return a FEN string
     */
    String getFenString();

    /**
     * Gets the colour of a promotionPiece on a square.
     * Returns null if no promotionPiece is on the square.
     *
     * @param position the square to check
     * @return the colour of the promotionPiece
     */
    @Nullable
    PieceColour getColour(Coordinate position);

    /**
     * Gets the possible moves of a specific promotionPiece.
     * Returns an empty collection if no promotionPiece is on the square and if there are no possible moves.
     *
     * @param position the square to check
     * @return a collection of possible moves
     */
    Bitboard getPossibleMoves(Coordinate position);

    /**
     * Gets all the pieces on the board for a specific colour.
     *
     * @param colour the coloured pieces you want
     * @return a collection of the coordinates of the pieces.
     */
    Bitboard getAllColourPieces(PieceColour colour);

    boolean isPromotion(Coordinate oldPos, Coordinate newPos);

    ChessLogic getLogic();

    Chessboard getBoard();
}
