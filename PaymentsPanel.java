/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import db.DBConnection;
import ui.UITheme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PaymentsPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtStudentName, txtRoomNumber, txtAmount;
    private JButton btnAdd, btnUpdate, btnDelete;

    private int selectedPaymentId = -1;

    public PaymentsPanel() {
        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);

        initTable();
        initForm();
        loadPayments();
    }

    private void initTable() {
        tableModel = new DefaultTableModel(new String[]{
                "ID", "Student Name", "Room Number", "Amount", "Payment Date"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    selectedPaymentId = (int) tableModel.getValueAt(row, 0);
                    txtStudentName.setText((String) tableModel.getValueAt(row, 1));
                    txtRoomNumber.setText((String) tableModel.getValueAt(row, 2));
                    txtAmount.setText(tableModel.getValueAt(row, 3).toString());
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(UITheme.BACKGROUND);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtStudentName = new JTextField(10);
        txtRoomNumber = new JTextField(10);
        txtAmount = new JTextField(10);

        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");

        btnAdd.setBackground(UITheme.PRIMARY_BROWN); btnAdd.setForeground(Color.WHITE);
        btnUpdate.setBackground(UITheme.PRIMARY_BROWN); btnUpdate.setForeground(Color.WHITE);
        btnDelete.setBackground(UITheme.PRIMARY_BROWN); btnDelete.setForeground(Color.WHITE);

        btnAdd.addActionListener(e -> addPayment());
        btnUpdate.addActionListener(e -> updatePayment());
        btnDelete.addActionListener(e -> deletePayment());

        gbc.gridx=0; gbc.gridy=0; formPanel.add(new JLabel("Student Name"), gbc);
        gbc.gridx=1; formPanel.add(txtStudentName, gbc);

        gbc.gridx=0; gbc.gridy=1; formPanel.add(new JLabel("Room Number"), gbc);
        gbc.gridx=1; formPanel.add(txtRoomNumber, gbc);

        gbc.gridx=0; gbc.gridy=2; formPanel.add(new JLabel("Amount"), gbc);
        gbc.gridx=1; formPanel.add(txtAmount, gbc);

        gbc.gridx=0; gbc.gridy=3; formPanel.add(btnAdd, gbc);
        gbc.gridx=1; formPanel.add(btnUpdate, gbc);
        gbc.gridx=2; formPanel.add(btnDelete, gbc);

        add(formPanel, BorderLayout.SOUTH);
    }

    private void loadPayments() {
        tableModel.setRowCount(0);
        String sql = "SELECT * FROM payments ORDER BY payment_date DESC";
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while(rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("payment_id"),
                        rs.getString("student_name"),
                        rs.getString("room_number"),
                        rs.getDouble("amount"),
                        rs.getDate("payment_date")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load payments", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addPayment() {
        String sql = "INSERT INTO payments(student_name, room_number, amount, payment_date) VALUES (?,?,?,GETDATE())";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, txtStudentName.getText());
            pst.setString(2, txtRoomNumber.getText());
            pst.setDouble(3, Double.parseDouble(txtAmount.getText()));

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Payment added successfully");
            clearForm();
            loadPayments();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add payment", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePayment() {
        if(selectedPaymentId == -1) { JOptionPane.showMessageDialog(this, "Select a payment first"); return; }

        String sql = "UPDATE payments SET student_name=?, room_number=?, amount=? WHERE payment_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, txtStudentName.getText());
            pst.setString(2, txtRoomNumber.getText());
            pst.setDouble(3, Double.parseDouble(txtAmount.getText()));
            pst.setInt(4, selectedPaymentId);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Payment updated successfully");
            clearForm();
            loadPayments();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update payment", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletePayment() {
        if(selectedPaymentId == -1) { JOptionPane.showMessageDialog(this, "Select a payment first"); return; }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this payment?", "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if(confirm != JOptionPane.YES_OPTION) return;

        String sql = "DELETE FROM payments WHERE payment_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, selectedPaymentId);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Payment deleted successfully");
            clearForm();
            loadPayments();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to delete payment", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        txtStudentName.setText("");
        txtRoomNumber.setText("");
        txtAmount.setText("");
        selectedPaymentId = -1;
    }
}
