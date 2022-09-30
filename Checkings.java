import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Checkings
{
    public static void check_move_Rook(int x1, int y1, int x2, int y2, Board board, Board_Status status)
    {
        Piece piece = board.chess[x1][y1].mypiece;

        if (piece.isKing()==true && Turn.White == board.myturn)
        {
            if(board.castle_white_king==true)
                if(x1==0 && y1==4 && x2==0 && y2==6)
                    Piece_Moves.change_place(0,7,0,5, board, status);

            if(board.castle_white_Queen==true)
                if(x1==0 && y1==4 && x2==0 && y2==2)
                    Piece_Moves.change_place(0,0,0,3, board, status);
        }
        else  if(piece.isKing()==true && Turn.Black == board.myturn)
        {
            if(board.castle_black_king==true)
                if(x1== 7&& y1==4 && x2== 7&& y2==6)
                    Piece_Moves.change_place(7,7,7,5, board, status);

            if(board.castle_black_Queen==true)
                if(x1== 7&& y1==4 && x2==7 && y2==2)
                    Piece_Moves.change_place(7,0,7,3, board, status);
        }
    }
    public static boolean check_check_color(Board board, Color c)
    {

        for (int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if (board.chess[i][j].ispiece() == false) continue;
                if (board.chess[i][j].mypiece.piece_color == c) continue;


                List<Point> list1 = board.chess[i][j].mypiece.getvalid();
                for (int k = 0; k < list1.size(); k++)
                {
                    if (board.chess[list1.get(k).x][list1.get(k).y].ispiece() == false) continue;
                    Piece piece = board.chess[list1.get(k).x][list1.get(k).y].mypiece;

                    if (piece.isKing() && piece.piece_color == c)
                        return true;
                }
            }
        }
        return false;
    }
    public static boolean check_position(Board board, Check check, Turn turn)
    {
        //boolean white_check = Checkings.check_check_color(board, Color.White);
        //boolean black_check = Checkings.check_check_color(board, Color.Black);

        boolean white_check = Checkings.check_possible_checks(board, Color.White);
        boolean black_check = Checkings.check_possible_checks(board, Color.Black);


        if (white_check && black_check) return false;
        if (white_check && turn == Turn.White) return false;
        if (black_check && turn == Turn.Black) return false;
        if (check == Check.White && white_check) return false;
        if (check == Check.Black && black_check) return false;

        return true;
    }
    public static Piece check_promotion(Piece p, int x2, int y2)
    {
        Board board = p.myBoard;
        int x1 = p.myPoint.x;
        int y1 = p.myPoint.y;
        if (p.isPawn() && (x2 == 7 || x2 == 0))
            board.chess[x1][y1].mypiece = new Queen(x1, y1, p.piece_color, p.myBoard);
        return board.chess[x1][y1].mypiece;
    }
    public static Square check_en_passant(Piece p, int x2, int y2)
    {
        if (p.isPawn() == false) return null;
        return ((Pawn) p).check_en_passant(x2, y2);
    }
    public static boolean check_check_squ(Board board, Color c, Point p)
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (board.chess[i][j].ispiece() == false) continue;

                if (board.chess[i][j].mypiece.piece_color == c || board.chess[i][j].mypiece.isKing()) continue;

                List<Point> list1 = board.chess[i][j].mypiece.getvalid();
                for (int k = 0; k < list1.size(); k++)
                {
                    if (list1.get(k).x == p.x && list1.get(k).y == p.y)
                        return true;
                }
            }
        }
        return false;
    }
    public static void check_make_castle_invalid(int x1, int x2, int y1, int y2, Board board)
    {
        Square[][] chess = board.chess;
        if (chess[x1][y1].ispiece() == false) return;
        Piece piece1 = chess[x1][y1].mypiece;
        if (piece1.isKing())
        {
            if (piece1.piece_color == Color.White)
            {
                board.castle_white_Queen = false;
                board.castle_white_king = false;
            }
            else
            {
                board.castle_black_Queen = false;
                board.castle_black_king = false;
            }
        }
        if (piece1.isRook())
        {
            if (piece1.piece_color == Color.White)
            {
                if (x1 == 0 && y1 == 7)
                    board.castle_white_king = false;
                else if (x1 == 0 && y1 == 0)
                    board.castle_white_Queen = false;
            }
            else
            {
                if (x1 == 7 && y1 == 7)
                    board.castle_black_king = false;
                else if (x1 == 7 && y1 == 0)
                    board.castle_black_Queen = false;
            }
        }
        if (chess[x2][y2].ispiece() == true)
        {
            Piece piece2 = chess[x2][y2].mypiece;

            if (piece2.isRook())
            {
                if (piece2.piece_color == Color.White)
                {
                    if (x2 == 0 && y2 == 7)
                        board.castle_white_king = false;
                    else if (x2 == 0 && y2 == 0)
                        board.castle_white_Queen = false;
                }
                else
                {
                    if (x2 == 7 && y2 == 7)
                        board.castle_black_king = false;
                    else if (x2 == 7 && y2 == 0)
                        board.castle_black_Queen = false;
                }
            }
        }
    }
    public static Game_Status check_game_over(Board board)
    {
        List<Move> list = Piece_Moves.get_all_moves(board);
        if (list.isEmpty() == false) return Game_Status.none;
        if (check_check_color(board, Color.White))
            return Game_Status.White;
        if (check_check_color(board, Color.Black))
            return Game_Status.Black;
        return Game_Status.Draw;
    }
    public static boolean check_possible_checks(Board board, Color c)
    {
        Piece piece = null;
        int x = 0, y = 0;

        if (c == Color.White)
        {
            x = board.King_White_Pos.x;
            y = board.King_White_Pos.y;
        }
        else
        {
            x = board.King_Black_Pos.x;
            y = board.King_Black_Pos.y;
        }

        piece = board.chess[x][y].mypiece;

        ArrayList<Point> straight = new ArrayList<>();
        ArrayList<Point> diagonal = new ArrayList<>();
        ArrayList<Point> knight = new ArrayList<>();
        ArrayList<Point> king = new ArrayList<>();


        Piece_Moves.move_diagonal(piece, diagonal);
        Piece_Moves.move_straight(piece, straight);
        Piece_Moves.move_Knight(piece, knight);
        Piece_Moves.move_King(piece, king);


        Square[][] chess = piece.myBoard.chess;

        for (Point p : diagonal)
        {
            if (chess[p.x][p.y].ispiece() == false) continue;
            Piece other = chess[p.x][p.y].mypiece;
            if (other.isQueen() || other.isBishop()) return true;
        }

        for (Point p : straight)
        {
            if (chess[p.x][p.y].ispiece() == false) continue;
            Piece other = chess[p.x][p.y].mypiece;
            if (other.isQueen() || other.isRook()) return true;
        }

        for (Point p : knight)
        {
            if (chess[p.x][p.y].ispiece() == false) continue;
            if (chess[p.x][p.y].mypiece.isKnight()) return true;
        }

        for (Point p : king)
        {
            if (chess[p.x][p.y].ispiece() == false) continue;
            if (chess[p.x][p.y].mypiece.isKing()) return true;
        }

        if (c == Color.White)
        {
            if (Square.isvalid_square(x + 1, y - 1) && chess[x + 1][y - 1].ispiece())
            {
                Piece other = chess[x + 1][y - 1].mypiece;
                if (other.isPawn() && other.piece_color == Color.Black)
                    return true;
            }
            if (Square.isvalid_square(x + 1, y + 1) && chess[x + 1][y + 1].ispiece())
            {
                Piece other = chess[x + 1][y + 1].mypiece;
                if (other.isPawn() && other.piece_color == Color.Black)
                    return true;
            }
        }
        else
        {
            if (Square.isvalid_square(x - 1, y - 1) && chess[x - 1][y - 1].ispiece())
            {
                Piece other = chess[x - 1][y - 1].mypiece;
                if (other.isPawn() && other.piece_color == Color.White)
                    return true;
            }
            if (Square.isvalid_square(x - 1, y + 1) && chess[x - 1][y + 1].ispiece())
            {
                Piece other = chess[x - 1][y + 1].mypiece;
                if (other.isPawn() && other.piece_color == Color.White)
                    return true;
            }
        }

        return false;
    }
}
