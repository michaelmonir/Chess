import java.awt.*;
import java.util.*;

public class Board
{
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    public Square[][] chess;
    public boolean[][] highlight;

    public static int num_of_moves = 0;

    public Turn myturn;
    public Check mycheck;
    public boolean castle_white_king;
    public boolean castle_white_Queen;
    public boolean castle_black_king;
    public boolean castle_black_Queen;
    public Square en_passent;
    public Point King_White_Pos;
    public Point King_Black_Pos;

    public Board()
    {
        chess = new Square[8][8];
        highlight = new boolean[8][8];

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                chess[i][j] = new Square(i, j);
        this.castle_white_king = false;
        this.castle_black_king = false;
        this.castle_white_Queen = false;
        this.castle_black_Queen = false;
        this.en_passent = null;
    }
    public static Board copy_Board(Board board)
    {
        Board newboard = new Board();
        newboard.en_passent = Square.copy_Square(board.en_passent);

        newboard.myturn = board.myturn;
        newboard.mycheck = board.mycheck;
        newboard.castle_white_king = board.castle_white_king;
        newboard.castle_white_Queen = board.castle_white_Queen;
        newboard.castle_black_king = board.castle_black_king;
        newboard.castle_black_Queen = board.castle_black_Queen;

        newboard.King_White_Pos = new Point(board.King_White_Pos.x, board.King_White_Pos.y);
        newboard.King_Black_Pos = new Point(board.King_Black_Pos.x, board.King_Black_Pos.y);

        newboard.chess = new Square[8][8];

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                newboard.chess[i][j] = new Square(i, j);
                if (board.chess[i][j].ispiece() == false) continue;
                Piece piece = board.chess[i][j].mypiece.copy_Piece(newboard);
                newboard.chess[i][j].mypiece = piece;
            }
        }
        return newboard;
    }
    public static String getfen(Board board)
    {
        Square [][] current = board.chess;
        Turn t = board.myturn;
        boolean cwk = board.castle_white_king;
        boolean cwq = board.castle_white_Queen;
        boolean cbk = board.castle_black_king;
        boolean cbq = board.castle_black_Queen;
        Square s = board.en_passent;

        String ans = "";

        for (int i = 7; i >= 0; i--)
        {
            for (int j = 0; j < 8; j++)
            {
                int suc = 0;

                for (; j < 8; j++)
                {
                    if (current[i][j].ispiece() == false) break;
                    suc++;
                }
                if (suc != 0)
                    ans += (Integer.toString(suc));

                if (j >= 8) continue;

                String c = "";

                if (current[i][j].mypiece.isBishop())
                    c = "b";
                else if (current[i][j].mypiece.isKnight())
                    c = "n";
                else if (current[i][j].mypiece.isKing())
                    c = "k";
                else if (current[i][j].mypiece.isPawn())
                    c = "p";
                else if (current[i][j].mypiece.isQueen())
                    c = "q";
                else if (current[i][j].mypiece.isRook())
                    c = "r";

                if (current[i][j].mypiece.piece_color == Color.White)
                    c = c.toUpperCase();
                ans += c;
                if (i != 0) ans += "/";
            }

        }

        if (t == Turn.White) ans += " w ";
        else ans += " b ";

        if (cwk) ans += "K";
        if (cwq) ans += "Q";
        if (cbk) ans += "k";
        if (cbq) ans += "q";

        ans += " ";

        String[] temp = new String[]{"a", "b", "c", "d", "e", "f", "g", "h"};

        if (s!= null)
        {
            int x = s.x, y = s.y;
            ans += temp[y];
            ans += Integer.toString(x);
        }
        return ans;
    }
    public static Board getboard(String s)
    {
        Board board = new Board();

        int x = 7; int y = 0;
        int index = 0;

        while (x >= 0)
        {
            if (s.charAt(index) == '/')
            {
                index++;
                continue;
            }

            if (Character.isDigit(s.charAt(index)))
            {
                y += Integer.parseInt(String.valueOf(s.charAt(index)));

                if (y == 8)
                {
                    y = 0;
                    x--;
                }
                index++;
                continue;
            }

            else if (String.valueOf(s.charAt(index)).toLowerCase().equals("b"))
                board.chess[x][y].mypiece = new Bishop(x, y, Color.Black, board);
            else if (String.valueOf(s.charAt(index)).toLowerCase().equals("k"))
            {
                board.chess[x][y].mypiece = new King(x, y, Color.Black, board);
                if (Character.isUpperCase(s.charAt(index)))
                    board.King_White_Pos = new Point(x, y);
                else
                    board.King_Black_Pos = new Point(x, y);
            }
            else if (String.valueOf(s.charAt(index)).toLowerCase().equals("p"))
                board.chess[x][y].mypiece = new Pawn(x, y, Color.Black, board);
            else if (String.valueOf(s.charAt(index)).toLowerCase().equals("q"))
                board.chess[x][y].mypiece = new Queen(x, y, Color.Black, board);
            else if (String.valueOf(s.charAt(index)).toLowerCase().equals("n"))
                board.chess[x][y].mypiece = new Knight(x, y, Color.Black, board);
            else if (String.valueOf(s.charAt(index)).toLowerCase().equals("r"))
                board.chess[x][y].mypiece = new Rook(x, y, Color.Black, board);

            if (Character.isUpperCase(s.charAt(index)))
                board.chess[x][y].mypiece.piece_color = Color.White;
            y++;
            if (y == 8)
            {
                y = 0; x--;
            }
            index++;
        }

        index++;
        if (s.charAt(index) == 'w') board.myturn = Turn.White;
        else board.myturn = Turn.Black;
        index+=2;

        if (s.charAt(index) == 'K')
        {
            board.castle_white_king = true;
            index++;
        }
        if (s.charAt(index) == 'Q')
        {
            board.castle_white_Queen = true;
            index++;
        }
        if (s.charAt(index) == 'k')
        {
            board.castle_black_king = true;
            index++;
        }
        if (s.charAt(index) == 'q')
        {
            board.castle_black_Queen = true;
            index++;
        }

        index++;

        if (index != s.length())
        {
            int yy = (int)s.charAt(index) - 97;
            int xx = (int)s.charAt(index + 1) - 49;

            board.en_passent = new Square(xx, yy);
        }
        return board;
    }
    public static boolean turn_equal_color(Turn t, Color c)
    {
        if (t == Turn.White && c == Color.White) return true;
        if (t == Turn.Black && c == Color.Black) return true;
        return false;
    }
    public void Print()
    {
        for (int i = 7; i >= 0; i--)
        {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < 8; j++)
            {
                if (this.highlight[i][j])
                    System.out.print(ANSI_RED);

                if (this.chess[i][j].ispiece() == false)
                {
                    System.out.print(" ■ ");
                    System.out.print(ANSI_RESET);
                    continue;
                }
                Piece piece = this.chess[i][j].mypiece;

                String c = "z";

                if (piece.isBishop())
                    c = "b";
                else if (piece.isKnight())
                    c = "n";
                else if (piece.isPawn())
                    c = "p";
                else if (piece.isKing())
                    c = "k";
                else if (piece.isRook())
                    c = "r";
                else if (piece.isQueen())
                    c = "q";

                if (piece.piece_color == Color.White)
                    c = c.toUpperCase();
                System.out.print(" " + c + " ");

                System.out.print(ANSI_RESET);
            }
            System.out.println();
        }

        System.out.print("_ ");
        for (int i = 0; i < 8; i++)
            System.out.print(" " + (i + 1) + " ");
        System.out.println();
    }
    public void make_highlight(int x, int y)
    {

        this.highlight[x][y] = true;
    }
    public void clear_highlight()
    {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                this.highlight[i][j] = false;
    }
}
enum Turn
{
    White, Black
}
enum Check
{
    White, Black, none
}

enum Game_Status
{
    White, Black, Draw, none
}

class Board_Status
{
    public Turn myturn;
    public Check mycheck;
    public boolean castle_white_king;
    public boolean castle_white_Queen;
    public boolean castle_black_king;
    public boolean castle_black_Queen;
    public Square en_passent;
    public Point King_White_Pos;
    public Point King_Black_Pos;
    public ArrayList<Square> list;

    public Board_Status(Turn t, Check c, boolean cwk, boolean cwq, boolean cbk, boolean cbq, Square s, Point p1, Point p2)
    {
        this.myturn = t;
        this.mycheck = c;
        this.castle_white_king = cwk;
        this.castle_white_Queen = cwq;
        this.castle_black_king = cbk;
        this.castle_black_Queen = cbq;
        this.King_White_Pos = new Point(p1.x, p1.y);
        this.King_Black_Pos = new Point(p2.x, p2.y);
        this.list = new ArrayList<>();
    }
}