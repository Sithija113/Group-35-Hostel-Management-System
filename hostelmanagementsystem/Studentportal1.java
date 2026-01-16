package view;

import ui.UITheme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class StudentsPanel extends JPanel {

    // ===== UI COMPONENTS =====
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtFirstName, txtLastName, txtGender,
            txtContact, txtEmail, txtRoomNumber;

    private JButton btnAdd, btnUpdate, btnDelete;

    private int selectedStudentId = -1;

    public StudentsPanel() {
        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);

        initTable();   // UI Table
        initForm();    // UI Form
    }

    // ===== TABLE UI =====
    private void initTable() {
        tableModel = new DefaultTableModel(
                new String[]{"ID", "First Name", "Last Name", "Gender",
                        "Contact", "Email", "Room Number"}, 0
        ) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    selectedStudentId = (int) tableModel.getValueAt(row, 0);
                    txtFirstName.setText(tableModel.getValueAt(row, 1).toString());
                    txtLastName.setText(tableModel.getValueAt(row, 2).toString());
                    txtGender.setText(tableModel.getValueAt(row, 3).toString());
                    txtContact.setText(tableModel.getValueAt(row, 4).toString());
                    txtEmail.setText(tableModel.getValueAt(row, 5).toString());
                    txtRoomNumber.setText(tableModel.getValueAt(row, 6).toString());
                }
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    // ===== FORM UI =====
    private void initForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(UITheme.BACKGROUND);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtFirstName = new JTextField(10);
        txtLastName = new JTextField(10);
        txtGender = new JTextField(10);
        txtContact = new JTextField(10);
        txtEmail = new JTextField(10);
        txtRoomNumber = new JTextField(10);

        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");

        gbc.gridx=0; gbc.gridy=0; form.add(new JLabel("First Name"), gbc);
        gbc.gridx=1; form.add(txtFirstName, gbc);

        gbc.gridx=0; gbc.gridy=1; form.add(new JLabel("Last Name"), gbc);
        gbc.gridx=1; form.add(txtLastName, gbc);

        gbc.gridx=0; gbc.gridy=2; form.add(new JLabel("Gender"), gbc);
        gbc.gridx=1; form.add(txtGender, gbc);

        gbc.gridx=0; gbc.gridy=3; form.add(new JLabel("Contact"), gbc);
        gbc.gridx=1; form.add(txtContact, gbc);

        gbc.gridx=0; gbc.gridy=4; form.add(new JLabel("Email"), gbc);
        gbc.gridx=1; form.add(txtEmail, gbc);

        gbc.gridx=0; gbc.gridy=5; form.add(new JLabel("Room No"), gbc);
        gbc.gridx=1; form.add(txtRoomNumber, gbc);

        gbc.gridx=0; gbc.gridy=6; form.add(btnAdd, gbc);
        gbc.gridx=1; form.add(btnUpdate, gbc);
        gbc.gridx=2; form.add(btnDelete, gbc);

        add(form, BorderLayout.SOUTH);
    }
}

