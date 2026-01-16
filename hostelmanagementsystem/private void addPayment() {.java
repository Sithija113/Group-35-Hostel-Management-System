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
        selectedPaymentId = -1;
    }
}