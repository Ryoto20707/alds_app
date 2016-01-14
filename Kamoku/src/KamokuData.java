import java.util.*;

public class KamokuData extends ArrayList<String> {

    // 学期、曜日、必修選択を英語のキーと日本語の値で持つハッシュマップ
    private static HashMap<String, String> semesterCode;
    private static HashMap<String, String> dayCode;
    private static HashMap<String, String> typeCode;

    public KamokuData(){
        // スーパークラス(ArrayList<String>)のコンストラクタを呼ぶことで普通のArrayListを作成する。
        super();
        // 各変換用ハッシュマップを設定
        semesterCode = new HashMap<String, String>();
        semesterCode.put("s", "春");
        semesterCode.put("f", "秋");
        semesterCode.put("?", "?");
        dayCode = new HashMap<String, String>();
        dayCode.put("mon", "月");
        dayCode.put("tue", "火");
        dayCode.put("wed", "水");
        dayCode.put("thu", "木");
        dayCode.put("fri", "金");
        dayCode.put("sat", "土");
        dayCode.put("sun", "日");
        dayCode.put("?"  , "?");
        typeCode = new HashMap<String, String>();
        typeCode.put("r", "必修");
        typeCode.put("o", "選択");
        typeCode.put("?", "?");
    }

    /**
     * 配列に戻す時に、学期・曜日・必修選択をcsvの英語表記から漢字表記に戻す。
     * 出力された配列は検索結果のテーブルにそのまま出る。
     * @return Object[]
     */
    @Override
    public Object[] toArray() {
        /*
         * ArrayListクラスはもともと配列に変換するtoArray()メソッドを持ってる。
         * まずはそれを素直にarrayに代入する。
         */
        Object[] array = super.toArray();
        /*
         * ここから学期、曜日、種別を日本語変換する。
         * SEMESTER, DAY, TYPEはKamoku.javaの冒頭参照
         */
        array[Kamoku.SEMESTER] = semesterCode.get(array[Kamoku.SEMESTER]);
        array[Kamoku.DAY]      = dayCode.     get(array[Kamoku.DAY]);
        array[Kamoku.TYPE]     = typeCode.    get(array[Kamoku.TYPE]);
        return array;
    }

    /**
     * 曜日を漢字で返す
     * @return String
     */
    public String getDay(){
        /*
         * this.get(Kamoku.DAY)でKamokuDataインスタンス自身の曜日情報("mon"~"sun")を返す。
         * 従ってこの関数はdayCode.get("mon") = "月"などを返す
         */
        return dayCode.get(this.get(Kamoku.DAY));
    }

    /**
     * 学期を漢字で返す
     * @return String
     */
    public String getSemester(){
        // 上記getDay()と似た動作で"春", "秋", "?"のいずれかを返す
        return semesterCode.get(this.get(Kamoku.SEMESTER));
    }
}
