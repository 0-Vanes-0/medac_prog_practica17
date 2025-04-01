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
        String[] columns = new String[] {"Nombre", "Año origen", "Popularidad", "URL", "¿Es imagen?"};
        DefaultTableModel dataModel;

        List<Meme> memes = SaveLoadManager.loadData();
        if (!memes.isEmpty()) {
            // Converting ArrayList to data for table:
            String[][] data = new String[memes.size()][5];
            for (int i = 0; i < memes.size(); i++) {
                data[i][0] = memes.get(i).getNombre();
                data[i][1] = String.valueOf(memes.get(i).getAnyoOrigen());
                data[i][2] = String.valueOf(memes.get(i).getPopularidad());
                data[i][3] = memes.get(i).getUrl();
                data[i][4] = String.valueOf(memes.get(i).isEsImagen());
            }
            dataModel = new DefaultTableModel(data, columns);
        } else {
            dataModel = new DefaultTableModel(columns, 0);
        }
        elementsTable.setModel(dataModel);

        elementsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        elementsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (!listSelectionEvent.getValueIsAdjusting()) { // To make sure we won't get wrong row
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
                // No selected row -> show error
                // Selected row -> show confirmation and if [YES] -> remove row
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
        frame.setMinimumSize(form.panelMain.getMinimumSize());

        // On close behavior:
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (form.elementsTable.getModel().getRowCount() > 0) {
                    // Perform actions before closing
                    int response = JOptionPane.showConfirmDialog(frame,
                            "Quieres guardar los cambios?",
                            "Confirmar salida", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    if (response == JOptionPane.YES_OPTION) { // Save data and close the window
                        DefaultTableModel model = (DefaultTableModel) form.elementsTable.getModel();
                        List<Meme> memes = new ArrayList<>();
                        for (int i = 0; i < model.getRowCount(); i++) {
                            Meme meme = new Meme(
                                    model.getValueAt(i, 0).toString(),
                                    Integer.parseInt(model.getValueAt(i, 1).toString()),
                                    Integer.parseInt(model.getValueAt(i, 2).toString()),
                                    model.getValueAt(i, 3).toString(),
                                    Boolean.parseBoolean(model.getValueAt(i, 4).toString())
                            );
                            memes.add(meme);
                        }
                        SaveLoadManager.saveData(memes);
                        frame.dispose();
                    } else if (response == JOptionPane.NO_OPTION) { // Close the window without saving
                        frame.dispose();
                    }
                    // If CANCEL is selected, do nothing
                } else {
                    frame.dispose();
                }
            }
        });
        
        frame.pack();
        frame.setVisible(true);
    }

    public void addElementToTable(Meme meme) {
        DefaultTableModel model = (DefaultTableModel) elementsTable.getModel();
        model.addRow(new Object[]{
                meme.getNombre(),
                meme.getAnyoOrigen(),
                meme.getPopularidad(),
                meme.getUrl(),
                meme.isEsImagen()
        } );
    }
}
