package web;

import dao.StudentDao;
import domain.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(urlPatterns = "/insertStudent")
public class insertStudent extends HttpServlet {
    StudentDao studentDao = new StudentDao(); // 只有一个对象

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String sname = req.getParameter("sname");
        String birthday = req.getParameter("birthday");
        String sex = req.getParameter("sex");
        Date date=null;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        try {
             date= simpleDateFormat.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Student student = new Student(sname, date, sex);
        int i = studentDao.insert(student);
        req.setAttribute("sid",i);
        req.getRequestDispatcher("insertResult.jsp").forward(req,resp);
    }
}
