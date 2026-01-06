import java.awt.*;
import javax.swing.*;

public class BusFormPanel extends JPanel {
    public JTextField busNumberField, busNameField, routeField, capacityField;
    public JButton insertBtn, updateBtn, deleteBtn, clearBtn;

    public BusFormPanel() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Utils.PRIMARY_COLOR, 2),
                "Bus Information", 0, 0, Utils.HEADER_FONT, Utils.PRIMARY_COLOR
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        add(Utils.createLabel("Bus Serial Number:", Utils.HEADER_FONT), gbc);
        gbc.gridx = 1;
        busNumberField = Utils.createStyledTextField();
        add(busNumberField, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        add(Utils.createLabel("Bus Name:", Utils.HEADER_FONT), gbc);
        gbc.gridx = 3;
        busNameField = Utils.createStyledTextField();
        add(busNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(Utils.createLabel("From & To :", Utils.HEADER_FONT), gbc);
        gbc.gridx = 1;
        routeField = Utils.createStyledTextField();
        add(routeField, gbc);

        gbc.gridx = 2; gbc.gridy = 1;
        add(Utils.createLabel("Seat Capacity:", Utils.HEADER_FONT), gbc);
        gbc.gridx = 3;
        capacityField = Utils.createStyledTextField();
        add(capacityField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Utils.DANGER_COLOR.RED);
        insertBtn = Utils.createStyledButton("Insert Bus", Utils.SUCCESS_COLOR);
        updateBtn = Utils.createStyledButton("Update Bus", Utils.WARNING_COLOR);
        deleteBtn = Utils.createStyledButton("Delete Bus", Utils.DANGER_COLOR);
        clearBtn = Utils.createStyledButton("Clear All", Utils.DARK_GRAY);
        buttonPanel.add(insertBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(clearBtn);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);
    }
}