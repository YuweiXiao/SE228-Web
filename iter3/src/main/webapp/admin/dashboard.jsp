<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>

<%
   String path = request.getContextPath();
   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<title>Alpha BookStore</title>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="<%=path %>/static/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=path %>/static/css/style.css">
	<link rel="stylesheet" type="text/css" href="<%=path %>/static/css/themes/bootstrap/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=path %>/static/css/themes/icon.css">
	<style type="text/css">
		iframe {
			width:100%
		}
	</style>
	<script type="text/javascript">
		const base_url = '<%= basePath%>';
	</script>

</head>
<body>
    <div style="width:96%; margin-left:auto;margin-right:auto;">
    	<p id="callback"> </p>
		<h1 class="page-header" style="padding-left: 20px">Dashboard</h1>
    	<div style="float:left;width:12%">
    		<ul class="nav">
	            <li class="active"><a href="#">Overview <span class="sr-only">(current)</span></a></li>
	            <li><a href="#" class="" onclick="addTab('Edit book','<%=basePath%>admin/manageBook')">Edit book</a></li>
	            <li>
				<a href="#" class="" onclick="addTab('Edit user','<%=basePath%>admin/manageUser')">Edit users</a></li>
	            <li><a href="#" class="" onclick="addTab('Edit orders','<%=basePath%>admin/manageOrder')">Edit orders</a></li>
	            <li><a href="#" class="" onclick="addTab('Sales Analysis 1','<%=basePath%>admin/analysis_salesByDay')">Sales Analysis - 1</a></li>
	            <li><a href="#" class="" onclick="addTab('Sales Analysis 2','<%=basePath%>admin/analysis_salesByGivenDate')">Sales Analysis - 2</a></li>
	            <li><a href="#" class="" onclick="addTab('Category Analysis','<%=basePath%>admin/analysis_amountByCategory')">Category Analysis</a></li>
	            <li><a href="#" class="" onclick="addTab('User Analysis','<%=basePath%>admin/analysis_userBehavior')">User Analysis</a></li>

          	</ul>
		</div>
		<div style="float: left;width:88%;">
			
			<div id="tt" class="easyui-tabs" style="width: inherit">
				<div title="Home" style="padding:20px">
					<p>Hello. </p>
					<p>Here, you can update\insert\delete\query books and users infomation.</p>
					<p>And you can update\insert\delete order info.</p>
					<p>If you want to create new order, you can go to the store index page, browse books and place orders</p>
				</div>
			</div>
		</div>
	</div>

	<script src="<%= path %>/static/js/jquery-1.12.2.min.js"></script>
	<script src="<%= path %>/static/js/bootstrap.min.js"></script>	
	<script src="<%= path %>/static/js/jquery.easyui.min.js"></script>
	<script src="<%= path %>/static/js/iframeResizer.min.js"></script>

	<script type="text/javascript">
		
		function addTab(title, url){
			if ($('#tt').tabs('exists', title)){
				$('#tt').tabs('select', title);
			} else {
				console.log($('#tt').outerHeight(true));
				console.log($('#tt').height());
				var height = $('#tt').outerHeight(true) - 70;
				var content = '<iframe scrolling="no" frameborder="0"  src="'+url+'"style="width:inherit;"></iframe>';
				$('#tt').tabs('add',{
					title:title,
					content:content,
					closable:true,
					
					border:false,
				});
			}
			$(function() {
				console.log('??');
				iFrameResize({
					log                     : false,                  // Enable console logging
					enablePublicMethods     : true,                  // Enable methods within iframe hosted page
					// resizedCallback         : function(messageData){ // Callback fn when resize is received
					// 	$('p#callback').html(
					// 		'<b>Frame ID:</b> '    + messageData.iframe.id +
					// 		' <b>Height:</b> '     + messageData.height +
					// 		' <b>Width:</b> '      + messageData.width + 
					// 		' <b>Event type:</b> ' + messageData.type
					// 	);
					// },
					// messageCallback         : function(messageData){ // Callback fn when message is received
					// 	$('p#callback').html(
					// 		'<b>Frame ID:</b> '    + messageData.iframe.id +
					// 		' <b>Message:</b> '    + messageData.message
					// 	);
					// 	alert(messageData.message);
					// },
					// closedCallback         : function(id){ // Callback fn when iFrame is closed
					// 	$('p#callback').html(
					// 		'<b>IFrame (</b>'    + id +
					// 		'<b>) removed from page.</b>'
					// 	);
					// }
				});
			});
		}
	</script>
</body>
</html>