package graphs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * In Dijkstra's the shortest node in terms of distance from a node is always finalized as their can't be a better distance to it.
 */
class Graph {
    // we keep a distance array and store max value there, we keep updating it for all the parents as long as
    // new distance to that node is less than current distance.
    int dist[];
    Set<Integer> visited;
    PriorityQueue<Node> pqueue;
    int V; // Number of vertices
    List<List<Node>> adj_list;

    //class constructor
    public Graph(int V) {
        this.V = V;
        dist = new int[V];
        visited = new HashSet<>();
        pqueue = new PriorityQueue<>(V, new Node());
    }

    // Dijkstra's Algorithm implementation
    public void algo_dijkstra(List<List<Node>> adj_list, int src_vertex) {
        this.adj_list = adj_list;

        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
        }

        // first add source vertex to PriorityQueue
        pqueue.add(new Node(src_vertex, 0));

        // Distance to the source from itself is 0
        dist[src_vertex] = 0;
        while (visited.size() != V) {

            // u is removed from PriorityQueue and has min distance
            int u = pqueue.remove().node;

            // add node to finalized list (visited)
            visited.add(u);
            graph_adjacentNodes(u);
        }
    }

    // this methods processes all neighbours of the just visited node
    private void graph_adjacentNodes(int u) {
        int edgeDistance = -1;
        int newDistance = -1;

        // process all neighbouring nodes of u
        for (int i = 0; i < adj_list.get(u).size(); i++) {
            Node v = adj_list.get(u).get(i);

            //  proceed only if current node is not in 'visited'
            if (!visited.contains(v.node)) {
                edgeDistance = v.cost;
                newDistance = dist[u] + edgeDistance;

                // compare distances
                if (newDistance < dist[v.node]) {
                    dist[v.node] = newDistance;
                }

                // Add the current vertex to the PriorityQueue
                pqueue.add(new Node(v.node, dist[v.node]));
            }
        }
    }
}

/*
used for weighted graphs.
 */
public class DijkstrasAlgorithm {
    public static void main(String arg[]) {
        int V = 6;
        int source = 0;
        // adjacency list representation of graph
        List<List<Node>> adj_list = new ArrayList<List<Node>>();
        // Initialize adjacency list for every node in the graph
        for (int i = 0; i < V; i++) {
            List<Node> item = new ArrayList<Node>();
            adj_list.add(item);
        }


        // Input graph edges
        adj_list.get(0).add(new Node(1, 5));
        adj_list.get(0).add(new Node(2, 3));
        adj_list.get(0).add(new Node(3, 2));
        adj_list.get(0).add(new Node(4, 3));
        adj_list.get(0).add(new Node(5, 3));
        adj_list.get(2).add(new Node(1, 2));
        adj_list.get(2).add(new Node(3, 3));
        // call Dijkstra's algo method
        Graph dpq = new Graph(V);
        dpq.algo_dijkstra(adj_list, source);

        // Print the shortest path from source node to all the nodes
        System.out.println("The shorted path from source node to other nodes:");
        System.out.println("Source\t\t" + "Node#\t\t" + "Distance");
        for (int i = 0; i < dpq.dist.length; i++) {
            System.out.println(source + " \t\t " + i + " \t\t " + dpq.dist[i]);
        }
    }
}

// Node class
class Node implements Comparator<Node> {
    public int node;
    public int cost;

    public Node() {
    } //empty constructor

    public Node(int node, int cost) {
        this.node = node;
        this.cost = cost;
    }

    @Override
    public int compare(Node node1, Node node2) {
        if (node1.cost < node2.cost) {
            return -1;
        }
        if (node1.cost > node2.cost) {
            return 1;
        }
        return 0;
    }
}
