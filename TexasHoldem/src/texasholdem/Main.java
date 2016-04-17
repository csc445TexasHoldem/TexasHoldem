package texasholdem;

import java.awt.Point;
import java.io.IOException;
import java.util.Arrays;

/**
 * Main class
 */
public class Main {

    public static void main(String[] args) {
        Point p1 = new Point(1, 5);
        try {
            byte[] b = SharedUtilities.toByteArray(p1);
            Point p2 = (Point)SharedUtilities.toObject(b);
            System.out.println(p1);
            System.out.println(p2);
            System.out.println(Arrays.toString(b));
            System.out.println(p1.equals(p2));
        }
        catch(IOException | ClassNotFoundException ioecnfe) {
            ioecnfe.printStackTrace();
        }
    }
}