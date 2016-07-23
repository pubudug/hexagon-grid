package io.github.pubudug.hexgrid;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class ShortestPathCalculator<T extends Hexagon> {
    private HexagonGrid<T> grid;
    private HexagonAttributes<T> hexagonAttributes;

    public ShortestPathCalculator(HexagonGrid<T> grid, HexagonAttributes<T> hexagonAttributes) {
        super();
        this.grid = grid;
        this.hexagonAttributes = hexagonAttributes;
    }

    public List<T> findShortestPath(T from, T to) {
        final Map<T, Integer> estimatedCost = new HashMap<>();
        Map<T, Integer> cost = new HashMap<>();
        Map<T, T> cameFrom = new HashMap<>();

        estimatedCost.put(from, 0);
        cost.put(from, 0);
        cameFrom.put(from, null);

        Queue<T> queue = new PriorityQueue<>(new Comparator<T>() {

            @Override
            public int compare(T o1, T o2) {
                return estimatedCost.get(o1) - estimatedCost.get(o2) > 0 ? 1 : -1;
            }

        });
        queue.add(from);

        while (!queue.isEmpty()) {
            T current = queue.poll();
            if (current.equals(to)) {
                break;
            }
            for (T next : grid.getNeighborsOf(current)) {
                int newCost = cost.get(current) + hexagonAttributes.getMovementCost(next);
                if (!cost.containsKey(next) || newCost < cost.get(next)) {
                    cost.put(next, newCost);
                    estimatedCost.put(next, newCost + heuristic(next, to));
                    queue.add(next);
                    cameFrom.put(next, current);
                }
            }
        }
        return backtrackShortestPath(from, to, cameFrom);
    }

    protected int heuristic(T from, T to) {
        return from.distanceTo(to);
    }

    private List<T> backtrackShortestPath(T from, T to, Map<T, T> cameFrom) {
        List<T> result = new LinkedList<>();
        result.add(to);
        T previous = to;
        while (!cameFrom.get(previous).equals(from)) {
            result.add(cameFrom.get(previous));
            previous = cameFrom.get(previous);
        }
        Collections.reverse(result);
        return result;
    }
}
