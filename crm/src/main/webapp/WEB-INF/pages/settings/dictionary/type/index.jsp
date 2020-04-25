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
			$("#createDicTypeBtn").click(function () {
				//跳转到创建页面
				window.location.href="settings/dictionary/type/toSave.do";
			});

			//给"全选"按钮添加单击事件
			$("#chd_all").click(function () {
				//获取"全选"按钮的checked属性值
				$("#tBody input[type='checkbox']").prop("checked",$(this).prop("checked"));
			});

			//给列表中所有的checkbox添加单击事件
			$("#tBody input[type='checkbox']").click(function () {
				//获取列表中所有checkbox
				if($("#tBody input[type='checkbox']").size()==$("#tBody input[type='checkbox']:checked").size()){
					$("#chd_all").prop("checked",true);
				}else{
					$("#chd_all").prop("checked",false);
				}
			});

			//给"编辑"按钮添加单击事件
			$("#editDicTypeBtn").click(function () {
				//收集参数
				var chdIds=$("#tBody input[type='checkbox']:checked");
				if(chdIds.size()==0){
					alert("请选择要编辑的记录");
					return;
				}
				if(chdIds.size()>1){
					alert("每次只能修改一条记录");
					return;
				}

				//获取选中的记录code
				var code=chdIds.get(0).value;

				//发送请求
				window.location.href="settings/dictionary/type/toEditDicType.do?code="+code;
			});

			//给"删除"按钮添加单击事件
			$("#deleteDicTypeBtn").click(function () {
				//收集参数
				var chdIds=$("#tBody input[type='checkbox']:checked");
				if(chdIds.size()==0){
					alert("请选择要删除的记录");
					return;
				}

				var ids="";
				$.each(chdIds,function () {
					ids+="code="+this.value+"&";
				});

				//code=xxx&code=xxxx&
				ids=ids.substr(0,ids.length-1);

				if(window.confirm("确定删除吗？")){
					//发送请求
					$.ajax({
						url:'settings/dictionary/type/deleteDicTypeByCodes.do',
						data:ids,
						type:'post',
						dataType:'json',
						success:function (data) {
							if(data.code=="1"){
								window.location.href="settings/dictionary/type/index.do";
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
				<h3>字典类型列表</h3>
			</div>
		</div>
	</div>
	<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
		<div class="btn-group" style="position: relative; top: 18%;">
		  <button id="createDicTypeBtn" type="button" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span> 创建</button>
		  <button id="editDicTypeBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
		  <button id="deleteDicTypeBtn" type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	<div style="position: relative; left: 30px; top: 20px;">
		<table class="table table-hover">
			<thead>
				<tr style="color: #B3B3B3;">
					<td><input id="chd_all" type="checkbox" /></td>
					<td>序号</td>
					<td>编码</td>
					<td>名称</td>
					<td>描述</td>
				</tr>
			</thead>
			<tbody id="tBody">
				<c:if test="${not empty dicTypeList}">
					<c:forEach items="${dicTypeList}" var="dt" varStatus="vs">
						<c:if test="${vs.count%2==0}">
							<tr class="active">
						</c:if>
						<c:if test="${vs.count%2!=0}">
							<tr>
						</c:if>
							<td><input type="checkbox" value="${dt.code}"/></td>
							<td>${vs.count}</td>
							<td>${dt.code}</td>
							<td>${dt.name}</td>
							<td>${dt.description}</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
	
</body>
</html>