import java.util.*;

public class KamokuData extends ArrayList<String> {

    // 学期、曜日、必修選択を英語のキーと日本語の値で持つハッシュマップ
    private static HashMap<String, String> semesterCode;
    private static HashMap<String, String> dayCode;
    private static HashMap<String, String> typeCode;

    public KamokuData(){
        super();
        // 各ハッシュマップを設定
        semesterCode = new HashMap<String, String>();
        semesterCode.put("s", "春");
        semesterCode.put("f", "秋");
        dayCode = new HashMap<String, String>();
        dayCode.put("mon", "月");
        dayCode.put("tue", "火");
        dayCode.put("wed", "水");
        dayCode.put("thu", "木");
        dayCode.put("fri", "金");
        dayCode.put("sat", "土");
        dayCode.put("sun", "日");
        typeCode = new HashMap<String, String>();
        typeCode.put("r", "必修");
        typeCode.put("o", "選択");
    }

    /**
     * 配列に戻す時に、学期・曜日・必修選択をcsvの英語表記から漢字表記に戻す。
     * 出力された配列は検索結果のテーブルにそのまま出る。
     * @return Object[]
     */
    @Override
    public Object[] toArray() {
        Object[] superArray = super.toArray();
        superArray[Kamoku.SEMESTER] = semesterCode.get(superArray[Kamoku.SEMESTER]);
        superArray[Kamoku.DAY]      = dayCode.     get(superArray[Kamoku.DAY]);
        superArray[Kamoku.TYPE]     = typeCode.    get(superArray[Kamoku.TYPE]);
        return superArray;
    }

    /**
     * 曜日を漢字で返す
     * @return String
     */
    public String getDay(){
        return dayCode.get(this.get(Kamoku.DAY));
    }

    /**
     * 学期を漢字で返す
     * @return String
     */
    public String getSemester(){
        return semesterCode.get(this.get(Kamoku.SEMESTER));
    }
}
