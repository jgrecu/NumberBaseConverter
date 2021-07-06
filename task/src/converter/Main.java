package converter;

import java.math.BigInteger;
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
            // KISS!
            System.out.println("Conversion result: " + new BigInteger(choice, src).toString(dst));
//            if (src == 10) {
//                BigInteger decimal = new BigInteger(choice);
//                System.out.println("Conversion result: " + convertDecimalToBase(decimal, dst));
//                System.out.println();
//            } else if (dst == 10) {
//                System.out.println("Conversion result: " + convertBaseToDecimal(choice, src));
//            } else {
//                System.out.println("Conversion result: " + convertDecimalToBase(convertBaseToDecimal(choice, src), dst));
//            }
        }
    }

//    public static String convertDecimalToBase(BigInteger decimal, int base) {
//        StringBuilder sb = new StringBuilder();
//        String digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//        while (!BigInteger.ZERO.equals(decimal)) {
//            int reminder = Integer.parseInt(decimal.mod(BigInteger.valueOf(base)).toString());
//            char c = digits.charAt(reminder);
//            sb.append((c + "").toLowerCase());
//            decimal = decimal.divide(BigInteger.valueOf(base));
//        }
//        return sb.reverse().toString();
//    }

//    public static BigInteger convertBaseToDecimal(String source, int base) {
//        String reversedSource = new StringBuilder(source).reverse().toString().toUpperCase();
//        BigInteger decimal = BigInteger.ZERO;
//        String digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//        for (int i = 0; i < reversedSource.length(); i++) {
//            char c = reversedSource.charAt(i);
//            int d = digits.indexOf(c);
//            decimal = decimal.add(BigInteger.valueOf((long) Math.pow(base, i) * d));
//        }
//        return decimal;
//    }
}
