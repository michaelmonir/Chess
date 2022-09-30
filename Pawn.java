import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece
{
    public Pawn(int xx, int yy, Color c, Board board) {super(xx, yy, c, board);}

    @Override
    public boolean isPawn()
    {
        return true;
    }

    @Override
    public Piece copy_Piece(Board board)
    {

        return new Pawn(this.myPoint.x, this.myPoint.y, this.piece_color, board);
    }

    public Square check_en_passant(int x2, int y2)
    {
        int x1 = this.myPoint.x;
        int y1 = this.myPoint.y;

        if (Math.abs(x2 - x1) == 2)
        {
            if (this.piece_color == Color.White)
                return new Square(x1 + 1, y1);
            else
                return new Square(x1 - 1, y1);
        }
        return null;
    }

    @Override
    public List<Point> getvalid()
    {
        List<Point> list = new ArrayList<>();

        int x = this.myPoint.x;
        int y = this.myPoint.y;

        Square[][] chess = this.myBoard.chess;

        if (this.piece_color == Color.White)
        {
            if (Square.isvalid_square(x+1, y - 1))
            {
                if (!chess[x + 1][y - 1].ispiece())
                {
                    if (Square.compare_square(this.myBoard.en_passent, new Point(x + 1, y - 1)))
                        list.add(new Point(x + 1, y - 1));
                }
                else if (( chess[x + 1][y - 1]).mypiece.piece_color != this.piece_color)
                    list.add(new Point(x + 1, y - 1));
            }
            if (Square.isvalid_square(x+1, y + 1))
            {
                if (!chess[x + 1][y + 1].ispiece())
                {
                    if (Square.compare_square(this.myBoard.en_passent, new Point(x + 1, y + 1)))
                        list.add(new Point(x + 1, y + 1));
                }
                else if (( chess[x + 1][y + 1]).mypiece.piece_color != this.piece_color)
                    list.add(new Point(x + 1, y + 1));
            }

            if (!chess[x + 1][y].ispiece())
                list.add(new Point(x + 1, y));
            if (x == 1 && !chess[x + 2][y].ispiece() && !chess[x + 1][y].ispiece())
                list.add(new Point(x + 2, y));
        }
        else
        {
            if (Square.isvalid_square(x - 1, y - 1))
            {
                if (!chess[x - 1][y - 1].ispiece())
                {
                    if (Square.compare_square(this.myBoard.en_passent, new Point(x - 1, y - 1)))
                        list.add(new Point(x - 1, y - 1));
                }
                else if (( chess[x - 1][y - 1]).mypiece.piece_color != this.piece_color)
                    list.add(new Point(x - 1, y - 1));
            }
            if (Square.isvalid_square(x - 1, y + 1))
            {
                if (!chess[x - 1][y + 1].ispiece())
                {
                    if (Square.compare_square(this.myBoard.en_passent, new Point(x - 1, y + 1)))
                        list.add(new Point(x - 1, y + 1));
                }
                else if (( chess[x - 1][y + 1]).mypiece.piece_color != this.piece_color)
                    list.add(new Point(x - 1, y + 1));
            }

            if (!chess[x - 1][y].ispiece())
                list.add(new Point(x - 1, y));
            if (x == 6 && !chess[x - 2][y].ispiece() && !chess[x - 1][y].ispiece())
                list.add(new Point(x - 2, y));
        }

        return list;
    }

}
