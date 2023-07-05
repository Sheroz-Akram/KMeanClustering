import java.util.List;
import java.util.ArrayList;

public class KMeanCluster {

    // This Variable is used to store the data of File CSV
    private List<List<Float>> data;

    // This is used to find that total number of clusters
    private int totalClusters;

    // This will store the center points of all the clusters
    private List<List<Float>> clusterCenterPoints;

    // Store all the Distances Between Center Point and All the Other Points
    List<List<Float>> distanceTable = new ArrayList<>();

    // This is used to store the Distribution of Points in each clusters
    List<List<Integer>> clustersPoints = new ArrayList<>();

    /**
     * Constructor will read the data from csv and setup all the variables
     * 
     * @param fileName The path and name of the file that we want to read.
     * 
     */
    public KMeanCluster(String fileName, int totalClusters) {

        // Read Data from CSV file
        try {
            data = CSVFileReader.readCSV(fileName);
        } catch (Exception e) {
            System.err.println("Error has occured in reading data from csv file.");
        }

        // Set the number of cluster
        this.totalClusters = totalClusters;

        // Setup the Cluster Center Points
        clusterCenterPoints = new ArrayList<List<Float>>();

        // Distance table is used to Find the distance Between Center Point and All the
        // other Points

    }

    /**
     * This function will display the content of variable data
     */
    public void tableDisplay() {
        // Print the content of the CSV file
        System.out.println("The Dataset is Given As: ");
        for (List<Float> row : data) {
            for (Float element : row) {
                System.out.print(element + "\t");
            }
            System.out.println();
        }
    }

    /**
     * Set you custom center points for the cluster
     * 
     * @param centerPoints Pass the List of center points
     */
    public void setupCenterPoints(List<List<Float>> centerPoints) {

        // If Center Points match the number of clusters
        if (centerPoints.size() == totalClusters) {
            clusterCenterPoints.clear();
            clusterCenterPoints = centerPoints;

            // Display the cluster points
            System.out.println("The cluster center points are given as:");
            System.out.println(clusterCenterPoints);
        } else {
            System.out.println("The center points should have index up to the total number of clusters");
        }
    }

    /**
     * This function will find the random points from the dataset and assign points
     * to each clusters.
     */
    public void setupRandomCenterPoints() {

        List<Integer> randomPoints = new ArrayList<Integer>();

        // Select Random Points for each cluster
        for (int i = 0; i < totalClusters; i++) {

            // Find the Random Number
            int randomPoint = (int) (Math.random() * (data.size() - 1 + 1) + 0);

            // Check if the Point already exist or Not
            boolean isFound = false;
            for (Integer x : randomPoints) {

                // If Already Exist then skip the point
                if (randomPoint == x) {
                    isFound = true;
                }
            }

            // if the Number is Found Skip
            if (isFound) {
                i--;
                continue;
            }

            // Add a new Random Number if does not Exist already
            randomPoints.add(randomPoint);

        }

        // Print All the Random Points Generated
        System.out.println("The Cluster Random Center Points Are: ");

        // Set the New Cluster Center Points
        clusterCenterPoints.clear();
        for (Integer x : randomPoints) {
            List<Float> point = data.get(x);
            clusterCenterPoints.add(point);
        }

        System.out.println(clusterCenterPoints);
    }

    /**
     * Perform Euclidean Operation to Find the Distance Between two points
     * The dimension of both the points should be equal
     * 
     * @param firstPoint The 1st Point of N Dimentsion
     * 
     * @param secondPoint The 2nd Point of N Dimension
     */
    private float euclideanDistance(List<Float> firstPoint, List<Float> secondPoint) {

        // The Points must be of same dimentions
        if (firstPoint.size() != secondPoint.size()) {
            System.out.println("Points Must be of Same Dimentions");
            return 0;
        }

        // Use the Eulidean Formula to find Distance
        double totalSum = 0.0f;
        for (int i = 0; i < firstPoint.size(); i++) {
            // Find the Distance between each coordinate and Square that
            // Also add all the coordinates distance
            totalSum += Math.pow((firstPoint.get(i) - secondPoint.get(i)), 2);
        }

        // Find the Square Root of the Total Distance difference
        float distance = (float) Math.sqrt(totalSum);

        return distance;
    }

    /**
     * This function is used to perform Euclidean Distance Measurement to find the
     * distance between center point to all the other points
     * 
     * @return Distance Table Which store the distance of each point with it center
     * of each cluster
     */
    public List<List<Float>> performDistanceCalculation() {

        // Find the Distance of Each Point from the Center Points
        for (List<Float> dataPoint : data) {

            // Store the Distance of Current Center Point and Other Points
            List<Float> clusterDistances = new ArrayList<>();

            // Get all the cluster center points
            for (List<Float> clusterCenter : clusterCenterPoints) {

                // Find the Distance Between Center Point and Data Point
                float distanceBetweenPoints = euclideanDistance(clusterCenter, dataPoint);

                // Add the distance to array of distances
                clusterDistances.add(distanceBetweenPoints);

            }

            // Combine the Cluster Distance to the whole distance Table
            distanceTable.add(clusterDistances);
        }
        return distanceTable;
    }

    /**
     * 
     * This function find the distribution of all the points in each clusters
     * 
     * @return Distribution of Points in each cluster
     */
    public List<List<Integer>> findPointsDistribution() {

        // Find the Points in Each Cluster
        for (int j = 0; j < clusterCenterPoints.size(); j++) {

            // Get all the Points in Each Cluster
            List<Integer> clusterInsidePoints = new ArrayList<>();

            // get All the Distances
            for (int k = 0; k < distanceTable.size(); k++) {

                // Get the Distance Data for that Point
                List<Float> list = distanceTable.get(k);

                // Set the Initial Distance to First One
                float minValue = list.get(0);
                int clusterPosition = 0;

                // Find the Mininum Distance Cluster
                for (int i = 1; i < list.size(); i++) {

                    if (minValue >= list.get(i)) {
                        minValue = list.get(i);
                        clusterPosition = i;
                    }
                }

                if (clusterPosition == j) {
                    clusterInsidePoints.add(k);
                }

            }

            // We Find all the Points in that Cluster
            // Now we Combine them
            clustersPoints.add(clusterInsidePoints);

        }

        System.out.println("Points Distribution are calculated!");
        return clustersPoints;
    }

    /**
     * 
     * This Function find the new Centers points using the Points Distribution in
     * Each Cluster
     * 
     * @return new Center Points for each cluster
     * 
     */
    public List<List<Float>> findNewCenterPoints() {

        // Find the Total Sum and Take the Average
        List<List<Float>> newPoints = new ArrayList<>();

        for (int i = 0; i < clustersPoints.size(); i++) {

            // Find the Point in that Cluster
            List<Integer> list = clustersPoints.get(i);

            // Set the Initial Point Data
            newPoints.add(data.get(list.get(0)));

            // Get the value of each point in that cluster and Sum it
            for (int k = 1; k < list.size(); k++) {

                // Get the Point data
                List<Float> pointData = data.get(list.get(k));

                // Add the Point Coordinates to the total Sum
                for (int j = 0; j < pointData.size(); j++) {
                    newPoints.get(i).set(j, newPoints.get(i).get(j) + pointData.get(j));
                }
            }

            // Now We Find the Average by Total
            for (int j = 0; j < newPoints.get(i).size(); j++) {
                newPoints.get(i).set(j, newPoints.get(i).get(j) / clustersPoints.get(i).size());
            }
        }

        return newPoints;
    }
}
