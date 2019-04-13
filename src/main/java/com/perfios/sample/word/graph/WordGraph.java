package com.perfios.sample.word.graph;

import javafx.util.Pair;

import java.util.*;

public class WordGraph {

    private Map<String, LinkedHashSet<String>> wordMap;

    public WordGraph() {
        this.wordMap = new HashMap<>();
    }

    private void addVertex(String str) {
        if (!wordMap.containsKey(str))
            wordMap.put(str, new LinkedHashSet<>());
    }

    //2-way edge
    private void addEdge(String s1, String s2) {
        int editDistance = findEditDistance(s1, s2);
        if (editDistance == 1) {
            if (wordMap.containsKey(s1)) {
                wordMap.get(s1).add(s2);
            }

            if (wordMap.containsKey(s2)) {
                wordMap.get(s2).add(s1);
            }
        }
    }

    public void loadWordGraph(List<String> wordList) {
        for (int i = 0; i < wordList.size(); i++) {
            String s1 = wordList.get(i);
            addVertex(s1);
            for (int j = i + 1; j < wordList.size(); j++) {
                String s2 = wordList.get(j);
                addVertex(s2);
                addEdge(s1, s2);
            }
        }
    }

    //implementation of Dijkstra's shortest-path
    // but for undirected weighted graph where all edge weights are considered as 1
    public LinkedList<String> shortestPath(String node, String target) {
        String source = node;
        LinkedList<String> queue = new LinkedList<>();
        LinkedList<String> pathStack = new LinkedList<>();
        Map<String, Pair<Integer, String>> distanceMap = new HashMap<>();
        distanceMap.put(source, new Pair<>(0, source)); // initialize source with 0 distance

        pathStack.push(target);
        queue.offer(source);
        while(!queue.isEmpty()) {
            node = queue.poll();
            int nodeDist = distanceMap.get(node).getKey();
            Iterator<String> iterator = wordMap.get(node).iterator();
            while(iterator.hasNext()) {
                String next = iterator.next();
                if(distanceMap.get(next) == null) {
                    distanceMap.put(next, new Pair<>(nodeDist+1, node));
                    queue.add(next);
                }
                else {
                    int currDist = distanceMap.get(next).getKey();
                    if(currDist > nodeDist+1) {
                        distanceMap.put(next, new Pair<>(nodeDist+1, node));
                        queue.add(next);
                    }
                }
            }
        }

        if(distanceMap.get(target) != null) {
            while(pathStack.peek() != source) {
                node = pathStack.peek();
                String via = distanceMap.get(node).getValue();
                pathStack.push(via);
            }
        }

        return pathStack;
    }


    public static int findEditDistance(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int editDist = -1;
        if (m == n) {
            editDist = 0;
            for (int i = 0; i < n; i++) {
                if (s1.charAt(i) != s2.charAt(i))
                    editDist++;
            }
        }

        return editDist;
    }
}
