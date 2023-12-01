package editor;

import java.util.ArrayList;

public class ErrorDisplay implements IErrorDisplay {

    public void displayErrors(ArrayList<String> errors) {
        for (String error : errors) {
            System.out.println("ERROR: " + error);
        }
    }

    public void displaySuccessMessage() {
        System.out.println("No errors found!");
    }
}