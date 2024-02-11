# Sudoku
- A simple sudoku game without graphical user interface (GUI).
# Requirement
- Python 3.5 or higher
# How to Use
1. Download and extract the files.
2. Run the command prompt as an administrator.
3. Install PyInstaller using the following command:

   ```Bash
   pip install pyinstaller
   
4. In the command prompt, navigate to the folder in which the file sudoku.py is located. For example:

   ```Bash
   cd /users/user_name/Downloads/Sudoku-Main

5. Convert the file sudoku.py into an executable file using the following command:

   ```Bash
   pyinstaller --onefile --icon=icon.ico sudoku.py

6. The file sudoku.spec and the folders dist and build will be generated inside the folder Sudoku-Main.
7. The sudoku game application is located inside the folder dist.
8. You can copy and run the sudoku game application on any computer without Python installed.
