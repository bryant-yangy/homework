package web;

import dao.StudentDao;
import domain.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/findStudentById")
public class findStudentById extends HttpServlet {
    StudentDao studentDao = new StudentDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String sid = req.getParameter("sid");
        int a= Integer.valueOf(sid);
        Student stu = studentDao.findById(a);
        req.setAttribute("student", stu);
        req.getRequestDispatcher("updateResult1.jsp").forward(req, resp);
    }
}
