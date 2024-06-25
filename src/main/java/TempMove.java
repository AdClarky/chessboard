import java.util.ArrayList;
import java.util.List;

public class TempMove {
    private final Board board;
    private final Piece piece;
    private final int x;
    private final int y;
    private final List<MoveValue> movesToUndo = new ArrayList<>(3);
    private final Iterable<MoveValue> movesMade;
    private final ArrayList<Piece> tempPieces = new ArrayList<>(2);
    private boolean undone = false;


    public TempMove(int x, int y, Piece piece, Board board){
        this.x = x;
        this.y = y;
        this.piece = piece;
        this.board = board;
        movesMade = piece.getMoves(x, y, board);
        makeMove();
    }

    public void makeMove(){
        undone = false;
        for(MoveValue move : movesMade){
            if(!board.isSquareBlank(move.newX(), move.newY())) { // taking
                if(move.newX() == move.piece().getX() && move.newY() == move.piece().getY())
                    board.getColourPieces(move.piece()).add(move.piece());
                Piece pieceTaken = board.getPiece(move.newX(), move.newY());
                movesToUndo.add(new MoveValue(pieceTaken, move.newX(), move.newY()));
                board.getColourPieces(pieceTaken).remove(pieceTaken);
            }
            Piece pieceToMove = move.piece();
            movesToUndo.add(new MoveValue(pieceToMove, pieceToMove.getX(), pieceToMove.getY()));
            board.setSquare(pieceToMove.getX(), pieceToMove.getY(), new Blank(pieceToMove.getX(), pieceToMove.getY()));
            board.setSquare(move.newX(), move.newY(), pieceToMove);
            pieceToMove.setX(move.newX());
            pieceToMove.setY(move.newY());
        }
    }

    public void undo(){
        undone = true;
        for(MoveValue move : movesToUndo.reversed()){
            Piece pieceToMove = move.piece();
            if(pieceToMove.getX() == move.newX() && pieceToMove.getY() == move.newY()){
                // a piece moving to the same spot only occurs as the last move when it's a promotion
                if(move == movesToUndo.getLast())
                    board.getColourPieces(pieceToMove).remove(pieceToMove);
                else
                    board.getColourPieces(move.piece()).add(move.piece());
            }
            board.setSquare(pieceToMove.getX(), pieceToMove.getY(), new Blank(pieceToMove.getX(), pieceToMove.getY()));
            board.setSquare(move.newX(), move.newY(), pieceToMove);
            pieceToMove.setX(move.newX());
            pieceToMove.setY(move.newY());
        }
    }

    public int getX() {return x;}
    public int getY() {return y;}
    public Piece getPiece() {return piece;}
    public List<MoveValue> getMovesToUndo() {return undone ? piece.getMoves(x, y, board) : movesToUndo;}
}
