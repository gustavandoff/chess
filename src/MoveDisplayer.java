
import java.awt.Color;
import java.awt.Graphics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gusta
 */
public class MoveDisplayer {

    private int size, x, y, xIndex, yIndex;
    private Color color = Color.green;

    public MoveDisplayer(int size, int x, int y, int xIndex, int yIndex) {
        this.size = size;
        this.x = x;
        this.y = y;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getxIndex() {
        return xIndex;
    }

    public int getyIndex() {
        return yIndex;
    }

    public Color getColor() {
        return color;
    }

    public void draw(Graphics g) {
        g.setColor(color);

        int sizeDiff = (int) Math.round(size * 0.4);
        g.fillOval(x + (int) (sizeDiff / 2), y + (int) (sizeDiff / 2), size - (sizeDiff), size - (sizeDiff));
    }
}
