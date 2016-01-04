import java.util.ArrayList;
import java.util.HashMap;

public class KamokuData extends ArrayList<String> {

    private static final int SUBJECT  = 0;
    private static final int SEMESTER = 1;
    private static final int DAY      = 2;
    private static final int CLASS    = 3;
    private static final int EXAM     = 4;
    private static final int REPORT   = 5;
    private static final int TYPE     = 6;

    private static HashMap<String, String> semesterCode;
    private static HashMap<String, String> dayCode;
    private static HashMap<String, String> typeCode;

    public KamokuData(){
        super();
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

    @Override
    public Object[] toArray() {
        Object[] superArray = super.toArray();
        superArray[SEMESTER] = semesterCode.get(superArray[SEMESTER]);
        superArray[DAY]      = dayCode.     get(superArray[DAY]);
        superArray[TYPE]     = typeCode.    get(superArray[TYPE]);
        return superArray;
    }

    public String getDay(){
        return dayCode.get(this.get(DAY));
    }

    public String getSemester(){
        return semesterCode.get(this.get(SEMESTER));
    }
}
