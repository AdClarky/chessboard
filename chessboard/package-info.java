/**
 * API for a chess board.
 * Board is the primary interface and when initialised will generate a standard chess board.<br>
 * Each piece contains a black and white icon.<br>
 * When interacting with the board, you should use the Board class only.
 * Square selection should be handled externally and only the moves should be passed into the board.
 * BoardListener is used when at the end of a move being made. The board stores the individual moves made which can
 * be found using the getMoves method on Board. <br>
 * Each piece can provide the possible moves for highlighting on a board.
 */
package chessboard;