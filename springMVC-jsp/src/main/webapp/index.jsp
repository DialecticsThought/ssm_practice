<%--
  Created by IntelliJ IDEA.
  User: 刘嘉豪
  Date: 2021/8/25
  Time: 20:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>首页</h1>
<%--pageContext最小的域对象 范围是这个jsp   contextPath是上下文路径--%>
<a href="${pageContext.request.contextPath}/success">success.jsp</a>

<form action="${pageContext.request.contextPath}/testPOJO" method="post">
    <%--表单中的name属性值 与pojo类中的属性名保持一致--%>
    工号 <input type="text" name="id"><br/>
    姓名 <input type="text" name="lastnName"><br/>
    邮箱 <input type="text" name="email"><br/>
    部门编号 <input type="text" name="dept.id"><br/>
    部门名称 <input type="text" name="dept.name"><br/>
    <input type="submit"value="提交">

</form>

</body>
</html>
