package converter;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number in decimal system: ");
        int decimal = scanner.nextInt();
        System.out.println("Enter target base: ");
        int base = scanner.nextInt();
        System.out.println("Conversion result: " + Integer.toString(decimal, base));
    }

    public static String convertDecimalToBase(int decimal, int base) {
        StringBuilder sb = new StringBuilder();
        while (decimal > 0) {
            int reminder = decimal % base;
            if (base == 16 && reminder > 10) {
                sb.append((char) ('a' + reminder - 10));
            } else {
                sb.append(reminder);
            }
            decimal /= base;
        }
        return sb.reverse().toString();
    }
}
