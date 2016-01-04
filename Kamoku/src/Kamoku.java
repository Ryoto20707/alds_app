import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class Kamoku extends JFrame{

    // csvの配列インデックス
    private static final int SUBJECT  = 0;
    private static final int SEMESTER = 1;
    private static final int DAY      = 2;
    private static final int CLASS    = 3;
    private static final int EXAM     = 4;
    private static final int REPORT   = 5;
    private static final int TYPE     = 6;

    private static final String[] header = new String[]{"科目名", "学期", "曜日", "時限", "試験", "レポート", "種別"};

    private JPanel panel;
    private JPanel searchWindow;
    private JPanel highLevelSearchWindow;
    private JPanel resultWindow;
    private JLabel semesterLabel;
    private JLabel dayLabel;
    private JLabel classLabel;
    private JTable resultTable;
    private JButton searchButton;
    private JCheckBox noExam;
    private JCheckBox noReport;
    private JComboBox semesterBox;
    private JComboBox dayBox;
    private JComboBox classBox ;
    private JScrollPane resultPane;
    private JRadioButton requiredButton;
    private JRadioButton optionalButton;
    private JRadioButton allTypeButton;

    public Kamoku() {
        ArrayList<KamokuData> array = readList();
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                createResultTable(array);
            }
        });
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(panel);
        pack();
        setVisible(true);
    }

    private ArrayList<KamokuData> readList(){
        final ArrayList<KamokuData> array = new ArrayList<KamokuData>();
        try {
            InputStreamReader isr = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("kamoku.csv"), "SJIS");
            BufferedReader br = new BufferedReader(isr);

            String line;
            StringTokenizer token;
            while((line = br.readLine()) != null) {
                KamokuData kamoku = new KamokuData();
                token = new StringTokenizer(line, ",");
                while(token.hasMoreTokens()) {
                    kamoku.add(token.nextToken());
                }
                array.add(kamoku);
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }

    private void createResultTable(ArrayList<KamokuData> array){
        DefaultTableModel model = new DefaultTableModel(new String[0][7], header);

        int count = 0;
        int size  = array.size();
        while(count < size){
            KamokuData kamoku = array.get(count++);
            if(!semesterBox.getSelectedItem().equals("") && !kamoku.getSemester().equals(semesterBox.getSelectedItem())) continue;
            if(!dayBox.     getSelectedItem().equals("") && !kamoku.getDay().     equals(dayBox.     getSelectedItem())) continue;
            if(!classBox.   getSelectedItem().equals("") && !kamoku.get(CLASS).   equals(classBox.   getSelectedItem())) continue;
            if(requiredButton.isSelected() && !kamoku.get(TYPE  ).equals("r")) continue;
            if(optionalButton.isSelected() && !kamoku.get(TYPE  ).equals("o")) continue;
            if(noExam.        isSelected() &&  kamoku.get(EXAM  ).equals("o")) continue;
            if(noReport.      isSelected() &&  kamoku.get(REPORT).equals("o")) continue;
            Object[] kamokuArray = kamoku.toArray();
            model.addRow(kamokuArray);
        }
        resultTable.setModel(model);
    }

    private void createUIComponents() {
        resultTable = new JTable(new String[][]{}, header);
    }
}