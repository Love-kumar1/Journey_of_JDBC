package ch01_Exception_handling;

public class Nested_tryCatch_2 {
    public static void main(String[] args) {
//        Nested try and catch

        int arr[] = new int[7];

        try {
            int num = 10/0;
            try {
                arr[6] = 10;
            }
            catch (ArithmeticException e) {
                System.out.println(e.getMessage());
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

//        NOTE : the order of the catch blocks should always child to parent


    }
}
