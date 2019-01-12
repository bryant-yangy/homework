import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/servlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println(username+"   "+password);
        int i=0;
        try {
           i= Mysql.insert(username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(i==1){
            req.getRequestDispatcher("success.jsp").forward(req,resp);
        }else{
            req.getRequestDispatcher("false.jsp").forward(req,resp);
        }
    }
}
