<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getLocalPort()+request.getContextPath()+"/";
%>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

	<!--  PAGINATION plugin -->
	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>

<script type="text/javascript">

	$(function(){

		//给"开始日期"和"结束日期"添加日历
		$(".mydate").datetimepicker({
			language:'zh-CN',//使用中文
			format:'yyyy-mm-dd',//时间格式
			minView:'month',//最小可选时间
			initialDate:new Date(),//默认显示时间
			autoclose:true,//选中日期之后是否自动关闭日历
			todayBtn:true//是否显示当天按钮
		});

		//给"创建"按钮添加单击事件
		$("#createActivityBtn").click(function () {
			//查询所有的用户
			$.ajax({
				url:'workbench/activity/queryAllUsers.do',
				type:'post',
				dataType:'json',
				success:function (data) {//[{id:xxx,name:xxx},{id:xx,name},.....]
					var htmlStr="";
					$.each(data,function (index,obj) {
						htmlStr+="<option value=\""+obj.id+"\">"+obj.name+"</option>";
					});

					$("#create-marketActivityOwner").html(htmlStr);
				}
			});

			//清空表单中所有的数据
			//form元素的dom对象:reset()重置表单中所有输入组件。
			$("#createActivityForm")[0].reset();

			$("#createActivityModal").modal("show");
		});
		
		//给"保存"按钮添加单击事件
		$("#saveCreateActivityBtn").click(function () {
			//收集参数
			var owner=$("#create-marketActivityOwner").val();
			var name=$.trim($("#create-marketActivityName").val());
			var startDate=$("#create-startDate").val();
			var endDate=$("#create-endDate").val();
			var cost=$.trim($("#create-cost").val());
			var description=$.trim($("#create-describe").val());

			//参数验证
			if(owner==""){
				alert("所有者不能为空");
				return;
			}
			if(name==""){
				alert("名称不能为空");
				return;
			}
			if(startDate!=""&&endDate!=""){
				if(startDate>endDate){
					alert("开始日期不能比结束日期大");
					return;
				}
			}
			var regExp=/^([1-9]\d*|0)$/;
			if(!regExp.test(cost)){
				alert("成本只能是非负整数");
				return;
			}

			//发送请求
			$.ajax({
				url:'workbench/activity/saveCreateActivity.do',
				data:{
					owner:owner,
					name:name,
					startDate:startDate,
					endDate:endDate,
					cost:cost,
					description:description
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					if(data.code=="1"){
						//关闭模态窗口
						$("#createActivityModal").modal("hide");
						//刷新市场活动列表，保持每页显示条数不变
						queryActivityForPageByCondition(1,$("#paginationDiv").bs_pagination('getOption','rowsPerPage'));
					}else{
						//提示信息
						alert("创建失败");
						//模态窗口不关闭
						$("#createActivityModal").modal("show");
					}
				}
			});
		});


		//当页面加载完成之后，默认显示第一页数据，每页显示10条
		queryActivityForPageByCondition(1,10);

		//给"查询"按钮添加单击事件
		$("#queryActivityBtn").click(function () {
			//保持每页显示条数不变
			queryActivityForPageByCondition(1,$("#paginationDiv").bs_pagination('getOption','rowsPerPage'));
		});

		//全选和取消全选(作业)

		//给"修改"按钮添加单击事件
		$("#editActivityBtn").click(function () {
			//收集参数
			var chdIds=$("#tBody input[type='checkbox']:checked");
			if(chdIds.size()==0){
				alert("请选择要修改的记录");
				return;
			}
			if(chdIds.size()>1){
				alert("一次只能修改一条记录");
				return;
			}

			var id=chdIds[0].value;

			//发送请求
			$.ajax({
				url:'workbench/activity/toEditActivity.do',
				data:{
					id:id
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					//显示所有的用户
					var htmlStr="";
					$.each(data.userList,function (index,obj) {
						if(obj.id==data.activity.owner){
							htmlStr+="<option value=\""+obj.id+"\" selected=\"true\">"+obj.name+"</option>";
						}else{
							htmlStr+="<option value=\""+obj.id+"\">"+obj.name+"</option>";
						}
					});
					$("#edit-marketActivityOwner").html(htmlStr);

					//显示市场活动的其它字段信息
					$("#edit-marketActivityId").val(data.activity.id);
					$("#edit-marketActivityName").val(data.activity.name);
					$("#edit-startDate").val(data.activity.startDate);
					$("#edit-endDate").val(data.activity.endDate);
					$("#edit-cost").val(data.activity.cost);
					$("#edit-describe").val(data.activity.description);

					//显示模态窗口
					$("#editActivityModal").modal("show");
				}
			});
		});

		//给"更新"按钮添加单击事件
		$("#saveEditActivityBtn").click(function () {
			//收集参数
			var id=$("#edit-marketActivityId").val();
			var owner=$("#edit-marketActivityOwner").val();
			var name=$.trim($("#edit-marketActivityName").val());
			var startDate=$("#edit-startDate").val();
			var endDate=$("#edit-endDate").val();
			var cost=$.trim($("#edit-cost").val());
			var description=$.trim($("#edit-describe").val());
			//表单验证(作业)

			//发送请求
			$.ajax({
				url:'workbench/activity/saveEditActivity.do',
				data:{
					id:id,
					owner:owner,
					name:name,
					startDate:startDate,
					endDate:endDate,
					cost:cost,
					description:description
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					if(data.code=="1"){
						//关闭模态窗口
						$("#editActivityModal").modal("hide");
						//刷新市场活动列表
						queryActivityForPageByCondition($("#paginationDiv").bs_pagination('getOption','currentPage'),$("#paginationDiv").bs_pagination('getOption','rowsPerPage'));
					}else{
						alert("创建失败");
						$("#editActivityModal").modal("show");
					}
				}
			});
		});

		//给"删除"按钮添加单击事件
		$("#deleteActivityBtn").click(function () {
			//收集参数
			var chdIds=$("#tBody input[type='checkbox']:checked");
			if(chdIds.size()==0){
				alert("请选择要删除的记录");
				return;
			}
			var ids="";
			$.each(chdIds,function () {
				ids+="id="+$(this).val()+"&";
			});
			ids=ids.substr(0,ids.length-1);

			if(window.confirm("确定删除吗？")){
				//发送请求
				$.ajax({
					url:'workbench/activity/deleteActivity.do',
					data:ids,
					type:'post',
					dataType:'json',
					success:function (data) {
						if(data.code=="1"){
							//刷新市场活动列表，显示第一页数据，保持每页显示条数不变
							queryActivityForPageByCondition(1,$("#paginationDiv").bs_pagination('getOption','rowsPerPage'));
						}else{
							alert("删除失败");
						}
					}
				});
			}
		});

		//给"批量导出"按钮添加单击事件
		$("#exportActivityAllBtn").click(function () {
			window.location.href="workbench/activity/exportActivity.do";
		});

		//给"选择导出"按钮添加单击事件
		$("#exportActivityXzBtn").click(function () {
			//收集参数
			var chdIds=$("#tBody input[type='checkbox']:checked");
			if(chdIds.size()==0){
				alert("请选择要导出的市场活动");
				return;
			}

			var ids="";
			$.each(chdIds,function () {
				ids+="id="+this.value+"&";
			});
			ids=ids.substr(0,ids.length-1);

			//发送请求
			window.location.href="workbench/activity/exportSelectedActivity.do?"+ids;
		});

		//给"导入"按钮添加单击事件
		$("#importActivityBtn").click(function () {
			//收集参数
			var activityFile=$("#activityFile").val();//xx.xx.xx.XLS

			var suffix=activityFile.substr(activityFile.lastIndexOf(".")+1).toLowerCase();
			if(!(suffix=="xls"||suffix=="xlsx")){
				alert("只支持xls或者xlsx文件");
				return;
			}

			//用户选中的文件本身在file组件的dom对象的files属性中保存
			if($("#activityFile")[0].files[0].size>5*1024*1024){
				alert("文件大小不超过5MB");
				return;
			}

			//FormData是ajax提供的一个接口,能够模拟键值对向服务器发送参数；
			//FormData最大的优势既能够发送文本数据，也能发送二进制数据
			var formData=new FormData();
			formData.append("username","zhansan");
			formData.append("activityFile",$("#activityFile")[0].files[0]);

			//发送请求
			$.ajax({
				url:'workbench/activity/importActivity.do',
				data:formData,
				type:'post',
				dataType:'json',
				processData:false,//默认情况下，ajax每次在对所有参数进行urlencoded编码之前，统一转化为文本类型数据，把processData设置为false，可以阻止这种行为。
				contentType:false,//默认情况下，ajax每次向服务器发送请求之前，都会统一对参数进行urlencoded编码，把contentType设置为false，可以阻止这种行为。
				success:function (data) {
					if(data.code=="1"){
						//提示信息
						alert("成功导入"+data.count+"条数据");
						//关闭模态窗口
						$("#importActivityModal").modal("hide");
						//刷新市场活动列表
						queryActivityForPageByCondition(1,$("#paginationDiv").bs_pagination('getOption','rowsPerPage'));
					}else{
						alert("导入失败，请检查文件格式");
						$("#importActivityModal").modal("show");
					}
				}
			});
		});
	});


	//分页查询市场活动
	function queryActivityForPageByCondition(pageNo,pageSize) {
		//收集参数
		//var pageNo=1;
		//var pageSize=10;
		var name=$("#query-name").val();
		var owner=$("#query-owner").val();
		var startDate=$("#query-startDate").val();
		var endDate=$("#query-endDate").val();
		//发送请求
		$.ajax({
			url:'workbench/activity/queryActivityForPageByCondition.do',
			data:{
				pageNo:pageNo,
				pageSize:pageSize,
				name:name,
				owner:owner,
				startDate:startDate,
				endDate:endDate
			},
			type:'post',
			dataType:'json',
			success:function (data) {
				//显示总条数
				//$("#totalRowsId").html(data.totalRows);
				//显示数据列表
				var htmlStr="";
				$.each(data.dataList,function (index,obj) {
					if(index%2==0){
						htmlStr+="<tr class=\"active\">";
					}else{
						htmlStr+="<tr>";
					}
					htmlStr+="<td><input type=\"checkbox\" value=\""+obj.id+"\"/></td>";
					htmlStr+="<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/activity/detailActivity.do?id="+obj.id+"'\">"+obj.name+"</a></td>";
					htmlStr+="<td>"+obj.owner+"</td>";
					htmlStr+="<td>"+obj.startDate+"</td>";
					htmlStr+="<td>"+obj.endDate+"</td>";
					htmlStr+="</tr>";
				});
				//把htmlStr显示在列表中
				$("#tBody").html(htmlStr);

				//计算总页数
				var totalPages=1;
				if(data.totalRows%pageSize==0){
					totalPages=data.totalRows/pageSize;
				}else{
					totalPages=parseInt(data.totalRows/pageSize)+1;
				}

				//显示翻页信息
				$("#paginationDiv").bs_pagination({
					currentPage:pageNo,//当前页
					rowsPerPage:pageSize,//每页显示条数
					totalRows:data.totalRows,//总条数
					totalPages: totalPages,//总页数
					visiblePageLinks:2,//最多可以显示的页号卡片数
					showGoToPage:true,//是否显示跳转到第几页
					showRowsPerPage:true,//是否显示每页显示多少条
					showRowsInfo:true,//是否显示分页信息
					onChangePage: function(e,pageObj) { // returns page_num and rows_per_page after a link has clicked
						//alert(pageObj.currentPage);
						//alert(pageObj.rowsPerPage);
						queryActivityForPageByCondition(pageObj.currentPage,pageObj.rowsPerPage);
					}
				});
			}
		});
	}
