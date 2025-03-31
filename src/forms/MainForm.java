package forms;

import javax.swing.*;

/**
 * Author: Ivan + Konstantin
 */

public class MainForm {
    private JPanel panelMain;
    private JLabel titleLabel;
    private JTable elementsTable;
    private JButton newButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private JButton fileButton;
    private JButton aboutButton;

    public MainForm() {

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MyForm");
        MainForm form = new MainForm();
        frame.setContentPane(form.panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(form.panelMain.getMinimumSize());
        frame.pack();
        frame.setVisible(true);
    }
}
