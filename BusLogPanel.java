import java.awt.*;
import javax.swing.*;

public class BusLogPanel extends JPanel {
    private JTextArea logArea;

    public BusLogPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Utils.SUCCESS_COLOR, 2),
                "Display Log", 0, 0, Utils.HEADER_FONT, Utils.SUCCESS_COLOR
        ));

        logArea = new JTextArea();
        logArea.setFont(Utils.REGULAR_FONT);
        logArea.setEditable(false);
        logArea.setBackground(Utils.LIGHT_GRAY);
        logArea.setForeground(Utils.DARK_GRAY);

        JScrollPane logScrollPane = new JScrollPane(logArea);
        logScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(logScrollPane, BorderLayout.CENTER);
    }

    public void addLog(String action, String busName, String busNumber, String dateTime) {
        String logEntry = String.format("[%s] %s - Bus: %s | Number: %s\n",
                dateTime, action, busName, busNumber);
        logArea.append(logEntry);
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
}