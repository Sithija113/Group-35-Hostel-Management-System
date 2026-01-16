/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author User
 */
public class RoomPanel {
    
}
package view;

import db.DBConnection;
import ui.UITheme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RoomsPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtRoomNumber, txtType, txtCapacity, txtOccupied;
    private int selectedRoomId = -1;

    public RoomsPanel() {
        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        setPreferredSize(new Dimension(750, 400));

        initTable();
        initForm();

        SwingUtilities.invokeLater(this::loadRooms);
    }

    private void initTable() {
        tableModel = new DefaultTableModel(new String[]{
                "ID", "Room Number", "Type", "Capacity", "Occupied"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    selectedRoomId = (int) tableModel.getValueAt(row, 0);
                    txtRoomNumber.setText((String) tableModel.getValueAt(row, 1));
                    txtType.setText((String) tableModel.getValueAt(row, 2));
                    txtCapacity.setText(tableModel.getValueAt(row, 3).toString());
                    txtOccupied.setText(tableModel.getValueAt(row, 4).toString());
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(750, 250));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(UITheme.BACKGROUND);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtRoomNumber = new JTextField(10);
        txtType = new JTextField(10);
        txtCapacity = new JTextField(10);
        txtOccupied = new JTextField(10);

        gbc.gridx=0; gbc.gridy=0; formPanel.add(new JLabel("Room Number"), gbc);
        gbc.gridx=1; formPanel.add(txtRoomNumber, gbc);

        gbc.gridx=0; gbc.gridy=1; formPanel.add(new JLabel("Type"), gbc);
        gbc.gridx=1; formPanel.add(txtType, gbc);

        gbc.gridx=0; gbc.gridy=2; formPanel.add(new JLabel("Capacity"), gbc);
        gbc.gridx=1; formPanel.add(txtCapacity, gbc);

        gbc.gridx=0; gbc.gridy=3; formPanel.add(new JLabel("Occupied"), gbc);
        gbc.gridx=1; formPanel.add(txtOccupied, gbc);

        add(formPanel, BorderLayout.SOUTH);
    }

    private void loadRooms() {
        tableModel.setRowCount(0);
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM rooms")) {

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("room_id"),
                        rs.getString("room_number"),
                        rs.getString("type"),
                        rs.getInt("capacity"),
                        rs.getInt("occupied")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load rooms", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}