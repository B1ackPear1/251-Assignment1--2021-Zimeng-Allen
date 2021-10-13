import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JPopupMenuFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.testng.testcase.AssertJSwingTestngTestCase;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.testng.annotations.BeforeClass;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.*;
import static org.assertj.swing.launcher.ApplicationLauncher.application;
import static org.assertj.swing.finder.WindowFinder.findFrame;

public class TextEditorTest extends AssertJSwingTestngTestCase{

    protected FrameFixture window;
    protected JPopupMenuFixture menu;
    protected JTextComponentFixture text;
    private final File TextOpen = new File("TextOpen");

    @Override
    protected void onSetUp() {
        TextEditor frame = GuiActionRunner.execute(TextEditor::new);
        window = new FrameFixture(robot(),frame);
        window.show();
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
        text.enterText("a,b,c,d,e,f,g,h,i,g,k,l,m,n");
        menu.menuItem("Search").click();
//        window.textBox("panel").requireText("a");
//        window.optionPane().requireMessage("a");

        window.textBox("jta").select("a");
           }

}