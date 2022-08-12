package graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * <P>
 * This class represents a general "directed graph", which could be used for any
 * purpose. The graph is viewed as a collection of vertices, which are sometimes
 * connected by weighted, directed edges.
 * </P>
 * 
 * <P>
 * This graph never store duplicate vertices.
 * </P>
 * 
 * <P>
 * The weights always be non-negative integers.
 * </P>
 * 
 * <P>
 * The WeightedGraph is capable of performing three algorithms:
 * Depth-First-Search, Breadth-First-Search, and Djikatra's.
 * </P>
 * 
 * <P>
 * The WeightedGraph maintains a collection of "GraphAlgorithmObservers", which
 * are notified during the performance of the graph algorithms to update the
 * observers on how the algorithms are progressing.
 * </P>
 */
public class WeightedGraph<V> {

	/*
	 * Use map to implement a weighted, directed graph.
	 */
	private Map<V, Map<V, Integer>> myGraph;

	/*
	 * Collection of observers.
	 */
	private Collection<GraphAlgorithmObserver<V>> observerList;

	/**
	 * Initialize the data structures to "empty", including the collection of
	 * GraphAlgorithmObservers (observerList).
	 */
	public WeightedGraph() {
		myGraph = new HashMap<V, Map<V, Integer>>();
		observerList = new HashSet<GraphAlgorithmObserver<V>>();
	}

	/**
	 * Add a GraphAlgorithmObserver to the collection maintained by this graph
	 * (observerList).
	 * 
	 * @param observer
	 */
	public void addObserver(GraphAlgorithmObserver<V> observer) {
		observerList.add(observer);
	}

	/**
	 * Add a vertex to the graph. If the vertex is already in the graph, throw an
	 * IllegalArgumentException.
	 * 
	 * @param vertex
	 *            vertex to be added to the graph
	 * @throws IllegalArgumentException
	 *             if the vertex is already in the graph
	 */
	public void addVertex(V vertex) {
		if (myGraph.containsKey(vertex)) {
			throw new IllegalArgumentException();
		}
		Map<V, Integer> nullMap = new HashMap<V, Integer>();
		myGraph.put(vertex, nullMap);
	}

	/**
	 * Searches for a given vertex.
	 * 
	 * @param vertex
	 *            the vertex we are looking for
	 * @return true if the vertex is in the graph, false otherwise.
	 */
	public boolean containsVertex(V vertex) {
		return myGraph.containsKey(vertex);
	}

	/**
	 * <P>
	 * Add an edge from one vertex of the graph to another, with the weight
	 * specified.
	 * </P>
	 * 
	 * <P>
	 * The two vertices must already be present in the graph.
	 * </P>
	 * 
	 * <P>
	 * This method throws an IllegalArgumentException in three cases:
	 * </P>
	 * <P>
	 * 1. The "from" vertex is not already in the graph.
	 * </P>
	 * <P>
	 * 2. The "to" vertex is not already in the graph.
	 * </P>
	 * <P>
	 * 3. The weight is less than 0.
	 * </P>
	 * 
	 * @param from
	 *            the vertex the edge leads from
	 * @param to
	 *            the vertex the edge leads to
	 * @param weight
	 *            the (non-negative) weight of this edge
	 * @throws IllegalArgumentException
	 *             when either vertex is not in the graph, or the weight is
	 *             negative.
	 */
	public void addEdge(V from, V to, Integer weight) {
		if (!myGraph.containsKey(from) || !myGraph.containsKey(to) || weight < 0) {
			throw new IllegalArgumentException();
		}
		Map<V, Integer> fromMap = myGraph.get(from);
		fromMap.put(to, weight);
	}

	/**
	 * <P>
	 * Returns weight of the edge connecting one vertex to another. Returns null if
	 * the edge does not exist.
	 * </P>
	 * 
	 * <P>
	 * Throws an IllegalArgumentException if either of the vertices specified are
	 * not in the graph.
	 * </P>
	 * 
	 * @param from
	 *            vertex where edge begins
	 * @param to
	 *            vertex where edge terminates
	 * @return weight of the edge, or null if there is no edge connecting these
	 *         vertices
	 * @throws IllegalArgumentException
	 *             if either of the vertices specified are not in the graph.
	 */
	public Integer getWeight(V from, V to) {
		if (!myGraph.containsKey(from) || !myGraph.containsKey(to)) {
			throw new IllegalArgumentException();
		}
		Map<V, Integer> fromMap = myGraph.get(from);
		return fromMap.get(to);
	}

