package smartron.servlets;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.*;
import java.io.InputStream;
import java.util.Properties;

public class NameChangeServlet extends HttpServlet {
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

            //String email = request.getParameter("email").split("@")[0];
            String id = request.getParameter("id");
            String nameOfTest = request.getParameter("name");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url,user,pass);


            Statement stmt = conn.createStatement();
            String sql = "select * from answerkey where exam_id=?";
            PreparedStatement checkStatement = conn.prepareStatement(sql);
            checkStatement.setString(1,id);
            ResultSet rs =checkStatement.executeQuery();
            rs.next();
            String instructorId =rs.getString(2);
            String answers = rs.getString(3);
            String updatedAnswers = rs.getString(4);
            String gradeScale = rs.getString(5);
            String oldName = rs.getString(6);

            sql = "delete from answerkey where exam_id=?";
            checkStatement =  conn.prepareStatement(sql);
            checkStatement.setString(1,id);
            checkStatement.execute();

            sql = "insert into answerkey (exam_id, instructor_id, answers, updated_answers, grade_scale,exam_name) values (?,?,?,?,?,?)";
            checkStatement = conn.prepareStatement(sql);
            checkStatement.setString(1,id);
            checkStatement.setString(2,instructorId);
            checkStatement.setString(3,answers);
            checkStatement.setString(4,updatedAnswers);
            checkStatement.setString(5,gradeScale);
            checkStatement.setString(6,nameOfTest);
            checkStatement.execute();




            PrintWriter out = response.getWriter();
           out.println(oldName + " " + id + " " + nameOfTest );

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
