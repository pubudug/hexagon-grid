package io.github.pubudug.hexgrid;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class ShortestPathCalculator<C extends Coordinate> {
    private CoordinateGrid<C> grid;
    private CoordinateAttributes<C> hexagonAttributes;

    public ShortestPathCalculator(CoordinateGrid<C> grid, CoordinateAttributes<C> hexagonAttributes) {
        super();
        this.grid = grid;
        this.hexagonAttributes = hexagonAttributes;
    }

    public List<C> findShortestPath(C from, C to) {
        final Map<C, Integer> estimatedCost = new HashMap<>();
        Map<C, Integer> cost = new HashMap<>();
        Map<C, C> cameFrom = new HashMap<>();

        estimatedCost.put(from, 0);
        cost.put(from, 0);
        cameFrom.put(from, null);

        Queue<C> queue = new PriorityQueue<>(new Comparator<C>() {

            @Override
            public int compare(C o1, C o2) {
                return estimatedCost.get(o1) - estimatedCost.get(o2) > 0 ? 1 : -1;
            }

        });
        queue.add(from);

        while (!queue.isEmpty()) {
            C current = queue.poll();
            if (current.equals(to)) {
                break;
            }
            for (C next : grid.getNeighborsOf(current)) {
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

    protected int heuristic(C from, C to) {
        return from.distanceTo(to);
    }

    private List<C> backtrackShortestPath(C from, C to, Map<C, C> cameFrom) {
        List<C> result = new LinkedList<>();
        result.add(to);
        C previous = to;
        while (!cameFrom.get(previous).equals(from)) {
            result.add(cameFrom.get(previous));
            previous = cameFrom.get(previous);
        }
        Collections.reverse(result);
        return result;
    }
}
