package editor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Editor {

	private final IIOHandler ioHandler;
	private final ISpellChecker spellChecker;
	private final IErrorDisplay errorDisplay;

	public Editor() {
		this.spellChecker = new SpellChecker();
		this.errorDisplay = new ErrorDisplay();
		this.ioHandler = new IOHandler();
	}
	
	public void runEditor() {
		System.out.println("Running editor...");
		System.out.println("Enter text:");
		String text = ioHandler.readInput();

		ArrayList<String> errors = spellChecker.check(text);
		if (!errors.isEmpty()) {
			errorDisplay.displayErrors(errors);
		} else {
			errorDisplay.displaySuccessMessage();
		}
	}
	
	public static void main(String[] args) {
		Editor editor = new Editor();
		editor.runEditor();
	}
}
