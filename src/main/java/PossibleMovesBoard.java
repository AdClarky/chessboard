public class PossibleMovesBoard {
    private boolean[][] board = new boolean[8][8];

    public PossibleMovesBoard() {

    }

    public void possibleMove(Coordinate coordinate){
        board[coordinate.y()][coordinate.x()] = true;
    }

    public void clear(){
        board = new boolean[8][8];
    }

    public boolean isPossible(Coordinate coordinate){
        return board[coordinate.y()][coordinate.x()];
    }
}
