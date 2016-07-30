<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>

<%
   String path = request.getContextPath();
   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
	<meta charset="UTF-8">
	
	<s:if test = "#title == null">
		<title>Alpha BookStore</title>
	</s:if><s:else>
		<title><s:property value="#title"/></title>
	</s:else>

	<link rel="stylesheet" type="text/css" href="<%=path %>/static/css/themes/bootstrap/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=path %>/static/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=path %>/static/css/themes/color.css">
	<link rel="stylesheet" type="text/css" href="<%=path %>/static/css/style.css">

	
	<style type="text/css">
	html{
		
	}
	body {
		height: initial;
		min-height:300px;
	}
	#dlg {
		width:400px;
		height:280px;
		padding:10px 20px;
	}
	</style>
</head>

<body style="min-height:400px;width:96%;margin: 30px auto">

	