package ch01_Exception_handling;

import java.util.Scanner;

public class Handling_1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

//        System.out.print("Enter your divisor: ");
//        int divisor = input.nextInt();
//
//        System.out.print("Enter your dividend: ");
//        int dividend = input.nextInt();
//
//        try {
//            int result = dividend/divisor;
//            System.out.println("your end result is: "+result);
//        }
//        catch (ArithmeticException e) {
//            System.out.println("The diviser can't be zero");
//        }


        int arr[] = new int[5];

        try {
            arr[6] = 7;
        }
        catch (ArrayIndexOutOfBoundsException | ArithmeticException e) {
            System.out.println(e.getMessage());
        }

        input.close();



    }
}
