import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите свое математическое выражение:");
        String input = scanner.nextLine();
        try {
            String result = calc(input);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("генерирует исключение");
        }
    }

    public static String calc(String input) throws Exception {

        String[] operators = {"+", "-", "*", "/"};

        String[] parts = input.trim().split(" ");

        if (parts.length != 3) {
            throw new Exception("Неверный ввод");
        }
        String leftOperand = parts[0];
        String operator = parts[1];
        String rightOperand = parts[2];

        boolean validOperator = false;
        for (String op : operators) {
            if (operator.equals(op)) {
                validOperator = true;
                break;
            }
        }
        if (!validOperator) {
            throw new Exception("Недопустимый оператор");
        }

        boolean leftIsRoman = isRoman(leftOperand);
        boolean rightIsRoman = isRoman(rightOperand);
        if (leftIsRoman && rightIsRoman) {

            int leftNum = romanToArabic(leftOperand);
            int rightNum = romanToArabic(rightOperand);

            int result = performCalculation(leftNum, rightNum, operator);

            if (result < 1) {
                throw new Exception("Результат меньше единицы, что недопустимо для римских цифр");
            }
            return arabicToRoman(result);
        } else if (!leftIsRoman && !rightIsRoman) {

            int leftNum = Integer.parseInt(leftOperand);
            int rightNum = Integer.parseInt(rightOperand);

            int result = performCalculation(leftNum, rightNum, operator);
            return Integer.toString(result);
        } else {

            throw new Exception("Различные системы счисления не допускаются");
        }
    }

    private static boolean isRoman(String str) {
        // Roman numerals regex pattern
        return str.matches("[IVXLCDM]+");
    }

    private static int romanToArabic(String roman) throws Exception {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);

        int total = 0;
        int prevValue = 0;
        for (int i = roman.length() - 1; i >= 0; i--) {
            char currentChar = roman.charAt(i);
            int currentValue = romanMap.get(currentChar);
            if (currentValue < prevValue) {
                total -= currentValue;
            } else {
                total += currentValue;
            }
            prevValue = currentValue;
        }
        return total;
    }

    private static String arabicToRoman(int number) {

        int[] arabicNumerals = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanNumerals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder roman = new StringBuilder();
        int i = 0;
        while (number > 0) {
            if (number >= arabicNumerals[i]) {
                roman.append(romanNumerals[i]);
                number -= arabicNumerals[i];
            } else {
                i++;
            }
        }
        return roman.toString();
    }

    private static int performCalculation(int left, int right, String operator) throws Exception {
        switch (operator) {
            case "+":
                return left + right;
            case "-":
                return left - right;
            case "*":
                return left * right;
            case "/":
                if (right == 0) {
                    throw new Exception("Деление на ноль не допускается");
                }
                return left / right;
            default:
                throw new Exception("Неизвестный оператор");
        }
    }
}
