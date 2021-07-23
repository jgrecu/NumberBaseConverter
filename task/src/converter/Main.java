package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    // I know this is a small program and everything's contained in one class but members should usually be private.
    // Second thing - it's a good practice to make variables final if you're not going to to modify them.
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            printMenu();
            String choice = scanner.nextLine();
            String[] choices = choice.split(" ");
            if (choices.length > 1) {
                // I'd first verify that user input is actually a number, otherwise the program displays
                // NumberFormatException. In general - when dealing with user data you want to make sure that it is what
                // it's supposed to be. E.g. you could use a simple regex. Alternatively you can also catch the
                // NumberFormatException and print a nicer message to the user.
                //
                // Same goes for other places where you're reading input from user, e.g. verify that base is positive
                // and that it's not larger than what's supported by the program.
                final Pattern integerPattern = Pattern.compile("\\A\\d+\\z");
                if (!integerPattern.matcher(choices[0]).matches() || !integerPattern.matcher(choices[1]).matches()) {
                    // Handle incorrect input.
                }
                int srcBase = Integer.parseInt(choices[0]);
                int dstBase = Integer.parseInt(choices[1]);
                runConversion(srcBase, dstBase);
            } else if (choices.length == 1 && choices[0].equals("/exit")) {
                scanner.close();
                break;
            }
        }
    }

    public static void printMenu() {
        System.out.println("Enter two numbers in format: {source base} {target base} (To quit type /exit) ");
    }

    public static void runConversion(int src, int dst) {
        while (true) {
            System.out.println("Enter number in base " + src + " to convert to base " + dst + " (To go back type /back) ");
            String choice = scanner.nextLine();
            if ("/back".equals(choice)) {
                break;
            }

            // All cases are covered by the third one that's a generic conversion from base X to base Y.
            // This code is less optimal since for conversions from or to base 10 we're doing redundant operations,
            // however it's easier to understand because there's only 1 execution path instead of the previous 3.
            // Oftentimes for enterprise applications (especially for model-t) such micro-optimisations are not
            // bringing any meaningful performance benefits and it's better to focus on readability and
            // maintainability, which I would suggest here.

            // Convert the input to decimal, then convert from decimal to the desired base.
            final BigDecimal inputInDecimal = convertBaseToDecimal(choice, src);
            final String outputInTargetBase = convertDecimalToBase(inputInDecimal, dst);
            System.out.println("Conversion result: " + outputInTargetBase);
        }
    }

    public static String convertDecimalToBase(BigDecimal decimal, int base) {
        StringBuilder sb = new StringBuilder();
        String digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        BigInteger bigIntegerPart = decimal.abs().toBigInteger();
        double fractionalPart = decimal.subtract(new BigDecimal(bigIntegerPart)).doubleValue();

        String[] strings = decimal.toString().split("\\.");

        if (BigInteger.ZERO.equals(bigIntegerPart)) {
            sb.append("0");
        }

        while (!BigInteger.ZERO.equals(bigIntegerPart)) {
            // Nitpick: typo
            int remainder = Integer.parseInt(bigIntegerPart.mod(BigInteger.valueOf(base)).toString());
            char c = digits.charAt(remainder);
            // Nitpick: while c + "" is correct, it left me wondering for a moment why would you add an empty string
            // to the char. It's good to use a dedicated method or at least leave a comment.
            sb.append(Character.toString(c).toLowerCase());
            bigIntegerPart = bigIntegerPart.divide(BigInteger.valueOf(base));
        }

        if (strings.length == 1) {
            sb = sb.reverse();
        } else if (strings.length > 1) {
            sb = sb.reverse().append(".");
            for (int i = 0; i < 5; i++) {
                // Nitpick: typo
                int remainder = (int) (fractionalPart * base);
                char c = digits.charAt(remainder);
                sb.append((c + "").toLowerCase());
                fractionalPart = fractionalPart * base - remainder;
            }
        }
        return sb.toString();
    }

    public static BigDecimal convertBaseToDecimal(String source, int base) {
        String[] strings = source.split("\\.");
        String reversedSourceInt = new StringBuilder(strings[0]).reverse().toString().toUpperCase();

        // Nitpick: isn't this an integer?
        BigInteger integerPartOfResult = BigInteger.ZERO;
        String digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 0; i < reversedSourceInt.length(); i++) {
            char c = reversedSourceInt.charAt(i);
            int d = digits.indexOf(c);
            integerPartOfResult = integerPartOfResult.add(BigInteger.valueOf((long) Math.pow(base, i) * d));
        }

        BigDecimal total = new BigDecimal(integerPartOfResult);

        if (strings.length > 1) {
            String sourceFractional = strings[1].toUpperCase();
            BigDecimal bigDecimal = BigDecimal.ZERO;
            for (int i = 0; i < sourceFractional.length(); i++) {
                char c = sourceFractional.charAt(i);
                int d = digits.indexOf(c);
                bigDecimal = bigDecimal.add(BigDecimal.valueOf(Math.pow(base, -(i+1)) * d));
            }
            bigDecimal = bigDecimal.setScale(5, RoundingMode.HALF_UP);
            total = bigDecimal.add(total);
        }
        return total;
    }
}
