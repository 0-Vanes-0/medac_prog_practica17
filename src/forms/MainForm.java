package forms;

import classes.Meme;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CreateForm createForm = new CreateForm(MainForm.this);
                createForm.setVisible(true);
            }
        });
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

    public void addElementToTable(Meme meme) {

    }
}
