package forms;

import javax.swing.*;
import java.awt.*;

public class CreateForm extends JFrame {
    private JTextField nameField;
    private JComboBox<String> typeComboBox;
    private JCheckBox activeCheckBox;
    private JTextField amountField;
    private JTextField descriptionField;
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
        nameField = new JTextField();

        JLabel typeLabel = new JLabel("Tipo:");
        String[] types = {"Tipo 1", "Tipo 2", "Tipo 3"};
        typeComboBox = new JComboBox<>(types);

        JLabel activeLabel = new JLabel("Activo:");
        activeCheckBox = new JCheckBox();

        JLabel amountLabel = new JLabel("Cantidad:");
        amountField = new JTextField();
        
        JLabel descriptionLabel = new JLabel("Descripción:");
        descriptionField = new JTextField();

        saveButton = new JButton("Guardar");
        cancelButton = new JButton("Cancelar");

        // Добавление компонентов 
        add(nameLabel);
        add(nameField);
        add(typeLabel);
        add(typeComboBox);
        add(activeLabel);
        add(activeCheckBox);
        add(amountLabel);
        add(amountField);
        add(descriptionLabel);
        add(descriptionField);
        add(saveButton);
        add(cancelButton);

        // Действие при нажатии кнопки сохранения
        saveButton.addActionListener(e -> saveElement(nameLabel, amountLabel, descriptionLabel));

        // Действие при нажатии кнопки отмены
        cancelButton.addActionListener(e -> dispose());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void saveElement(JLabel nameLabel, JLabel amountLabel, JLabel descriptionLabel) {
        String name = nameField.getText();
        String type = (String) typeComboBox.getSelectedItem();
        boolean isActive = activeCheckBox.isSelected();
        String amount = amountField.getText();
        String description = descriptionField.getText();

        // Сброс цвета меток на черный
        nameLabel.setForeground(Color.BLACK);
        amountLabel.setForeground(Color.BLACK);
        descriptionLabel.setForeground(Color.BLACK);

        // Проверка на заполненность полей (boolean)
        boolean hasError = false;
        if (name.isEmpty()) {
            nameLabel.setForeground(Color.RED);
            hasError = true;
        }
        if (amount.isEmpty()) {
            amountLabel.setForeground(Color.RED);
            hasError = true;
        }
        if (description.isEmpty()) {
            descriptionLabel.setForeground(Color.RED);
            hasError = true;
        }
        
        if (hasError) {
            JOptionPane.showMessageDialog(this, "¡Complete todos los campos!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Проверка является ли количество числом
        int amountValue;
        try {
            amountValue = Integer.parseInt(amount);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad debe ser un número!", "Error", JOptionPane.ERROR_MESSAGE);
            amountLabel.setForeground(Color.RED);
            return;
        }

        // Создание  Element и добавление его в первую форму и ArrayList
        Element newElement = new Element(name, type, isActive, amountValue, description);
        mainForm.addElementToList(newElement);
        mainForm.addElementToTable(name, type, isActive, amount, description);

        //Сообщение сохранения
        JOptionPane.showMessageDialog(this, "¡Elemento guardado con éxito!", "W", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