	/**
	 * <P>
	 * This method performs a Breadth-First-Search on the graph. The search begins
	 * at the "start" vertex and conclude once the "end" vertex has been reached.
	 * </P>
	 * 
	 * <P>
	 * Before the search begins, this method goes through the collection of
	 * Observers, calling notifyBFSHasBegun on each one.
	 * </P>
	 * 
	 * <P>
	 * Just after a particular vertex is visited, this method goes through the
	 * collection of observers calling notifyVisit on each one (passing in the
	 * vertex being visited as the argument.)
	 * </P>
	 * 
	 * <P>
	 * After the "end" vertex has been visited, this method goes through the
	 * collection of observers calling notifySearchIsOver on each one, after which
	 * the method should terminate immediately, without processing further vertices.
	 * </P>
	 * 
	 * @param start
	 *            vertex where search begins
	 * @param end
	 *            the algorithm terminates just after this vertex is visited
	 */
	public void DoBFS(V start, V end) {
		for (GraphAlgorithmObserver<V> o : observerList) {
			o.notifyBFSHasBegun();
		}
		Set<V> visitedSet = new HashSet<V>();
		Queue<V> queue = new ArrayDeque<V>();
		queue.add(start);
		while (!queue.isEmpty()) {
			V curVertex = queue.poll();
			if (!visitedSet.contains(curVertex)) {
				Set<V> curSet = myGraph.get(curVertex).keySet();
				visitedSet.add(curVertex);
				for (GraphAlgorithmObserver<V> o : observerList) {
					o.notifyVisit(curVertex);
				}
				for (V key : curSet) {
					if (!visitedSet.contains(key)) {
						queue.add(key);
					}
				}
			}
			if (curVertex.equals(end)) {
				for (GraphAlgorithmObserver<V> o : observerList) {
					o.notifySearchIsOver();
				}
				return;
			}
		}
	}

	/**
	 * <P>
	 * This method performs a Depth-First-Search on the graph. The search begins at
	 * the "start" vertex and conclude once the "end" vertex has been reached.
	 * </P>
	 * 
	 * <P>
	 * Before the search begins, this method goes through the collection of
	 * Observers, calling notifyDFSHasBegun on each one.
	 * </P>
	 * 
	 * <P>
	 * Just after a particular vertex is visited, this method goes through the
	 * collection of observers calling notifyVisit on each one (passing in the
	 * vertex being visited as the argument.)
	 * </P>
	 * 
	 * <P>
	 * After the "end" vertex has been visited, this method goes through the
	 * collection of observers calling notifySearchIsOver on each one, after which
	 * the method should terminate immediately, without visiting further vertices.
	 * </P>
	 * 
	 * @param start
	 *            vertex where search begins
	 * @param end
	 *            the algorithm terminates just after this vertex is visited
	 */
	public void DoDFS(V start, V end) {
		for (GraphAlgorithmObserver<V> o : observerList) {
			o.notifyDFSHasBegun();
		}
		Set<V> visitedSet = new HashSet<V>();
		Stack<V> stack = new Stack<V>();
		stack.add(start);
		while (!stack.isEmpty()) {
			V curVertex = stack.pop();
			if (!visitedSet.contains(curVertex)) {
				Set<V> curSet = myGraph.get(curVertex).keySet();
				visitedSet.add(curVertex);
				for (GraphAlgorithmObserver<V> o : observerList) {
					o.notifyVisit(curVertex);
				}
				for (V key : curSet) {
					if (!visitedSet.contains(key)) {
						stack.add(key);
					}
				}
			}
			if (curVertex.equals(end)) {
				for (GraphAlgorithmObserver<V> o : observerList) {
					o.notifySearchIsOver();
				}
				return;
			}
		}
	}

