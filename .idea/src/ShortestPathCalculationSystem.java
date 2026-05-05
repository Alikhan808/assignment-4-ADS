import java.util.Scanner;

public class ShortestPathCalculationSystem {
    public static void main(String[] systemArguments) {
        Scanner consoleReader = new Scanner(System.in);
        final int simulatedInfinity = 99999999;
        System.out.println("Enter the total number of cities in the network:");
        int totalNetworkCities = consoleReader.nextInt();
        String[] cityDesignations = new String[totalNetworkCities];
        System.out.println("Enter the designations for the " + totalNetworkCities + " cities:");
        for (int cityIndex = 0; cityIndex < totalNetworkCities; cityIndex++) {
            cityDesignations[cityIndex] = consoleReader.next();
        }
        int[][] routeDistancesMatrix = new int[totalNetworkCities][totalNetworkCities];
        for (int rowPointer = 0; rowPointer < totalNetworkCities; rowPointer++) {
            for (int colPointer = 0; colPointer < totalNetworkCities; colPointer++) {
                if (rowPointer == colPointer) {
                    routeDistancesMatrix[rowPointer][colPointer] = 0;
                } else {
                    routeDistancesMatrix[rowPointer][colPointer] = simulatedInfinity;
                }
            }
        }
        System.out.println("Enter the total number of connecting roads:");
        int totalConnectingRoads = consoleReader.nextInt();
        System.out.println("Input the roads sequentially:");
        for (int roadIndex = 0; roadIndex < totalConnectingRoads; roadIndex++) {
            int sourceCity = consoleReader.nextInt();
            int destinationCity = consoleReader.nextInt();
            int physicalDistance = consoleReader.nextInt();
            routeDistancesMatrix[sourceCity][destinationCity] = physicalDistance;
            routeDistancesMatrix[destinationCity][sourceCity] = physicalDistance;
        }
        System.out.println("Enter the index of the starting departure city:");
        int startingCityIndex = consoleReader.nextInt();
        System.out.println("Enter the index of the final destination city:");
        int finalDestinationIndex = consoleReader.nextInt();
        discoverShortestRouteBetweenCities(routeDistancesMatrix, cityDesignations, totalNetworkCities, startingCityIndex, finalDestinationIndex, simulatedInfinity);
        consoleReader.close();
    }
    public static void discoverShortestRouteBetweenCities(int[][] distanceMatrix, String[] cityNames, int totalCitiesCount, int departureIndex, int destinationIndex, int infinityRepresentation) {
        int[] optimalCalculatedDistances = new int[totalCitiesCount];
        boolean[] trackingVisitedCities = new boolean[totalCitiesCount];
        int[] precedingCityTracker = new int[totalCitiesCount];
        for (int index = 0; index < totalCitiesCount; index++) {
            optimalCalculatedDistances[index] = infinityRepresentation;
            trackingVisitedCities[index] = false;
            precedingCityTracker[index] = -1;
        }
        optimalCalculatedDistances[departureIndex] = 0;
        for (int processingRound = 0; processingRound < totalCitiesCount - 1; processingRound++) {
            int closestUnvisitedCity = locateNearestUnvisitedCity(optimalCalculatedDistances, trackingVisitedCities, totalCitiesCount, infinityRepresentation);
            trackingVisitedCities[closestUnvisitedCity] = true;
            for (int neighboringCity = 0; neighboringCity < totalCitiesCount; neighboringCity++) {
                boolean cityIsUnvisited = !trackingVisitedCities[neighboringCity];
                boolean roadExists = distanceMatrix[closestUnvisitedCity][neighboringCity] != infinityRepresentation;
                boolean pathIsCurrentlyKnown = optimalCalculatedDistances[closestUnvisitedCity] != infinityRepresentation;
                if (cityIsUnvisited && roadExists && pathIsCurrentlyKnown) {
                    int newlyCalculatedDistance = optimalCalculatedDistances[closestUnvisitedCity] + distanceMatrix[closestUnvisitedCity][neighboringCity];
                    if (newlyCalculatedDistance < optimalCalculatedDistances[neighboringCity]) {
                        optimalCalculatedDistances[neighboringCity] = newlyCalculatedDistance;
                        precedingCityTracker[neighboringCity] = closestUnvisitedCity;
                    }
                }
            }
        }
        printFinalDiscoveredRoute(cityNames, optimalCalculatedDistances, precedingCityTracker, departureIndex, destinationIndex, totalCitiesCount, infinityRepresentation);
    }
    private static int locateNearestUnvisitedCity(int[] currentDistances, boolean[] visitedStatuses, int totalCitiesCount, int infinityRepresentation) {
        int smallestFoundDistance = infinityRepresentation;
        int indexOfClosestCity = -1;
        for (int index = 0; index < totalCitiesCount; index++) {
            if (!visitedStatuses[index] && currentDistances[index] <= smallestFoundDistance) {
                smallestFoundDistance = currentDistances[index];
                indexOfClosestCity = index;
            }
        }
        return indexOfClosestCity;
    }
    private static void printFinalDiscoveredRoute(String[] cityNames, int[] shortestDistances, int[] precedingCityTracker, int departureIndex, int destinationIndex, int totalCitiesCount, int infinityRepresentation) {
        if (shortestDistances[destinationIndex] == infinityRepresentation) {
            System.out.println("It is impossible to establish a route between " + cityNames[departureIndex] + " and " + cityNames[destinationIndex]);
            return;
        }
        System.out.println("Absolute minimum travel distance is: " + shortestDistances[destinationIndex]);
        System.out.print("Optimal travel route is: ");
        int[] routeTracePath = new int[totalCitiesCount];
        int pathStepsCounter = 0;
        int tracingCityPointer = destinationIndex;
        while (tracingCityPointer != -1) {
            routeTracePath[pathStepsCounter] = tracingCityPointer;
            pathStepsCounter++;
            tracingCityPointer = precedingCityTracker[tracingCityPointer];
        }
        for (int stepIndex = pathStepsCounter - 1; stepIndex >= 0; stepIndex--) {
            System.out.print(cityNames[routeTracePath[stepIndex]]);
            if (stepIndex > 0) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }
}