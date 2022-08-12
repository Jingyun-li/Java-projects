package graph;

import graph.WeightedGraph;
import maze.Juncture;
import maze.Maze;

/**
 * <P>
 * The MazeGraph is an extension of WeightedGraph. The constructor converts a
 * Maze into a graph.
 * </P>
 */
public class MazeGraph extends WeightedGraph<Juncture> {

	public Maze theMaze;
	/*
	 * STUDENTS: SEE THE PROJECT DESCRIPTION FOR A MUCH MORE DETAILED EXPLANATION
	 * ABOUT HOW TO WRITE THIS CONSTRUCTOR
	 */

	/**
	 * <P>
	 * Construct the MazeGraph using the "maze" contained in the parameter to
	 * specify the vertices (Junctures) and weighted edges.
	 * </P>
	 * 
	 * <P>
	 * The Maze is a rectangular grid of "junctures", each defined by its X and Y
	 * coordinates, using the usual convention of (0, 0) being the upper left
	 * corner.
	 * </P>
	 * 
	 * <P>
	 * Each juncture in the maze should be added as a vertex to this graph.
	 * </P>
	 * 
	 * <P>
	 * For every pair of adjacent junctures (A and B) which are not blocked by a
	 * wall, two edges should be added: One from A to B, and another from B to A.
	 * The weight to be used for these edges is provided by the Maze. (The Maze
	 * methods getMazeWidth and getMazeHeight can be used to determine the number of
	 * Junctures in the maze. The Maze methods called "isWallAbove",
	 * "isWallToRight", etc. can be used to detect whether or not there is a wall
	 * between any two adjacent junctures. The Maze methods called "getWeightAbove",
	 * "getWeightToRight", etc. should be used to obtain the weights.)
	 * </P>
	 * 
	 * @param maze
	 *            to be used as the source of information for adding vertices and
	 *            edges to this MazeGraph.
	 */
	public MazeGraph(Maze maze) {
		theMaze = maze;
		Juncture[][] js = new Juncture[maze.getMazeHeight()][maze.getMazeWidth()];
		for (int y = 0; y < maze.getMazeHeight(); y++) {
			for (int x = 0; x < maze.getMazeWidth(); x++) {
				js[y][x] = new Juncture(x,y);
				addVertex(js[y][x]);
			}
		}
		for (int y = 0; y < maze.getMazeHeight() ; y++) {
			for (int x = 0; x < maze.getMazeWidth() ; x++) {
				if (!maze.isWallToRight(js[y][x])) {
					addEdge(js[y][x], js[y][x + 1], maze.getWeightToRight(js[y][x]));
					addEdge(js[y][x + 1], js[y][x], maze.getWeightToRight(js[y][x]));
				}
				if (!maze.isWallBelow(js[y][x])) {
					addEdge(js[y][x], js[y + 1][x], maze.getWeightBelow(js[y][x]));
					addEdge(js[y + 1][x], js[y][x], maze.getWeightBelow(js[y][x]));
				}
			}
		}
	}
}
