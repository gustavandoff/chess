
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gustav.andoff
 */
public class Piece {

    private String colorS, name;
    private Color colorC;
    private int size, x, y;
    private double moveDisplayerSizeMult = 0.75;
    private boolean hasMoved = false;
    private ArrayList<MoveDisplayer> mds = new ArrayList<>();
    private ArrayList<Integer> moveCombos = new ArrayList<>();

    public Piece(String color, String name, int size) {
        this.colorS = color;
        this.colorC = (color.equals("white") ? Color.white : Color.black);
        this.size = size;
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getColorS() {
        return colorS;
    }

    public String getName() {
        return name;
    }

    public double getMoveDisplayerSizeMult() {
        return moveDisplayerSizeMult;
    }

    public ArrayList<MoveDisplayer> getMds() {
        return mds;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public ArrayList<Integer> getMoveCombos() {
        return moveCombos;
    }

    public void clearMds() {
        mds.clear();
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * sätter moveCombos till rätt värden
     */
    public void setMoveCombos() {
        switch (name) { // bestämmer hur pjäsen kan gå beroende på vad den har för namn
            case "king": // kung

                /**
                 * moveCombos innehåller alla drag som en pjäs kan göra. Arrayn
                 * är indelad i grupper om 3. Varje grupp talar om en riktning
                 * pjäsen kan gå. Den den första siffran i varje grupp talar om
                 * hur många rutor i x-led pjäsen går med ett steg. Den andra
                 * siffran talar om hur många rutor i y-led pjäsen går med ett
                 * steg. Den tredje rutan talar om hur många steg pjäsen kan ta
                 * per tur. T.ex. kungen kan gå 1 steg åt varje håll - rakt och
                 * diagonalt.
                 */
                moveCombos.add(0); // antal rutor i x-led
                moveCombos.add(1); // antal rutor i y-led
                moveCombos.add(1); // antal steg per tur

                moveCombos.add(0);
                moveCombos.add(-1);
                moveCombos.add(1);

                moveCombos.add(1);
                moveCombos.add(0);
                moveCombos.add(1);

                moveCombos.add(-1);
                moveCombos.add(0);
                moveCombos.add(1);

                moveCombos.add(1);
                moveCombos.add(1);
                moveCombos.add(1);

                moveCombos.add(-1);
                moveCombos.add(1);
                moveCombos.add(1);

                moveCombos.add(-1);
                moveCombos.add(-1);
                moveCombos.add(1);

                moveCombos.add(1);
                moveCombos.add(-1);
                moveCombos.add(1);
                break;
            case "bishop": // löpare
                moveCombos.add(1);
                moveCombos.add(1);
                moveCombos.add(7);

                moveCombos.add(1);
                moveCombos.add(-1);
                moveCombos.add(7);

                moveCombos.add(-1);
                moveCombos.add(1);
                moveCombos.add(7);

                moveCombos.add(-1);
                moveCombos.add(-1);
                moveCombos.add(7);
                break;
            case "knight": // häst
                moveCombos.add(1);
                moveCombos.add(2);
                moveCombos.add(1);

                moveCombos.add(-1);
                moveCombos.add(2);
                moveCombos.add(1);

                moveCombos.add(1);
                moveCombos.add(-2);
                moveCombos.add(1);

                moveCombos.add(-1);
                moveCombos.add(-2);
                moveCombos.add(1);

                moveCombos.add(2);
                moveCombos.add(1);
                moveCombos.add(1);

                moveCombos.add(-2);
                moveCombos.add(1);
                moveCombos.add(1);

                moveCombos.add(-2);
                moveCombos.add(-1);
                moveCombos.add(1);

                moveCombos.add(2);
                moveCombos.add(-1);
                moveCombos.add(1);
                break;
            case "queen": // drottning
                moveCombos.add(0);
                moveCombos.add(1);
                moveCombos.add(7);

                moveCombos.add(0);
                moveCombos.add(-1);
                moveCombos.add(7);

                moveCombos.add(1);
                moveCombos.add(0);
                moveCombos.add(7);

                moveCombos.add(-1);
                moveCombos.add(0);
                moveCombos.add(7);

                moveCombos.add(1);
                moveCombos.add(1);
                moveCombos.add(7);

                moveCombos.add(-1);
                moveCombos.add(1);
                moveCombos.add(7);

                moveCombos.add(-1);
                moveCombos.add(-1);
                moveCombos.add(7);

                moveCombos.add(1);
                moveCombos.add(-1);
                moveCombos.add(7);
                break;
            case "pawn": // bonde
                moveCombos.add(0);
                moveCombos.add(1);
                moveCombos.add(2);
                break;
            case "rook": // torn
                moveCombos.add(0);
                moveCombos.add(1);
                moveCombos.add(7);

                moveCombos.add(0);
                moveCombos.add(-1);
                moveCombos.add(7);

                moveCombos.add(1);
                moveCombos.add(0);
                moveCombos.add(7);

                moveCombos.add(-1);
                moveCombos.add(0);
                moveCombos.add(7);
                break;
        }
    }

    /**
     * anropas för att rita ut punkterna dit en pjäs kan flyttas
     *
     * @param board spelbrädan
     * @param squareSize sidlängden på rutorna
     * @param pXIndex indexet i x-led för pjäsen
     * @param pYIndex indexet i y-led för pjäsen
     * @param move om den är false ska metoden bara kolla var pjäsen kan gå utan
     * att faktiskt rita ut några moveDisplayers
     * @return om pjäsen har någonstans att gå returneras true, annars false
     */
    public boolean showMoves(Piece[][] board, int squareSize, int pXIndex, int pYIndex, boolean move) {
        int mdX;
        int mdY;
        boolean canMove = false; // visar om pjäsen kan gå. om den någon gång hittar en ruta att gå till sätts canMove till true
        setMoveCombos();

        for (int i = 0; i < moveCombos.size(); i += 3) { // i ökar med 3 eftersom det är en ny grupp per 3 index
            mdX = moveCombos.get(i); // mdX sätts till första siffran i varje grupp
            mdY = moveCombos.get(i + 1); // mdY sätts till andra siffran i varje grupp
            mdY *= (colorS.equals("black") && name.equals("pawn") ? -1 : 1);// om det är en svart bonde ska den gå neråt istället för uppåt

            for (int j = 1; j <= moveCombos.get(i + 2); j++) { // kör lika många gånger som den tredje siffran i varje grupp
                if ((pXIndex + mdX * j <= 7 && pXIndex + mdX * j >= 0) && (pYIndex + mdY * j <= 7 && pYIndex + mdY * j >= 0) && (board[pXIndex + mdX * j][pYIndex + mdY * j] == null || (board[pXIndex + mdX * j][pYIndex + mdY * j].isOpCol(this)) && !name.equals("pawn"))) {
                    Piece[][] testBoard = copyArray(board);
                    testBoard[pXIndex + mdX * j][pYIndex + mdY * j] = testBoard[pXIndex][pYIndex];
                    testBoard[pXIndex][pYIndex] = null;
                    if (!canAnyCaptureKing(testBoard, squareSize)) {
                        if (move) {
                            mds.add(new MoveDisplayer((int) (squareSize * moveDisplayerSizeMult), x + (mdX * j * squareSize), y - (mdY * j * squareSize), pXIndex + j * mdX, pYIndex + j * mdY));
                        }
                        canMove = true;
                    }
                    if (board[pXIndex + mdX * j][pYIndex + mdY * j] != null && board[pXIndex + mdX * j][pYIndex + mdY * j].isOpCol(this)) { // om senaste md:n placerades på en fientlig pjäs så ska ingen anna md sättas ut bakom den
                        break;
                    }
                } else {
                    break;
                }
                if (name.equals("pawn") && hasMoved) { // när en bonde har gått en gång får den bara gå 1 steg framåt istället för 2 som den kan i början
                    break;
                }
            }
        }
        if (name.equals("pawn")) { // bonden ska kunna gå snett för att fånga
            int isWhite = colorS.equals("white") ? 1 : -1; // om bonden är svart ska den gå neråt
            for (int i = -1; i <= 1; i += 2) {
                if ((pXIndex + i <= 7 && pXIndex + i >= 0) && (pYIndex + isWhite <= 7 && pYIndex + isWhite >= 0) && board[pXIndex + i][pYIndex + isWhite] != null && board[pXIndex + i][pYIndex + isWhite].isOpCol(this)) {
                    Piece[][] testBoard = copyArray(board);
                    testBoard[pXIndex + i][pYIndex + isWhite] = testBoard[pXIndex][pYIndex];
                    testBoard[pXIndex][pYIndex] = null;
                    if (!canAnyCaptureKing(testBoard, squareSize)) {
                        if (move) {
                            mds.add(new MoveDisplayer((int) (squareSize * moveDisplayerSizeMult), x + (i * squareSize), y - (isWhite * squareSize), pXIndex + i, pYIndex + isWhite));
                        }
                        canMove = true;
                    }
                }
            }
        }
        if ((name.equals("rook") || name.equals("king")) && !hasMoved) { // man ska kunna göra rockad
            String piece = name.equals("rook") ? "king" : "rook"; // om man trycker på kungen ska den kolla efter tornet och tvärt om
            for (int j = -1; j <= 1; j += 2) {
                for (int i = j; i != 5 * j; i += j) {
                    if (pXIndex + i <= 7 && pXIndex + i >= 0 && board[pXIndex + i][pYIndex] != null) {
                        if (board[pXIndex + i][pYIndex].getName().equals(piece) && !board[pXIndex + i][pYIndex].isOpCol(this) && !board[pXIndex + i][pYIndex].hasMoved()) {
                            Piece[][] testBoard = copyArray(board);
                            int xRIndex;
                            int yRIndex;
                            int xKIndex;
                            int yKIndex;

                            if (piece.equals("rook")) {
                                xRIndex = pXIndex;
                                yRIndex = pYIndex;
                                xKIndex = pXIndex + i;
                                yKIndex = pYIndex;
                            } else {
                                xKIndex = pXIndex;
                                yKIndex = pYIndex;
                                xRIndex = pXIndex + i;
                                yRIndex = pYIndex;
                            }
                            if (xRIndex > xKIndex) {
                                testBoard[xRIndex - 2][yRIndex] = testBoard[xRIndex][yRIndex];
                                testBoard[xKIndex + 2][yKIndex] = testBoard[xKIndex][yKIndex];
                            } else {
                                testBoard[xRIndex + 3][yRIndex] = testBoard[xRIndex][yRIndex];
                                testBoard[xKIndex - 2][yKIndex] = testBoard[xKIndex][yKIndex];
                            }
                            testBoard[xRIndex][yRIndex] = null;
                            testBoard[xKIndex][yKIndex] = null;

                            if (!canAnyCaptureKing(testBoard, squareSize)) {
                                if (move) {
                                    mds.add(new MoveDisplayer((int) (squareSize * moveDisplayerSizeMult), x + (i * squareSize), y, pXIndex + i, pYIndex));
                                    mds.get(mds.size() - 1).setColor(Color.red);
                                }
                                canMove = true;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        return canMove;
    }

    /**
     * kollar om den här pjäsen kan fånga kungen
     *
     * @param board spelbrädan
     * @param squareSize sidlängden på rutorna
     * @param pXIndex indexet i x-led för pjäsen
     * @param pYIndex indexet i y-led för pjäsen
     * @param moveCombos ArrayListan moveCombos
     * @return true eller false beroeonde på om pjäsen kan ta kungen eller inte
     */
    public boolean canThisCaptureKing(Piece[][] board, int squareSize, int pXIndex, int pYIndex, ArrayList<Integer> moveCombos) {
        if (name.equals("pawn")) { // bonden ska kunna gå snett för att fånga
            int isWhite = colorS.equals("white") ? 1 : -1; // om bonden är svart ska den gå neråt
            for (int i = -1; i <= 1; i += 2) {
                if ((pXIndex + i <= 7 && pXIndex + i >= 0) && (pYIndex + isWhite <= 7 && pYIndex + isWhite >= 0) && board[pXIndex + i][pYIndex + isWhite] != null && board[pXIndex + i][pYIndex + isWhite].getName().equals("king") && board[pXIndex + i][pYIndex + isWhite].isOpCol(this)) {
                    return true;
                }
            }
        } else {
            int checkedX; // x-värde på den ruta som kollas om kungen är där
            int checkedY; // y-värde på den ruta som kollas om kungen är där
            for (int i = 0; i < moveCombos.size(); i += 3) { // i ökar med 3 eftersom det är en ny grupp per 3 index
                checkedX = moveCombos.get(i); // sätts till första siffran i varje grupp
                checkedY = moveCombos.get(i + 1); // sätts till andra siffran i varje grupp

                for (int j = 1; j <= moveCombos.get(i + 2); j++) { // kör lika många gånger som den tredje siffran i varje grupp
                    if ((pXIndex + checkedX * j <= 7 && pXIndex + checkedX * j >= 0) && (pYIndex + checkedY * j <= 7 && pYIndex + checkedY * j >= 0) && (board[pXIndex + checkedX * j][pYIndex + checkedY * j] == null || board[pXIndex + checkedX * j][pYIndex + checkedY * j].isOpCol(this))) {
                        if (board[pXIndex + checkedX * j][pYIndex + checkedY * j] != null) {
                            if (board[pXIndex + checkedX * j][pYIndex + checkedY * j].getName().equals("king")) {
                                return true;
                            }
                            if (board[pXIndex + checkedX * j][pYIndex + checkedY * j].isOpCol(this)) { // om senaste md:n placerades på en fientlig pjäs så ska ingen anna md sättas ut bakom den
                                break;
                            }
                        }
                    } else {
                        break;
                    }
                }
            }
        }

        return false;
    }

    /**
     * går igenom varje pjäs och kollar om någon kan ta kungen
     *
     * @param board spelbrädan
     * @param squareSize varje rutas storlek
     * @return om någon pjäs kan ta kungen returneras true, annars false
     */
    public boolean canAnyCaptureKing(Piece[][] board, int squareSize) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null && board[i][j].isOpCol(this) && board[i][j].canThisCaptureKing(board, squareSize, i, j, board[i][j].getMoveCombos())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * kollar om en pjäs har motsatt färg denna
     *
     * @param p pjäsen som ska kollas
     * @return true eller false beroende på om den är motsatt eller inte
     */
    public boolean isOpCol(Piece p) {
        return !p.getColorS().equals(colorS);
    }

    public void drawMoveDisplayer(Graphics g) {
        for (int i = 0; i < mds.size(); i++) {
            mds.get(i).draw(g);
        }
    }

    private Piece[][] copyArray(Piece[][] oldBoard) {
        Piece[][] newBoard = new Piece[oldBoard.length][oldBoard.length];
        for (int i = 0; i < oldBoard.length; i++) {
            for (int j = 0; j < oldBoard.length; j++) {
                newBoard[i][j] = oldBoard[i][j];
            }
        }
        return newBoard;
    }

    public void draw(Graphics g) {
        g.setColor(colorC);
        switch (name) { // varje pjäs ska ritas ut på ett eget sätt
            case "king": // kung
                g.fillRect(x + size / 3, y, size / 3, size);
                g.fillRect(x, y + size / 3, size, size / 3);

                break;
            case "bishop": // löpare
                g.fillRect(x + size / 3, y + size / 3, size / 3, size * 2 / 3);
                g.fillRect(x + size / 4, y + size / 5, size / 2, size / 4);
                g.fillRect(x + size / 3, y + size / 10, size / 3, size / 3);
                g.fillRect(x + size * 5 / 12, y, size / 5, size / 3);

                break;
            case "knight": // häst
                g.fillRect(x, y, (int) (size * 0.6), size);
                g.fillRect(x, y + (int) (size * 0.1), size, size / 3);
                break;
            case "queen": // drottning
                g.fillRect(x, y + size / 2, size, size / 2);

                g.fillRect(x, y, size / 6, size / 2);
                g.fillRect(x + (int) (size / 3.2), y, size / 6, size / 2);
                g.fillRect(x + (int) (size / 1.5) - size / 7, y, size / 6, size / 2);
                g.fillRect(x + size - size / 6, y, size / 6, size / 2);

                break;
            case "pawn": // bonde
                g.fillOval(x + size / 4, y + size / 3, size / 2, size / 2);
                g.fillRect(x + size / 3, y + size / 2, size / 3, size / 2);
                break;
            case "rook": // torn
                g.fillRect(x + size / 4, y, size / 2, size);
                break;
        }
    }

    @Override
    public String toString() {
        switch (name) {
            case "pawn":
                return "Bonde";
            case "rook":
                return "Torn";
            case "knight":
                return "Häst";
            case "bishop":
                return "Löpare";
            case "queen":
                return "Drottning";
            case "king":
                return "Kung";
        }
        return "hejhej harald. det här kommer ändå aldrig att nås i koden";
    }
}
