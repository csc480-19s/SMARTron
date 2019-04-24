package smartron.servlets;

import GUIMiddleware.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResultServlet extends HttpServlet {
    String instID;
    String examID;
    String[] args = new String[2];

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        instID = req.getParameter("instID");

        String[] inst = instID.split("@");
        examID = req.getParameter("examID");
        args[0] = inst[0];
        args[1] = examID;

            response(resp, "InstructorID: " + instID + " " + "ExamID: " + examID);

        try {
            GUIMiddleware.Main.main(args);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void response(HttpServletResponse resp, String msg)
            throws IOException {
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("<t1>" + msg + "</t1>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        response(resp, "InstructorID: " + instID + " " + "ExamID: " + examID);

    }

}
