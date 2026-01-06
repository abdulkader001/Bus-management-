import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class BusTablePanel extends JPanel {
    private JTable busTable;
    private DefaultTableModel tableModel;

    public BusTablePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Utils.PRIMARY_COLOR, 2),
                "Bus List", 0, 0, Utils.HEADER_FONT, Utils.PRIMARY_COLOR
        ));

        String[] columns = {"Bus Serial Number", "Bus Name", "From & To", "Seat Capacity"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        busTable = new JTable(tableModel);
        busTable.setFont(Utils.REGULAR_FONT);
        busTable.setRowHeight(25);
        busTable.setSelectionBackground(Utils.SECONDARY_COLOR);
        busTable.setSelectionForeground(Color.WHITE);

        JTableHeader header = busTable.getTableHeader();
        header.setBackground(Utils.PRIMARY_COLOR);
        header.setForeground(Color.black);
        header.setFont(Utils.HEADER_FONT);

        JScrollPane scrollPane = new JScrollPane(busTable);
        scrollPane.setPreferredSize(new Dimension(600, 200));

        add(scrollPane, BorderLayout.CENTER);
    }

    public JTable getBusTable() { return busTable; }
    public DefaultTableModel getTableModel() { return tableModel; }
}