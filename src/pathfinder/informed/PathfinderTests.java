package pathfinder.informed;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit tests for Maze Pathfinder. Tests include completeness and
 * optimality.
 */
public class PathfinderTests {

    @Test
    public void testPathfinder_t0() {
        String[] maze = {
                "XXXXXXX",
                "XI...KX",
                "X.....X",
                "X.X.XGX",
                "XXXXXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        // result will be a 2-tuple (isSolution, cost) where
        // - isSolution = 0 if it is not, 1 if it is
        // - cost = numerical cost of proposed solution
        int[] result = prob.testSolution(solution);
        assertEquals(1, result[0]); // Test that result is a solution
        assertEquals(6, result[1]); // Ensure that the solution is optimal
    }

    @Test
    public void testPathfinder_t1() {
        String[] maze = {
                "XXXXXXX",
                "XI....X",
                "X.MMM.X",
                "X.XKXGX",
                "XXXXXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        int[] result = prob.testSolution(solution);
        assertEquals(1, result[0]);  // Test that result is a solution
        assertEquals(14, result[1]); // Ensure that the solution is optimal
    }

    @Test
    public void testPathfinder_t2() {
        String[] maze = {
                "XXXXXXX",
                "XI.G..X",
                "X.MMMGX",
                "X.XKX.X",
                "XXXXXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        int[] result = prob.testSolution(solution);
        assertEquals(1, result[0]);  // Test that result is a solution
        assertEquals(10, result[1]); // Ensure that the solution is optimal
    }

    @Test
    public void testPathfinder_t3() {
        String[] maze = {
                "XXXXXXX",
                "XI.G..X",
                "X.MXMGX",
                "X.XKX.X",
                "XXXXXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        assertNull(solution); // Ensure that Pathfinder knows when there's no solution
    }

    @Test
    public void testPathfinder_t4() {
        String[] maze = {
                "XXXXXXX",
                "X..I..X",
                "X..M..X",
                "X.MMMGX",
                "X..KX.X",
                "XXXXXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        int[] result = prob.testSolution(solution);
        assertEquals(1, result[0]);  // Test that result is a solution
        assertEquals(14, result[1]); // Ensure that the solution is optimal
    }

    @Test
    public void testPathfinder_t5() {
        String[] maze = {
                "XXXXXXXX",
                "X...IM.X",
                "X...X..X",
                "X...X..X",
                "XX.MXMGX",
                "X.....KX",
                "XXXXXXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        int[] result = prob.testSolution(solution);
        assertEquals(1, result[0]);  // Test that result is a solution
        assertEquals(9, result[1]); // Ensure that the solution is optimal
    }


    @Test
    public void testPathfinder_t6() {
        String[] maze = {
                "XXXXXXXX",
                "X....GIX",
                "X.XXXXXX",
                "X......X",
                "XXXXXX.X",
                "XK.....X",
                "XXXXXXXX"
        };
        MazeProblem prob = new MazeProblem(maze);
        ArrayList<String> solution = Pathfinder.solve(prob);

        int[] result = prob.testSolution(solution);
        assertEquals(1, result[0]);  // Test that result is a solution
        assertEquals(37, result[1]); // Ensure that the solution is optimal
    }

}