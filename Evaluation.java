import java.awt.*;
import java.util.List;


public class Evaluation
{
    public static int[][] King_table =
    {
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-20, -30, -30, -40, -40, -30, -30, -20},
            {-10, -20, -20, -20, -20, -20, -20, -10},
            {20,  20,   0,   0,   0,   0,  20,  20},
            {20,  30,  10,   0,   0,  10,  30,  20}
    };
    public static int[][] King_Endgame_table =
    {
            {-50,-40,-30,-20,-20,-30,-40,-50},
            {-30,-20,-10,  0,  0,-10,-20,-30},
            {-30,-10, 20, 30, 30, 20,-10,-30},
            {-30,-10, 30, 40, 40, 30,-10,-30},
            {-30,-10, 30, 40, 40, 30,-10,-30},
            {-30,-10, 20, 30, 30, 20,-10,-30},
            {-30,-30,  0,  0,  0,  0,-30,-30},
            {-50,-30,-30,-30,-30,-30,-30,-50}
    };
    public static int[][] Pawn_table =
    {
            {0,  0,  0,  0,  0,  0,  0,  0},
            {50, 50, 50, 50, 50, 50, 50, 50},
            {10, 10, 20, 30, 30, 20, 10, 10},
            {5,  5, 10, 27, 27, 10,  5,  5},
            {0,  0,  0, 25, 25,  0,  0,  0},
            {5, -5,-10,  0,  0,-10, -5,  5},
            {5, 10, 10,-25,-25, 10, 10,  5},
            {0,  0,  0,  0,  0,  0,  0,  0}
    };
    public static int[][] Knight_table =
    {
            {-50, -40, -30, -30, -30, -30, -40, -50},
            {-40, -20, 0, 0, 0, 0, -20, -40},
            {-30, 0, 10, 15, 15, 10, 0, -30},
            {-30, 5, 15, 20, 20, 15, 5, -30},
            {-30, 0, 15, 20, 20, 15, 0, -30},
            {-30, 5, 10, 15, 15, 10, 5, -30},
            {-40, -20, 0, 5, 5, 0, -20, -40},
            {-50, -40, -20, -30, -30, -20, -40, -50}
    };
    public static int[][] Bishop_table =
    {
            {-20, -10, -10, -10, -10, -10, -10, -20},
            {-10, 0, 0, 0, 0, 0, 0, -10},
            {-10, 0, 5, 10, 10, 5, 0, -10},
            {-10, 5, 5, 10, 10, 5, 5, -10},
            {-10, 0, 10, 10, 10, 10, 0, -10},
            {-10, 10, 10, 10, 10, 10, 10, -10},
            {-10, 5, 0, 0, 0, 0, 5, -10},
            {-20, -10, -40, -10, -10, -40, -10, -20}
    };
    public static int[][] Rook_table =
    {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {5, 10, 10, 10, 10, 10, 10, 5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {0, 0, 0, 5, 5, 0, 0, 0}
    };

    public static int[][] Queen_table =
    {
            {-20,-10,-10, -5, -5,-10,-10,-20},
            {-10,  0,  0,  0,  0,  0,  0,-10},
            {-10,  0,  5,  5,  5,  5,  0,-10},
            {-5,  0,  5,  5,  5,  5,  0, -5},
            {0,  0,  5,  5,  5,  5,  0, -5},
            {-10,  5,  5,  5,  5,  5,  0,-10},
            {-10,  0,  5,  0,  0,  0,  0,-10},
            {-20,-10,-10, -5, -5,-10,-10,-20}
    };


    public static int pieceval(Board board)
    {
        Square [][] chess = board.chess;

        int white = 0;
        int black = 0;

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (chess[i][j].ispiece() == false) continue;

                int val = 0;

                Piece piece = chess[i][j].mypiece;

                if (piece.isKing()) continue;
                if (piece.isPawn()) val = 1;
                else if (piece.isKnight()) val = 3;
                else if (piece.isBishop()) val = 3;
                else if (piece.isRook()) val = 5;
                else if (piece.isQueen()) val = 9;

                if (piece.piece_color == Color.White) white += val;
                else black += val;
            }
        }

        return (white - black);
    }
    public static int func2(Board board)
    {
        Square[][] chess = board.chess;
        boolean [][] part_white = new boolean[8][8];
        boolean [][] part_black = new boolean[8][8];

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (chess[i][j].ispiece() == false) continue;

                Piece piece = chess[i][j].mypiece;

                List<Point> list = piece.getvalid();

                for (Point p : list)
                {
                    if (piece.piece_color == Color.White) part_white[p.x][p.y] = true;
                    else part_black[p.x][p.y] = true;
                }
            }
        }
        int white = 0, black = 0;

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (part_white[i][j]) white++;
                if (part_black[i][j]) black++;
            }
        }

        int pev = 0;

        if (white >= black)
            pev = white/black - 1;
        else
            pev = 1 - black/white;

        pev *= 20;
        return pev;
    }
    public static int two_bishops(Board board)
    {
        int white = 0, black = 0;

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (board.chess[i][j].ispiece() == false || board.chess[i][j].mypiece.isBishop() == false) continue;
                if (board.chess[i][j].mypiece.piece_color == Color.White) white++;
                else black++;
            }
        }
        if (white != 2 && black == 2)
            return -1;
        else if (white == 2 && black != 2)
            return 1;
        return 0;
    }
    public static boolean is_endgame(Board board)
    {
        int minors = 0, majors = 0, queens = 0;

        for (int i = 0; i < 8; i++)
        {
            for (int j= 0; j < 8; j++)
            {
                if (board.chess[i][j].ispiece() == false) continue;
                Piece piece = board.chess[i][j].mypiece;
                if (piece.isRook()) majors++;
                else if (piece.isQueen()) queens++;
                else if (piece.isBishop() || piece.isKnight()) minors++;
            }
        }
        if (queens == 0) return true;
        else if (majors != 0) return false;
        else if (minors <= 1) return true;
        else return false;
    }

    public static int table_piece(Board board)
    {
        boolean isengame = Evaluation.is_endgame(board);

        int white = 0, black = 0;
        Square[][] chess = board.chess;

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (chess[i][j].ispiece() == false) continue;
                int x = i, y = j;
                Piece piece = chess[i][j].mypiece;
                if (piece.piece_color == Color.Black) y = 8 - i - 1;

                int current = 0;

                if (piece.isKnight())
                    current = Knight_table[x][y];
                else if (piece.isBishop())
                    current = Bishop_table[x][y];
                else if (piece.isQueen())
                    current = Queen_table[x][y];
                else if (piece.isPawn())
                    current = Pawn_table[x][y];
                else if (piece.isRook())
                    current = Rook_table[x][y];
                else if (piece.isKing() && isengame)
                    current = King_Endgame_table[x][y];
                else
                    current = King_table[x][y];
                if (piece.piece_color == Color.White) white += current;
                else black += current;
            }
        }
        return white - black;
    }

    public static int func(Board board)
    {
        if (board.myturn == Turn.White) return table_piece(board);
        else return -table_piece(board);
    }
}
