package smartron.servlets;

import com.google.gson.Gson;

import Database.DataSource;
import smartron.entities.Exam;
import smartron.entities.Professor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ExamServlet extends HttpServlet {
     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Gson gson = new Gson();

	Connection conn = null;


    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        Professor professor = new Professor();
        
        String lakerNetEmail = request.getParameter("em").split("@")[0];
        professor.setEmail(lakerNetEmail);
        professor.setName(request.getParameter("nm"));
        try{
        	// Gets a connection using the datasource class and a loaded in db.properties file
            conn = DataSource.getInstance().getBasicDataSource().getConnection();
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
        } finally {
        	if (conn != null) {
        		try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        try{
            String email = request.getParameter("email").split("@")[0];
            String id = request.getParameter("id");
            String nameOfTest = request.getParameter("name");
            String number = request.getParameter("num");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            conn = DataSource.getInstance().getBasicDataSource().getConnection();
            Statement stmt = conn.createStatement();
            String sql = "select instructor_id from instructor where inst_email=?";
            PreparedStatement checkStatement = conn.prepareStatement(sql);
            checkStatement.setString(1,email);
            String instructorId;
            ResultSet rs =checkStatement.executeQuery();
            if(!rs.next()){
                instructorId =email;
                stmt.executeUpdate("insert into instructor (instructor_id,inst_email,answer_key_length) values ('" + email + "','" + email + "','" + number + "')");
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
        } finally {
        	if (conn != null) {
        		try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }

    }
}

