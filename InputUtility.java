import java.util.Scanner;

public class InputUtility {
    public static int getValidIntInput() {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();

        try {
            int x = Integer.parseInt(input);
            return x;
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid input");
            return getValidIntInput();
        }
    }

    public static int getValidMenuInput(int[] choices) {
        int x = getValidIntInput();
        for (int choice : choices) {
            if (x == choice) {
                return x;
            } 
        }
        return getValidMenuInput(choices);
    }

    public static String getValidStrInput() {
        Scanner in = new Scanner(System.in);
        String x = in.nextLine();
        return x;
    }

    public static float getValidFloatInput() {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();

        try {
            float x = Float.parseFloat(input);
            return x;
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid input");
            return getValidFloatInput();
        }
    }

}
