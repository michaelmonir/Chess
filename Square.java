import java.awt.*;

public class Square
{
    public int x;
    public int y;

    public Piece mypiece;

    public static Square copy_Square(Square s)
    {
        if (s == null) return null;
        return new Square(s.x, s.y, s.mypiece);
    }
    public static boolean compare_square(Square s1, Square s2)
    {
        if (s1 == null || s2 == null) return false;
        if (s1.x == s2.x && s1.y == s2.y) return true;
        return false;
    }
    public static boolean compare_square(Square s1, Point s2)
    {
        if (s1 == null || s2 == null) return false;
        if (s1.x == s2.x && s1.y == s2.y) return true;
        return false;
    }
    public static boolean compare_square(Point s1, Point s2)
    {
        if (s1 == null || s2 == null) return false;
        if (s1.x == s2.x && s1.y == s2.y) return true;
        return false;
    }
    public Square(int xx, int yy)
    {
        this.x = xx;
        this.y = yy;
    }
    public Square(int xx, int yy, Piece piece)
    {
        this(xx, yy);
        if (piece != null)
            this.mypiece = piece.copy_Piece(piece.myBoard);
        else
            this.mypiece = null;
    }
    public static boolean isvalid_square(int x1, int y1)
    {
        if (x1 < 0 || y1 < 0 || x1 >= 8 || y1 >= 8) return false;
        return true;
    }
    public boolean ispiece()
    {
        if (this.mypiece != null) return true;
        return false;
    }
}
