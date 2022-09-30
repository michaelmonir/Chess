import java.awt.*;
import java.util.List;


public abstract class Piece
{
    public Point myPoint;

    public Board myBoard;

    public Color piece_color;

    public Piece(int xx, int yy, Color c, Board board)
    {
        this.myBoard = board;
        //mysquare = board.chess[xx][yy];
        myPoint = new Point(xx, yy);

        this.piece_color = c;
    }
    public Piece copy_Piece(Board board)
    {

        return null;
    }
    public boolean isKnight()
    {
        return false;
    }
    public boolean isKing()
    {
        return false;
    }
    public boolean isPawn()
    {
        return false;
    }
    public boolean isBishop()
    {
        return false;
    }
    public boolean isQueen()
    {
        return false;
    }
    public boolean isRook()
    {
        return false;
    }
    public List<Point> getvalid()
    {
        return null;
    }
    public void move(int x2, int y2)
    {
        this.myPoint = new Point(x2, y2);
    }

}
enum Color
{
    White, Black
}



