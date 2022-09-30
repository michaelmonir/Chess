import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece
{
    public Bishop(int xx, int yy, Color c, Board board) {super(xx, yy, c, board);}

    @Override
    public boolean isBishop()
    {
        return true;
    }

    @Override
    public Piece copy_Piece(Board board)
    {

        return new Bishop(this.myPoint.x, this.myPoint.y, this.piece_color, board);
    }
    @Override
    public List<Point> getvalid()
    {
        int x1 = this.myPoint.x, y1 = this.myPoint.y;
        List<Point> list = new ArrayList<>();

        Square[][] chess = this.myBoard.chess;

        Piece_Moves.move_diagonal(this, list);

        return list;
    }
}
