# Chess
A Chess game implemented in Java.
## Instructions for use
### ChessInterface.java
#### Initialisation
The primary interface for the game. When creating an instance, you can create a default chess board by leaving the 
constructor blank or by inputting a FEN string for a preset position.

#### Making a move
To make a move, you can call ChessInterface.makeMove(). This validates a move is a valid chess move then moves the piece if
it is. If you input an invalid chess move, an error will be thrown.

Each piece can calculate what moves are possible. This can help prevent inputting an invalid move. Use getPossibleMoves
to calculate it.

#### Board Listener
To be notified about events, use the Board Listener interface and then add it to the board. The board has 3 events 
which it sends events for.
* Move Made: after a move has been made.
* Board changed: after a move has been undone/redone.
* Checkmate: After a king has been checkmated, the position of the king is given.
* Draw: gives the position of both of the kings.

After a move made event or board changed event, running getLastMove() returns the individual move made to play the move.
Some examples:
* When en passanting, the pawn being taken is moved back one square then the pawn takes.
* When promoting, the pawn moves to the promotion square and then the queen "takes" the pawn.
* When undoing a move, a piece that may have been taken will "reappear". This is indicated by a move having the same old
and new coordinates.

#### Auto player
Contains an autoplay function. Not a chess engine but can play through a game. Give autoplay the path of a PGN file and
it will play through the game one move at a time as if a real player was playing them.

###### PGN Parser
Included is a PGN parser which extracts each move made in a PGN file.

#### FEN Strings
The board can generator a FEN string from the current board position at any time. Just call getFenString() at any point.

#### Icon
Each piece contains an Icon which can be used in a Swing GUI.

#### Limitations
Currently, all promotions are to queen. There is no option for under-promotion.