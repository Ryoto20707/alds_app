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

    //テーブルのヘッダ
    private static final String[] header = new String[]{"科目名", "学期", "曜日", "時限", "試験", "レポート", "種別"};

    // JFrameに出てくる要素　
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

    // Mainでコンストラクタを呼ぶ、コンストラクタ内で画面起動
    public Kamoku() {
        // csvのデータを読み込む
        ArrayList<KamokuData> array = readList();

        // 検索ボタンが押された時の反応
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // テーブルに検索結果を表示する
                createResultTable(array);
            }
        });

        // 画面起動のコード
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(panel);
        pack();
        setVisible(true);
    }

    /**
     * csvのデータを格納したArrayListを返すコード
     * ArrayListに格納されるのはKamokuDataクラスのインスタンスで
     * KamokuDataクラスはStringのArrayListを継承している。
     * よってコンストラクタの返り値は実質多次元配列である。
     * @return ArrayList
     */
    private ArrayList<KamokuData> readList(){
        final ArrayList<KamokuData> array = new ArrayList<KamokuData>();
        try {
            // csvをShift-JISで読み込む。jarファイルに出力することを考慮しInputStreamReaderの第一引数は変更してある。
            InputStreamReader isr = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("kamoku.csv"), "SJIS");
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringTokenizer token;

            // 一行ごとの操作
            while((line = br.readLine()) != null) {
                KamokuData kamoku = new KamokuData();
                token = new StringTokenizer(line, ",");
                // カンマごとに区切ってKamokuData(ArrayList)に加える
                while(token.hasMoreTokens()) {
                    kamoku.add(token.nextToken());
                }
                // 一行読み込んだらKamokuDataのArrayListに加える
                array.add(kamoku);
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }

    /**
     * JTable(検索結果テーブル)に検索結果を出力する
     * @param array 科目情報のテーブル
     */
    private void createResultTable(ArrayList<KamokuData> array) {
        // JTableの中身をTableModelで決定、初期状態はheaderのみで中身は0行7列。
        DefaultTableModel model = new DefaultTableModel(new String[0][7], header);

        int count = 0;
        int size  = array.size();
        while(count < size) {
            // KamokuDataのArrayListから一つずつ取得
            KamokuData kamoku = array.get(count++);

            // 曜日と時間が空でない時に一致しないものは除外
            if(!semesterBox.getSelectedItem().equals("") && !kamoku.getSemester().equals(semesterBox.getSelectedItem())) continue;
            if(!dayBox.     getSelectedItem().equals("") && !kamoku.getDay().     equals(dayBox.     getSelectedItem())) continue;
            if(!classBox.   getSelectedItem().equals("") && !kamoku.get(CLASS).   equals(classBox.   getSelectedItem())) continue;

            // 高度な検索条件に一致しないものは除外
            if(requiredButton.isSelected() && !kamoku.get(TYPE  ).equals("r")) continue;
            if(optionalButton.isSelected() && !kamoku.get(TYPE  ).equals("o")) continue;
            if(noExam.        isSelected() &&  kamoku.get(EXAM  ).equals("o")) continue;
            if(noReport.      isSelected() &&  kamoku.get(REPORT).equals("o")) continue;
            // 除外されなければTableModelに追加。toArrayで"mon"->"月"や"r"->"必修"などに修正。
            Object[] kamokuArray = kamoku.toArray();
            model.addRow(kamokuArray);
        }
        resultTable.setModel(model);
    }

    private void createUIComponents() {
        // 検索結果のテーブルの初期状態。ヘッダーだけつける。
        resultTable = new JTable(new String[][]{}, header);
    }
}