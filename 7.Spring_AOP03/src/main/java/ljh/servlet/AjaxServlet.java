package ljh.servlet;

import com.google.gson.Gson;
import ljh.bean.Student;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AjaxServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        Student student1 = new Student("aaa", "admin@qq.com", 18);
        Student student2 = new Student("aaa2", "admin@qq.com", 18);
        Student student3 = new Student("aaa3", "admin@qq.com", 18);
        List<Student> students=new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);

        Gson gson = new Gson();

        String s = gson.toJson(students);

        System.out.println(s);

        resp.getWriter().write(s);

    }
}
