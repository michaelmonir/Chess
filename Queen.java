import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece
{
    public Queen(int xx, int yy, Color c, Board board) {super(xx, yy, c, board);}

    @Override
    public boolean isQueen()
    {
        return true;
    }

    @Override
    public Piece copy_Piece(Board board)
    {

        return new Queen(this.myPoint.x, this.myPoint.y, this.piece_color, board);
    }

    @Override
    public List<Point> getvalid()
    {
        int x1 = this.myPoint.x, y1 = this.myPoint.y;
        List<Point> list = new ArrayList<>();


        Square[][] chess = this.myBoard.chess;

        // Bishop
        Piece_Moves.move_diagonal(this, list);

        // rook
        Piece_Moves.move_straight(this, list);

        return list;
    }
}
