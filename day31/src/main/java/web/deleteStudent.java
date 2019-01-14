package web;

import dao.StudentDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/deleteStudent")
public class deleteStudent extends HttpServlet {
    StudentDao studentDao = new StudentDao(); // 只有一个对象

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sid = req.getParameter("sid");
        int a= Integer.valueOf(sid);
        boolean b = studentDao.delete(a);
        req.setAttribute("result",b);
        req.getRequestDispatcher("deleteResult.jsp").forward(req,resp);
    }
}
