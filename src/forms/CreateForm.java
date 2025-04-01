package forms;

import classes.Meme;

import javax.swing.*;
import java.awt.*;

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
        this.mainForm = mainForm;

        setTitle("Crear nuevo elemento");
        setSize(400, 300);
        setLayout(new GridLayout(6, 2));

        // Создание компонентов
        JLabel nameLabel = new JLabel("Nombre:");
        nameField = new JTextField(20);

        JLabel anyoLabel = new JLabel("Año origen:");
        anyoField = new JTextField(4);

        JLabel popularidadLabel = new JLabel("Popularidad:");
        popularidadField = new JTextField(10);

        JLabel urlLabel = new JLabel("URL:");
        urlField = new JTextField("https://google.com");

        JLabel imagenLabel = new JLabel("¿Es imagen?");
        imagenCheckBox = new JCheckBox();

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
        saveButton.addActionListener(e -> saveElement());

        // Действие при нажатии кнопки отмены
        cancelButton.addActionListener(e -> dispose());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void saveElement() {
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
        mainForm.addElementToTable(meme);

        // Сообщение об успешном сохранении
        JOptionPane.showMessageDialog(this, "¡Elemento guardado con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        // Закрытие формы
        dispose();
    }
}
