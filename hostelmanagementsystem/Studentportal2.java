import db.DBConnection;
import java.sql.*;
import javax.swing.*;

    // ===== LOAD STUDENTS =====
    private void loadStudents() {
        tableModel.setRowCount(0);
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM students")) {

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("student_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender"),
                        rs.getString("contact"),
                        rs.getString("email"),
                        rs.getString("room_number")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Load failed");
        }
    }

    // ===== ADD STUDENT =====
    private void addStudent() {
        String sql = "INSERT INTO students VALUES (null,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, txtFirstName.getText());
            ps.setString(2, txtLastName.getText());
            ps.setString(3, txtGender.getText());
            ps.setString(4, txtContact.getText());
            ps.setString(5, txtEmail.getText());
            ps.setString(6, txtRoomNumber.getText());

            ps.executeUpdate();
            loadStudents();
            clearForm();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Insert failed");
        }
    }

    // ===== UPDATE STUDENT =====
    private void updateStudent() {
        String sql = "UPDATE students SET first_name=?, last_name=?, gender=?, contact=?, email=?, room_number=? WHERE student_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, txtFirstName.getText());
            ps.setString(2, txtLastName.getText());
            ps.setString(3, txtGender.getText());
            ps.setString(4, txtContact.getText());
            ps.setString(5, txtEmail.getText());
            ps.setString(6, txtRoomNumber.getText());
            ps.setInt(7, selectedStudentId);

            ps.executeUpdate();
            loadStudents();
            clearForm();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Update failed");
        }
    }

    // ===== DELETE STUDENT =====
    private void deleteStudent() {
        String sql = "DELETE FROM students WHERE student_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, selectedStudentId);
            ps.executeUpdate();
            loadStudents();
            clearForm();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Delete failed");
        }
    }

    private void clearForm() {
        txtFirstName.setText("");
        txtLastName.setText("");
        txtGender.setText("");
        txtContact.setText("");
        txtEmail.setText("");
        txtRoomNumber.setText("");
        selectedStudentId = -1;
    }
