<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getLocalPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath %>">
    <title>演示文件上传</title>
</head>
<body>
    <%--
        文件上传的表单：
            1、必须使用file组件上传文件。
            2、发送请求的方式只能是post。
               get:参数在请求头中发送，URL后提交，只能提交文本数据，不能提交二进制数据，参数会暴露在地址栏，参数的长度有限制。
               post:参数在请求体中发送，不但能提交文本数据，还能提交二进制数据，参数不会暴露在地址栏，理论上长度没有限制。
            3、必须指定表单的编码方式为：multipart/form-data。
               默认情况下，浏览器每次向服务提交数据之前，都会统一对表单数据进行编码，默认使用application/x-www-form-urlencoded编码方式，这种编码方式只能对文本数据进行编码；
               有文件上传的表单，必须设置enctype="multipart/form-data"。
    --%>
    <form action="workbench/activity/upload.do" method="post" enctype="multipart/form-data">
        <input type="file" name="myFile"><br>
        <input type="text" name="username"><br>
        <input type="submit" value="提交">
    </form>
</body>
</html>
