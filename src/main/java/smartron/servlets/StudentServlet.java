package smartron.servlets;

import GUIMiddleware.JSONBuilder;
import com.google.gson.Gson;
import smartron.entities.Student;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentServlet extends HttpServlet {

    private Gson gson = new Gson();
    JSONBuilder jb = new JSONBuilder();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

//        List<Student> students = new ArrayList<>();

//        students.add(new Student("Sushmita Banerjee", "A", 100, 100));
//        students.add(new Student("Anshuman Banerjee", "A", 100, 100));
//        students.add(new Student("Nathan O'Leary", "A", 100, 100));
//        students.add(new Student("Robert Sgroi", "A", 100, 100));
//
//        String studentJsonString = this.gson.toJson(students);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


//        File studentpage = new File("Bystudent.txt");
//        Scanner studentPageScanner = new Scanner(studentpage);
//        String studentPageEndpointString = studentPageScanner.nextLine();
//        System.out.println(studentPageEndpointString);

       // out.print(jb.getBystudent());
        out.flush();
//        out.print(studentJsonString);
//        out.flush();
    }

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        doGet(request, response);
    }
}

