import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Main {
    private static final List<String> history = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Calculator!");

        while (true) {
            System.out.print("Choose mode: (1) Console (2) File : ");
            String mode = scanner.nextLine();

            if (mode.equals("2")) {
                processFile("input.txt", "output.txt");
                System.out.println("File processing complete. Results saved in output.txt.");
                break;
            }

            System.out.print("Please enter your arithmetic expression: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("history")) {
                showHistory();
                continue;
            }

            try {
                double result = evaluate(input);
                System.out.println("Result: " + result);
                history.add(input + " = " + result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.print("Do you want to continue? (y/n): ");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("n")) {
                break;
            }
        }

        System.out.println("Thank you for using the Calculator!");
        scanner.close();
    }

    private static void processFile(String inputFile, String outputFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    double result = evaluate(line);
                    writer.write(line + " = " + result);
                    writer.newLine();
                    history.add(line + " = " + result);
                } catch (Exception e) {
                    writer.write("Error evaluating: " + line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }

    private static double evaluate(String expression) throws Exception {
        expression = expression.replace(" ", "");

        if (expression.matches("power\\(.*\\)")) {
            return extractAndEvaluateFunction(expression, "power");
        } else if (expression.matches("sqrt\\(.*\\)")) {
            return extractAndEvaluateFunction(expression, "sqrt");
        } else if (expression.matches("abs\\(.*\\)")) {
            return extractAndEvaluateFunction(expression, "abs");
        } else if (expression.matches("round\\(.*\\)")) {
            return extractAndEvaluateFunction(expression, "round");
        } else {
            return evaluateExpression(expression);
        }
    }

    private static double extractAndEvaluateFunction(String expression, String function) throws Exception {
        Pattern pattern = Pattern.compile(function + "\\(([^)]+)\\)");
        Matcher matcher = pattern.matcher(expression);
        if (matcher.find()) {
            String[] args = matcher.group(1).split(",");
            switch (function) {
                case "power":
                    return Math.pow(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
                case "sqrt":
                    return Math.sqrt(Double.parseDouble(args[0]));
                case "abs":
                    return Math.abs(Double.parseDouble(args[0]));
                case "round":
                    return Math.round(Double.parseDouble(args[0]));
            }
        }
        throw new Exception("Invalid function syntax: " + expression);
    }

    private static double evaluateExpression(String expression) {
        Deque<Double> numbers = new ArrayDeque<>();
        Deque<Character> operations = new ArrayDeque<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
                StringBuilder num = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    num.append(expression.charAt(i++));
                }
                i--;
                numbers.push(Double.parseDouble(num.toString()));
            } else if (c == '(') {
                operations.push(c);
            } else if (c == ')') {
                while (!operations.isEmpty() && operations.peek() != '(') {
                    numbers.push(applyOperation(operations.pop(), numbers.pop(), numbers.pop()));
                }
                operations.pop();
            } else if (isOperator(c)) {
                while (!operations.isEmpty() && precedence(operations.peek()) >= precedence(c)) {
                    numbers.push(applyOperation(operations.pop(), numbers.pop(), numbers.pop()));
                }
                operations.push(c);
            }
        }

        while (!operations.isEmpty()) {
            numbers.push(applyOperation(operations.pop(), numbers.pop(), numbers.pop()));
        }

        return numbers.pop();
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
    }

    private static int precedence(char op) {
        return (op == '+' || op == '-') ? 1 : (op == '*' || op == '/' || op == '%') ? 2 : 0;
    }

    private static double applyOperation(char op, double b, double a) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return (b == 0) ? Double.POSITIVE_INFINITY : a / b;
            case '%': return a % b;
            default: return 0;
        }
    }

    private static void showHistory() {
        System.out.println("Calculation History:");
        for (String record : history) {
            System.out.println(record);
        }
    }
}
