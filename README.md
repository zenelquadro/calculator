Java Collections Calculator
Overview

This project is a console-based calculator that performs basic arithmetic operations (+, -, *, /, %) and includes additional functions like power, square root, absolute value, and rounding. The calculator supports both interactive mode (console-based input) and file processing mode (reading expressions from a file and saving results to another file).
Features

✔ Supports arithmetic operations: +, -, *, /, %
✔ Implements functions:

    power(base, exponent) → Exponentiation
    sqrt(number) → Square root
    abs(number) → Absolute value
    round(number) → Rounding to the nearest integer
    ✔ Handles invalid inputs and provides error messages
    ✔ Maintains a calculation history
    ✔ Two modes:
    Console Mode → Users input expressions manually
    File Mode → Reads expressions from input.txt and writes results to output.txt

Usage Instructions
Running the Calculator

Compile and run the Java file:

javac Calculator.java
java Calculator

The program will prompt the user to choose a mode:
1️⃣ Console Mode: Users enter expressions interactively.
2️⃣ File Mode: The program reads input expressions from input.txt and writes results to output.txt.
File Processing

    Create a text file input.txt with expressions:

3 + 5
10 / 2
power(2,3)
sqrt(16)
round(4.7)
abs(-8)
100 % 3

Run the program in file mode, and results will be saved in output.txt:

    3 + 5 = 8.0
    10 / 2 = 5.0
    power(2,3) = 8.0
    sqrt(16) = 4.0
    round(4.7) = 5.0
    abs(-8) = 8.0
    100 % 3 = 1.0

Implementation Details
Algorithms & Data Structures Used

    Expression Parsing: Uses stacks (Deque) to evaluate mathematical expressions with operator precedence.
    Function Handling: Uses regular expressions (Pattern and Matcher) to detect and extract function arguments.
    File Handling: Uses BufferedReader and BufferedWriter for reading input expressions and writing results.
    History Storage: Uses an ArrayList to store previous calculations.

Improvements Made

 Fixed function parsing using regex (previously, function extraction was incorrect).
 Added file input/output support to allow batch processing.
 Improved error handling for invalid inputs and division by zero.
Challenges Encountered

Parsing Mathematical Expressions

    Initially, the calculator did not handle expressions like "3 + 5 * 2" correctly due to missing operator precedence handling. This was fixed using stacks (Deque) for numbers and operators.

 Function Parsing Issues

    Early versions failed to properly extract arguments from functions like power(2,3). Using regular expressions solved this issue.

Handling Input from Files

    Implementing correct file reading and writing logic was necessary to allow batch processing.
