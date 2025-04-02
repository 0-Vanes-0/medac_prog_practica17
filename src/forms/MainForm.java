package forms;

import classes.Meme;
import classes.SaveLoadManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
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

    private static JFrame frame;

    private JPanel panelMain;
    private JLabel titleLabel;
    private JTable elementsTable;
    private JButton newButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private JScrollPane tableScrollPane;

    private int selectedRow = -1;
    private boolean hasChanges = false;

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
                        ((DefaultTableModel) elementsTable.getModel()).removeRow(selectedRow);
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

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Al principio, elige una fila.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    DefaultTableModel model = (DefaultTableModel) elementsTable.getModel();
                    Meme meme = new Meme(
                            model.getValueAt(selectedRow, 0).toString(),
                            Integer.parseInt(model.getValueAt(selectedRow, 1).toString()),
                            Integer.parseInt(model.getValueAt(selectedRow, 2).toString()),
                            model.getValueAt(selectedRow, 3).toString(),
                            Boolean.parseBoolean(model.getValueAt(selectedRow, 4).toString())
                    );
                    CreateForm createForm = new CreateForm(MainForm.this, meme);
                    createForm.setVisible(true);
                }
            }
        });
    }

    public static void main(String[] args) {
        frame = new JFrame("MyForm");
        MainForm form = new MainForm();
        frame.setContentPane(form.panelMain);
        frame.setMinimumSize(form.panelMain.getMinimumSize());

        // On close behavior:
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (form.elementsTable.getModel().getRowCount() > 0 && form.hasChanges) {
                    showSaveChangesDialog(form);
                } else {
                    frame.dispose();
                }
            }
        });

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Fichero");
        JMenuItem saveMenuItem = new JMenuItem("Guardar");
        JMenuItem exitMenuItem = new JMenuItem("Salir");
        JMenu helpMenu = new JMenu("Ayuda");
        JMenuItem aboutMenuItem = new JMenuItem("Info sobre creadores");
        fileMenu.add(saveMenuItem);
        fileMenu.add(exitMenuItem);
        helpMenu.add(aboutMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        frame.setJMenuBar(menuBar);

        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (form.hasChanges) {
                    saveData(form);
                    JOptionPane.showMessageDialog(frame, "Guardado!", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "No hay cambios.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                exit(form);
            }
        });
        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(frame, "Autores: Ivan y Konstatin\nMainForm por Ivan\nCreateForm por Konstantin", "Informacion", JOptionPane.INFORMATION_MESSAGE);
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
        hasChanges = true;
    }

    public void modifyCurrentElement(Meme meme) {
        DefaultTableModel model = (DefaultTableModel) elementsTable.getModel();
        model.setValueAt(meme.getNombre(), selectedRow, 0);
        model.setValueAt(meme.getAnyoOrigen(), selectedRow, 1);
        model.setValueAt(meme.getPopularidad(), selectedRow, 2);
        model.setValueAt(meme.getUrl(), selectedRow, 3);
        model.setValueAt(meme.isEsImagen(), selectedRow, 4);
        hasChanges = true;
    }

    private static void exit(MainForm form) {
        if (form.elementsTable.getModel().getRowCount() > 0 && form.hasChanges) {
            showSaveChangesDialog(form);
        } else {
            frame.dispose();
        }
    }

    private static void showSaveChangesDialog(MainForm form) {
        // Perform actions before closing
        int response = JOptionPane.showConfirmDialog(frame,
                "Quieres guardar los cambios?",
                "Confirmar salida", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) { // Save data and close the window
            saveData(form);
            frame.dispose();
        } else if (response == JOptionPane.NO_OPTION) { // Close the window without saving
            frame.dispose();
        }
        // If CANCEL is selected, do nothing
    }

    private static void saveData(MainForm form) {
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
        form.hasChanges = false;
    }
}
