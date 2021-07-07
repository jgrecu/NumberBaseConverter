package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            printMenu();
            String choice = scanner.nextLine();
            String[] choices = choice.split(" ");
            if (choices.length > 1) {
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

            if (src == 10) {
                BigDecimal decimal = new BigDecimal(choice);
                System.out.println("Conversion result: " + convertDecimalToBase(decimal, dst));
                System.out.println();
            } else if (dst == 10) {
                System.out.println("Conversion result: " + convertBaseToDecimal(choice, src));
            } else {
                 System.out.println("Conversion result: " + convertDecimalToBase(convertBaseToDecimal(choice, src), dst));
            }
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
            int reminder = Integer.parseInt(bigIntegerPart.mod(BigInteger.valueOf(base)).toString());
            char c = digits.charAt(reminder);
            sb.append((c + "").toLowerCase());
            bigIntegerPart = bigIntegerPart.divide(BigInteger.valueOf(base));
        }

        if (strings.length == 1) {
            sb = sb.reverse();
        } else if (strings.length > 1) {
            sb = sb.reverse().append(".");
            for (int i = 0; i < 5; i++) {
                int reminder = (int) (fractionalPart * base);
                char c = digits.charAt(reminder);
                sb.append((c + "").toLowerCase());
                fractionalPart = fractionalPart * base - reminder;
            }
        }
        return sb.toString();
    }

    public static BigDecimal convertBaseToDecimal(String source, int base) {
        String[] strings = source.split("\\.");
        String reversedSourceInt = new StringBuilder(strings[0]).reverse().toString().toUpperCase();

        BigInteger decimal = BigInteger.ZERO;
        String digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        
        for (int i = 0; i < reversedSourceInt.length(); i++) {
            char c = reversedSourceInt.charAt(i);
            int d = digits.indexOf(c);
            decimal = decimal.add(BigInteger.valueOf((long) Math.pow(base, i) * d));
        }

        BigDecimal total = new BigDecimal(decimal);

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
