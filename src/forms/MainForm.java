package forms;

import classes.Meme;
import classes.SaveLoadManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

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
    private JScrollPane tableScrollPane;

    private int selectedRow = -1;

    public MainForm() {
        List<Meme> memes = SaveLoadManager.loadData();
        String[][] data = new String[memes.size()][5];
        for (int i = 0; i < memes.size(); i++) {
            data[i][0] = memes.get(i).getNombre();
            data[i][1] = String.valueOf(memes.get(i).getAnyoOrigen());
            data[i][2] = String.valueOf(memes.get(i).getPopularidad());
            data[i][3] = memes.get(i).getUrl();
            data[i][4] = String.valueOf(memes.get(i).isEsImagen());
        }
        if (!memes.isEmpty()) {
            TableModel dataModel = new DefaultTableModel(
                    data,
                    new String[] {"Nombre", "Anyo origen", "Popularidad", "url", "Es imagen?"}
            );
            elementsTable.setModel(dataModel);
        }

        elementsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        elementsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (!listSelectionEvent.getValueIsAdjusting()) {
                    int row = elementsTable.getSelectedRow();
                    if (row != -1) {
                        selectedRow = row;
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Al principio, elige una fila.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    int response = JOptionPane.showConfirmDialog(
                            null,
                            "Estas segur@?",
                            "Eliminacion",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );
                    if (response == JOptionPane.YES_OPTION) {
                        int modelRow = elementsTable.convertRowIndexToModel(selectedRow);
                        ((DefaultTableModel) elementsTable.getModel()).removeRow(modelRow);
                        selectedRow = -1;
                    }
                }
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
}
