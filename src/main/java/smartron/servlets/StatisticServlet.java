package smartron.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import GUIMiddleware.JSONBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StatisticServlet extends HttpServlet {
    private Gson gson = new Gson();
    JSONBuilder jb = new JSONBuilder();
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

       List<String> lst = new ArrayList<>();



        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        File statsPage = new File("Stats.txt");
        Scanner studentPageScanner = new Scanner(statsPage);
        String studentPageEndpointString = studentPageScanner.nextLine();
        System.out.println(studentPageEndpointString);

        out.print(jb.getBystats());
        out.flush();
    }

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        doGet(request, response);
    }
}
