<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/1/13
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:if test="${empty student}">
        <h1>查不到该学生</h1>
        <a href="index.html">返回主页</a>
    </c:if>
    <c:if test="${ not empty student}">
        <table border="1" width="100%">
            <thead>
            <tr>
                <th>编号</th>
                <th>姓名</th>
                <th>生日</th>
                <th>性别</th>
            </tr>
            </thead>
            <tbody>

                <tr>
                    <td>${student.sid}</td>
                    <td>${student.sname}</td>
                    <td><fmt:formatDate value="${student.birthday}" pattern="yyyy-MM-dd"/></td>
                    <td>${student.sex}</td>
                </tr>
            </tbody>
        </table>
        <p>请填写要修改的数据</p>
        <form action="updateStudent">
        <p>
            学号<input type="text" name="sid" value="${student.sid}" hidden>
        </p>
        <p>
            姓名<input type="text" name="sname" value="${student.sname}">
        </p>
        <p>
            生日<input type="date" name="birthday" value="${student.birthday}">
        </p>
        <p>
            性别 男<input type="radio" name="sex" value="男">
            女<input type="radio" name="sex" value="女">
        </p>
            <input type="submit">
        </form>
    </c:if>
</body>
</html>
