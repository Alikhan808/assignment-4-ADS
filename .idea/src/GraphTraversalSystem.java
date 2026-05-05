import java.util.Scanner;

public class GraphTraversalSystem {
    public static void main(String[] systemArguments) {
        Scanner consoleReader = new Scanner(System.in);
        System.out.println("Provide the total number of vertices in the graph:");
        int totalGraphVertices = consoleReader.nextInt();
        String[] vertexNames = new String[totalGraphVertices];
        System.out.println("Provide the names for each of the " + totalGraphVertices + " vertices:");
        for (int vertexIndex = 0; vertexIndex < totalGraphVertices; vertexIndex++) {
            vertexNames[vertexIndex] = consoleReader.next();
        }
        int[][] graphAdjacencyMatrix = new int[totalGraphVertices][totalGraphVertices];
        System.out.println("Provide the total number of edges:");
        int totalGraphEdges = consoleReader.nextInt();
        System.out.println("Provide the edges by entering pairs of connecting indices:");
        for (int edgeIndex = 0; edgeIndex < totalGraphEdges; edgeIndex++) {
            int sourceVertexIndex = consoleReader.nextInt();
            int destinationVertexIndex = consoleReader.nextInt();
            graphAdjacencyMatrix[sourceVertexIndex][destinationVertexIndex] = 1;
        }
        System.out.println("Provide the index of the vertex where the search should begin:");
        int startingVertexIndex = consoleReader.nextInt();
        executeDepthFirstSearchAlgorithm(graphAdjacencyMatrix, vertexNames, totalGraphVertices, startingVertexIndex);
        executeBreadthFirstSearchAlgorithm(graphAdjacencyMatrix, vertexNames, totalGraphVertices, startingVertexIndex);
        consoleReader.close();
    }
    public static void executeDepthFirstSearchAlgorithm(int[][] adjacencyMatrix, String[] nodeNames, int totalNodes, int startingNode) {
        System.out.print("Output of Depth First Search trace: ");
        boolean[] trackingVisitedNodes = new boolean[totalNodes];
        traverseGraphDepthFirstRecursively(adjacencyMatrix, nodeNames, totalNodes, startingNode, trackingVisitedNodes);
        System.out.println();
    }
    private static void traverseGraphDepthFirstRecursively(int[][] adjacencyMatrix, String[] nodeNames, int totalNodes, int currentNode, boolean[] visitedNodesTracker) {
        visitedNodesTracker[currentNode] = true;
        System.out.print(nodeNames[currentNode] + " ");
        for (int neighboringNode = 0; neighboringNode < totalNodes; neighboringNode++) {
            boolean edgeExists = adjacencyMatrix[currentNode][neighboringNode] == 1;
            boolean isNotVisited = !visitedNodesTracker[neighboringNode];

            if (edgeExists && isNotVisited) {
                traverseGraphDepthFirstRecursively(adjacencyMatrix, nodeNames, totalNodes, neighboringNode, visitedNodesTracker);
            }
        }
    }
    public static void executeBreadthFirstSearchAlgorithm(int[][] adjacencyMatrix, String[] nodeNames, int totalNodes, int startingNode) {
        System.out.print("Output of Breadth First Search trace: ");
        boolean[] trackingVisitedNodes = new boolean[totalNodes];
        int[] traversalQueueData = new int[totalNodes];
        int queueHeadPosition = 0;
        int queueTailPosition = 0;
        trackingVisitedNodes[startingNode] = true;
        traversalQueueData[queueTailPosition] = startingNode;
        queueTailPosition++;
        while (queueHeadPosition < queueTailPosition) {
            int processingNode = traversalQueueData[queueHeadPosition];
            queueHeadPosition++;
            System.out.print(nodeNames[processingNode] + " ");
            for (int neighboringNode = 0; neighboringNode < totalNodes; neighboringNode++) {
                boolean edgeExists = adjacencyMatrix[processingNode][neighboringNode] == 1;
                boolean isNotVisited = !trackingVisitedNodes[neighboringNode];

                if (edgeExists && isNotVisited) {
                    trackingVisitedNodes[neighboringNode] = true;
                    traversalQueueData[queueTailPosition] = neighboringNode;
                    queueTailPosition++;
                }
            }
        }
        System.out.println();
    }
}