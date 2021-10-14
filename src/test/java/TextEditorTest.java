import org.apache.commons.io.FileUtils;
import org.junit.Test;

import javax.swing.*;

import java.awt.*;
import java.io.*;

import static org.junit.Assert.*;

public class TextEditorTest {

    @Test
    public void savelistening() throws IOException {
        JTextArea jta = new JTextArea();
        File file = new File("text");
        jta.setText("123");
        BufferedWriter bf = new BufferedWriter(new FileWriter(file));
        bf.write("123");
        bf.flush();
        bf.close();
        BufferedReader bf1 = new BufferedReader(new FileReader(file));
        String string = bf1.readLine();
        bf1.close();
        assertEquals("123",string);
    }

    @Test
    public void openlistening() throws IOException {
        File file = new File("text");
        BufferedReader bf1 = new BufferedReader(new FileReader(file));
        String string = bf1.readLine();
        bf1.close();
        assertEquals("123",string);
    }

    @Test
    public void searchlistening() {
        JTextArea jta = new JTextArea();
        jta.setText("123");
        String inputV = "3";
        int length = inputV.length();
        int a = jta.getText().indexOf(inputV);
        jta.setSelectionStart(a);
        jta.setSelectionEnd(a + length);
        String select = jta.getSelectedText();
        assertEquals("3",select);
    }
}