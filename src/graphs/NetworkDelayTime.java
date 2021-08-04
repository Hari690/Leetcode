package graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * You are given a network of n nodes, labeled from 1 to n. You are also given times, a list of travel times as directed edges times[i] =
 * (ui, vi, wi), where ui is the source node, vi is the target node, and wi is the time it takes for a signal to travel from source to
 * target.
 *
 * We will send a signal from a given node k. Return the time it takes for all the n nodes to receive the signal. If it is impossible for
 * all the n nodes to receive the signal, return -1.
 */

/*
Boils down to finding shortest path from one node to all others and check if all nodes are visited.
 */
public class NetworkDelayTime {

    public class Edge{
        int dst;
        int wt;
        Edge(int dst , int wt){
            this.dst = dst;
            this.wt = wt;
        }
    }
    public class pair{
        int v;
        int dist;
        pair(int v, int dist){
            this.v = v;
            this.dist = dist;
        }
    }
    public int networkDelayTime(int[][] times, int N, int K) {
        ArrayList<Edge>[] graph = new ArrayList[N+1];
        for(int i=0;i<N+1;i++) graph[i] = new ArrayList<>();
        for(int i = 0; i<times.length; i++){
            Edge newE = new Edge(times[i][1],times[i][2]);
            graph[times[i][0]].add(newE);
        }

        pair src = new pair(K,0);
        PriorityQueue<pair> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.dist));
        pq.add(src);
        int [] len = new int[N+1];
        Arrays.fill(len,Integer.MAX_VALUE);
        len[K] = 0;
        int time = 0;
        while(pq.size()!=0){
            pair rvtx = pq.remove();
            for(Edge e : graph[rvtx.v]){
                pair tmp = new pair(e.dst, e.wt + rvtx.dist);
                // because of distance check cycle will get handled because we will not update unless distance is smaller.
                if(len[tmp.v]>tmp.dist){
                    pq.add(tmp);
                    len[tmp.v] = e.wt+rvtx.dist;
                }
            }
        }
        for(int i=1;i<=N;++i)
        {
            if(len[i]==Integer.MAX_VALUE)
                return -1;
            time = Math.max(time,len[i]);
        }
        return time;
    }
}