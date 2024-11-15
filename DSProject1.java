import java.util.Scanner;


public class DSProject1{

    // Declare the Scanner instance at the class level
    public static Scanner input = new Scanner(System.in);
    public static SearchEngine SE = new SearchEngine();


    public static void main(String[] args) {
        // You can now use the Scanner instance here
        
        SE.Data("dataset/stop.txt","dataset/dataset.csv");
    }
}