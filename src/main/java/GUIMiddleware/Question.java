package GUIMiddleware;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Question {
    private int a, b, c, d, e = 0;

    public void increment(String n) {
        switch (n) {
            case "0":
                a++;
                break;
            case "1":
                b++;
                break;
            case "2":
                c++;
                break;
            case "3":
                d++;
                break;
            case "4":
                e++;
                break;
            default:
               // System.out.println("SMARTron question object add Error");
                break;
        }
    }

    //This method finds the question-number association (i.e. "2" -> C) and increments the
    //corresponding variable

    public JSONArray returnList() {
        JSONObject aa = new JSONObject();
        aa.put("name", "A");
        aa.put("value", this.a);
        JSONObject bb = new JSONObject();
        bb.put("name", "B");
        bb.put("value", this.b);
        JSONObject cc = new JSONObject();
        cc.put("name", "C");
        cc.put("value", this.c);
        JSONObject dd = new JSONObject();
        dd.put("name", "D");
        dd.put("value", this.d);
        JSONObject ee = new JSONObject();
        ee.put("name", "E");
        ee.put("value", this.e);

        JSONArray arr = new JSONArray();

        arr.add(aa);
        arr.add(bb);
        arr.add(cc);
        arr.add(dd);
        arr.add(ee);

        return arr;
    }

    //This builds json for the statistics by question
}
