import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class Kamoku extends JFrame{

    private JPanel panel;
    private JPanel searchWindow;
    private JComboBox dayBox;
    private JLabel dayLabel;
    private JComboBox classBox;
    private JLabel classLabel;
    private JPanel highLevelSearchWindow;
    private JButton searchButton;
    private JPanel resultWindow;
    private JList resultList;
    private JCheckBox required;
    private JCheckBox optional;
    private JCheckBox noExam;
    private JCheckBox noReport;

    public Kamoku() {
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JLabel msg = new JLabel("クリックされました");
                JOptionPane.showMessageDialog(panel, msg);
            }
        });
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(panel);
        pack();
        setVisible(true);
    }

    public void setData(Kamoku data) {
    }

    public void getData(Kamoku data) {
    }

    public boolean isModified(Kamoku data) {
        return false;
    }
}