import java.util.ArrayList;

public class Board {
    private final ArrayList<BoardListener> boardListeners = new ArrayList<>(1);
    private Piece pieceSelected = null;
    private int turn = Piece.DOWN;
    private final Piece[][] board  =  new Piece[8][8];
    private King whiteKing;
    private King blackKing;
    private Piece tempPiece = null;
    private int oldX;
    private int oldY;

    public Board(){
        board[0][0] = new Rook(0, 0, Rook.white, Piece.DOWN);
        board[0][1] = new Knight(1, 0, Knight.white, Piece.DOWN);
        board[0][2] = new Bishop(2, 0, Bishop.white, Piece.DOWN);
        board[0][3] = new Queen(3, 0, Queen.white, Piece.DOWN);
        whiteKing = new King(4, 0, King.white, Piece.DOWN);
        board[0][4] = whiteKing;
        board[0][5] = new Bishop(5, 0, Bishop.white, Piece.DOWN);
        board[0][6] = new Knight(6, 0, Knight.white, Piece.DOWN);
        board[0][7] = new Rook(7, 0, Rook.white, Piece.DOWN);
        board[7][0] = new Rook(0, 7, Rook.black, Piece.UP);
        board[7][1] = new Knight(1, 7, Knight.black, Piece.UP);
        board[7][2] = new Bishop(2, 7, Bishop.black, Piece.UP);
        board[7][3] = new Queen(3, 7, Queen.black, Piece.UP);
        blackKing = new King(4, 7, King.black, Piece.UP);
        board[7][4] = blackKing;
        board[7][5] = new Bishop(5, 7, Bishop.black, Piece.UP);
        board[7][6] = new Knight(6, 7, Knight.black, Piece.UP);
        board[7][7] = new Rook(7, 7, Rook.black, Piece.UP);
        for(int x = 0; x < 8; x++){
            board[6][x] = new Pawn(x, 6, Pawn.black, Piece.UP);
            board[1][x] = new Pawn(x, 1, Pawn.white, Piece.DOWN);
        }
    }

    private void nextTurn(){
        if(turn == Piece.DOWN)
            turn = Piece.UP;
        else
            turn = Piece.DOWN;
    }

    public int getTurn(){return turn;}

    public King getKing(){
        if(turn == Piece.UP){
            return blackKing;
        }
        return whiteKing;
    }

    public boolean inCheck(int x, int y, Piece pieceToCheck){
        tempMove(x, y, pieceToCheck);
        Coordinate kingPos = new Coordinate(getKing().getX(), getKing().getY());
        for(Piece[] row : board){
            for(Piece piece : row){
                if(piece == null || piece.getDirection() == pieceToCheck.getDirection())
                    continue;
                if(piece.getPossibleMoves(this).contains(kingPos)) {
                    undoTempMove(pieceToCheck);
                    return true;
                }
            }
        }
        undoTempMove(pieceToCheck);
        return false;
    }

    public void tempMove(int x, int y, Piece piece){
        tempPiece = board[y][x];
        piece.setX(x);
        piece.setY(y);
        board[y][x] = piece;
    }

    public void undoTempMove(Piece piece){
        board[piece.getY()][piece.getX()] = tempPiece;
        board[oldY][oldX] = piece;
        piece.setY(oldY);
        piece.setX(oldX);
    }

    public Piece getPiece(int x, int y){
        return board[y][x];
    }

    public void squareClicked(int x, int y){
        Piece piece = getPiece(x, y);
        if(piece == null){ // if clicked a blank square
            if(pieceSelected == null)
                return;
            moveValidPieces(x, y, pieceSelected);
        } else if(piece.getDirection() == turn) { // if the player's piece
            setSelectedPiece(piece);
        } else if(pieceSelected != null){ // if enemy piece
            moveValidPieces(x, y, pieceSelected);
        }
    }

    private void moveValidPieces(int x, int y, Piece piece){
        if(!piece.getPossibleMoves(this).contains(new Coordinate(x, y))){ // if invalid move
            setSelectedPiece(null);
            return;
        }
        if(piece instanceof Pawn pawn) {
            if(y == 7 || y == 0) { // if pawn promotion
                board[pawn.getY()][pawn.getX()] = null;
                if (pawn.getDirection() == Piece.UP)
                    board[y][x] = new Queen(x, y, Queen.black, Piece.UP);
                else if (pawn.getDirection() == Piece.DOWN)
                    board[y][x] = new Queen(x, y, Queen.white, Piece.DOWN);
                notifyBoardChanged(pawn, board[y][x]);
            }else if(x != pawn.getX() && (board[y][x] == null)){ // if passanting
                movePiece(x, y, board[y-pawn.getDirection()][x]);
                movePiece(x, y, pawn);
            }else{
                pawn.setCanBePassanted(Math.abs(y - pawn.getY()) == 2);
                movePiece(x, y, pawn);
            }
        }else if(piece instanceof King && Math.abs(x - piece.getX()) == 2){ // castling
            if(x - piece.getX() == -2) { // long castle
                movePiece(3, y, board[y][0]);
            }else {
                movePiece(5, y, board[y][7]);
            }
            movePiece(x, y, piece);
        }else{ // if not a pawn promotion
            movePiece(x, y, piece);
        }
        pieceSelected = null;
        nextTurn();
    }

    private void movePiece(int x, int y, Piece piece){
        board[piece.getY()][piece.getX()] = null;
        piece.setX(x);
        piece.setY(y);
        board[y][x] = piece;
        notifyBoardChanged(piece, piece);
    }

    private void setSelectedPiece(Piece piece){
        if(pieceSelected != null){
            notifyPieceSelected(pieceSelected);
        }
        pieceSelected = piece;
        notifyPieceSelected(piece);
    }

    public void addBoardListener(BoardListener listener){
        boardListeners.add(listener);
    }

    private void notifyBoardChanged(Piece piece, Piece newPiece){
        for(BoardListener listener : boardListeners){
            listener.boardChanged(piece, newPiece);
        }
    }

    private void notifyPieceSelected(Piece pieceSelected){
        for(BoardListener listener : boardListeners){
            listener.pieceSelected(pieceSelected);
        }
    }
}
