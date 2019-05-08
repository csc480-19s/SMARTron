package smartron.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.DataSource;

import java.io.PrintWriter;
import java.sql.*;

public class NameChangeServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Connection conn = null;
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        try{
            //String email = request.getParameter("email").split("@")[0];
            String id = request.getParameter("id");
            String nameOfTest = request.getParameter("name");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            conn = DataSource.getInstance().getBasicDataSource().getConnection();

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
            String number = rs.getString(7);

            sql = "delete from answerkey where exam_id=?";
            checkStatement =  conn.prepareStatement(sql);
            checkStatement.setString(1,id);
            checkStatement.execute();

            sql = "insert into answerkey (exam_id, instructor_id, answers, updated_answers, grade_scale, exam_name, answer_key_length) values (?,?,?,?,?,?,?)";
            checkStatement = conn.prepareStatement(sql);
            checkStatement.setString(1,id);
            checkStatement.setString(2,instructorId);
            checkStatement.setString(3,answers);
            checkStatement.setString(4,updatedAnswers);
            checkStatement.setString(5,gradeScale);
            checkStatement.setString(6,nameOfTest);
            checkStatement.setString(7,number);
            checkStatement.execute();




            PrintWriter out = response.getWriter();
           out.println(oldName + " " + id + " " + nameOfTest );
           conn.close();

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
