package forms;

import javax.swing.*;

public class MainForm {
    private JPanel panelMain;
    private JLabel titleLabel;
    private JTable elementsTable;
    private JButton newButton;
    private JButton deleteButton;
    private JButton modifyButton;

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
