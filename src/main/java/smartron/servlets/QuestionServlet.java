package smartron.servlets;

import GUIMiddleware.JSONBuilder;
import com.google.gson.Gson;
import smartron.entities.Question;
import smartron.entities.QuestionResponse;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.AreaAveragingScaleFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionServlet extends HttpServlet {
    private Gson gson = new Gson();
    JSONBuilder jb = new JSONBuilder();
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {


        List<Question> questions = new ArrayList<>();

        ArrayList<QuestionResponse> data = new ArrayList<>();

//        data.add(new QuestionResponse("A", 10));
//        data.add(new QuestionResponse("B", 5));
//        data.add(new QuestionResponse("C", 2));
//        data.add(new QuestionResponse("D", 2));
//        data.add(new QuestionResponse("E", 1));
//
//
//        questions.add(new Question("Exam 1", "VWXYZ", 1, "A", data));
//        questions.add(new Question("Exam 1", "VWXYZ", 2, "A", data));
//        questions.add(new Question("Exam 1", "VWXYZ", 3, "A", data));

        String questionJsonString = this.gson.toJson(questions);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
       // out.print(jb.getByquestion());
        out.flush();
    }

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        doGet(request, response);
    }
}
