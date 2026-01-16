/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import ui.UITheme;

import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    private final String ADMIN_USER = "admin";
    private final String ADMIN_PASS = "admin123";

    public LoginForm() {
        setTitle("Hostel Management System - Login");
        setSize(420, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UITheme.BACKGROUND);
        mainPanel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        topPanel.setBackground(UITheme.BACKGROUND);



        JLabel lblTitle = new JLabel("Hostel Management System");
        lblTitle.setFont(UITheme.TITLE_FONT);
        lblTitle.setForeground(UITheme.PRIMARY_BROWN);

        topPanel.add(lblTitle);

        JPanel formPanel = new JPanel();
        formPanel.setBackground(UITheme.BACKGROUND);
        formPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(UITheme.NORMAL_FONT);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(UITheme.NORMAL_FONT);

        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBackground(UITheme.PRIMARY_BROWN);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(UITheme.NORMAL_FONT);

        btnLogin.addActionListener(e -> login());

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(lblUsername, gbc);

        gbc.gridx = 1;
        formPanel.add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(lblPassword, gbc);

        gbc.gridx = 1;
        formPanel.add(txtPassword, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(btnLogin, gbc);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void login() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        if (username.equals(ADMIN_USER) && password.equals(ADMIN_PASS)) {
            JOptionPane.showMessageDialog(this,
                    "Login Successful",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            this.dispose();
            new Dashboard().setVisible(true);

        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}