</script>
</head>
<body>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
						<div class="modal-dialog" role="document" style="width: 85%;">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">
										<span aria-hidden="true">×</span>
									</button>
									<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
								</div>
								<div class="modal-body">

									<form id="createActivityForm" class="form-horizontal" role="form">

										<div class="form-group">
											<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
											<div class="col-sm-10" style="width: 300px;">
												<select class="form-control" id="create-marketActivityOwner">
													<option></option>
													<%--<option>zhangsan</option>
                                                      <option>lisi</option>
                                                      <option>wangwu</option>--%>
												</select>
											</div>
											<label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
											<div class="col-sm-10" style="width: 300px;">
												<input type="text" class="form-control" id="create-marketActivityName">
											</div>
										</div>

										<div class="form-group">
											<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
											<div class="col-sm-10" style="width: 300px;">
												<input type="text" class="form-control mydate" id="create-startDate" readonly>
											</div>
											<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
											<div class="col-sm-10" style="width: 300px;">
												<input type="text" class="form-control mydate" id="create-endDate" readonly>
											</div>
										</div>
										<div class="form-group">

											<label for="create-cost" class="col-sm-2 control-label">成本</label>
											<div class="col-sm-10" style="width: 300px;">
												<input type="text" class="form-control" id="create-cost">
											</div>
										</div>
										<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="saveCreateActivityBtn" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
						<input type="hidden" id="edit-marketActivityId">
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner">
								  <%--<option>zhangsan</option>
								  <option>lisi</option>
								  <option>wangwu</option>--%>
								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-startDate" value="2020-10-10">
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-endDate" value="2020-10-20">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" value="5,000">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="saveEditActivityBtn" type="button" class="btn btn-primary">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 导入市场活动的模态窗口 -->
    <div class="modal fade" id="importActivityModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 85%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">导入市场活动</h4>
                </div>
                <div class="modal-body" style="height: 350px;">
                    <div style="position: relative;top: 20px; left: 50px;">
                        请选择要上传的文件：<small style="color: gray;">[仅支持.xls或.xlsx格式]</small>
                    </div>
                    <div style="position: relative;top: 40px; left: 50px;">
                        <input type="file" id="activityFile">
                    </div>
                    <div style="position: relative; width: 400px; height: 320px; left: 45% ; top: -40px;" >
                        <h3>重要提示</h3>
                        <ul>
                            <li>操作仅针对Excel，仅支持后缀名为XLS/XLSX的文件。</li>
                            <li>给定文件的第一行将视为字段名。</li>
                            <li>请确认您的文件大小不超过5MB。</li>
                            <li>日期值以文本形式保存，必须符合yyyy-MM-dd格式。</li>
                            <li>日期时间以文本形式保存，必须符合yyyy-MM-dd HH:mm:ss的格式。</li>
                            <li>默认情况下，字符编码是UTF-8 (统一码)，请确保您导入的文件使用的是正确的字符编码方式。</li>
                            <li>建议您在导入真实数据之前用测试文件测试文件导入功能。</li>
                        </ul>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button id="importActivityBtn" type="button" class="btn btn-primary">导入</button>
                </div>
            </div>
        </div>
    </div>
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input id="query-name" class="form-control" type="text">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input id="query-owner" class="form-control" type="text">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="query-startDate" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="query-endDate">
				    </div>
				  </div>
				  
				  <button id="queryActivityBtn" type="button" class="btn btn-default">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button id="createActivityBtn" type="button" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button id="editActivityBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button id="deleteActivityBtn" type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				<div class="btn-group" style="position: relative; top: 18%;">
                    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importActivityModal" ><span class="glyphicon glyphicon-import"></span> 上传列表数据（导入）</button>
                    <button id="exportActivityAllBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（批量导出）</button>
                    <button id="exportActivityXzBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（选择导出）</button>
                </div>
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="tBody">
						<%--<tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detdetail.jsp>发传单</a></td>
                            <td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detdetail.jsp>发传单</a></td>
                            <td>zhangsan</td>
                            <td>2020-10-10</td>
                            <td>2020-10-20</td>
                        </tr>--%>
					</tbody>
				</table>
				<div id="paginationDiv"></div>
			</div>
			
			<%--<div style="height: 50px; position: relative;top: 30px;">
				<div>
					<button type="button" class="btn btn-default" style="cursor: default;">共<b id="totalRowsId">50</b>条记录</button>
				</div>
				<div class="btn-group" style="position: relative;top: -34px; left: 110px;">
					<button type="button" class="btn btn-default" style="cursor: default;">显示</button>
					<div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							10
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">20</a></li>
							<li><a href="#">30</a></li>
						</ul>
					</div>
					<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>
				</div>
				<div style="position: relative;top: -88px; left: 285px;">
					<nav>
						<ul class="pagination">
							<li class="disabled"><a href="#">首页</a></li>
							<li class="disabled"><a href="#">上一页</a></li>
							<li class="active"><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a href="#">下一页</a></li>
							<li class="disabled"><a href="#">末页</a></li>
						</ul>
					</nav>
				</div>
			</div>--%>
			
		</div>
		
	</div>
</body>
</html>