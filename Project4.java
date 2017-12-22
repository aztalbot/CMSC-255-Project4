/**
 * PROJECT 4
 * Self-Organizing Lists
 * Name: Andrew Talbot
 * Class: CMSC 256 - Sec 901
 * Semester: Fall 2017
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Project4 {

    private static SelfOrganizingList<String> organizedList = new SelfOrganizingList<>();

    public static void main(String[] args) {

        printHeading();

        String originalFile, searchList;

        switch(args.length) {
            case 0:
                originalFile = promptForFileName(true, true);
                readFile(originalFile, true);
                searchList = promptForFileName(true, false);
                readFile(searchList, false);
                break;
            case 1:
                originalFile = args[0];
                readFile(originalFile, true);
                searchList = promptForFileName(true, false);
                readFile(searchList, false);
                break;
            default: // 2 or greater
                originalFile = args[0];
                readFile(originalFile, true);
                searchList = args[1];
                readFile(searchList, false);
                break;
        }

        // Print original list, now sorted after accesses
        System.out.println(organizedList.toString());
    }

    private static void readFile(String path, boolean mode) {
        // create instance of file class and check for file path's existence
        try {
            Scanner input = openFile(path, mode);
            String line;

            // while data remains in the file
            while (input.hasNext()) {
                line = input.nextLine().trim();
                if(mode)
                    organizedList.add(line);
                else
                    organizedList.contains(line);
            }

            input.close(); // close scanner
        } catch (FileNotFoundException noFile) {
            System.out.println("The program was unable to load the file. Please restart.");
        }
    }

    /**
     * Gets a file path from the keyboard (alternative prompts depending on entry point)
     * @return
     *      String
     */
    private static String promptForFileName(boolean firstPrompt, boolean mode) {
        String alternatePrompt = (firstPrompt) ? ": " : " (\":q\" to quit): ";
        String contents = (mode) ? "the original list" : "the search terms";
        System.out.println("Enter the full path to the file containing " + contents + alternatePrompt);
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

    /**
     * Attempts to open the file, prompts for a different file if none exists.
     * User has the option to quit the program.
     * @return
     *      Scanner
     */
    private static Scanner openFile(String pathName, boolean mode) throws FileNotFoundException {
        File file = new File(pathName);
        while (!file.exists()) {
            System.out.println("You didn't input a valid file name. Try again!");
            file = new File(promptForFileName(false, mode));
            if(file.getName().equals(":q")) System.exit(0);
        }
        // System.out.println("Showing songs from " + file.getName() + "\n");
        return new Scanner(file);
    }

    /**
     * Prints name, project number, course identifier and current semester to console.
     */
    private static void printHeading() {

        String name, semester, projectNumber, courseID;

        name = "Andrew Talbot";
        projectNumber = "Project #4";
        courseID = "CMSC 256, Section 901";
        semester = "Fall 2017";

        String[] data = {"****** HEADING ******", name, projectNumber, courseID, semester, "====================="};

        System.out.println(String.join("\n", data));
    }
}
