import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece
{
    public Knight(int xx, int yy, Color c, Board board) {super(xx, yy, c, board);}

    @Override
    public boolean isKnight()
    {
        return true;
    }

    @Override
    public Piece copy_Piece(Board board)
    {

        return new Knight(this.myPoint.x, this.myPoint.y, this.piece_color, board);
    }

    @Override
    public List<Point> getvalid()
    {
        List<Point> list = new ArrayList<>();
        Piece_Moves.move_Knight(this, list);
        return list;
    }
}
