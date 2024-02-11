#include <cstdlib>
#include <ctime>
#include <iostream>

using namespace std;

// Create a 9 x 9 array of integers to represent the Sudoku
int sudoku[9][9];

// Define a method to check if a number is safe for a cell
bool checkCell(int row, int column, int number)
{
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
bool fillSudoku(int row, int column)
{
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
        int random = rand() % 9 + 1;

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
void removeCells(int n)
{
    // Repeat n times
    while (n > 0) {
        // Generate a random row and column
        int row = rand() % 9;
        int column = rand() % 9;

        // If the cell is not empty, remove the number and decrement k
        if (sudoku[row][column] != 0) {
            sudoku[row][column] = 0;
            n--;
        }
    }
}

// Define a method to display the Sudoku
void displaySudoku()
{
    // Use loops and cout statements to show the Sudoku
    for (int i = 0; i < 9; i++) {
        if (i % 3 == 0)
            cout << "-------------------------" << endl;

        for (int j = 0; j < 9; j++) {
            if (j % 3 == 0)
                cout << "| ";

            // Print a blank if the cell's value is 0, otherwise print the value
            cout << (sudoku[i][j] == 0 ? "  " : to_string(sudoku[i][j]) + " ");
        }

        cout << "|" << endl;
    }

    cout << "-------------------------" << endl;
}

int main()
{
    int emptyCells, number;

    // Select Sudoku difficulty
    cout << "Enter the number of empty cells in the Sudoku: ";
    cin >> emptyCells;

    // Validate the user's input
    while (emptyCells < 0 || emptyCells > 81) {
        cout << "Invalid input, please try again: ";
        cin >> emptyCells;
    }

    // Generate a Sudoku
    fillSudoku(0, 0);

    // Generate a solution for the Sudoku
    int solution[9][9];

    for (int i = 0; i < 9; i++)
        for (int j = 0; j < 9; j++)
            solution[i][j] = sudoku[i][j];

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
                cout << "Enter a number for the cell (" << (i + 1) << ", " << (j + 1) << "): ";
                cin >> number;

                // Validate the user's input
                while (number < 1 || number > 9) {
                    cout << "Invalid input, please try again: ";
                    cin >> number;
                }

                // Place the number in the cell
                sudoku[i][j] = number;

                // Update and display the Sudoku
                displaySudoku();
            }
        }
    }

    // Test if the user wins or not
    bool test = true;

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
        cout << "Congratulations! You have solved the Sudoku!" << endl;
    else
        cout << "You failed to solve the Sudoku!" << endl;

    // Keep the console open until the user presses enter
    cout << "Press enter to exit...";
    cin.ignore();
    cin.get();

    // Terminate the program
    exit(0);
}