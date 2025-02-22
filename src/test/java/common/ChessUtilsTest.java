package common;

@SuppressWarnings("SpellCheckingInspection")
class ChessUtilsTest {
//    @Test
//    void doCoordinatesMapCorrectly() {
//        assertEquals("h1", ChessUtils.coordsToChess(0, 0));
//        assertEquals("g2", ChessUtils.coordsToChess(1, 1));
//        assertEquals("f3", ChessUtils.coordsToChess(2, 2));
//        assertEquals("e4", ChessUtils.coordsToChess(3, 3));
//        assertEquals("d5", ChessUtils.coordsToChess(4, 4));
//        assertEquals("c6", ChessUtils.coordsToChess(5, 5));
//        assertEquals("b7", ChessUtils.coordsToChess(6, 6));
//        assertEquals("a8", ChessUtils.coordsToChess(7, 7));
//        assertEquals("h8", ChessUtils.coordsToChess(0, 7));
//        assertEquals("a1", ChessUtils.coordsToChess(7, 0));
//        assertEquals("d8", ChessUtils.coordsToChess(4, 7));
//    }
//
//    @Test
//    void dRookDisambiguated(){
//        Chessboard board = new Chessboard();
//        Rook dRook = new Rook(new Coordinate(4,7), PieceColour.BLACK);
//        Rook hRook = new Rook(new Coordinate(0,7), PieceColour.BLACK);
//        SequencedCollection<Piece> possiblePieces = new ArrayList<>(2);
//        possiblePieces.add(dRook);
//        possiblePieces.add(hRook);
//        ChessUtils.disambiguatePiece(possiblePieces, "Rdf8");
//        assertEquals(1, possiblePieces.size());
//        assertEquals(dRook, possiblePieces.getFirst());
//    }
//
//    @Test
//    void hRookDisambiguated(){
//        Chessboard board = new Chessboard();
//        Rook dRook = new Rook(new Coordinate(4,7), PieceColour.BLACK);
//        Rook hRook = new Rook(new Coordinate(0,7), PieceColour.BLACK);
//        SequencedCollection<Piece> possiblePieces = new ArrayList<>(2);
//        possiblePieces.add(dRook);
//        possiblePieces.add(hRook);
//        ChessUtils.disambiguatePiece(possiblePieces, "Rhf8");
//        assertEquals(1, possiblePieces.size());
//        assertEquals(hRook, possiblePieces.getFirst());
//    }
//
//    @Test
//    void RookA5Disambiguated() {
//        Chessboard board = new Chessboard();
//        Rook a1Rook = new Rook(new Coordinate(7, 0), PieceColour.BLACK);
//        Rook a5hRook = new Rook(new Coordinate(7, 4), PieceColour.BLACK);
//        SequencedCollection<Piece> possiblePieces = new ArrayList<>(2);
//        possiblePieces.add(a1Rook);
//        possiblePieces.add(a5hRook);
//        ChessUtils.disambiguatePiece(possiblePieces, "R5a3");
//        assertEquals(1, possiblePieces.size());
//        assertEquals(a5hRook, possiblePieces.getFirst());
//    }
//
//    @Test
//    void RookA1Disambiguated() {
//        Chessboard board = new Chessboard();
//        Rook a1Rook = new Rook(new Coordinate(7, 0), PieceColour.BLACK);
//        Rook a5hRook = new Rook(new Coordinate(7, 4), PieceColour.BLACK);
//        SequencedCollection<Piece> possiblePieces = new ArrayList<>(2);
//        possiblePieces.add(a1Rook);
//        possiblePieces.add(a5hRook);
//        ChessUtils.disambiguatePiece(possiblePieces, "R1a3");
//        assertEquals(1, possiblePieces.size());
//        assertEquals(a1Rook, possiblePieces.getFirst());
//    }
//
//    @Test
//    void queenDoubleDisambiguation(){
//        Chessboard board = new Chessboard();
//        Queen queenE4 = new Queen(new Coordinate(3,3), PieceColour.BLACK);
//        Queen queenH4 = new Queen(new Coordinate(0,3), PieceColour.BLACK);
//        Queen queenH1 = new Queen(new Coordinate(0,0), PieceColour.BLACK);
//        SequencedCollection<Piece> possiblePieces = new ArrayList<>(2);
//        possiblePieces.add(queenH1);
//        possiblePieces.add(queenH4);
//        possiblePieces.add(queenE4);
//        ChessUtils.disambiguatePiece(possiblePieces, "Qh4e1");
//        assertEquals(1, possiblePieces.size());
//        assertEquals(queenH4, possiblePieces.getFirst());
//    }
//
//    @Test
//    void shortCastleWhite() {
//        assertDoesNotThrow(()->{
//            Chessboard board = new ChessboardBuilder().fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w KQkq - 0 1");
//            MoveValue move = ChessUtils.chessToMove(board, "O-O");
//            assertEquals(new MoveValue(board.getPiece(3,0), new Coordinate(1, 0)), move);
//        });
//    }
//
//    @Test
//    void longCastleWhite() {
//        assertDoesNotThrow(()->{
//            Chessboard board = new ChessboardBuilder().fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w KQkq - 0 1");
//            MoveValue move = ChessUtils.chessToMove(board, "O-O-O");
//            assertEquals(new MoveValue(board.getPiece(3,0), new Coordinate(5, 0)), move);
//        });
//    }
//
//    @Test
//    void shortCastleBlack() {
//        assertDoesNotThrow(()->{
//            Chessboard board = new ChessboardBuilder().fromFen("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R b kq - 0 1");
//            MoveValue move = ChessUtils.chessToMove(board, "O-O");
//            assertEquals(new MoveValue(board.getPiece(3,7), new Coordinate(1, 7)), move);
//        });
//    }
//
//    @Test
//    void longCastleBlack() {
//        assertDoesNotThrow(()->{
//            Chessboard board = new ChessboardBuilder().fromFen("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R b kq - 0 1");
//            MoveValue move = ChessUtils.chessToMove(board, "O-O-O");
//            assertEquals(new MoveValue(board.getPiece(3,7), new Coordinate(5, 7)), move);
//        });
//    }
//
//    @Test
//    void basicPawnMove() {
//        assertDoesNotThrow(()->{
//            Chessboard board = new ChessboardBuilder().fromFen("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w kq - 0 1");
//            MoveValue move = ChessUtils.chessToMove(board, "e4");
//            assertEquals(new MoveValue(board.getPiece(3,1), new Coordinate(3, 3)), move);
//        });
//    }
//
//    @Test
//    void takingPawnMove() {
//        assertDoesNotThrow(()->{
//            Chessboard board = new ChessboardBuilder().fromFen("r3k2r/ppp1pppp/8/3p4/4P3/8/PPPP1PPP/R3K2R w kq - 0 1");
//            MoveValue move = ChessUtils.chessToMove(board, "exd5");
//            assertEquals(new MoveValue(board.getPiece(3,3), new Coordinate(4, 4)), move);
//        });
//    }
//
//    @Test
//    void knightMove() {
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        assertDoesNotThrow(()->{
//            MoveValue move = ChessUtils.chessToMove(board, "Nf3");
//            assertEquals(new MoveValue(board.getPiece(1,0), new Coordinate(2, 2)), move);
//        });
//    }
}