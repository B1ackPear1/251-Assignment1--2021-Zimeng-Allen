//import com.sun.jdi.event.StepEvent;
import org.apache.commons.io.FileUtils;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.Sides;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class TextEditor extends JFrame {
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
    private JMenuItem cutitem = new JMenuItem("cut");
    private JMenuItem selectitem = new JMenuItem("Select");
    private JMenuItem aboutitem = new JMenuItem("About");
    private JMenuItem printitem = new JMenuItem("Print");
    private JPopupMenu right = new JPopupMenu();
    private JFileChooser jfc = new JFileChooser();
    private JScrollPane scroll = new JScrollPane(jta);
    private JLabel time = new JLabel("Time");
    private Clipboard clipboard;
    static FileNameExtensionFilter filter1 = new FileNameExtensionFilter("java","java");
    static FileNameExtensionFilter filter2 = new FileNameExtensionFilter("python","py");
    static FileNameExtensionFilter filter3 = new FileNameExtensionFilter("c","c");
    static FileNameExtensionFilter filter4 = new FileNameExtensionFilter("txt","txt");
    static FileNameExtensionFilter filter5 = new FileNameExtensionFilter("rtf","rtf");
    static FileNameExtensionFilter filter6 = new FileNameExtensionFilter("odt","odt");

    public static void main(String[] args) {
        TextEditor te = new TextEditor();
        te.function(te);
        te.timeAndDate();
        File f = new File(te.getClass().getResource("").getPath());
        System.out.println(f);
    }

    public void function(TextEditor textEditor){
        textEditor.window();
        textEditor.rightlistening();
        textEditor.searchlistening();
        textEditor.newlistening();
        textEditor.openlistening();
        textEditor.exitlistening();
        textEditor.savelistening();
        textEditor.aboutlistening();
        textEditor.printlistening();
    }

    public void window() {
        //set the window of the editor
        frame.setVisible(true);
        jmb.add(filemenu);
        jmb.add(searchmenu);
        jmb.add(viewmenu);
        jmb.add(managemenu);
        jmb.add(helpmenu);
        jmb.add(time);
        filemenu.add(newitem);
        filemenu.add(openitem);
        filemenu.add(saveitem);
        filemenu.add(printitem);
        searchmenu.add(searchitem);
        managemenu.add(exititem);
        helpmenu.add(aboutitem);
        frame.setJMenuBar(jmb);
        frame.setSize(500, 500);
//        frame.getContentPane().setBackground(Color.orange);
        right.add(copyitem);
        right.add(pasteitem);
        right.add(cutitem);
        right.add(selectitem);
        jta.setCaretPosition(jta.getText().length());
        jta.setLineWrap(true);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.getContentPane().add(scroll);
    }

    public void rightlistening(){
        jta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (e.isPopupTrigger()){
                    right.show(frame,e.getX(),e.getY());
//                    if (jta.getSelectionEnd() - jta.getSelectionStart() > 0)
                    if (jta.isFocusable()){
                        copylistening();
                        pastelistening();
                        cutlistening();
                        selectalllistening();
                    }}
            }
        });
    }

    public void copylistening(){
        copyitem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                jta.copy();
            }
        });
    }

    public void pastelistening(){
        pasteitem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                Transferable text = new StringSelection(jta.getSelectedText());
                jta.paste();
                clipboard.setContents(text,null);
            }
        });
    }

    public void cutlistening(){
        cutitem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                jta.cut();
            }
        });
    }

    public void selectalllistening(){
        selectitem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                jta.selectAll();
            }
        });
    }

    public void news() throws IOException {
        JOptionPane panel = new JOptionPane();
        panel.setBackground(Color.cyan);
        int n = JOptionPane.showConfirmDialog(frame, "Are you sure you want to create a new window?");
        if (n == 0){
            saveanother();
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
    public String name_save;
    public void saveanother() throws IOException {
        if (frame.getTitle().equals("Text Editor")){
            JFileChooser jfc1 = new JFileChooser();
//            jfc1.setFileFilter(filter);
//            jfc1.addChoosableFileFilter(filter);
//            jfc1.setFileFilter(filter1);
//            jfc1.setFileFilter(filter2);
//            jfc1.setFileFilter(filter3);
//            jfc1.setFileFilter(filter4);
//            jfc1.setFileFilter(filter);
//            String name;
            try{
                if (jfc1.showSaveDialog(frame)==JFileChooser.APPROVE_OPTION){
                    File f = jfc1.getSelectedFile();
                    if(jfc1.getSelectedFile()!=null){
                        name_save = f.getAbsolutePath() + ".txt";
//                        name = f.getAbsolutePath();
                    }else{
                        name_save = "assignment";
                    }
//                String fullpath = path + "\\" + name;
//                File file = new File(name,fullpath);
//                if (!file.exists()){
//                    file.createNewFile();
//                }
                    BufferedWriter bf = new BufferedWriter(new FileWriter(name_save));
                    bf.write(jta.getText());
                    bf.flush();
                    bf.close();
                }
            }catch (Exception ex){
                JOptionPane.showMessageDialog(frame,ex.getMessage());
            }
        }else {
            String fullpath = frame.getTitle();
            FileUtils.write(new File(fullpath),jta.getText(),"utf-8",false);
        }

    }

    public void savelistening(){
        saveitem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    saveanother();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    public String name_open;
    public void open(){
//        jfc.setFileFilter(filter1);
//        jfc.setFileFilter(filter2);
//        jfc.setFileFilter(filter3);
//        jfc.setFileFilter(filter4);
//        jfc.setFileFilter(filter5);
//        jfc.setFileFilter(filter6);
        try {
            if (jfc.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
                return;
            }
            name_open = jfc.getSelectedFile().getAbsolutePath();
            String framename = jfc.getSelectedFile().getName();
            BufferedReader bf = new BufferedReader(new FileReader(name_open));
            String string = bf.readLine();
            String content = "";
            byte[] b = new byte[(int) name_open.length()];
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
//            jta.requestFocusInWindow();
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

    public void OpenPrinter(File file, String printerName) throws PrinterException {
        if (file == null) {
//            System.err.println("Not find the file");
            JOptionPane.showMessageDialog(frame,"Not find the file");
        }
        InputStream fis = null;
        try {
            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
            aset.add(new Copies(1));
            aset.add(Sides.DUPLEX);
            PrintService printService = null;
            if (printerName != null) {
                PrintService[] printServices = PrinterJob.lookupPrintServices();
                if (printServices == null || printServices.length == 0) {
//                    System.out.print("print failure. No available printer");
                    JOptionPane.showMessageDialog(frame,"print failure. No available printer");
                    return;
                }
                for (int i = 0; i < printServices.length; i++) {
                    System.out.println(printServices[i].getName());
                    if (printServices[i].getName().contains(printerName)) {
                        printService = printServices[i];
                        break;
                    }
                }
                if (printService == null) {
//                    System.out.print("print failure, not find " + printerName);
                    JOptionPane.showMessageDialog(frame,"print failure, not find " + printerName);
                    return;
                }
            }
            fis = new FileInputStream(file);
            Doc doc = new SimpleDoc(fis, flavor, null);
            DocPrintJob job = printService.createPrintJob();
            job.print(doc, aset);
        } catch (FileNotFoundException | PrintException e1) {
            JOptionPane.showMessageDialog(frame,e1.getMessage());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(frame,e.getMessage());
                }
            }
        }
    }

    public void printlistening(){
        final String[] name = new String[1];
        printitem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                try {
                    saveanother();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                String printerName = "HP MFP M436 PCL6";
                if (frame.getTitle().equals("Text Editor")){
                    name[0] = name_save;
                }else {
                    name[0] = name_open;
                }
                try {
                    OpenPrinter(new File(name[0]), printerName);
                } catch (PrinterException printerException) {
                    printerException.printStackTrace();
                }
            }
        });
    }
    public void timeAndDate(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                long timemillis = System.currentTimeMillis();
                SimpleDateFormat df = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                time.setText(df.format(new Date(timemillis)));
            }
        };
        timer.schedule(task, 1000, 1000);
    }
}