package converter;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            String choice = scanner.nextLine();
            if ("/from".equals(choice)) {
                System.out.println("Enter number in decimal system: ");
                int decimal = scanner.nextInt();
                System.out.println("Enter target base: ");
                int base = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Conversion result: " + convertDecimalToBase(decimal, base));
                System.out.println();
            } else if ("/to".equals(choice)) {
                System.out.println("Enter source number: ");
                String source = scanner.nextLine();
                System.out.println("Enter source base: ");
                int base = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Conversion to decimal result: " + convertBaseToDecimal(source, base));
                System.out.println();
            } else if ("/exit".equals(choice)) {
                break;
            } else {
                continue;
            }
        }
    }

    public static void printMenu() {
        System.out.println("Do you want to convert /from decimal or /to decimal? (To quit type /exit)");
    }

    public static String convertDecimalToBase(int decimal, int base) {
        StringBuilder sb = new StringBuilder();
        while (decimal > 0) {
            int reminder = decimal % base;
            if (base == 16 && reminder > 9) {
                sb.append((char) ('a' + reminder - 10));
            } else {
                sb.append(reminder);
            }
            decimal /= base;
        }
        return sb.reverse().toString();
    }

    public static int convertBaseToDecimal(String source, int base) {
        //return Integer.parseInt(source, base);
        String reversedSource = new StringBuilder(source).reverse().toString().toUpperCase();
        int decimal = 0;
        String digits = "0123456789ABCDEF";
        for (int i = 0; i < reversedSource.length(); i++) {
            char c = reversedSource.charAt(i);
            int d = digits.indexOf(c);

            decimal += (int) Math.pow(base, i) * d;
        }
        return decimal;
    }
}
