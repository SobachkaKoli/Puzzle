package com.example.puzzle_number;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PuzzleLogic {

    static class Edge {
        int to;
        int weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public static List<String> readFragmentsFromFile(File file) throws IOException {
        List<String> fragments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    fragments.add(line.trim());
                }
            }
        }
        return fragments;
    }

    public static void validateFragments(List<String> fragments) {
        for (String fragment : fragments) {
            if (!fragment.matches("\\d+")) {
                throw new IllegalArgumentException("All fragments must be numeric. Invalid fragment: " + fragment);
            }
        }
    }

    public static String buildLargestSequence(List<String> fragments) {
        int n = fragments.size();
        Map<Integer, List<Edge>> graph = new HashMap<>();

        for (int i = 0; i < n; i++) {
            graph.putIfAbsent(i, new ArrayList<>());
            String end = fragments.get(i).substring(fragments.get(i).length() - 2);

            for (int j = 0; j < n; j++) {
                if (i != j) {
                    String start = fragments.get(j).substring(0, 2);
                    if (end.equals(start)) {
                        graph.get(i).add(new Edge(j, 2)); // 2-символьне перекриття
                    }
                }
            }
        }

        StringBuilder bestPath = new StringBuilder();
        for (int i = 0; i < n; i++) {
            Set<Integer> visited = new HashSet<>();
            StringBuilder currentPath = new StringBuilder();
            dfs(graph, fragments, i, visited, currentPath, bestPath);
        }
        return bestPath.toString();
    }

    private static void dfs(Map<Integer, List<Edge>> graph, List<String> fragments, int current, Set<Integer> visited, StringBuilder currentPath, StringBuilder bestPath) {
        visited.add(current);
        int originalLength = currentPath.length();

        if (currentPath.length() == 0) {
            currentPath.append(fragments.get(current));
        }

        boolean extended = false;

        for (Edge edge : graph.getOrDefault(current, Collections.emptyList())) {
            if (!visited.contains(edge.to)) {
                extended = true;

                String nextFragment = fragments.get(edge.to);
                int overlap = edge.weight;
                String uniquePart = nextFragment.substring(overlap);

                currentPath.append(uniquePart);
                dfs(graph, fragments, edge.to, visited, currentPath, bestPath);

                currentPath.setLength(originalLength);
            }
        }

        if (!extended && currentPath.length() > bestPath.length()) {
            bestPath.setLength(0);
            bestPath.append(currentPath);
        }

        visited.remove(current);
    }

}
