import sys
import random

# Create a 9 x 9 array of integers to represent the Sudoku
sudoku = [[0 for _ in range(9)] for _ in range(9)]


def checkCell(row, column, number):
    # Define a method to check if a number is safe for a cell

    # Check the row for any conflicts
    for i in range(9):
        if sudoku[row][i] == number:
            return False

    # Check the column for any conflicts
    for i in range(9):
        if sudoku[i][column] == number:
            return False

    # Check the 3 x 3 sub-grid for any conflicts
    rowStart = row - row % 3
    colStart = column - column % 3

    for i in range(rowStart, rowStart + 3):
        for j in range(colStart, colStart + 3):
            if sudoku[i][j] == number:
                return False

    # If no conflicts, return true
    return True


def fillSudoku(row, column):
    # Define a method to fill the Sudoku with random numbers

    # If the Sudoku is full, return true
    if row == 9:
        return True

    # If the current cell is not empty, move to the next cell
    if sudoku[row][column] != 0:
        if column == 8:
            return fillSudoku(row + 1, 0)
        else:
            return fillSudoku(row, column + 1)

    # Try each possible number from 1 to 9
    for _ in range(1, 10):
        # Generate a random number
        random_num = random.randint(1, 9)

        # Check if the number is safe for the cell
        if checkCell(row, column, random_num):
            # Place the number in the cell
            sudoku[row][column] = random_num

            # Move to the next cell
            if column == 8:
                if fillSudoku(row + 1, 0):
                    return True
            else:
                if fillSudoku(row, column + 1):
                    return True

            # If the number leads to an invalid state, remove it and try another number
            sudoku[row][column] = 0

    # If no number works for the cell, return false
    return False


def removeCells(n):
    # Define a method to remove n cells from the Sudoku

    # Repeat n times
    while n > 0:
        # Generate a random row and column
        row = random.randint(0, 8)
        column = random.randint(0, 8)

        # If the cell is not empty, remove the number and decrement k
        if sudoku[row][column] != 0:
            sudoku[row][column] = 0
            n -= 1


def displaySudoku():
    # Define a method to display the Sudoku

    # Use loops and print statements to show the Sudoku
    for i in range(9):
        if i % 3 == 0:
            print("-------------------------")

        for j in range(9):
            if j % 3 == 0:
                print("| ", end="")

            # Print a blank if the cell's value is 0, otherwise print the value
            print(sudoku[i][j] if sudoku[i][j] != 0 else " ", end=" ")

        print("|")

    print("-------------------------")


# Select Sudoku difficulty
emptyCells = int(input("Enter the number of empty cells in the Sudoku: "))

# Validate the user's input
while emptyCells < 0 or emptyCells > 81:
    print("Invalid input, please try again: ")
    emptyCells = int(input())

# Generate a Sudoku
fillSudoku(0, 0)

# Generate a solution for the Sudoku
solution = [row[:] for row in sudoku]

# Generate empty cells
removeCells(emptyCells)

# Display the Sudoku
displaySudoku()

# Use a loop to iterate over each cell
for i in range(9):
    for j in range(9):
        # If the cell is empty, ask the user to enter a number
        if sudoku[i][j] == 0:
            # Read the user's input as an integer
            number = int(
                input(f"Enter a number for the cell ({i + 1}, {j + 1}): "))

            # Validate the user's input
            while number < 1 or number > 9:
                print("Invalid input, please try again: ")
                number = int(input())

            # Place the number in the cell
            sudoku[i][j] = number

            # Update and display the Sudoku
            displaySudoku()

# Test if the user wins or not
test = True

for i in range(9):
    for j in range(9):
        if sudoku[i][j] != solution[i][j]:
            test = False
            break

    if not test:
        break

# Display the game’s win or lose result
if test:
    print("Congratulations! You have solved the Sudoku!")
else:
    print("You failed to solve the Sudoku!")

# Keep the console open until the user presses enter
input("Press enter to exit...")

# Terminate the program
sys.exit(0)
