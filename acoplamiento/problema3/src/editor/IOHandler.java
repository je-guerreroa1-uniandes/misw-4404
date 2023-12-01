package editor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOHandler implements IIOHandler {
    @Override
    public String readInput() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void writeOutput(String output) {
        System.out.println(output);
    }
}
