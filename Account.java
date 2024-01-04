
import java.io.FileWriter;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

public class Account {
    // keeps list of account numbers
    static int accountID = 10000;

    int userID;
    String firstName;
    String lastName;
    String password;
    float balance;

    public Account(String firstName, String lastName, String password, float balance) {
        this.userID = accountID+1;
        accountID++;
        
        this.firstName = firstName; 
        this.lastName = lastName;
        this.password = password;
        this.balance = balance;

        try (FileWriter fw = new FileWriter(userID + ".txt")) {
            fw.write(userID + "\n");
            fw.write(firstName + "\n");
            fw.write(lastName + "\n");
            fw.write(password + "\n");
            fw.write(balance + "\n");
            fw.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public Account(int userID, String password) {
        this.userID = userID;
        this.password = password;
        
        // must read from file.
        this.firstName = FileUtility.readLineFromFile(userID+".txt", 2);
        this.lastName = FileUtility.readLineFromFile(userID+".txt", 3);
        this.balance = Float.parseFloat(FileUtility.readLineFromFile(userID+".txt", 5));
    }

    public boolean userExists(int userID) {
        return false;
    }

    public String getTransactionList() {
        String transactionList = FileUtility.readFileIndex(userID + ".txt", 6);
        return transactionList;
    }
 
    public void depositFunds(float amount) {
        // For time stamping the transaction.
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  

        // Updating the balance from file
        try {
            this.balance = Float.parseFloat(FileUtility.readLineFromFile(this.userID+".txt", 5));
        } catch(Exception e) {
            System.out.println("Could not grab correct balance");
            balance = 0.0f;
        }
        System.out.println("Before Deposit: " + this.balance);
        this.balance += amount;
        System.out.println("After Deposit: " + this.balance);

         FileUtility.writeToEndOfFile(this.userID+".txt", "Deposit: " + amount + " Time: " + dtf.format(now));
    
    }

    public void withdrawFunds(float amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
        }
    }

    public float getBalance() {
        return this.balance;
    }

    public void sendFunds(float amount, int benefiterID) {
        if (amount < 0) {return;}
    }

}

