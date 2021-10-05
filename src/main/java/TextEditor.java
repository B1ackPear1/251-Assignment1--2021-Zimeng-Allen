//import com.sun.jdi.event.StepEvent;
import org.apache.commons.io.FileUtils;

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
    private JMenuItem aboutitem = new JMenuItem("About");
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
        textEditor.aboutlistening();
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
        filemenu.add(saveitem);
        searchmenu.add(searchitem);
        managemenu.add(exititem);
        helpmenu.add(aboutitem);
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

    public void news() throws IOException {
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
                try {
                    news();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void save() throws IOException {
        if (frame.getTitle().equals("Text Editor")){
            saveanother();
        }else {
            String fullpath = frame.getTitle();
            FileUtils.write(new File(fullpath),jta.getText(),"utf-8",false);
        }
    }

    public void saveanother() {
        JFileChooser jfc1 = new JFileChooser();
//        jfc1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        String name;
        try{
//            jfc1.addChoosableFileFilter(new FileNameExtensionFilter("txt","文本文档"));
            if (jfc1.showSaveDialog(frame)==JFileChooser.APPROVE_OPTION){
                String path = jfc1.getCurrentDirectory().getAbsolutePath();
//                        if (!dir.exists()){
//                            dir.getParentFile().mkdirs();
//                        }
//                        dir.createNewFile();
                File f = jfc1.getSelectedFile();
                if(jfc1.getSelectedFile()!=null){
//                    name = f.getName();
                    name = f.getAbsolutePath() + ".txt";
//                    name = jfc1.getSelectedFile().getName();
                }else{
                    name = "assignment";
                }
//                String fullpath = path + "\\" + name;
//                File file = new File(name,fullpath);
//                if (!file.exists()){
//                    file.createNewFile();
//                }
                BufferedWriter bf = new BufferedWriter(new FileWriter(name));
                bf.write(jta.getText());
                bf.flush();
                bf.close();
            }
        }catch (Exception ex){
            JOptionPane.showMessageDialog(frame,ex.getMessage());
        }

    }

    public void savelistening(){
        saveitem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    save();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void open(){
        try {
            if (jfc.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
                return;
            }
            String FileName = jfc.getSelectedFile().getAbsolutePath();
            String framename = jfc.getSelectedFile().getName();
            BufferedReader bf = new BufferedReader(new FileReader(FileName));
            String string = bf.readLine();
            String content = "";
            while (string != null) {
                content += string;
                content += "\n";
                string = bf.readLine();
            }
            bf.close();
            frame.setTitle(framename);
            jta.setText(content);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage());
        }
    }

    public void openlistening(){
        openitem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                open();
            }
        });
    }
    class searching extends JDialog {
        //a new searching window
        public searching() {
//            setVisible(true);
//            setBounds(100,100,250,100);
//            Container container = getContentPane();
//            container.setBackground(Color.cyan);
//            JTextField jTextField = new JTextField();
//            container.add(jTextField);
//            container.add(new JLabel("Please input your searching word:"));
            JOptionPane panel = new JOptionPane();
            panel.setBackground(Color.cyan);
            String inputV = JOptionPane.showInputDialog("Please input a word ");
            int length = inputV.length();
            int a = jta.getText().indexOf(inputV);
            jta.requestFocus();
            jta.setSelectionStart(a);
            jta.setSelectionEnd(a + length);

        }
    }

    public void searchlistening() {
        searchitem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                new searching();
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

    public void about(){
        String author = "This editor is written by Zimeng Fu and Allen Wang";
        JOptionPane aboutt = new JOptionPane();
        aboutt.setBackground(Color.cyan);
        aboutt.showMessageDialog(frame, author,"Author",1);
    }

    public void aboutlistening(){
        aboutitem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                about();
            }
        });
    }
}