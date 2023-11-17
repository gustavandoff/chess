
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gustav.andoff
 */
public class Board {

    private Piece[][] board = new Piece[8][8];
    private Piece[] capturedWhitePieces = new Piece[32];
    private Piece[] capturedBlackPieces = new Piece[32];
    private int capturedWhiteAmount = 0;
    private int capturedBlackAmount = 0;
    private int squareSize = 55; // varje rutas sidlängd
    private double pieceSizeMult = 0.75; // pjäsernas sidlängd av rutornas sidlängd
    private String turn = "white";
    private String lastTurn = "white";

    public Board() {
        placeStart();
    }

    public Piece[][] getBoard() {
        return board;
    }

    public double getPieceSizeMult() {
        return pieceSizeMult;
    }

    public int getSquareSize() {
        return squareSize;
    }

    public String getTurn() {
        return turn;
    }

    public Piece[] getCapturedWhitePieces() {
        return capturedWhitePieces;
    }

    public Piece[] getCapturedBlackPieces() {
        return capturedBlackPieces;
    }

    public int getCapturedWhiteAmount() {
        return capturedWhiteAmount;
    }

    public int getCapturedBlackAmount() {
        return capturedBlackAmount;
    }

    /**
     * byter namn på en pjäs på brädet
     *
     * @param xIndex x-värdet på pjäsen som ska byta namn
     * @param yIndex y-värdet på pjäsen som ska byta namn
     * @param newName pjäsens nya namn
     */
    public void setBoardPieceName(int xIndex, int yIndex, String newName) {
        board[xIndex][yIndex].setName(newName);
        board[xIndex][yIndex].showMoves(board, squareSize, xIndex, yIndex, false); // jag måste anropa showMoves för att spelet ska förstå att en ny pjäs står där
        turn = lastTurn;
    }

    /**
     * när man fångar en pjäs läggs den till i listan capturedWhitePieces eller
     * capturedBlackPieces beroende på om den är vit eller svart
     *
     * @param xIndex x-indexet av den pjäs som
     * @param yIndex y-indexet av den pjäs som
     * @return returnerar färgen av den fångade pjäsen
     */
    public String addCaptured(int xIndex, int yIndex) {
        if (board[xIndex][yIndex].getColorS().equals("white")) {
            capturedWhitePieces[capturedWhiteAmount] = board[xIndex][yIndex];
            capturedWhiteAmount++;
            return "white";
        } else {
            capturedBlackPieces[capturedBlackAmount] = board[xIndex][yIndex];
            capturedBlackAmount++;
            return "black";
        }
    }

    /**
     * flyttar en pjäs till en annan ruta/index i board
     *
     * @param capturerXIndex x-indexet av den pjäs som ska flyttas
     * @param capturerYIndex y-indexet av den pjäs som ska flyttas
     * @param capturedXIndex x-indexet av den ruta som pjäsen flyttar till
     * @param capturedYIndex y-indexet av den ruta som pjäsen flyttar till
     * @return om bonden har gått till kanten returnerar den true
     */
    public boolean movePiece(int capturerXIndex, int capturerYIndex, int capturedXIndex, int capturedYIndex) {
        board[capturerXIndex][capturerYIndex].setHasMoved(true);
        board[capturerXIndex][capturerYIndex].getMds().clear();
        board[capturedXIndex][capturedYIndex] = board[capturerXIndex][capturerYIndex];
        board[capturerXIndex][capturerYIndex] = null;
        if ((capturedYIndex == 7 || capturedYIndex == 0) && board[capturedXIndex][capturedYIndex].getName().equals("pawn")) { // när man går till motsatt kant med en bonde ska det bli en annan pjäs
            lastTurn = turn;
            turn = "paused";

            return true;
        }

        pieceMoved(capturedXIndex, capturedYIndex);
        return false;
    }

    /**
     * metod som utför rockad (castle på eng.)
     *
     * @param xRIndex tornets x-index
     * @param yRIndex tornets y-index
     * @param xKIndex kungens x-index
     * @param yKIndex kungens y-index
     */
    public void castle(int xRIndex, int yRIndex, int xKIndex, int yKIndex) {
        board[xRIndex][yRIndex].setHasMoved(true);
        board[xRIndex][yRIndex].getMds().clear();
        board[xKIndex][yKIndex].setHasMoved(true);
        board[xKIndex][yKIndex].getMds().clear();

        if (xRIndex > xKIndex) {
            board[xRIndex - 2][yRIndex] = board[xRIndex][yRIndex];
            board[xKIndex + 2][yKIndex] = board[xKIndex][yKIndex];
        } else {
            board[xRIndex + 3][yRIndex] = board[xRIndex][yRIndex];
            board[xKIndex - 2][yKIndex] = board[xKIndex][yKIndex];
        }
        board[xRIndex][yRIndex] = null;
        board[xKIndex][yKIndex] = null;

        pieceMoved(xKIndex - 2, yKIndex);
    }

    /**
     * varje gång en pjäs går (med movePiece eller castle) så ska den byta tur
     * och kolla om det är schackmatt eller schack
     *
     * @param xIndex pjäsen som gick senasts x-värde
     * @param yIndex pjäsen som gick senasts y-värde
     */
    public void pieceMoved(int xIndex, int yIndex) {
        if (turn.equals("black")) { // byter vems tur det är efter man har gått
            turn = "white";
        } else if (turn.equals("white")) {
            turn = "black";
        }
        if (!testIfCheckMate(board[xIndex][yIndex])) {
            testIfCheck();
        }
    }

