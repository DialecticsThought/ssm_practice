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
  <div id="displayStudent"></div>
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
          $('#displayStudent').empty()
          //遍历对象 $()实现jquery包装
          //index表示第几个  item表示对应的项
          $(data).each(function (index,item){
            //this代表每一次遍历的对象
            //alert(this);
            $('#displayStudent')
                    .append("<br/>学生姓名"+item.lastName).append("<br/>学生年龄"+item.age).append("<br/>学生邮箱"+item.email)
          })
        }
      })
      //取消默认行为
      return false;
    });
  </script>
  </body>
</html>
