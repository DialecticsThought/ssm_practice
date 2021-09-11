<%--
  Created by IntelliJ IDEA.
  User: ���κ�
  Date: 2021/7/19
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="webjars/jquery/3.6.0/jquery.min.js"></script>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <%
    pageContext.setAttribute("ctp",request.getContextPath());
  %>
  <body>
  <a href="ajax">ajax</a>
  <script type="text/javascript">
    $("a[href='ajax']").click(function (){
      alert("hello world");
      //项目中直接用绝对路径
      $.ajax({
        url:"${ctp}/ajax",
        type:"POST",
        dataType:"JSON",
        success:function (data){
          console.log(data);
        }
      })
      //取消默认行为
      return false;
    });
  </script>
  </body>
</html>
