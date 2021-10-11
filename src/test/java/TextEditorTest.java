import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.testng.testcase.AssertJSwingTestngTestCase;
import org.junit.After;
import org.junit.Before;
import org.testng.annotations.BeforeClass;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.*;
import static org.assertj.swing.launcher.ApplicationLauncher.application;
import static org.assertj.swing.finder.WindowFinder.findFrame;

public class TextEditorTest {

    private FrameFixture window;
    private final File TextOpen = new File("TextOpen");

    @BeforeClass
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @Before
    public void setUp() {
        TextEditor frame = GuiActionRunner.execute(TextEditor::new);
        window = new FrameFixture(frame);
        window.show(); // shows the frame to test
    }


    @org.junit.Test
    public void savelistening() {

    }

    @org.junit.Test
    public void open() {
        window.menuItem("Open").click();
        window.fileChooser().selectFile(TextOpen);
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(TextOpen));
            String s = null;
            while((s = br.readLine())!=null){
                result.append(System.lineSeparator()).append(s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        window.textBox().requireText(String.valueOf(result));
    }

        @org.junit.Test
    public void searchlistening() {
        window.textBox("jta").enterText("a,b,c,d,e,f,g,h,i,g,k,l,m,n");
        window.menuItem("Search").click();
//        window.textBox("panel").requireText("a");
        window.optionPane().requireMessage("a");
        window.textBox("jta").select("a");
    }

    @After
    public void tearDown() {
        window.cleanUp();
    }
}