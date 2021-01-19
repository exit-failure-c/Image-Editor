import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class MatrixImage implements Image {
    public int[][] field;
    
    /**
     * A Constructor for the MatrixImage class
     *
     * @param sx the size on the x-axis
     * @param sy the size on the y-axis
     */
    public MatrixImage(int sx, int sy) {
        field = new int[sx][sy];
    }

    /**
     * A deep dopy constructor for a given MatrixImage that
     *
     * @param that the to be copied MatrixImage
     */
    public MatrixImage(MatrixImage that) {
        this(that.sizeX(), that.sizeY());
        for (int x = 0; x < sizeX(); x++) {
            field[x] = that.field[x].clone();
        }
    }

    /**
     * Initializes a MatrixImage from a given file
     *
     * @param filename the file
     * @throws java.io.FileNotFoundException if there is no such file
     */
    public MatrixImage(String filename) throws java.io.FileNotFoundException {
        System.setIn(new FileInputStream(filename));
        Scanner in = new Scanner(System.in);
        int sx = in.nextInt();
        int sy = in.nextInt();
        field = new int[sx][sy];
        for (int y = 0; y < sy; y++) {
            for (int x = 0; x < sx; x++) {
                field[x][y] = in.nextInt();
            }
        }
    }

    /**
     * @return the size on the x-axis
     */
    @Override
    public int sizeX() {
        return field.length;
    }

    /**
     * @return the size on the y-axis
     */
    @Override
    public int sizeY() {
        return field[0].length;
    }

    /**
     * Calculates the contrast between two coordinates/nodes
     *
     * @param p0 first coordinate
     * @param p1 second coordinate
     * @return the absolute value of the contrast
     * @throws InputMismatchException if the coordinates are not in the image
     */
    @Override
    public double contrast(Coordinate p0, Coordinate p1) throws InputMismatchException {
        if (p0.x > sizeX() || p0.x <0 ||
            p1.x > sizeX() || p0.x <0) throw new InputMismatchException("the Coordinate is not in image");
        if (p0.y > sizeY() || p0.x <0 ||
            p1.y > sizeY() || p0.x <0) throw new InputMismatchException("the Coordinate is not in image");

        double one = field[p0.x][p0.y] ;
        double two = field[p1.x][p1.y];
        double contrast = Math.abs(one - two);
        return contrast;
    }

    /**
     * Removes the given vertical path from the image.
     * Create a deep copy of the image with the correct new Matrix size.
     *
     * @param path the do be deleted vertical path
     */
    @Override
    public void removeVPath(int[] path) {
        // TODO
//        for (int k=0;k<path.length; k++)
//            System.out.print(path[k]+" ");
//
//        System.out.println("");
//        System.out.println("");
        int x = sizeX()-1;
        int y = sizeY();
        int[][] my = new int[x][y];
        for (int zeile=0; zeile<sizeY(); zeile++){
            int to_del = path[zeile]%sizeX();
            boolean find = false;
            for(int spalte=0; spalte<sizeX(); spalte++){
                if(spalte == to_del)
                    find = true;
                if(spalte != to_del && find == true)
                    my[spalte-1][zeile] = field[spalte][zeile];
                if (spalte != to_del && find == false ){
                    my[spalte][zeile] = field[spalte][zeile];
                }
            }
        }
        field = my;
    }

    @Override
    public String toString() {
        String str = "";
        for (int y = 0; y < sizeY(); y++) {
            for (int x = 0; x < sizeX(); x++) {
                str += field[x][y] + " ";
            }
            str += "\n";
        }
        return str;
    }

    @Override
    public void render() {
        System.out.println(toString());
    }

    public static void main(String[] args) {
        MatrixImage m = new MatrixImage(8, 5);
//        m.render();
        int[] path = new int[]{3, 10, 17, 26, 35};
        m.field = new int[][]{  {4, 8, 4, 8, 4},
                                {4, 8, 1, 8, 4},
                                {4, 1, 4, 1, 4},
                                {1, 8, 4, 8, 1},
                                {1, 8, 4, 8, 1},
                                {4, 1, 4, 1, 4},
                                {4, 8, 1, 8, 4},
                                {4, 8, 4, 8, 4}};
        m.render();
        m.removeVPath(path);
        m.render();
//        Coordinate p0 = new Coordinate(0,0);
//        Coordinate p1 = new Coordinate(0,1);
//        System.out.println(m.contrast(p0,p1));
    }
}

