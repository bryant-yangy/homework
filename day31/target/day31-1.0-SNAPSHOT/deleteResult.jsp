<%--
  Created by IntelliJ IDEA.
  User: 12652
  Date: 2019/1/14
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:if test="${result}" >
        <h1>该学生已删除</h1>
    </c:if>
    <c:if test="${not result}" >
        <h1>该学生不存在</h1>
    </c:if>
    <a href="index.html">返回主页</a>
</body>
</html>
