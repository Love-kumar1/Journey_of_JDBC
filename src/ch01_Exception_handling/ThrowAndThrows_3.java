package ch01_Exception_handling;

import java.util.Scanner;

public class ThrowAndThrows_3 {

    public static void divide(int dividend, int divisor) throws ArithmeticException {
        System.out.println(dividend/divisor);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int age = input.nextInt();

        if (age < 18) {
            throw new RuntimeException("you can not vote");
        }
        else {
            System.out.println("not eligible");
        }
    }
}
