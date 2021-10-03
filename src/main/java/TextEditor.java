import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class TextEditor extends JFrame {
//    public boolean neworopen = true;
    static JFrame frame = new JFrame("Text Editor");
    private JMenuBar jmb = new JMenuBar();
    private JTextArea jta = new JTextArea();
    private JMenu filemenu = new JMenu("File");
    private JMenu searchmenu = new JMenu("Search");
    private JMenu viewmenu = new JMenu("View");
    private JMenu managemenu = new JMenu("Manage");
    private JMenu helpmenu = new JMenu("Help");
    private JMenuItem newitem = new JMenuItem("New");
    private JMenuItem openitem = new JMenuItem("Open");
    private JMenuItem saveitem = new JMenuItem("Save");
    private JMenuItem searchitem = new JMenuItem("Search");
    private JMenuItem exititem = new JMenuItem("Exit");
    private JMenuItem copyitem = new JMenuItem("Copy");
    private JMenuItem pasteitem = new JMenuItem("Paste");
    private JPopupMenu right = new JPopupMenu();
    private JFileChooser jfc = new JFileChooser();
    private JScrollPane scroll = new JScrollPane(jta);
    static FileNameExtensionFilter filter = new FileNameExtensionFilter("txt","文本文档");


    public static void main(String[] args) {
        TextEditor te = new TextEditor();
        te.function(te);
    }

    public void function(TextEditor textEditor){
        textEditor.window();
        textEditor.searchlistening();
        textEditor.newlistening();
        textEditor.openlistening();
        textEditor.exitlistening();
        textEditor.savelistening();
    }

    public void window() {
        //set the window of the editor
        frame.setVisible(true);
        jmb.add(filemenu);
        jmb.add(searchmenu);
        jmb.add(viewmenu);
        jmb.add(managemenu);
        jmb.add(helpmenu);
        filemenu.add(newitem);
        filemenu.add(openitem);
        searchmenu.add(searchitem);
        managemenu.add(saveitem);
        managemenu.add(exititem);
        frame.setJMenuBar(jmb);
        frame.setSize(500, 500);
//        frame.getContentPane().setBackground(Color.orange);
        right.add(copyitem);
        right.add(pasteitem);
        jta.setCaretPosition(jta.getText().length());
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.getContentPane().add(scroll);
    }

    public void news(){
        JOptionPane panel = new JOptionPane();
        panel.setBackground(Color.cyan);
        int n = JOptionPane.showConfirmDialog(frame, "Are you sure you want to create a new window?");
        if (n == 0){
            if (frame.getTitle().equals("Text Editor")){
                saveanother();
            }else {
                save();
            }
            jta.setText("");
            frame.setTitle("Text Editor");
        }
    }

    public void newlistening() {
//        create a new window
        newitem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                news();
            }
        });
    }

    public void save(){

    }

    public void saveanother() {

    }

    public void savelistening(){
        saveitem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                save();
            }
        });
    }

    public void open(){

    }

    public void openlistening(){
        openitem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                open();
            }
        });
    }
    public void searching(){

    }

    public void searchlistening() {
        searchitem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                searching();
            }
        });
    }

    public void exitlistening(){
        exititem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                frame.dispose();
                System.exit(0);
            }
        });
    }
}