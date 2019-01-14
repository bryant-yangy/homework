package dao;

import domain.Student;
import util.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

// 数据访问对象
public class StudentDao {

    // 查询所有
    public List<Student> findAll() {
        try (Connection conn = JdbcUtils.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("select * from student")) {
                ResultSet rs = stmt.executeQuery();
                List<Student> list = new ArrayList<>();
                while (rs.next()) {
                    Student stu = new Student();
                    stu.setSid(rs.getInt("sid"));
                    stu.setSname(rs.getString("sname"));
                    stu.setBirthday(rs.getDate("birthday"));
                    stu.setSex(rs.getString("sex"));
                    list.add(stu);
                }
                return list;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // 添加学生
    public int  insert(Student student) {
        try (Connection conn = JdbcUtils.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("insert into student(sname,birthday,sex) values(?,?,?)",Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1,student.getSname());
                stmt.setDate(2,new java.sql.Date(student.getBirthday().getTime()));
                stmt.setString(3,student.getSex());
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);
                return  id;

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 根据 id 查询
    public Student findById(int sid) {
        try (Connection conn = JdbcUtils.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("select * from student")) {
                ResultSet rs = stmt.executeQuery();
                Student stu=null;
                while (rs.next()) {
                    if (sid == rs.getInt("sid")) {
                        stu = new Student();
                        stu.setSid(rs.getInt("sid"));
                        stu.setSname(rs.getString("sname"));
                        stu.setBirthday(rs.getDate("birthday"));
                        stu.setSex(rs.getString("sex"));
                        break;
                    }
                }
                return stu;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    // 修改学生
    public void update(Student student) {
        try (Connection conn = JdbcUtils.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("update student set sname=?,birthday=?,sex=? where sid=?")) {
                stmt.setInt(4,student.getSid());
                stmt.setString(1,student.getSname());
                stmt.setDate(2,new java.sql.Date(student.getBirthday().getTime()));
                stmt.setString(3,student.getSex());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // 删除学生
    public boolean delete(int sid) {
        try (Connection conn = JdbcUtils.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("delete from student where sid=?")) {
                stmt.setInt(1,sid);
                int i = stmt.executeUpdate();
                if(i==1){
                    return true;
                }else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        StudentDao s =new StudentDao();
        Student student = new Student(1010, "wangp", new Date(), "男");
        s.update(student);
    }
}
