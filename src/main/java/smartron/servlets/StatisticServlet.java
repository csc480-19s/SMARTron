package smartron.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class StatisticServlet extends HttpServlet {
    private Gson gson = new Gson();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

       List<String> lst = new ArrayList<>();

       lst.add("Waiting for Statistics JSON");

        String questionJsonString = this.gson.toJson(lst);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(questionJsonString);
        out.flush();
    }

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        doGet(request, response);
    }
}
