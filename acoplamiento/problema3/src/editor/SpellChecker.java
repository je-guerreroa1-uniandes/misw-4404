package editor;

import java.util.ArrayList;

public class SpellChecker implements ISpellChecker {

    @Override
    public ArrayList<String> check(String text) {
        // Aquí va tu lógica actual de verificación ortográfica
        String[] words = text.split("\\s+");
        ArrayList<String> errors = new ArrayList<>();
        String[] realWords = {"foo", "bar"}; // Ejemplo de palabras válidas

        for (String word : words) {
            boolean isValid = false;
            for (String realWord : realWords) {
                if (word.equalsIgnoreCase(realWord)) {
                    isValid = true;
                    break;
                }
            }
            if (!isValid) {
                errors.add(word);
            }
        }
        return errors;
    }
}
