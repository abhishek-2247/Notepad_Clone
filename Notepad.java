import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


public class Notepad extends JFrame implements ActionListener {
    JMenuItem neww;
    JTextArea textArea = new JTextArea();
    boolean isFirst = true;
    boolean alreadySaved = false;

    Notepad() {
        JMenuBar mb = new JMenuBar();

        mb.setBackground(Color.CYAN);
        Font f1 = new Font("monospaced", Font.BOLD, 16);

        // belpow is file menu
        JMenu file = new JMenu("FILE     ");
        file.setMnemonic('F');
        mb.add(file);

        neww = new JMenuItem("New");
        neww.setFont(f1);
        file.add(neww);
        file.addSeparator();
        neww.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        neww.addActionListener(this);

        JMenuItem open = new JMenuItem("Open");
        open.setFont(f1);
        file.add(open);
        file.addSeparator();
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        open.addActionListener(this);

        JMenuItem save = new JMenuItem("Save");
        save.setFont(f1);
        file.add(save);
        file.addSeparator();
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        save.addActionListener(this);

        JMenuItem print = new JMenuItem("Print");
        print.setFont(f1);
        file.add(print);
        file.addSeparator();
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        print.addActionListener(this);

        JMenuItem exit = new JMenuItem("Exit");
        exit.setFont(f1);
        file.add(exit);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, ActionEvent.CTRL_MASK));
        exit.addActionListener(this);

        Font f2 = new Font("Segoe UI", Font.BOLD, 16);
        file.setFont(f2);
        setJMenuBar(mb);

        // bewlow is edit menu
        JMenu edit = new JMenu("Edit       ");
        edit.setFont(f2);
        mb.add(edit);
        edit.setMnemonic('E');

        JMenuItem cut = new JMenuItem("Cut");
        cut.setFont(f1);
        edit.add(cut);
        edit.addSeparator();
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        cut.addActionListener(this);

        JMenuItem copy = new JMenuItem("Copy");
        copy.setFont(f1);
        edit.add(copy);
        edit.addSeparator();
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        copy.addActionListener(this);

        JMenuItem paste = new JMenuItem("Paste");
        paste.setFont(f1);
        edit.add(paste);
        edit.addSeparator();
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        paste.addActionListener(this);

        JMenuItem selectall = new JMenuItem("Select All");
        selectall.setFont(f1);
        edit.add(selectall);
        edit.addSeparator();
        selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        selectall.addActionListener(this);

        // Below is view menu
        JMenu view = new JMenu("View   ");
        view.setMnemonic('V');
        view.setFont(f2);
        mb.add(view);

        JMenuItem help = new JMenuItem("About");
        help.setFont(f1);
        view.add(help);
        help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        help.addActionListener(this);

        // set image icon
        try {
            Image i = ImageIO.read(new File("./images/edit_icon.png"));
            setIconImage(i);
        } catch (Exception as) {
            System.out.println(as.getMessage());
        }

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("monospaced", Font.PLAIN, 16));

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.GRAY);
        setTitle("ABHIS NOTEPAD");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Notepad();
    }


    String text = "";
    File opened = null;
    boolean isopen = false;
    About abb = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        //new page
        if (command.equals("New")) {
            alreadySaved = false;
            isopen = true;
            textArea.setText("");

            if (isFirst) {
                JScrollPane scrollPane = new JScrollPane(textArea);
                add(scrollPane, BorderLayout.CENTER);
                isFirst = false;
            }
            setVisible(true);
            requestFocusInWindow(true);
            
        }

        // open feature
        else if (command.equals("Open")) {
            alreadySaved = true;
            isopen = true;
            JFileChooser chooser = new JFileChooser();
            chooser.setAcceptAllFileFilterUsed(false);
            String[] ext = { "txt", "java", "py", "c", "cpp", "php", "js", "html", "css" };
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Only .txt files", ext);
            chooser.addChoosableFileFilter(filter);

            int action = chooser.showOpenDialog(this);
            if (action != JFileChooser.APPROVE_OPTION) {
                return;
            }

            opened = chooser.getSelectedFile();
            try {
                BufferedReader br = new BufferedReader(new FileReader(opened));
                textArea.setText("");
                textArea.read(br, null);

                if (isFirst) {
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    add(scrollPane, BorderLayout.CENTER);
                    isFirst = false;
                }

                setVisible(true);
                br.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        // saving
        else if (command.equals("Save")) {
            if (isopen) {
                if (!alreadySaved) {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setApproveButtonText("save");

                    int action = chooser.showOpenDialog(this);

                    if (action != JFileChooser.APPROVE_OPTION) {
                        return;
                    }

                    opened = new File(chooser.getSelectedFile() + ".txt");
                    alreadySaved = true;
                }

                BufferedWriter out = null;
                try {
                    out = new BufferedWriter(new FileWriter(opened));
                    textArea.write(out);
                    JOptionPane.showMessageDialog(this, "File Saved....");
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "No file is opened");
            }
        
        } 
        
        //printing
        else if (command.equals("Print")) {
            if(isopen){
                try {
                    textArea.print();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            else{
                    JOptionPane.showMessageDialog(this, "No file is opened");
            }
        } 

        //exit
        else if (command.equals("Exit")) 
        {
            System.exit(0);
        } 

        //cut
        else if (command.equals("Cut")) {
            text = textArea.getSelectedText();
            textArea.replaceRange("", textArea.getSelectionStart(), textArea.getSelectionEnd());
            if (text == null) {
                JOptionPane.showMessageDialog(this, "Nothing to Cut");
            }
            else{
                Clipboard board = Toolkit.getDefaultToolkit().getSystemClipboard();
                board.setContents(new StringSelection(text), null);
            }
        } 

        //copy
        else if (command.equals("Copy")) 
        {
            text = textArea.getSelectedText();
            if (text == null) {
                JOptionPane.showMessageDialog(this, "Nothing to copy");
            }
            else{
                Clipboard board = Toolkit.getDefaultToolkit().getSystemClipboard();
                board.setContents(new StringSelection(text), null);
            }
        } 

        //pasting
        else if (command.equals("Paste")) 
        {
            Clipboard board = Toolkit.getDefaultToolkit().getSystemClipboard();
            try {
                String cliptext = (String) board.getData(DataFlavor.stringFlavor);
                if (cliptext == null) {
                    JOptionPane.showMessageDialog(this, "Nothing to paste");
                    return;
                }
                textArea.insert(cliptext, textArea.getCaretPosition());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Nothing to paste");
            }
            
        } 

        //select all feature
        else if (command.equals("Select All")) {
            textArea.selectAll();
        } 
        
        //About application
        else if (command.equals("About")) 
        {
            if(abb == null){
                abb = new About();
            }
            abb.setVisible(true);
            abb.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        } 

        else {
            JOptionPane.showMessageDialog(this, "Unknown command: " + command);
        }
    }

}

