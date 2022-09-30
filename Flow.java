import java.awt.*;
import java.util.*;
import java.util.List;

public class Flow
{
    public static Board myboard;
    public static Stack<Board_Status> prev_moves;
    public static Game_Type myType;
    public static void startgame()
    {
        Flow.prev_moves = new Stack<>();
        //Flow.myType = Game_Type.Two_Player;

        System.out.println("Please Choose one of the following game types");
        System.out.println("1- 2 Players");
        System.out.println("2- White vs Computer");
        System.out.println("3- Computer vs Black");
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();

        if (x == 1)
            Flow.myType = Game_Type.Two_Player;
        else if (x == 2)
            Flow.myType = Game_Type.White_Computer;
        else
            Flow.myType = Game_Type.Black_Computer;

    }
    public static void loadgame()
    {
        String s = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq ";
        Flow.loadgame(s);
        //Flow.myboard = Board.getboard(s);
        //make_move();
    }
    public static void loadgame(String s)
    {
        Flow.myboard = Board.getboard(s);
        while (!Flow.func()) {}

    }
    public static void error()
    {

        System.out.println("Invalid Input");
    }
    public static void welcome()
    {
        myboard.Print();
        if (myboard.myturn  == Turn.White)
            System.out.println("White to move");
        else
            System.out.println("Black to move");
    }
    public static void Game_Over_messege(Game_Status status)
    {
        if (status == Game_Status.White)
            System.out.print("White Won !!!");
        else if (status == Game_Status.Black)
            System.out.print("Black Won !!!");
        else
            System.out.print("Draw !!!");
    }
    public static void Player_Move()
    {
        welcome();

        Scanner sc = new Scanner(System.in);

        int x1, y1, x2, y2;

        if (Test.queue.size() == 0)
        {
            System.out.println("Enter the first Square");
            y1 = sc.nextInt();
            x1 = sc.nextInt();

            x1--;
            y1--;
            if (Square.isvalid_square(x1, y1) == false)
            {
                error(); return;
            }
            if (myboard.chess[x1][y1].ispiece() == false)
            {
                error();
                return;
            }
            if (Board.turn_equal_color(myboard.myturn, myboard.chess[x1][y1].mypiece.piece_color) == false)
            {
                error();
                return;
            }

            Piece piece = myboard.chess[x1][y1].mypiece;
            List<Point> list = piece.getvalid();
            list = Piece_Moves.filter_moves(piece.myPoint.x, piece.myPoint.y, list);

            for (Point p : list)
                myboard.make_highlight(p.x, p.y);

            myboard.Print();

            System.out.println("Please enter the second square");
            y2 = sc.nextInt();
            x2 = sc.nextInt();
            myboard.clear_highlight();
            x2--; y2--;
            if (Square.isvalid_square(x2, y2) == false)
            {
                error();
                return;
            }
        }
        else
        {
            x1 = Test.queue.peek().x1;
            x2 = Test.queue.peek().x2;
            y1 = Test.queue.peek().y1;
            y2 = Test.queue.peek().y2;
            Test.queue.poll();

            x1--; x2--; y1--; y2--;
        }
        if (myboard.chess[x1][y1].ispiece() == false)
        {
            error();
            return;
        }
        Piece piece = myboard.chess[x1][y1].mypiece;
        if (Board.turn_equal_color(myboard.myturn, piece.piece_color) == false)
        {
            error();
            return;
        }
        List<Point> list = piece.getvalid();
        list = Piece_Moves.filter_moves(piece.myPoint.x, piece.myPoint.y, list);
        for (Point p : list)
        {
            if (p.x == x2 && p.y == y2)
            {
                Piece_Moves.make_move_on_Board(x1, y1, x2, y2, myboard);   //make_move();
                return;
            }
        }
        error();
    }
    public static void Comp_Move()
    {
        Pair pair = Ai.Minimax(0);
        Move move = pair.second;
        Piece_Moves.make_move_on_Board(move.x1, move.y1, move.x2, move.y2, Flow.myboard);
    }
    public static boolean func()
    {
        Game_Status status = Checkings.check_game_over(Flow.myboard);

        if (status != Game_Status.none)
        {
            Flow.Game_Over_messege(status);
            return true;
        }
        Turn turn = Flow.myboard.myturn;

        if (Flow.myType == Game_Type.White_Computer)
        {
            if (turn == Turn.White)
                Player_Move();
            else
                Comp_Move();
        }
        else if (Flow.myType == Game_Type.Black_Computer)
        {
            if (turn == Turn.Black)
                Player_Move();
            else
                Comp_Move();
        }
        else
            Player_Move();

        return false;
    }
    public static void alternate(Board board)
    {
        if (board.myturn == Turn.White)
            board.myturn = Turn.Black;
        else
            board.myturn = Turn.White;
    }
    public static void erase_Pieace(Board board, int x, int y, Board_Status status)
    {
        status.list.add(Square.copy_Square(board.chess[x][y]));
        board.chess[x][y].mypiece = null;
    }

}

enum Game_Type
{
    Two_Player, White_Computer, Black_Computer
}


