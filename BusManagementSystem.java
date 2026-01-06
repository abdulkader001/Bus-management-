import java.awt.*;
import javax.swing.*;

public class BusManagementSystem extends JFrame {
    private BusTablePanel tablePanel;
    private BusFormPanel formPanel;
    private BusLogPanel logPanel;
    private JTextField searchField;
    private JButton searchBtn;
    private int selectedRow = -1;

    public BusManagementSystem() {
        initializeGUI();
        setupEventHandlers();
    }

    private void initializeGUI() {
        setTitle("Bus Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1200, 700);
        setLocationRelativeTo(null);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 10));


        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        JLabel titleLabel = new JLabel("Dhundul Poribohon Seba");
        titleLabel.setFont(Utils.TITLE_FONT);
        titleLabel.setForeground(Utils.PRIMARY_COLOR);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(Utils.DANGER_COLOR.blue);
        searchField = new JTextField(15);
        searchField.setFont(Utils.REGULAR_FONT);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Utils.PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        searchBtn = Utils.createStyledButton("Search", Utils.PRIMARY_COLOR);
        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.EAST);

        formPanel = new BusFormPanel();
        tablePanel = new BusTablePanel();

        leftPanel.add(topPanel, BorderLayout.NORTH);
        leftPanel.add(formPanel, BorderLayout.CENTER);
        leftPanel.add(tablePanel, BorderLayout.SOUTH);

    
        logPanel = new BusLogPanel();
        JPanel rightWrapper = new JPanel(new BorderLayout());
        rightWrapper.setBackground(Color.WHITE);
        rightWrapper.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));
        rightWrapper.setPreferredSize(new Dimension(350, 700));
        rightWrapper.add(logPanel, BorderLayout.CENTER);

        add(leftPanel, BorderLayout.CENTER);
        add(rightWrapper, BorderLayout.EAST);
        getContentPane().setBackground(Utils.LIGHT_GRAY);

        
        logPanel.addLog("System initialized", "System", "N/A", Utils.getCurrentDateTime());
    }

    private void setupEventHandlers() {

        formPanel.insertBtn.addActionListener(e -> insertBus());
        formPanel.updateBtn.addActionListener(e -> updateBus());
        formPanel.deleteBtn.addActionListener(e -> deleteBus());
        formPanel.clearBtn.addActionListener(e -> clearFields());
        searchBtn.addActionListener(e -> searchBus());

        tablePanel.getBusTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedRow = tablePanel.getBusTable().getSelectedRow();
                if (selectedRow >= 0) {
                    populateFields();
                }
            }
        });
        searchField.addActionListener(e -> searchBus());
    }

    private void insertBus() {
        if (validateFields()) {
            String busNumber = formPanel.busNumberField.getText().trim();
            String busName = formPanel.busNameField.getText().trim();
            String route = formPanel.routeField.getText().trim();
            String capacity = formPanel.capacityField.getText().trim();

            if (busNumberExists(busNumber)) {
                JOptionPane.showMessageDialog(this, "Bus number already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            tablePanel.getTableModel().addRow(new Object[]{busNumber, busName, route, capacity});
            logPanel.addLog("Bus inserted", busName, busNumber, Utils.getCurrentDateTime());
            clearFields();
            JOptionPane.showMessageDialog(this, "Bus inserted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateBus() {
        if (selectedRow >= 0 && validateFields()) {
            String busNumber = formPanel.busNumberField.getText().trim();
            String busName = formPanel.busNameField.getText().trim();
            String route = formPanel.routeField.getText().trim();
            String capacity = formPanel.capacityField.getText().trim();

            tablePanel.getTableModel().setValueAt(busNumber, selectedRow, 0);
            tablePanel.getTableModel().setValueAt(busName, selectedRow, 1);
            tablePanel.getTableModel().setValueAt(route, selectedRow, 2);
            tablePanel.getTableModel().setValueAt(capacity, selectedRow, 3);

            logPanel.addLog("Bus updated", busName, busNumber, Utils.getCurrentDateTime());
            clearFields();
            JOptionPane.showMessageDialog(this, "Bus updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a bus to update!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteBus() {
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this bus?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                String busName = (String) tablePanel.getTableModel().getValueAt(selectedRow, 1);
                String busNumber = (String) tablePanel.getTableModel().getValueAt(selectedRow, 0);

                tablePanel.getTableModel().removeRow(selectedRow);
                logPanel.addLog("Bus deleted", busName, busNumber, Utils.getCurrentDateTime());
                clearFields();
                selectedRow = -1;
                JOptionPane.showMessageDialog(this, "Bus deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a bus to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void searchBus() {
        String searchTerm = searchField.getText().trim().toLowerCase();
        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search term!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean found = false;
        for (int i = 0; i < tablePanel.getTableModel().getRowCount(); i++) {
            String busNumber = tablePanel.getTableModel().getValueAt(i, 0).toString().toLowerCase();
            String busName = tablePanel.getTableModel().getValueAt(i, 1).toString().toLowerCase();
            String route = tablePanel.getTableModel().getValueAt(i, 2).toString().toLowerCase();

            if (busNumber.contains(searchTerm) || busName.contains(searchTerm) || route.contains(searchTerm)) {
                tablePanel.getBusTable().setRowSelectionInterval(i, i);
                tablePanel.getBusTable().scrollRectToVisible(tablePanel.getBusTable().getCellRect(i, 0, true));
                populateFields();
                logPanel.addLog("Bus searched", searchTerm, "N/A", Utils.getCurrentDateTime());
                found = true;
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "No bus found matching the search criteria!", "Not Found", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearFields() {
        formPanel.busNumberField.setText("");
        formPanel.busNameField.setText("");
        formPanel.routeField.setText("");
        formPanel.capacityField.setText("");
        tablePanel.getBusTable().clearSelection();
        selectedRow = -1;
    }

    private void populateFields() {
        if (selectedRow >= 0) {
            formPanel.busNumberField.setText((String) tablePanel.getTableModel().getValueAt(selectedRow, 0));
            formPanel.busNameField.setText((String) tablePanel.getTableModel().getValueAt(selectedRow, 1));
            formPanel.routeField.setText((String) tablePanel.getTableModel().getValueAt(selectedRow, 2));
            formPanel.capacityField.setText((String) tablePanel.getTableModel().getValueAt(selectedRow, 3));
        }
    }

    private boolean validateFields() {
        if (formPanel.busNumberField.getText().trim().isEmpty() ||
                formPanel.busNameField.getText().trim().isEmpty() ||
                formPanel.routeField.getText().trim().isEmpty() ||
                formPanel.capacityField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            Integer.parseInt(formPanel.capacityField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Capacity must be a valid number!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean busNumberExists(String busNumber) {
        for (int i = 0; i < tablePanel.getTableModel().getRowCount(); i++) {
            if (selectedRow != i && tablePanel.getTableModel().getValueAt(i, 0).toString().equals(busNumber)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new BusManagementSystem().setVisible(true));
    }
}