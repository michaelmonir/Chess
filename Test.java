import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class Test
{
    public static Queue<Move> queue = new LinkedList<>();
    public static void load_game_from_file(File myObj)
    {
        try {
            Scanner sc = new Scanner(myObj);

            String initial = sc.nextLine();


            while (sc.hasNextLine())
            {
                int x1, y1, x2, y2;

                y1 = sc.nextInt();
                x1 = sc.nextInt();
                y2 = sc.nextInt();
                x2 = sc.nextInt();
                Test.queue.add(new Move(x1, y1, x2, y2));
            }
            sc.close();
            Flow.loadgame(initial);
        } catch (FileNotFoundException e) {}
    }

    public static void test1()
    {
        File myObj = new File("D:\\m\\test1.txt");
        load_game_from_file(myObj);
    }
    public static void test2()
    {
        File myObj = new File("D:\\m\\test2.txt");
        load_game_from_file(myObj);
    }
    public static void test3()
    {
        File myObj = new File("D:\\m\\test3.txt");
        load_game_from_file(myObj);
    }

}

class Move
{
    int x1; int y1; int x2; int y2;

    public Move(int a, int b, int c, int d)
    {
        x1 = a; y1 = b; x2 = c; y2 = d;
    }
    public Move(Point p1, Point p2)
    {
        int a = p1.x, b = p1.y, c = p2.x, d = p2.y;
        x1 = a; y1 = b; x2 = c; y2 = d;
    }
}


