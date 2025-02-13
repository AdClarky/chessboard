package chessboard;

import common.PGNParser;
import exception.InvalidMoveException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Parses a set of moves in a text file and then plays the move in order to a given board.
*/
public class Autoplay {
    private final Collection<String> moves = new ArrayList<>(30);
    private final ChessInterface chessInterface;

    /**
     * Constructs an {@code Autoplay} object and parses a PGN file for chess moves.
     * @param chessInterface the game where the moves will be played.
     * @param path the path of the PGN file.
     * @throws IOException if there is an error while accessing the file.
     */
    public Autoplay(@NotNull ChessInterface chessInterface, Path path) throws IOException {
        this.chessInterface = chessInterface;
        moves.addAll(new PGNParser(path).getMoves());
    }

    /**
     * Constructs an {@code Autoplay} object and takes a collection
     * of chess moves as the move that will be made.
     * @param chessInterface the game where the moves will be played.
     * @param moves the collection of moves where each string is one move.
     */
    public Autoplay(@NotNull ChessInterface chessInterface, Collection<String> moves){
        this.chessInterface = chessInterface;
        this.moves.addAll(moves);
    }

    /**
     * Plays each move individually with a delay.
     * @param delay the amount of time after each move in MS that the program stops.
     * @throws InterruptedException when the thread is sleeping and an interrupt occurs.
     * @throws InvalidMoveException a given move was invalid.
     */
    public void play(int delay) throws InterruptedException, InvalidMoveException {
        for(String move : moves){
            chessInterface.makeMove(move);
            TimeUnit.MILLISECONDS.sleep(delay);

        }
    }

    /**
     * Plays each move individually with no built-in delay.
     * @throws InterruptedException when the thread is sleeping and an interrupt occurs.
     * @throws InvalidMoveException a given move was invalid.
     */
    public void play() throws InterruptedException, InvalidMoveException {
        play(0);
    }

    /**
     * Gets the moves which are stored in the autoplayer.
     * @return the moves saved in the object, where each String is one move.
     */
    public Collection<String> getMoves(){return moves;}
}
