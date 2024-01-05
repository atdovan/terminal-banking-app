import java.io.File;

public class Main {
    public static void main(String[] args) {
        FileUtility.writeDefaultAccountCountFile();

        boolean exitLogin = false;
        boolean exitMain = false;
        int choice;
        int[] choices;
        Account user = new Account();

        clearConsole();
        System.out.println("############");
        System.out.println("# Andrew's #");
        System.out.println("#   Bank   #");
        System.out.println("############" + "\n");

        // operating through the login menu
        while (!exitLogin) {
            choices = printLoginMenu();
            choice = InputUtility.getValidMenuInput(choices);

            // main menu finished
            switch (choice) {
                case 1: {
                    clearConsole();
                    int userID = checkUserID();
                    String password = checkUserPassword(userID);
                    user = loginUser(userID, password);
                    exitLogin = true;
                    break;
                }
                case 2: {
                    user = createUser();
                    exitLogin = true;
                    break;
                }
                case 3: {
                    System.out.println("Case 3");
                    int userID = checkUserID();
                    System.out.println("Password: " + forgotPassword(userID) + "\n");
                    break;
                }
                case 4: {
                    exit();
                    break;
                }
                default: {
                    System.out.println(choice);
                    break;
                }
            }
        }

        clearConsole();

        while (!exitMain) {
            choices = printMainMenu(user);
            choice = InputUtility.getValidMenuInput(choices);
            switch (choice) {
                case 1: {
                    System.out.println("Current Balance: " + user.getBalance(user.userID));
                    break;
                }
                case 2: {
                    System.out.println(user.getTransactionList());
                    // gives user time to view list... (4 seconds)
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    break;
                }
                case 3: {
                    System.out.println("Enter amount to deposit");
                    float amount = InputUtility.getValidFloatInput();
                    user.depositFunds(amount);
                    break;
                }
                case 4: {
                    System.out.println("Enter amount to withdraw");
                    float amount = InputUtility.getValidFloatInput();
                    user.withdrawFunds(amount);
                    break;
                }
                case 5: {
                    System.out.println("Enter amount to send");
                    float amount = InputUtility.getValidFloatInput();
                    System.out.println("Enter userID to send to");
                    int benefiterID = InputUtility.getValidIntInput();
                    user.sendFunds(amount, benefiterID);
                    break;
                }
                case 6: {
                    clearConsole();
                    exit();
                    break;
                }
                default: {
                    // System.out.println(choice);
                    break;
                }
            }
        }
    }

    public static int[] printLoginMenu() {
        System.out.println("1. Access Account");
        System.out.println("2. Create Account");
        System.out.println("3. Recover Account");
        System.out.println("4. Exit Program");

        return new int[] { 1, 2, 3, 4 };
    }

    public static int[] printMainMenu(Account user) {
        try {
            Thread.sleep(1400);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
        clearConsole();
        System.out.println("Welcome, " + user.firstName + "\n");
        System.out.println("1. Get Balance");
        System.out.println("2. Get Transactions List");
        System.out.println("3. Deposit Funds");
        System.out.println("4. Withdraw Funds");
        System.out.println("5. Send Funds");
        System.out.println("6. Exit");

        return new int[] { 1, 2, 3, 4, 5, 6 };
    }

    public static int checkUserID() {
        int userID = 0;
        boolean validUserID = false;
        while (!validUserID) {
            System.out.println("Enter UserID: ");
            userID = InputUtility.getValidIntInput();
            validUserID = true;
            break;
            // System.out.println("Please enter a valid First Name");
        }
        // Check if userID exists.
        File myFile = new File(userID + ".txt");
        if (!myFile.exists()) {
            System.out.println("UserID is not registered");
            userID = checkUserID();
        }
        return userID;
    }

    public static String checkUserPassword(int userID) {
        String password = "";
        boolean validPassword = false;
        // UPDATE: int passwordAttemps = 5;
        String correctPass = FileUtility.readLineFromFile(userID + ".txt", 4);

        while (!validPassword) {
            System.out.println("Enter Password: ");
            password = InputUtility.getValidStrInput();
            if (password.length() > 0) {
                validPassword = true;
                break;
            }
            clearConsole();
            System.out.println("Please enter a valid Password");
        }
        // already know file exists so we can read..
        if (!password.equals(correctPass)) {
            System.out.println("Password does not match the UserID");
            System.out.println("Correct password: " + correctPass);
            password = checkUserPassword(userID);
        }
        return password;
    }

    public static String forgotPassword(int userID) {
        String password = FileUtility.readLineFromFile(userID + ".txt", 4);
        return password;

    }

    public static Account loginUser(int userID, String password) {
        return new Account(userID, password);
    }

    public static void exit() {
        System.out.println("exiting...");
        System.exit(0);
    }

    public final static void clearConsole() {
        System.out.print("\033c");
    }

    public static Account createUser() {
        String firstName = "";
        String lastName = "";
        String password = "";
        float balance = 0.0f;
        boolean validFirst = false;
        boolean validLast = false;
        boolean validPass = false;
        // boolean validBalance = false;

        // Clear console first:
        clearConsole();
        while (!validFirst) {
            System.out.println("Enter First Name: ");
            firstName = InputUtility.getValidStrInput();
            if (firstName.length() > 0) {
                validFirst = true;
                break;
            }
            System.out.println("Please enter a valid First Name");
        }
        clearConsole();

        while (!validLast) {
            System.out.println("Enter Last Name: ");
            lastName = InputUtility.getValidStrInput();
            if (lastName.length() > 0) {
                validLast = true;
                break;
            }
            System.out.println("Please enter a valid Last Name");
        }

        clearConsole();
        while (!validPass) {
            System.out.println("Enter Password: ");
            password = InputUtility.getValidStrInput();
            if (password.length() > 0) {
                validLast = true;
                break;
            }
            System.out.println("Please enter a valid Password");
        }

        clearConsole();
        System.out.println("Enter starting balance: ");
        balance = InputUtility.getValidFloatInput();

        clearConsole();
        return new Account(firstName, lastName, password, balance);
    }
}
