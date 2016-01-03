import javax.swing.*;
import java.awt.event.*;
import java.util.*;

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
                showSelected();
            }
        });
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(panel);
        pack();
        setVisible(true);
    }

    private void showSelected(){
        ArrayList<String> options = new ArrayList<String>();
        if(required.isSelected()) {
            options.add("必修科目");
        }
        if(optional.isSelected()) {
            options.add("選択科目");
        }
        if(noExam.isSelected()) {
            options.add("試験なし");
        }
        if(noReport.isSelected()) {
            options.add("レポートなし");
        }
        JLabel msg = new JLabel(options.toString());
        JOptionPane.showMessageDialog(panel, msg);
    }

    public void setData(Kamoku data) {
    }

    public void getData(Kamoku data) {
    }

    public boolean isModified(Kamoku data) {
        return false;
    }
}