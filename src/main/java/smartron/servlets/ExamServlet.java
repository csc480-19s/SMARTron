package smartron.servlets;

import com.google.gson.Gson;
import smartron.entities.Exam;
import smartron.entities.Professor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.InputStream;
import java.util.Properties;


public class ExamServlet extends HttpServlet {
     private Gson gson = new Gson();



    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        Professor professor = new Professor();
        List<Exam> exams = new ArrayList<>();

        ServletContext context = getServletContext();
        InputStream is = context.getResourceAsStream("db.properties");
        Properties props = new Properties();
        props.load(is);
        String url = props.getProperty("url");
        String user = props.getProperty("user");
        String pass = props.getProperty("password");

        String lakerNetEmail = request.getParameter("em").split("@")[0];
        professor.setEmail(lakerNetEmail);
        professor.setName(request.getParameter("nm"));
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url,user,pass);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select instructor_id from instructor where inst_email='" + lakerNetEmail+"'");
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            List<String> list = new ArrayList<>();
            while (rs.next()){
                list.add(rs.getString(1));

            }
            if(list.size()==0){

            }else {
                String instId =list.get(0);
                rs = stmt.executeQuery("select exam_id,exam_name from answerkey where instructor_id='"  + instId +"'");
                HashMap<String,String> keyNameMap = new HashMap<>();
                while (rs.next()){
                    keyNameMap.put(rs.getString(1),rs.getString(2));
                    System.out.println(rs.getString(1) + " : " + rs.getString(2) );
                }
                for (String s: keyNameMap.keySet()
                     ) {
                    Exam e = new Exam(s,keyNameMap.get(s),"");
                    professor.addExam(e);
                }
                out.print(this.gson.toJson(professor));
                out.flush();
            }



        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error Selecting");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        try{
            ServletContext context = getServletContext();
            InputStream is = context.getResourceAsStream("db.properties");
            Properties props = new Properties();
            props.load(is);
            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String pass = props.getProperty("password");

            String email = request.getParameter("email").split("@")[0];
            String id = request.getParameter("id");
            String nameOfTest = request.getParameter("name");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url,user,pass);
            Statement stmt = conn.createStatement();
            String sql = "select instructor_id from instructor where inst_email=?";
            PreparedStatement checkStatement = conn.prepareStatement(sql);
            checkStatement.setString(1,email);
            String instructorId;
            ResultSet rs =checkStatement.executeQuery();
            if(!rs.next()){
                instructorId =email;
                stmt.executeUpdate("insert into instructor (instructor_id,inst_email) values ('" + email + "','" + email + "')");
            }
            else {
                rs.beforeFirst();
                rs.next();
                instructorId = rs.getString(1);
            }

            stmt.executeUpdate("insert into answerkey (exam_id,instructor_id,exam_name) values ('"+ id + "','" + instructorId +"','" + nameOfTest +"')");
            PrintWriter out = response.getWriter();
            out.println(email + " " + id + " " + nameOfTest );

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

