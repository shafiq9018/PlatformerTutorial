public class Print2DArray {
    public static void main(String[] args) {
        // Example 2D array
        int[][] array = {
                {11, 3, 11, 3},
                {3, 11, 3, 11},
                {11, 11, 3, 11},
                {3, 3, 11, 11}
        };

        // Print the 2D array with spaces and X's
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] == 11) {
                    System.out.print(" "); // Print space
                } else if (array[i][j] == 3) {
                    System.out.print("X"); // Print X
                } else {
                    System.out.print("?"); // For unexpected values
                }
            }
            System.out.println(); // Move to the next row
        }
    }
}