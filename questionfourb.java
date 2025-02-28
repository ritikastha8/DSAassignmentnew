// import java.util.*;

// public class questionfourb {
//     public static int minRoadsToCollectPackages(int[] packages, int[][] roads) {
//         int n = packages.length;
//         List<List<Integer>> graph = new ArrayList<>();
        
//         // Build adjacency list
//         for (int i = 0; i < n; i++) {
//             graph.add(new ArrayList<>());
//         }
//         for (int[] road : roads) {
//             graph.get(road[0]).add(road[1]);
//             graph.get(road[1]).add(road[0]);
//         }

//         // Find optimal starting position (first node with a package)
//         int start = 0;
//         for (int i = 0; i < n; i++) {
//             if (packages[i] == 1) {
//                 start = i;
//                 break;
//             }
//         }

//         // Perform BFS to find min roads needed
//         return bfs(graph, packages, start);
//     }

//     private static int bfs(List<List<Integer>> graph, int[] packages, int start) {
//         Queue<Integer> queue = new LinkedList<>();
//         Set<Integer> visited = new HashSet<>();
//         queue.add(start);
//         visited.add(start);
//         int roads = 0;

//         while (!queue.isEmpty()) {
//             int size = queue.size();
//             boolean collected = false;
            
//             for (int i = 0; i < size; i++) {
//                 int node = queue.poll();
                
//                 // Check nearby nodes within distance 2 for packages
//                 for (int neighbor : graph.get(node)) {
//                     if (!visited.contains(neighbor)) {
//                         queue.add(neighbor);
//                         visited.add(neighbor);
//                         roads++;
//                         if (packages[neighbor] == 1) {
//                             collected = true;
//                         }
//                     }
//                 }
//             }

//             // If a package is collected, move back to the start
//             if (collected) {
//                 roads++;
//             }
//         }

//         return roads;
//     }

//     public static void main(String[] args) {
//         // Test case 1
//         int[] packages1 = {1, 0, 0, 0, 0, 1};
//         int[][] roads1 = {{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}};
//         System.out.println(minRoadsToCollectPackages(packages1, roads1)); // Output: 2

//         // Test case 2
//         int[] packages2 = {0, 0, 0, 1, 1, 0, 0, 1};
//         int[][] roads2 = {{0, 1}, {0, 2}, {1, 3}, {1, 4}, {2, 5}, {5, 6}, {5, 7}};
//         System.out.println(minRoadsToCollectPackages(packages2, roads2)); // Output: 2
//     }
// }


import java.util.*;

public class questionfourb {
    public static int minRoads(int[] packages, int[][] roads) {
        int n = packages.length;
        // Build the graph
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] road : roads) {
            int u = road[0];
            int v = road[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        int minRoads = Integer.MAX_VALUE;

        // Try each node as the starting location
        for (int start = 0; start < n; start++) {
            boolean[] visited = new boolean[n];
            Queue<Integer> queue = new LinkedList<>();
            queue.add(start);
            visited[start] = true;
            int roadsTraversed = 0;
            int packagesCollected = 0;

            // Perform BFS up to distance 2
            for (int distance = 0; distance <= 2; distance++) {
                int levelSize = queue.size();
                for (int i = 0; i < levelSize; i++) {
                    int current = queue.poll();
                    if (packages[current] == 1) {
                        packagesCollected++;
                    }
                    for (int neighbor : graph.get(current)) {
                        if (!visited[neighbor]) {
                            visited[neighbor] = true;
                            queue.add(neighbor);
                            roadsTraversed++;
                        }
                    }
                }
            }

            // Check if all packages are collected
            if (packagesCollected == countPackages(packages)) {
                // Return to the starting location
                roadsTraversed += 2; // Traverse back to the starting location
                minRoads = Math.min(minRoads, roadsTraversed);
            }
        }

        return minRoads;
    }

    private static int countPackages(int[] packages) {
        int count = 0;
        for (int pkg : packages) {
            if (pkg == 1) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        // Example 1
        int[] packages1 = {1, 0, 0, 0, 0, 1};
        int[][] roads1 = {{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}};
        System.out.println(minRoads(packages1, roads1)); // Output: 2

        // Example 2
        int[] packages2 = {0, 0, 0, 1, 1, 0, 0, 1};
        int[][] roads2 = {{0, 1}, {0, 2}, {1, 3}, {1, 4}, {2, 5}, {5, 6}, {5, 7}};
        System.out.println(minRoads(packages2, roads2)); // Output: 2
    }
}
