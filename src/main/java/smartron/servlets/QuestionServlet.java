package smartron.servlets;

import com.google.gson.Gson;
import smartron.entities.Question;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionServlet extends HttpServlet {
    private Gson gson = new Gson();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        List<Question> questions = new ArrayList<>();

        HashMap<String, Integer> rf = new HashMap<>();
        HashMap<String, Integer> rp = new HashMap<>();

        rf.put("A",10);
        rf.put("B",5);
        rf.put("C",1);
        rf.put("D",2);
        rf.put("E",2);

        rp.put("A",(10/18)*100);
        rp.put("B",(5/18)*100);
        rp.put("C",(1/18)*100);
        rp.put("D",(2/18)*100);
        rp.put("E",(2/18)*100);

        questions.add(new Question("ABCDE", 1, "A", rf, rp));
        questions.add(new Question("ABCDE", 2, "B", rf, rp));
        questions.add(new Question("ABCDE", 3, "C", rf, rp));
        questions.add(new Question("ABCDE", 4, "D", rf, rp));

        String questionJsonString = this.gson.toJson(questions);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(questionJsonString);
        out.flush();
    }
}
