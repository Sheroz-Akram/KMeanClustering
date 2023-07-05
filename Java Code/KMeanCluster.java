import java.util.List;
import java.util.ArrayList;

public class KMeanCluster {
    
    // This Variable is used to store the data of File CSV
    private List<List<Float>> data;

    // This is used to find that total number of clusters 
    private int totalClusters;

    // This will store the center points of all the clusters
    private List<List<Float>> clusterCenterPoints;

    /*
     * Constructor will read the data from csv and setup all the variables
     * 
     * @param fileName The path and name of the file that we want to read.
     * 
     */
    public KMeanCluster(String fileName, int totalClusters){

        // Read Data from CSV file
        try{
            data = CSVFileReader.readCSV(fileName);
        }
        catch(Exception e){
            System.err.println("Error has occured in reading data from csv file.");
        }

        // Set the number of cluster
        this.totalClusters = totalClusters;

        // Setup the Cluster Center Points
        clusterCenterPoints = new ArrayList<List<Float>>();

    }

    /*
     * This function will display the content of variable data
     */
    public void tableDisplay(){
        // Print the content of the CSV file
        System.out.println("The Dataset is Given As: ");
        for (List<Float> row : data) {
            for(Float element : row){
                System.out.print(element + "\t");
            }
            System.out.println();
        }
    }

    /*
     * Set you custom center points for the cluster
     * 
     * @param centerPoints Pass the List of center points
     */
    public void setupCenterPoints(List<List<Float>> centerPoints){

        // If Center Points match the number of clusters
        if(centerPoints.size() == totalClusters){
            clusterCenterPoints.clear();
            clusterCenterPoints = centerPoints;

            // Display the cluster points
            System.out.println("The cluster center points are given as:");
            System.out.println(clusterCenterPoints);
        }
        else{
            System.out.println("The center points should have index up to the total number of clusters");
        }
    }

    /*
     * This function will find the random points from the dataset and assign points to each clusters.
     */
    public void setupRandomCenterPoints(){

        List<Integer> randomPoints = new ArrayList<Integer>();

        // Select Random Points for each cluster
        for (int i = 0; i < totalClusters; i++) {
            
            // Find the Random Number
            int randomPoint = (int)(Math.random()*(data.size()-1+1)+0); 

            // Check if the Point already exist or Not
            boolean isFound = false;
            for (Integer x : randomPoints) {

                // If Already Exist then skip the point
                if(randomPoint == x){
                    isFound = true;
                }
            }

            // if the Number is Found Skip
            if(isFound){
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
}
