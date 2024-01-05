import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.io.RandomAccessFile;

public class FileUtility {
    public static String readFile(String fileName) {
        String data = "";
        try {
            File myFile = new File(fileName);
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                data += myReader.nextLine();
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Could not find file or other error occured");
            e.printStackTrace();
        }
        return data;
    }

    public static String readFileRange(String file, int startLine, int endLine) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int currentLine = 0;
            String entirety = "";

            // Read lines until the specified line is reached
            while ((line = br.readLine()) != null && startLine <= endLine) {
                currentLine++;
                if (currentLine >= startLine && currentLine <= endLine) {
                    entirety += line;
                }
            }
            return entirety;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return null if the specified line is not found or an error occurs
        return null;
    }

    public static String readFileIndex(String file, int startLine) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int currentLine = 0;
            String entirety = "";

            // Read lines until the specified line is reached
            while ((line = br.readLine()) != null) {
                currentLine++;
                if (currentLine >= startLine) {
                    entirety += line;
                }
            }
            return entirety;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return null if the specified line is not found or an error occurs
        return null;
    }

    public static String readLineFromFile(String file, int lineNumber) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int currentLine = 0;

            // Read lines until the specified line is reached
            while ((line = br.readLine()) != null) {
                currentLine++;
                if (currentLine == lineNumber) {
                    return line;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return null if the specified line is not found or an error occurs
        return null;
    }

    public static void writeToEndOfFile(String file, String content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            // Append the new content to the end of the file
            bw.write(content);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to write to end of file");
        }
    }

    public static void writeNewUser(int userID, String firstName, String lastName, String password, float balance) {
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

    // used for keeping track of userID numbers
    public static void writeDefaultAccountCountFile() {
        String file = "accounts.txt";
        File myFile = new File(file);
        if (myFile.exists()) {
            return;
        } else {
            try (FileWriter fw = new FileWriter("accounts.txt")) {
                fw.write("10001\n");
                fw.close();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }

    public static boolean fileExists(String file) {
        File myFile = new File(file);
        if (myFile.exists()) { return true; }
        return false;
    }

    public static void changeLineInFile(String file, int targetLine, String update) {
        targetLine++;
        update += "\n";
        try (RandomAccessFile myFile = new RandomAccessFile(file, "rw")) {
            long position = 0;
            int currentLine = 1;

            while (currentLine < targetLine) {
                position = myFile.getFilePointer();
                String line = myFile.readLine();
                if (line == null) {
                    break;
                }
                currentLine++;
            }

            myFile.seek(position);
            myFile.writeBytes(update);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
