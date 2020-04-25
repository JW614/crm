<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getLocalPort()+request.getContextPath()+"/";
%>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function () {
            //给"创建"按钮添加单击事件
            $("#createDicValueBtn").click(function () {
                window.location.href="settings/dictionary/value/toSaveDicValue.do";
            });

            //实现全选和取消全选(作业)

			//给"编辑"按钮添加单击事件
			$("#editDicValueBtn").click(function () {
				var chdIds=$("#tBody input[type='checkbox']:checked");
				if(chdIds.size()==0){
					alert("请选择要编辑的记录");
					return;
				}
				if(chdIds.size()>1){
					alert("每次只能编辑一条记录");
					return;
				}
				var id=chdIds[0].value;

				window.location.href="settings/dictionary/value/toEditDicValue.do?id="+id;
			});

			//给"删除"按钮添加单击事件
			$("#deleteDicValueBtn").click(function () {
				//收集参数
				var chdIds=$("#tBody input[type='checkbox']:checked");
				if(chdIds.size()==0){
					alert("请选择要删除的记录");
					return;
				}

				if(window.confirm("确定删除吗？")){
					var ids="";
					$.each(chdIds,function() {
						ids+="id="+this.value+"&";
					});
					ids=ids.substr(0,ids.length-1);//id=xxxx&id=xxx&....&id=xxx

					//发送请求
					$.ajax({
						url:'settings/dictionary/value/deleteDicValueByIds.do',
						data:ids,
						type:'post',
						dataType:'json',
						success:function (data) {//json(字符串)====>data(data是个js对象)
							if(data.code=="1"){
								window.location.href="settings/dictionary/value/index.do";
							}else{
								alert("删除失败");
							}
						}
					});
				}
			});
        });
    </script>
</head>
<body>

	<div>
		<div style="position: relative; left: 30px; top: -10px;">
			<div class="page-header">
				<h3>字典值列表</h3>
			</div>
		</div>
	</div>
	<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
		<div class="btn-group" style="position: relative; top: 18%;">
		  <button id="createDicValueBtn" type="button" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span> 创建</button>
		  <button id="editDicValueBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
		  <button id="deleteDicValueBtn" type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	<div style="position: relative; left: 30px; top: 20px;">
		<table class="table table-hover">
			<thead>
				<tr style="color: #B3B3B3;">
					<td><input type="checkbox" /></td>
					<td>序号</td>
					<td>字典值</td>
					<td>文本</td>
					<td>排序号</td>
					<td>字典类型编码</td>
				</tr>
			</thead>
			<tbody id="tBody">
				<c:if test="${not empty dicValueList}">
					<c:forEach items="${dicValueList}" var="dv" varStatus="vs">
						<c:if test="${vs.count%2==0}">
							<tr class="active">
						</c:if>
						<c:if test="${vs.count%2!=0}">
							<tr>
						</c:if>

							<td><input type="checkbox" value="${dv.id}"/></td>
							<td>${vs.count}</td>
							<td>${dv.value}</td>
							<td>${dv.text}</td>
							<td>${dv.orderNo}</td>
							<td>${dv.typeCode}</td>
						</tr>
					</c:forEach>
				</c:if>

				<%--<tr class="active">
					<td><input type="checkbox" /></td>
					<td>1</td>
					<td>m</td>
					<td>男</td>
					<td>1</td>
					<td>sex</td>
				</tr>
				<tr>
					<td><input type="checkbox" /></td>
					<td>2</td>
					<td>f</td>
					<td>女</td>
					<td>2</td>
					<td>sex</td>
				</tr>
				<tr class="active">
					<td><input type="checkbox" /></td>
					<td>3</td>
					<td>1</td>
					<td>一级部门</td>
					<td>1</td>
					<td>orgType</td>
				</tr>
				<tr>
					<td><input type="checkbox" /></td>
					<td>4</td>
					<td>2</td>
					<td>二级部门</td>
					<td>2</td>
					<td>orgType</td>
				</tr>
				<tr class="active">
					<td><input type="checkbox" /></td>
					<td>5</td>
					<td>3</td>
					<td>三级部门</td>
					<td>3</td>
					<td>orgType</td>
				</tr>--%>
			</tbody>
		</table>
	</div>
	
</body>
</html>