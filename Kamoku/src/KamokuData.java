import java.util.ArrayList;
import java.util.HashMap;

public class KamokuData extends ArrayList<String> {

    private static final int SUBJECT = 0;
    private static final int DAY     = 1;
    private static final int CLASS   = 2;
    private static final int EXAM    = 3;
    private static final int REPORT  = 4;
    private static final int TYPE    = 5;

    private static HashMap<String, String> dayCode;
    private static HashMap<String, String> typeCode;

    public KamokuData(){
        super();
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
        superArray[DAY]  = dayCode. get(superArray[DAY]);
        superArray[TYPE] = typeCode.get(superArray[TYPE]);
        return superArray;
    }

    public String getDay(){
        return dayCode.get(this.get(DAY));
    }
}
