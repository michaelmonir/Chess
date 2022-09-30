import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece
{
    public King(int xx, int yy, Color c, Board board) {super(xx, yy, c, board);}
    @Override
    public boolean isKing()
    {
        return true;
    }
    @Override
    public Piece copy_Piece(Board board)
    {

        return new King(this.myPoint.x, this.myPoint.y, this.piece_color, board);
    }
    @Override
    public List<Point> getvalid()
    {
        List<Point> list = new ArrayList<>();
        Piece_Moves.move_King(this, list);

        if(this.Castle_King_side()){
            if(this.piece_color==Color.White){
                list.add(new Point(0,6));
            }
            else{
                list.add(new Point(7,6));
            }
        }
        if(this.Castle_queen_side()){
            if(this.piece_color==Color.White){
                list.add(new Point(0,2));
            }
            else{
                list.add(new Point(7,2));
            }
        }
        return list;
    }
    public boolean Castle_queen_side()
    {
        if(this.myBoard.castle_white_Queen==false && this.myBoard.castle_black_Queen==false)return false;
        int x=7;
        if (this.piece_color==Color.White)
            x=0;
        for (int i=1;i<=4;i++)
        {
            Point nei=new Point(x,i);
            if(i!=4 &&this.myBoard.chess[x][i].ispiece()==true )
                return false;
            if(i!=4 && Checkings.check_check_squ(this.myBoard,this.piece_color,nei)==true )
                return false;
        }
        return true;
    }
    public boolean Castle_King_side()
    {
        if(this.myBoard.castle_white_king==false && this.myBoard.castle_black_king==false)return false;
        int x=7;
        if (this.piece_color==Color.White)
            x=0;
        for (int i=4;i<=6;i++)
        {
            Point nei=new Point(x,i);
            if(i!=4 &&this.myBoard.chess[x][i].ispiece()==true )
                return false;
            if(i!=4 && Checkings.check_check_squ(this.myBoard,this.piece_color,nei)==true )
                return false;
        }
        return true;
    }
}
