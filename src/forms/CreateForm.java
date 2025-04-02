package forms;

import classes.Meme;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CreateForm extends JFrame {
    private JTextField nameField;
    private JTextField anyoField;
    private JTextField popularidadField;
    private JTextField urlField;
    private JCheckBox imagenCheckBox;
    private JButton saveButton;
    private JButton cancelButton;
    private MainForm mainForm;

    public CreateForm(MainForm mainForm) {
        this(mainForm, null);
    }

    public CreateForm(MainForm mainForm, Meme meme) {
        this.mainForm = mainForm;

        setTitle("Crear nuevo elemento");
        setSize(400, 300);
        setLayout(new GridLayout(6, 2));

        // Создание компонентов
        JLabel nameLabel = new JLabel("Nombre:");
        nameField = new JTextField(20);
        if (meme != null) {
            nameField.setText(meme.getNombre());
        }

        JLabel anyoLabel = new JLabel("Año origen:");
        anyoField = new JTextField(4);
        if (meme != null) {
            anyoField.setText(String.valueOf(meme.getAnyoOrigen()));
        }

        JLabel popularidadLabel = new JLabel("Popularidad:");
        popularidadField = new JTextField(10);
        if (meme != null) {
            popularidadField.setText(String.valueOf(meme.getPopularidad()));
        }

        JLabel urlLabel = new JLabel("URL:");
        urlField = new JTextField("https://google.com");
        if (meme != null) {
            urlField.setText(meme.getUrl());
        }

        JLabel imagenLabel = new JLabel("¿Es imagen?");
        imagenCheckBox = new JCheckBox();
        if (meme != null) {
            imagenCheckBox.setEnabled(meme.isEsImagen());
        }

        saveButton = new JButton("Guardar");
        cancelButton = new JButton("Cancelar");

        // Добавление компонентов
        add(nameLabel);
        add(nameField);
        add(anyoLabel);
        add(anyoField);
        add(popularidadLabel);
        add(popularidadField);
        add(urlLabel);
        add(urlField);
        add(imagenLabel);
        add(imagenCheckBox);
        add(saveButton);
        add(cancelButton);

        // Действие при нажатии кнопки сохранения
        if (meme != null) {
            saveButton.addActionListener(e -> saveElement(true));
        } else {
            saveButton.addActionListener(e -> saveElement(false));
        }

        // Действие при нажатии кнопки отмены
        cancelButton.addActionListener(e -> dispose());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void saveElement(boolean existingElement) {
        // Получаем данные из полей
        String nombre = nameField.getText();
        String url = urlField.getText();
        boolean esImagen = imagenCheckBox.isSelected();

        // Обработка года (anyo)
        int anyoOrigen;
        try {
            anyoOrigen = Integer.parseInt(anyoField.getText()); // Тип данных для года - int
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El año debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Обработка популярности (popularidad)
        int popularidad;
        try {
            popularidad = Integer.parseInt(popularidadField.getText()); // Тип данных для популярности - double
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La popularidad debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Создание нового объекта Meme
        Meme meme = new Meme(nombre, anyoOrigen, popularidad, url, esImagen);

        // Отправка данных в MainForm для добавления
        if (existingElement) {
            mainForm.modifyCurrentElement(meme);
        } else {
            mainForm.addElementToTable(meme);
        }

        // Сообщение об успешном сохранении
        JOptionPane.showMessageDialog(this, "¡Elemento guardado con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        // Закрытие формы
        dispose();
    }
}