	/**
	 * <P>
	 * Perform Dijkstra's algorithm, beginning at the "start" vertex.
	 * </P>
	 * 
	 * <P>
	 * The algorithm DOES NOT terminate when the "end" vertex is reached. It will
	 * continue until EVERY vertex in the graph has been added to the finished set.
	 * </P>
	 * 
	 * <P>
	 * Before the algorithm begins, this method goes through the collection of
	 * Observers, calling notifyDijkstraHasBegun on each Observer.
	 * </P>
	 * 
	 * <P>
	 * Each time a vertex is added to the "finished set", this method goes through
	 * the collection of Observers, calling notifyDijkstraVertexFinished on each one
	 * (passing the vertex that was just added to the finished set as the first
	 * argument, and the optimal "cost" of the path leading to that vertex as the
	 * second argument.)
	 * </P>
	 * 
	 * <P>
	 * After all of the vertices have been added to the finished set, the algorithm
	 * will calculate the "least cost" path of vertices leading from the starting
	 * vertex to the ending vertex. Next, it will go through the collection of
	 * observers, calling notifyDijkstraIsOver on each one, passing in as the
	 * argument the "lowest cost" sequence of vertices that leads from start to end
	 * (I.e. the first vertex in the list will be the "start" vertex, and the last
	 * vertex in the list will be the "end" vertex.)
	 * </P>
	 * 
	 * @param start
	 *            vertex where algorithm will start
	 * @param end
	 *            special vertex used as the end of the path reported to observers
	 *            via the notifyDijkstraIsOver method.
	 */

	public void DoDijsktra(V start, V end) {
		for (GraphAlgorithmObserver<V> o : observerList) {
			o.notifyDijkstraHasBegun();
		}
		BFT(start, new GraphAlgorithmObserver<V>() {

			@Override
			public void notifyDFSHasBegun() {

			}

			@Override
			public void notifyBFSHasBegun() {

			}

			@Override
			public void notifyVisit(V vertexBeingVisited) {
				table.put(vertexBeingVisited, new Info<V>(vertexBeingVisited));
			}

			@Override
			public void notifySearchIsOver() {

			}

			@Override
			public void notifyDijkstraHasBegun() {

			}

			@Override
			public void notifyDijkstraVertexFinished(V vertexAddedToFinishedSet, Integer costOfPath) {

			}

			@Override
			public void notifyDijkstraIsOver(List<V> path) {

			}
		});

		Info<V> info = table.get(start);
		info.distance = 0;
		Map<V, Info<V>> finishedMap = new HashMap<V, Info<V>>();
		LinkedList<Info<V>> lst = new LinkedList<Info<V>>();
		lst.add(info);
		do {
			info = lst.removeFirst();
			finishedMap.put(info.current, info);
			table.remove(info.current);
			for (GraphAlgorithmObserver<V> o : observerList) {
				o.notifyDijkstraVertexFinished(info.current, info.distance);
			}

			Map<V, Integer> vertex = myGraph.get(info.current);
			for (V v : vertex.keySet()) {
				Info<V> curr = table.get(v);
				if (curr != null) {
					int preDistance = info.distance;
					int curDistance = curr.distance;
					if (preDistance + vertex.get(v) < curDistance) {
						curDistance = preDistance + vertex.get(v);
						curr.predecessor = info.current;
						lst.remove(curr);
						ListInsert(lst, curr);
					}
				}
			}
		} while (lst.size() > 0);

		List<V> path = new ArrayList<V>();
		V v = end;
		do {
			path.add(0, v);
			v = finishedMap.get(v).predecessor;
		} while (!v.equals(start));
		for (GraphAlgorithmObserver<V> o : observerList) {
			o.notifyDijkstraIsOver(path);
		}
	}

	private Map<V, Info<V>> table = new HashMap<V, Info<V>>();

	private class Info<V> {
		V current = null;
		V predecessor = null;
		int distance = Integer.MAX_VALUE;

		public Info(V v) {
			current = v;
		}
	}

	/**
	 * A helper method that traverse through the whole map.
	 * 
	 * @param start
	 * @param ob
	 */
	private void BFT(V start, GraphAlgorithmObserver<V> ob) {
		Set<V> visitedSet = new HashSet<V>();
		Queue<V> queue = new ArrayDeque<V>();
		queue.add(start);
		while (!queue.isEmpty()) {
			V curVertex = queue.poll();
			if (!visitedSet.contains(curVertex)) {
				visitedSet.add(curVertex);
				ob.notifyVisit(curVertex);
			}
			Set<V> curSet = myGraph.get(curVertex).keySet();
			for (V key : curSet) {
				if (!visitedSet.contains(key)) {
					queue.add(key);
				}
			}
		}
	}

	/**
	 * Insert the Info to the Linked List according to the distance in an increasing
	 * order.
	 * 
	 * @param lst
	 * @param value
	 */
	private void ListInsert(LinkedList<Info<V>> lst, Info<V> value) {
		ListIterator<Info<V>> li = lst.listIterator();
		while (li.hasNext()) {
			Info<V> cur = li.next();
			if (value.distance <= cur.distance) {
				li.previous();
				li.add(value);
				return;
			}
		}

		lst.add(value);
	}

}