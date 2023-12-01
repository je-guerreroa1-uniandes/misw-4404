package editor;

import java.util.ArrayList;

public interface IErrorDisplay {
        void displayErrors(ArrayList<String> errors);

        void displaySuccessMessage();
}
