package chessboard;

import common.Coordinate;
import common.MoveValue;
import common.PieceColour;
import common.Pieces;
import exception.InvalidFenStringException;
import exception.InvalidMoveException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class ChessGame {
    private final Chessboard board;
    private final BoardHistory history;
    private final ChessLogic logic;

    public ChessGame() {
        board = new ChessboardBuilder().defaultSetup();
        history = new BoardHistory();
        logic = new ChessLogic(board, history);
        logic.calculatePossibleMoves();
    }

    public ChessGame(String fenString) throws InvalidFenStringException {
        ChessboardBuilder builder = new ChessboardBuilder();
        board = builder.fromFen(fenString);
        history = new BoardHistory(builder.getNumHalfMoves(), builder.getNumFullMoves());
        logic = new ChessLogic(board, history);
        logic.calculatePossibleMoves();
    }

    private ChessGame(Chessboard board, ChessLogic logic) {
        this.board = board;
        history = new BoardHistory();
        this.logic = logic;
    }

    public void makeMove(Coordinate oldPos, Coordinate newPos) throws InvalidMoveException {
        makeMove(oldPos, newPos, Pieces.QUEEN);
    }

    public void makeMove(Coordinate oldPos, Coordinate newPos, Pieces promotionPiece) throws InvalidMoveException {
        if(!logic.isValidMove(oldPos, newPos))
            throw new InvalidMoveException(oldPos, newPos);
        if(history.canRedoMove())
            history.clearRedoMoves();
        Move move = new Move(board, oldPos, newPos, logic.getPossibleMoves(), promotionPiece);
        history.push(move);
        logic.calculatePossibleMoves();
    }

    public boolean isMovePromotion(Coordinate oldPos, Coordinate newPos) {
        if(!logic.isValidMove(oldPos, newPos))
            return false;
        if(board.getPiece(oldPos) != Pieces.PAWN)
            return false;
        if((oldPos.y() == 1 && board.getColour(oldPos) == PieceColour.BLACK) || (oldPos.y() == 6 && board.getColour(oldPos) == PieceColour.WHITE))
            return true;
        return false;
    }

    public PieceColour getColour(Coordinate position){
        return board.getColour(position);
    }

    public Pieces getPiece(Coordinate position){
        return board.getPiece(position);
    }

    public Coordinate getKing(PieceColour colour){
        return board.getKingPos(colour);
    }

    public Coordinate getKing(){
        return board.getKingPos(board.getTurn());
    }

    public PieceColour getTurn(){
        return board.getTurn();
    }

    public MoveValue redoMove() {
        Move move = history.redoMove();
        logic.calculatePossibleMoves();
        return new MoveValue(move.getOldPos(), move.getNewPos());
    }

    public boolean canRedoMove(){
        return history.canRedoMove();
    }

    public MoveValue undoMove() {
        Move move = history.undoMove();
        PossibleMoves possibleMoves = move.getPossibleMoves();
        if(possibleMoves == null)
            logic.calculatePossibleMoves();
        else {
            logic.setPossibleMoves(possibleMoves);
        }
        return new MoveValue(move.getOldPos(), move.getNewPos());
    }

    public boolean canUndoMove(){
        return history.canUndoMove();
    }

    public int getNumHalfMoves(){
        return history.getNumHalfMoves();
    }

    public int getNumFullMoves(){
        return history.getNumFullMoves();
    }

    public long getCastlingRights(){
        return board.getCastlingRights();
    }

    public Coordinate getEnPassantSquare() {
        return board.getEnPassantSquare();
    }

    public boolean isDraw() {
        return logic.isDraw();
    }

    public boolean isCheckmate() {
        return logic.isCheckmate();
    }


    public MoveValue chessToMove(String move) throws InvalidMoveException {
        if("O-O".equals(move)) {
            return getCastlingMove(board.getTurn(), 6);
        }
        if("O-O-O".equals(move)) {
            return getCastlingMove(board.getTurn(), 2);
        }
        Coordinate newCoordinate = Coordinate.fromString(move);
        char pieceLetter;
        if(Character.isLowerCase(move.charAt(0))) // if a pawn
            pieceLetter = '\u0000';
        else // any other piece
            pieceLetter = move.charAt(0);
        ArrayList<Coordinate> possiblePieces = new ArrayList<>(2);
        for(Coordinate piecePos : board.getAllColourPositions(board.getTurn())){
            Pieces piece = board.getPiece(piecePos);
            if(piece.toCharacter() != pieceLetter) // if its not type of piece that moved
                continue;
            possiblePieces.add(piecePos);
        }
        possiblePieces.removeIf(piece -> !logic.isValidMove(piece, newCoordinate));
        if(possiblePieces.size() > 1)
            disambiguatePiece(possiblePieces, move);
        if(possiblePieces.isEmpty())
            throw new InvalidMoveException(move);
        Coordinate piece = possiblePieces.get(0);
        return new MoveValue(piece, newCoordinate);
    }

    private static MoveValue getCastlingMove(PieceColour colour, int newX){
        if(colour == PieceColour.BLACK){
            return new MoveValue(new Coordinate(4,7),new Coordinate(newX,7));
        }else{
            return new MoveValue(new Coordinate(4, 0), new Coordinate(newX, 0));
        }
    }

    private static void disambiguatePiece(Collection<Coordinate> possiblePieces, @NotNull CharSequence move){
        int length = move.length();
        for(int i = 0; i < length - 2; i++){
            if(Character.isLowerCase(move.charAt(i)) && move.charAt(i) != 'x'){ // x value given
                Coordinate correctX = Coordinate.fromString(move.charAt(i) + "0");
                possiblePieces.removeIf(piece -> piece.x() != correctX.x());
            }else if(Character.isDigit(move.charAt(i))){ // y value given
                Coordinate correctY = Coordinate.fromString("a" + move.charAt(i));
                possiblePieces.removeIf(piece -> piece.y() != correctY.y());
            }
        }
    }

    public Collection<Coordinate> getAllColourPieces(PieceColour turn) {
        return board.getAllColourPositions(turn);
    }

    public Collection<Coordinate> getPossibleMoves(Coordinate piece) {
        return logic.getPossibleMoves(piece);
    }

    ChessGame copy(){
        Chessboard boardCopy = board.copy();
        ChessLogic logicCopy = logic.copy(boardCopy);
        return new ChessGame(boardCopy, logicCopy);
    }
}
