<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.bjpowernode.crm.settings.domain.*,java.util.*" %>
<html>
<body>
<%
    int age=35;
    if(age>20){
%>
        <h2>Hello World!</h2>
<%
    }
%>

<hr>
<%
    request.setAttribute("age",15);
%>
<c:if test="${age>20}">
    <h2>Hello World!</h2>
</c:if>
<hr>

<%
    for(int i=1;i<=10;i++){
%>
        <h2>Hello World!</h2>
<%
    }
%>
<hr>
<%--
    vs.index：循环下表
    vs.count：循环次数
--%>
<c:forEach begin="0" end="10" varStatus="vs">
    <h2>${vs.index}Hello World!${vs.count}</h2>
</c:forEach>
<hr>
<%
    List<User> userList=new ArrayList<>();
    User user=new User();
    user.setId("1001");
    user.setName("zhangsan");
    user.setEmail("zhangsan@163.com");
    userList.add(user);

    user=new User();
    user.setId("1002");
    user.setName("lisi");
    user.setEmail("lisi@163.com");
    userList.add(user);

    user=new User();
    user.setId("1003");
    user.setName("wangwu");
    user.setEmail("wangwu@163.com");
    userList.add(user);

    //把数据保存到作用域中
    request.setAttribute("userList",userList);
%>

<table border="1">
    <tr>
        <th>ID</th>
        <th>name</th>
        <th>email</th>
    </tr>
    <%
        for(User u:userList){
    %>
    <tr>
    <td><%=u.getId()%></td>
    <td><%=u.getName()%></td>
    <td><%=u.getEmail()%></td>
</tr>
    <%
        }
    %>
    <hr>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>OrderNo</th>
            <th>name</th>
            <th>email</th>
        </tr>
        <c:forEach items="${userList}" var="u" varStatus="vs">
            <tr>
                <td>${u.id}</td>
                <td>${vs.count}</td>
                <td>${u.name}</td>
                <td>${u.email}</td>
            </tr>
        </c:forEach>
    </table>
</table>

</body>
</html>
