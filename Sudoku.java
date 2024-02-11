import java.util.Scanner;

public class Sudoku {
    // Create a 9 x 9 array of integers to represent the Sudoku
    static int[][] sudoku = new int[9][9];

    // Define a method to check if a number is safe for a cell
    static boolean checkCell(int row, int column, int number) {
        // Check the row for any conflicts
        for (int i = 0; i < 9; i++) {
            if (sudoku[row][i] == number)
                return false;
        }

        // Check the column for any conflicts
        for (int i = 0; i < 9; i++) {
            if (sudoku[i][column] == number)
                return false;
        }

        // Check the 3 x 3 sub-grid for any conflicts
        int rowStart = row - row % 3;
        int colStart = column - column % 3;

        for (int i = rowStart; i < rowStart + 3; i++) {
            for (int j = colStart; j < colStart + 3; j++) {
                if (sudoku[i][j] == number)
                    return false;
            }
        }

        // If no conflicts, return true
        return true;
    }

    // Define a method to fill the Sudoku with random numbers
    static boolean fillSudoku(int row, int column) {
        // If the Sudoku is full, return true
        if (row == 9)
            return true;

        // If the current cell is not empty, move to the next cell
        if (sudoku[row][column] != 0) {
            if (column == 8)
                return fillSudoku(row + 1, 0);
            else
                return fillSudoku(row, column + 1);
        }

        // Try each possible number from 1 to 9
        for (int num = 1; num <= 9; num++) {
            // Generate a random number
            int random = (int) (Math.random() * 9) + 1;

            // Check if the number is safe for the cell
            if (checkCell(row, column, random)) {
                // Place the number in the cell
                sudoku[row][column] = random;

                // Move to the next cell
                if (column == 8) {
                    if (fillSudoku(row + 1, 0))
                        return true;
                } else {
                    if (fillSudoku(row, column + 1))
                        return true;
                }

                // If the number leads to an invalid state, remove it and try another number
                sudoku[row][column] = 0;
            }
        }

        // If no number works for the cell, return false
        return false;
    }

    // Define a method to remove n cells from the Sudoku
    static void removeCells(int n) {
        // Repeat n times
        while (n > 0) {
            // Generate a random row and column
            int row = (int) (Math.random() * 9);
            int column = (int) (Math.random() * 9);

            // If the cell is not empty, remove the number and decrement k
            if (sudoku[row][column] != 0) {
                sudoku[row][column] = 0;
                n--;
            }
        }
    }

    // Define a method to display the Sudoku
    static void displaySudoku() {
        // Use loops and print statements to show the Sudoku
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0)
                System.out.println("-------------------------");

            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0)
                    System.out.print("| ");

                // Print a blank if the cell's value is 0, otherwise print the value
                System.out.print(sudoku[i][j] == 0 ? "  " : sudoku[i][j] + " ");
            }

            System.out.println("|");
        }

        System.out.println("-------------------------");
    }

    public static void main(String[] args) {
        // Create Scanner objects
        Scanner difficulty = new Scanner(System.in);
        Scanner cell = new Scanner(System.in);

        // Select Sudoku difficulty
        System.out.print("Enter the number of empty cells in the Sudoku: ");
        int emptyCells = difficulty.nextInt();

        // Validate the user's input
        while (emptyCells < 0 || emptyCells > 81) {
            System.out.print("Invalid input, please try again: ");
            emptyCells = difficulty.nextInt();
        }

        // Generate a Sudoku
        fillSudoku(0, 0);

        // Generate a solution for the Sudoku
        int[][] solution = new int[9][9];

        for (int i = 0; i < 9; i++)
            solution[i] = sudoku[i].clone();

        // Generate empty cells
        removeCells(emptyCells);

        // Display the Sudoku
        displaySudoku();

        // Use a loop to iterate over each cell
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // If the cell is empty, ask the user to enter a number
                if (sudoku[i][j] == 0) {
                    // Read the user's input as an integer
                    System.out.print("Enter a number for the cell (" + (i + 1) + ", " + (j + 1) + "): ");
                    int number = cell.nextInt();

                    // Validate the user's input
                    while (number < 1 || number > 9) {
                        System.out.print("Invalid input, please try again: ");
                        number = cell.nextInt();
                    }

                    // Place the number in the cell
                    sudoku[i][j] = number;

                    // Update and display the Sudoku
                    displaySudoku();
                }
            }
        }

        // Test if the user wins or not
        boolean test = true;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudoku[i][j] != solution[i][j]) {
                    test = false;
                    break;
                }
            }

            if (!test)
                break;
        }

        // Display the game’s win or lose result
        if (test)
            System.out.println("Congratulations! You have solved the Sudoku!");
        else
            System.out.println("You failed to solve the Sudoku!");

        // Keep the console open until the user presses enter
        System.out.print("Press enter to exit...");
        Scanner exit = new Scanner(System.in);
        exit.nextLine();

        // Close the Scanner objects
        difficulty.close();
        cell.close();
        exit.close();

        // Terminate the program
        System.exit(0);
    }
}