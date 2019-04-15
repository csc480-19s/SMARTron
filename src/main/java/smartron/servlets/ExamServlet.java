package smartron.servlets;

import com.google.gson.Gson;
import smartron.entities.Exam;
import smartron.entities.Professor;
import smartron.entities.Question;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExamServlet extends HttpServlet {
    private Gson gson = new Gson();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        Professor professor = new Professor();
        List<Exam> exams = new ArrayList<>();


        String name = request.getParameter("name");
        String lakerNetID = request.getParameter("em");
        professor.setEmail(lakerNetID);
        professor.setName(name);
        //
        // Database call here to get list of Exams
        //

        exams.add(new Exam("SDAHH","Exam the succ","now"));
        exams.add(new Exam("SDAHH","Exam the 2","now"));
        professor.setExams(exams);
        String questionJsonString = this.gson.toJson(professor);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(questionJsonString);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        try{
            String email = request.getParameter("email");
            String numQuest = request.getParameter("num");
            String nameOfTest = request.getParameter("name");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.println(this.gson.toJson(email + " " + numQuest + " " + nameOfTest ));


            //
            // Databse to put a new test in
            //
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

