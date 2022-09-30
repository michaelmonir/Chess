import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Piece_Moves
{
    public static void check_remove_pawn(Piece piece, int x2, int y2, Board_Status status)
    {
        int x1 = piece.myPoint.x;
        int y1 = piece.myPoint.y;

        Board board = piece.myBoard;

        if (piece.isPawn() == false) return;
        if (y1 == y2) return;
        if (board.chess[x2][y2].mypiece != null) return;

        if (x2 == 5)
            Flow.erase_Pieace(board, 4, y2, status);
        else
            Flow.erase_Pieace(board, 3, y2, status);
    }
    public static void move_straight(Piece piece, List<Point> list)
    {
        int x1 = piece.myPoint.x;
        int y1 = piece.myPoint.y;
        Square[][] chess = piece.myBoard.chess;
        for (int i = 1; i <= 8; i++) {
            int x2 = x1 + i, y2 = y1;
            if (!Square.isvalid_square(x2, y2)) break;

            if (chess[x2][y2].ispiece()) {
                if ((chess[x2][y2]).mypiece.piece_color != piece.piece_color)
                    list.add(new Point(x2, y2));
                break;
            }

            list.add(new Point(x2, y2));
        }

        for (int i = 1; i <= 8; i++) {
            int x2 = x1 - i, y2 = y1;
            if (!Square.isvalid_square(x2, y2)) break;

            if (chess[x2][y2].ispiece()) {
                if ((chess[x2][y2]).mypiece.piece_color != piece.piece_color)
                    list.add(new Point(x2, y2));
                break;
            }

            list.add(new Point(x2, y2));
        }

        for (int i = 1; i <= 8; i++) {
            int x2 = x1, y2 = y1 + i;
            if (!Square.isvalid_square(x2, y2)) break;

            if (chess[x2][y2].ispiece()) {
                if ((chess[x2][y2]).mypiece.piece_color != piece.piece_color)
                    list.add(new Point(x2, y2));
                break;
            }

            list.add(new Point(x2, y2));
        }

        for (int i = 1; i <= 8; i++) {
            int x2 = x1, y2 = y1 - i;
            if (!Square.isvalid_square(x2, y2)) break;

            if (chess[x2][y2].ispiece()) {
                if ((chess[x2][y2]).mypiece.piece_color != piece.piece_color)
                    list.add(new Point(x2, y2));
                break;
            }

            list.add(new Point(x2, y2));
        }
    }
    public static void move_Knight(Piece piece, List<Point> list)
    {
        int []dx = new int[]{1, 1, 2, 2, -1, -1, -2, -2};
        int []dy = new int[]{2, -2, 1, -1, 2, -2, 1, -1};

        Square[][] chess = piece.myBoard.chess;

        int x = piece.myPoint.x;
        int y = piece.myPoint.y;

        for (int ii = 0; ii < 8; ii++)
        {
            int i = x + dx[ii];
            int j = y + dy[ii];

            if (!Square.isvalid_square(i, j)) continue;

            list.add(new Point(i, j));

            if (!chess[i][j].ispiece())
                list.add(new Point(i, j));
            else if (( chess[i][j]).mypiece.piece_color != piece.piece_color)
                list.add(new Point(i, j));
        }
    }
    public static void move_King(Piece piece, List<Point> list)
    {
        Square[][] chess = piece.myBoard.chess;
        int x = piece.myPoint.x;
        int y = piece.myPoint.y;
        for (int i = x - 1; i <= x + 1; i++)
        {
            for (int j = y - 1; j <= y + 1; j++)
            {
                if (i == x && j == y) continue;
                if (!Square.isvalid_square(i, j)) continue;
                if (!chess[i][j].ispiece())
                    list.add(new Point(i, j));
                else if (( chess[i][j]).mypiece.piece_color != piece.piece_color)
                    list.add(new Point(i, j));;
            }
        }
    }
    public static void move_diagonal(Piece piece, List<Point> list)
    {
        int x1 = piece.myPoint.x;
        int y1 = piece.myPoint.y;
        Square[][] chess = piece.myBoard.chess;
        for (int i = 1; i <= 8; i++)
        {
            int x2 = x1 + i, y2 = y1 + i;
            if (!Square.isvalid_square(x2, y2)) break;

            if (chess[x2][y2].ispiece())
            {
                if ((chess[x2][y2]).mypiece.piece_color != piece.piece_color)
                    list.add(new Point(x2, y2));
                break;
            }

            list.add(new Point(x2, y2));
        }

        for (int i = 1; i <= 8; i++)
        {
            int x2 = x1 + i, y2 = y1 - i;
            if (!Square.isvalid_square(x2, y2)) break;

            if (chess[x2][y2].ispiece())
            {
                if ((chess[x2][y2]).mypiece.piece_color != piece.piece_color)
                    list.add(new Point(x2, y2));
                break;
            }

            list.add(new Point(x2, y2));
        }

        for (int i = 1; i <= 8; i++) {
            int x2 = x1 - i, y2 = y1 + i;
            if (!Square.isvalid_square(x2, y2)) break;

            if (chess[x2][y2].ispiece())
            {
                if ((chess[x2][y2]).mypiece.piece_color != piece.piece_color)
                    list.add(new Point(x2, y2));
                break;
            }

            list.add(new Point(x2, y2));
        }

        for (int i = 1; i <= 8; i++)
        {
            int x2 = x1 - i, y2 = y1 - i;
            if (!Square.isvalid_square(x2, y2)) break;

            if (chess[x2][y2].ispiece())
            {
                if ((chess[x2][y2]).mypiece.piece_color != piece.piece_color)
                    list.add(new Point(x2, y2));
                break;
            }

            list.add(new Point(x2, y2));
        }
    }
    public static void make_move_on_Board(int x1, int y1, int x2, int y2, Board board)
    {
        Board_Status status = new Board_Status(board.myturn, board.mycheck, board.castle_white_king, board.castle_black_Queen,
                board.castle_black_king, board.castle_black_Queen,
                Square.copy_Square(board.en_passent), board.King_White_Pos, board.King_Black_Pos
                );
        Piece piece = board.chess[x1][y1].mypiece;
        Checkings.check_move_Rook(x1, y1, x2, y2, board, status);

        piece = Checkings.check_promotion(board.chess[x1][y1].mypiece, x2, y2);

        if (piece.isKing())
        {
            if (piece.piece_color == Color.White)
                board.King_White_Pos = new Point(x2, y2);
            else
                board.King_Black_Pos = new Point(x2, y2);
        }

        // Update the en passent move
        board.en_passent = Checkings.check_en_passant(piece, x2, y2);

        Piece_Moves.check_remove_pawn(piece, x2, y2, status);

        Checkings.check_make_castle_invalid(x1, y1, x2, y2, board);
        Piece_Moves.change_place(x1, y1, x2, y2, board, status);
        Flow.alternate(board);

        Flow.prev_moves.add(status);
    }
    static void change_place(int x1, int y1, int x2, int y2, Board board, Board_Status status)
    {
        status.list.add(Square.copy_Square(board.chess[x1][y1]));
        status.list.add(Square.copy_Square(board.chess[x2][y2]));

        Piece piece = board.chess[x1][y1].mypiece;
        piece.move(x2, y2);
        board.chess[x2][y2].mypiece = piece;
        board.chess[x1][y1].mypiece = null;
    }
    public static List<Point> filter_moves(int x, int y, List<Point> list)
    {
        List<Point> newlist = new ArrayList();
        for (Point p : list)
        {
            Board board = Flow.myboard;
            Check check = board.mycheck; Turn turn = board.myturn;
            Piece piece = board.chess[x][y].mypiece;

            Piece_Moves.make_move_on_Board(piece.myPoint.x, piece.myPoint.y, p.x, p.y, board);

            if (Checkings.check_position(board, check, turn)) // 3aaaaaaaaaaaaaaaaaaaaaaaaaaa
                newlist.add(new Point(p.x, p.y));

            Piece_Moves.undo();
        }
        return newlist;
    }
    public static List<Move> get_all_moves(Board board)
    {
        List<Move> list = new ArrayList<Move>();
        Square[][] chess = board.chess;

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (chess[i][j].ispiece() == false) continue;
                if (Board.turn_equal_color(board.myturn, chess[i][j].mypiece.piece_color) == false) continue;

                List<Point> temp = chess[i][j].mypiece.getvalid();
                temp = Piece_Moves.filter_moves(i, j, temp);
                for (Point p : temp)
                    list.add(new Move(i, j, p.x, p.y));
            }
        }
        return list;
    }
    public static void undo()
    {
        Board_Status status = Flow.prev_moves.pop();

        Board board = Flow.myboard;

        board.myturn = status.myturn;
        board.mycheck = status.mycheck;
        board.castle_white_king = status.castle_white_king;
        board.castle_white_Queen = status.castle_white_Queen;
        board.castle_black_king = status.castle_black_king;
        board.castle_black_Queen = status.castle_black_Queen;
        board.en_passent = Square.copy_Square(status.en_passent);
        board.King_White_Pos = status.King_White_Pos;
        board.King_Black_Pos = status.King_Black_Pos;
        for (Square square : status.list)
            board.chess[square.x][square.y] = Square.copy_Square(square);
    }
}