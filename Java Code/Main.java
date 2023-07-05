import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {

        // Example usage
        String fileName = "File.csv";

        KMeanCluster cluster = new KMeanCluster(fileName, 2);

        // Custom Center Points
        List<List<Float>> centerPoints = new ArrayList<>();
        centerPoints.add(Arrays.asList(185f,72f));
        centerPoints.add(Arrays.asList(170f,56f));

        // Display the Table
        cluster.tableDisplay();

        // Setup Some Random Points
        cluster.tableDisplay(cluster.setupCenterPoints(centerPoints), "Custom Center Points", "Point");

        int i = 1;
        do {

            System.out.println("***** STEP " + i + " *****");
            // Here create the Distance Table
            cluster.tableDisplay(cluster.performDistanceCalculation(), "Distance Table", "Point");

            // Display the Table Distribution
            cluster.tableDisplayInteger(cluster.findPointsDistribution(), "Points in Cluster", "Cluster");

            // Get New Center Points
            cluster.tableDisplay(cluster.findNewCenterPoints(), "New Center Points", "Point");

            i++;

        } while (cluster.swapPoints());

        // Display the Table Distribution
        System.out.println("***** RESULT *****");
        System.out.println("K Mean Clustering");
        cluster.tableDisplayInteger(cluster.findPointsDistribution(), "Points in Cluster", "Cluster");

    }
}