import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Main{
    public static void main(String[] args) {
        
        // Example usage
        String fileName = "File.csv";
        
        KMeanCluster cluster = new KMeanCluster(fileName,3);

        cluster.tableDisplay();

        List<List<Float>> centerPoints = new ArrayList<>();
        centerPoints.add(Arrays.asList(16.0f, 17.0f, 18.0f));
        centerPoints.add(Arrays.asList(13.0f, 14.0f, 15.0f));
        centerPoints.add(Arrays.asList(4.0f, 5.0f, 6.0f));

        cluster.setupCenterPoints(centerPoints);
        cluster.setupRandomCenterPoints();

        cluster.performDistanceCalculation();

        System.out.print(cluster.findPointsDistribution());

        cluster.findNewCenterPoints();

    }
}