    /**
     * går igenom varje pjäs och kollar om någon kan ta kungen
     *
     * @return om någon pjäs kan ta kungen returneras true, annars false
     */
    public boolean testIfCheck() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null && board[i][j].canThisCaptureKing(board, squareSize, i, j, board[i][j].getMoveCombos())) {
                    JOptionPane.showMessageDialog(null, "Schack!");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * går igenom varje pjäs som är motsatt färg den som seanst gick och kollar
     * om någon kan gå någonstans
     *
     * @param p den pjäs om rörde på sig senast
     * @return true om det är schackmatt
     */
    public boolean testIfCheckMate(Piece p) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null && board[i][j].isOpCol(p) && board[i][j].showMoves(board, squareSize, i, j, false)) {
                    return false; // så fort den ser att någon pjäs kan få någonstans så avslutar den metoden
                }
            }
        }
        // Hamnar bara här om ingen pjäs kan gå - dvs det är schakmatt
        String col = p.getColorS().equals("white") ? "Vit" : "Svart";
        JOptionPane.showMessageDialog(null, "Schackmatt!\n " + col + " har vunnit!");
        return true;
    }

    public void showMoves(int pXIndex, int pYIndex) {
        if (board[pXIndex][pYIndex].getColorS().equals(turn)) {
            board[pXIndex][pYIndex].showMoves(board, squareSize, pXIndex, pYIndex, true);
        }
    }

    /**
     * Återställer ursprungsbrädet. Lägger till rätt pjäs på rätt plats i det
     * 2-dimensionella fältet board.
     */
    public void placeStart() {

        /**
         * [0][0] är längs ner till vänster. [1][0] är en ruta till höger osv
         *
         * [00][07] [01][07] [02][07] [03][07] [04][07] [05][07] [06][07]
         * [07][07] [00][06] [01][06] [02][06] [03][06] [04][06] [05][06]
         * [06][06] [07][06] [00][05] [01][05] [02][05] [03][05] [04][05]
         * [05][05] [06][05] [07][05] [00][04] [01][04] [02][04] [03][04]
         * [04][04] [05][04] [06][04] [07][04] [00][03] [01][03] [02][03]
         * [03][03] [04][03] [05][03] [06][03] [07][03] [00][02] [01][02]
         * [02][02] [03][02] [04][02] [05][02] [06][02] [07][02] [00][01]
         * [01][01] [02][01] [03][01] [04][01] [05][01] [06][01] [07][01]
         * [00][00] [01][00] [02][00] [03][00] [04][00] [05][00] [06][00]
         * [07][00]
         *
         */
        board[0][0] = new Piece("white", "rook", (int) (squareSize * pieceSizeMult));
        board[1][0] = new Piece("white", "knight", (int) (squareSize * pieceSizeMult));
        board[2][0] = new Piece("white", "bishop", (int) (squareSize * pieceSizeMult));
        board[3][0] = new Piece("white", "queen", (int) (squareSize * pieceSizeMult));
        board[4][0] = new Piece("white", "king", (int) (squareSize * pieceSizeMult));
        board[5][0] = new Piece("white", "bishop", (int) (squareSize * pieceSizeMult));
        board[6][0] = new Piece("white", "knight", (int) (squareSize * pieceSizeMult));
        board[7][0] = new Piece("white", "rook", (int) (squareSize * pieceSizeMult));
        for (int i = 0; i < 8; i++) {
            board[i][1] = new Piece("white", "pawn", (int) (squareSize * pieceSizeMult));
        }

        for (int i = 0; i < 7; i++) {
            for (int j = 2; j < 6; j++) {
                board[i][j] = null;
            }
        }

        for (int i = 0; i < 8; i++) {
            board[i][6] = new Piece("black", "pawn", (int) (squareSize * pieceSizeMult));
        }
        board[0][7] = new Piece("black", "rook", (int) (squareSize * pieceSizeMult));
        board[1][7] = new Piece("black", "knight", (int) (squareSize * pieceSizeMult));
        board[2][7] = new Piece("black", "bishop", (int) (squareSize * pieceSizeMult));
        board[3][7] = new Piece("black", "queen", (int) (squareSize * pieceSizeMult));
        board[4][7] = new Piece("black", "king", (int) (squareSize * pieceSizeMult));
        board[5][7] = new Piece("black", "bishop", (int) (squareSize * pieceSizeMult));
        board[6][7] = new Piece("black", "knight", (int) (squareSize * pieceSizeMult));
        board[7][7] = new Piece("black", "rook", (int) (squareSize * pieceSizeMult));
    }

    public void draw(Graphics g) {
        Color blackSquare = new Color(157, 87, 27);
        Color whiteSquare = new Color(230, 204, 171);
        int height = 7 * squareSize;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                g.setColor(i % 2 == 0 ? (j % 2 == 0 ? blackSquare : whiteSquare) : (j % 2 == 0 ? whiteSquare : blackSquare)); // Rutorna ska vara varannan svart, varannan vit. beroende på om i och j är jämnt/udda blir rutan svart eller vit
                g.fillRect(j * squareSize, height, squareSize, squareSize);

                if (board[j][i] != null) {
                    board[j][i].setX((int) (j * squareSize + squareSize * (1 - pieceSizeMult) / 2));
                    board[j][i].setY((int) (height + squareSize * (1 - pieceSizeMult) / 2));
                    board[j][i].draw(g);
                }
            }
            height -= squareSize;
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[j][i] != null) {
                    board[j][i].drawMoveDisplayer(g); // detta är en egen for-loop så att alla MoveDisplayers hamnar ovanpå rutorna
                }
            }
        }
    }
}
