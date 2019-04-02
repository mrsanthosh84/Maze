import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Created by smanicka on 2/4/19.
 */

public class MazeSolver {
    private String route = "";
    private static final char START = 'S';
    private static final char END = 'F';
    private int last_move_direction = 2;
    private int[] directions = new int[]{4,1,2,3,4,1,2,3,4};
    private boolean foundWayOut = false;
    private Map<Integer, Character> direction_letters = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        MazeSolver mazeSolver = new MazeSolver();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the file name");
        String fileName = scanner.next();
        char[][] maze = mazeSolver.readMaze(fileName);
        int count = 1;
        mazeSolver.printMaze(maze, count);
        mazeSolver.getOut(maze, count);
    }

    /**
     * Read a text field from the given directory
     *
     * @param filename
     * @return char[][]  - 2D array of maze
     * @throws FileNotFoundException
     */
    private char[][] readMaze(String filename) throws FileNotFoundException{
        Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)));
        List<String> input = new ArrayList<String>();
        while (scanner.hasNext()) {
            input.add(scanner.nextLine());
        }
        scanner.close();

        int width = input.get(0).length();
        int height = input.size();
        char[][] maze = new char[height][width];

        for (int j=0; j<height; j++) {
            String line = input.get(j);
            for (int i=0; i < width; i++) {
                maze[j][i] = line.charAt(i);
            }
        }

        return maze;
    }

    /**
     * Print the each movement of maze
     * @param maze
     * @param count
     */
    private void printMaze(char[][] maze, int count){
        System.out.println("-------------------Movement-"+count+"--------------");
        for (int i=0; i < maze.length; i++){
            for (int j=0; j < maze[0].length; j++) {
                System.out.print((char)maze[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    /**
     * Getting output of maze of each movements
     * @param maze
     * @param count
     */
    private void getOut(char[][] maze, int count){
        Coordinate coord = findStart(maze);
        while (!foundWayOut){
            count++;
            coord = makeMove(maze, coord);
            printMaze(maze, count);
        }
    }

    /**
     * Each movement iterations
     *
     * @param coord
     * @param direction
     * @return
     */
    private Coordinate move(Coordinate coord, int direction){
        Coordinate new_coord = new Coordinate(coord.i, coord.j);
        switch (direction){
            case 1:
                new_coord.j++;
                break;
            case 2:
                new_coord.i--;
                break;
            case 3:
                new_coord.j--;
                break;
            case 4:
                new_coord.i++;
                break;
        }
        return new_coord;
    }

    /**
     * Checking the good start position
     *
     * @param maze
     * @param coord
     * @return
     */
    private boolean checkIfGoodState(char[][] maze, Coordinate coord){
        if (maze[coord.i][coord.j] == END) {
            foundWayOut = true;
        }
        return (maze[coord.i][coord.j] == ' ') ? true : false;
    }

    /**
     * Make a movement of each position
     * @param maze
     * @param coord
     * @return
     */
    private Coordinate makeMove(char[][] maze, Coordinate coord){
        //try to do moves according to priority and break
        Coordinate new_coord = null;
        for (int i = last_move_direction - 1; i <= last_move_direction + 3; i++){
            new_coord = move(coord, directions[i]);
            if (checkIfGoodState(maze, new_coord)) {
                last_move_direction = directions[i];
                route += direction_letters.get(directions[i]);
                maze[new_coord.i][new_coord.j] = START;
                maze[coord.i][coord.j] = ' ';
                return new_coord;
            }
        }
        return null; //shouldnt happen by assumption
    }

    /**
     * Finding the starting position
     *
     * @param maze
     * @return
     */
    private Coordinate findStart(char[][] maze){
        Coordinate coord = new Coordinate(0, 0);
        for (int i = 0; i < maze.length; i++){
            for (int j = 0; j < maze[1].length; j ++){
                if (maze[i][j] == START){
                    coord.i = i;
                    coord.j = j;
                    break;
                }
            }
        }
        return coord;
    }

    class Coordinate {
        public int i;
        public int j;
        public Coordinate(int i, int j){
            this.i = i;
            this.j = j;
        }
    }
}
