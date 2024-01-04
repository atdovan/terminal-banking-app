import java.util.Scanner;

public class InputUtility {
    public static int getValidIntInput() {
        Scanner in = new Scanner(System.in);
        boolean valid = false;
        int a = -1;
        while (!valid) {
            a = in.nextInt();
            valid = true;
            // O(n) time, updates: switch choices to HashSet for O(1) get value.
            if (!valid) {
                System.out.println("Please enter valid input:");
            }

        }
         
        return a;
    }

    public static int getValidMenuInput(int[] choices) {
        Scanner in = new Scanner(System.in);
        boolean valid = false;
        int a = -1;
        while (!valid) {
            a = in.nextInt();
            // O(n) time, updates: switch choices to HashSet for O(1) get value.
            for (int choice : choices) {
                if (a == choice) {
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                System.out.println("Please enter valid input:");
            }

        }
         
        return a;
    }

    public static float getValidFloatInput() {
        Scanner in = new Scanner(System.in);
        boolean valid = false;
        float a = 0.0f;
        while (!valid) {
            a = in.nextFloat();
            valid = true;

            // System.out.println("Please enter valid input:");
        }
         
        return a;
    }

    public static String getValidStrInput() {
        Scanner in = new Scanner(System.in);
        boolean valid = false;
        String a = "";
        while (!valid) {
            a = in.nextLine();
            valid = true;

            // System.out.println("Please enter valid input:");
        }
         
        return a;
    }
}
