import java.util.Scanner;
import java.util.Arrays;

/**********************************************************************
    Daniel C. West
    CMSC 401 Assignment 4
    Minimum Cost Rod Cutting

    Given a rod N inches long and a set of M cutting points on the rod.
    Minimize the total cost of cutting the rod and output this value.
    Each cut divided the rod into two smaller sub-rods and the cost of
    that cut equals the length of the rod or sub-rod that is being cut.

    minCostOfCuts algorithm has been adapted from mytestaccount2
    on https://www.careercup.com/question?id=5188262471663616
***********************************************************************/

public class A4 {

    public static void main(String[] args) {

        // Variables to hold values for rod length and number of points to cut
        int rod_length = 0;
        int cut_points = 0;

        // Scanner to use values from the terminal
        Scanner in = new Scanner(System.in);

        // While loop to make sure the values for rod length are not lower than 2 or higher than 100
        while(rod_length < 2 || rod_length > 100) {
            // Take in a values from the terminal to represent the rod length
            System.out.println("Please enter a rod length between 2 and 100");
            rod_length = in.nextInt();
        }

        // While loop to make sure the number of cut points is not less than 1 or greater than the total
        // length minus 1
        while(cut_points < 1 || cut_points > rod_length - 1) {
            // Take in values from the terminal to represent the number of cut points
            System.out.println("Please enter the number of points to cut");
            cut_points = in.nextInt();
        }

        // Array to hold locations of each cut point
        int[] cut_locations = new int[cut_points];

        // For loop to go through the total number of cut points and put in
        // the specific cut position
        for (int i = 0; i < cut_points; i++) {
            // While loop to make sure the cut position is not less than or equal to zero
            // and there is no cut point greater than or equal to the total rod length
            while(cut_locations[i] <= 0 || cut_locations[i] >= rod_length) {
                // Take in the value of a specific cut position
                System.out.println("Please enter " + cut_points + " cut location(s) between 1 and " + (rod_length - 1));
                cut_locations[i] = in.nextInt();
            }
        }

        // Quick sort to put array in ascending order before it goes into minCostOfCuts method
        Arrays.sort(cut_locations);

        // Displays the optimal minimum cost of cutting the given rod
        System.out.println("The minimum cost of cutting is: " + minCostOfCuts(cut_locations, rod_length));
    }

    // Method to find the minimum cost of cuts, given the cut points and rod length
    /**
     The plan
     ======
     M markings => M+2 markings, including the beginning and the end of the log
     dp[i][j] = optimum cost cutting up log between marking i and marking j
     dp[i][j] = if |j-i| < 2 then 0, same marking or no inbetween markings
     dp[i][j] = dp[i][k]+dp[k][j]+logLen(i,j) over all k:i+1..j-1
     O(M**2) different (i,j) pairs:
     maximum O(M) different cuts between i and j:
     1 sum calculation, 1 length calculation  -> time:O(1), space:O(M)
     dp time: O((M**2)*M), space: O(M**2)
     **/
    private static int minCostOfCuts(int[] cut_points, int rod_length) {

        // Array to the hold first index, rod size and cut point values passed in
        int[] rod_size = rodSize(cut_points, rod_length);
        // 2d array to find the minimum optimal cut value
        int[][] find_minimum = new int[cut_points.length + 2][cut_points.length + 2];

        for(int between_point_count = 1; between_point_count <= cut_points.length; ++between_point_count) {

            for(int i = 0;; ++i) {

                int j = i + between_point_count + 1;

                if (j >= find_minimum.length) {
                    break;
                }

                int minimum_value = Integer.MAX_VALUE;
                int cut_cost = rod_size[j] - rod_size[i];

                for(int k = i + 1; k <= j - 1; ++k) {
                    minimum_value = Math.min(minimum_value, find_minimum[i][k] + find_minimum[k][j] + cut_cost);
                }
                find_minimum[i][j] = minimum_value;
            }
        }
        // Returns the optimal minimum from the 2d array, which is the last column
        // in the first row of the array
        return find_minimum[0][find_minimum.length - 1];
    }

    // Helper method to add the first and last index to the rod size array
    private static int[] rodSize(int[] cut_points, int length) {

        int[] return_array = new int[cut_points.length + 2];

        for(int i = 1; i <= cut_points.length; ++i) {
            return_array[i] = cut_points[i - 1];
        }
        return_array[return_array.length - 1] = length;

        return return_array;
    }
}
