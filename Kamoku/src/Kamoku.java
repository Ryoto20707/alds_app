import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class Kamoku extends JFrame{

    // csvの配列インデックス
    static final int SUBJECT  = 0;
    static final int SEMESTER = 1;
    static final int DAY      = 2;
    static final int CLASS    = 3;
    static final int EXAM     = 4;
    static final int REPORT   = 5;
    static final int TYPE     = 6;

    //テーブルのヘッダ
    private static final String[] header = new String[]{"科目名", "学期", "曜日", "時限", "試験", "レポート", "種別"};

    /*
     * JFrameに出てくる要素
     * JWindowは画面のまとまり
     * JLabelは画面上の文字
     * JTableは下の表
     * JButtonは検索ボタン
     * JCheckBoxはチェックボックス
     * JComboBoxはプルダウンボックス
     * JScrollPaneはスクロール画面。この中にテーブルが入ってる。
     * JRadioButtonはラジオボタン
     *
     * Panel、Labelはコードに直接影響しない
     */
    private JPanel panel;
    private JPanel searchWindow;          // 科目検索
    private JPanel highLevelSearchWindow; // 高度な検索
    private JPanel resultWindow;          // 検索結果
    private JLabel semesterLabel;         // "学期"
    private JLabel dayLabel;              // "曜日"
    private JLabel classLabel;            // "限"
    private JTable resultTable;           // 検索結果のテーブル
    private JButton searchButton;         // "検索"
    private JCheckBox noExam;             // "試験なし"
    private JCheckBox noReport;           // "レポートなし"
    private JComboBox semesterBox;        // 学期選択メニュー
    private JComboBox dayBox;             // 曜日選択メニュー
    private JComboBox classBox;           // 時限選択メニュー
    private JScrollPane resultPane;       // resultTableを囲うスクロール画面
    private JRadioButton requiredButton;  // "必修"
    private JRadioButton optionalButton;  // "選択"
    private JRadioButton allTypeButton;   // "指定しない"

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
     * よってこの関数の返り値は実質二次元配列である。
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
            // KamokuDataのArrayListから一つずつ取得。あとでcontinueしてもいいように、countはここでインクリメントしておく
            KamokuData kamoku = array.get(count++);

            // 学期と曜日と時間が空でない時に一致しないものは除外
            if(!semesterBox.getSelectedItem().equals("") && !kamoku.getSemester().equals(semesterBox.getSelectedItem())) continue;
            if(!dayBox.     getSelectedItem().equals("") && !kamoku.getDay().     equals(dayBox.getSelectedItem())) continue;
            if(!classBox.   getSelectedItem().equals("") && !kamoku.get(CLASS).   equals(classBox.getSelectedItem())) continue;

            // 高度な検索条件に一致しないものは除外
            if(requiredButton.isSelected() && !kamoku.get(TYPE  ).equals("r")) continue;
            if(optionalButton.isSelected() && !kamoku.get(TYPE  ).equals("o")) continue;
            if(noExam.        isSelected() &&  kamoku.get(EXAM  ).equals("o")) continue;
            if(noReport.      isSelected() &&  kamoku.get(REPORT).equals("o")) continue;

            /*
             * 除外されなければTableModelに行として追加。toArrayの時に"mon"->"月"や"r"->"必修"などに修正
             * （kamokuArrayがtoArray()をオーバーライドしてある）。
             */
            Object[] kamokuArray = kamoku.toArray();
            model.addRow(kamokuArray);
        }
        // 上で条件に一致した行だけが入ったTableModelを検索結果テーブルにセット
        resultTable.setModel(model);
    }

    private void createUIComponents() {
        // 検索結果のテーブルの初期状態。中身は空で、ヘッダーだけつける。
        resultTable = new JTable(new String[][]{}, header);
    }
}