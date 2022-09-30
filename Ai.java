import java.util.*;


public class Ai
{
    //public static void minimax()

    static int MAX = 100000000;
    static int MIN = -100000000;
    static int Max_Depth = 3;
    //HashMap<String, Integer> capitalCities = new HashMap<String, Integer>();

    static int minimax(int depth, int nodeIndex, Boolean maximizingPlayer, int alpha, int beta, Board board)
    {
        if (depth == 3) return Evaluation.func(board);

        if (maximizingPlayer)
        {
            int best = MIN;
            for (int i = 0; i < 2; i++)
            {
                int val = minimax(depth + 1, nodeIndex * 2 + i, false, alpha, beta, board);
                best = Math.max(best, val);
                alpha = Math.max(alpha, best);
                if (beta <= alpha)
                    break;
            }
            return best;
        }
        else
        {
            int best = MAX;
            for (int i = 0; i < 2; i++)
            {
                int val = minimax(depth + 1, nodeIndex * 2 + i, true, alpha, beta, board);
                best = Math.min(best, val);
                beta = Math.min(beta, best);
                if (beta <= alpha)
                    break;
            }
            return best;
        }
    }

    public static Pair Minimax(int depth)
    {
        Board board = Flow.myboard;
        Move blank = new Move(-1, -1, -1, -1);
        if (depth == 5)
            return new Pair(Evaluation.func(board), blank);

        Game_Status status = Checkings.check_game_over(board);

        if (status == Game_Status.White) return new Pair(MAX, blank);
        else if (status == Game_Status.Black) return new Pair(MIN, blank);
        else if (status == Game_Status.Draw) return new Pair(0, blank);

        List<Move> list = Piece_Moves.get_all_moves(board);
        List<Pair> newlist = new ArrayList<Pair>();

        int maxi = MIN, mini = MAX;

        for (Move move : list)
        {
            Piece_Moves.make_move_on_Board(move.x1, move.y1, move.x2, move.y2, board);
            Pair p = new Pair(Minimax(depth + 1).first, move);
            mini = Math.min(mini, p.first);
            maxi = Math.max(maxi, p.first);
            newlist.add(p);
            Piece_Moves.undo();
        }
        if ((depth % 2 == 0 && board.myturn == Turn.White) || (depth % 2 == 1 && board.myturn == Turn.Black))
        {
            for (Pair p : newlist) if (p.first == maxi) return p;
        }
        else
        {
            for (Pair p : newlist) if (p.first == mini) return p;
        }
        return null;
    }
}

class Pair
{
    Integer first;
    Move second;

    public Pair(Integer f, Move s)
    {
        first = f; second = s;
    }
}














