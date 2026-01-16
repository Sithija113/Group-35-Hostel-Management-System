
    private JButton btnAdd, btnUpdate, btnDelete;

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

        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");

        btnAdd.setBackground(UITheme.PRIMARY_BROWN); btnAdd.setForeground(Color.WHITE);
        btnUpdate.setBackground(UITheme.PRIMARY_BROWN); btnUpdate.setForeground(Color.WHITE);
        btnDelete.setBackground(UITheme.PRIMARY_BROWN); btnDelete.setForeground(Color.WHITE);

        btnAdd.addActionListener(e -> addRoom());
        btnUpdate.addActionListener(e -> updateRoom());
        btnDelete.addActionListener(e -> deleteRoom());

        gbc.gridx=0; gbc.gridy=4; formPanel.add(btnAdd, gbc);
        gbc.gridx=1; formPanel.add(btnUpdate, gbc);
        gbc.gridx=2; formPanel.add(btnDelete, gbc);
    }

    private void addRoom() {
        if (txtRoomNumber.getText().isEmpty() || txtType.getText().isEmpty()
                || txtCapacity.getText().isEmpty() || txtOccupied.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return;
        }

        String sql = "INSERT INTO rooms(room_number,type,capacity,occupied) VALUES (?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, txtRoomNumber.getText());
            pst.setString(2, txtType.getText());
            pst.setInt(3, Integer.parseInt(txtCapacity.getText()));
            pst.setInt(4, Integer.parseInt(txtOccupied.getText()));

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Room added successfully");
            clearForm();
            loadRooms();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add room", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateRoom() {
        if (selectedRoomId == -1) {
            JOptionPane.showMessageDialog(this,"Select a room first");
            return;
        }

        String sql = "UPDATE rooms SET room_number=?, type=?, capacity=?, occupied=? WHERE room_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, txtRoomNumber.getText());
            pst.setString(2, txtType.getText());
            pst.setInt(3, Integer.parseInt(txtCapacity.getText()));
            pst.setInt(4, Integer.parseInt(txtOccupied.getText()));
            pst.setInt(5, selectedRoomId);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this,"Room updated successfully");
            clearForm();
            loadRooms();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"Failed to update room","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteRoom() {
        if (selectedRoomId == -1) {
            JOptionPane.showMessageDialog(this,"Select a room first");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this room?", "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        String sql = "DELETE FROM rooms WHERE room_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, selectedRoomId);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this,"Room deleted successfully");
            clearForm();
            loadRooms();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"Failed to delete room","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        txtRoomNumber.setText("");
        txtType.setText("");
        txtCapacity.setText("");
        txtOccupied.setText("");
        selectedRoomId = -1;
        table.clearSelection();
    }