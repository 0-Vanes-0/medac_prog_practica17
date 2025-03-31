package forms;

import classes.Meme;
import classes.SaveLoadManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
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
