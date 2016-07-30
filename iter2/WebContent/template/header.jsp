<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>

<%
   String path = request.getContextPath();
   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
	<s:if test = "#title == null">
		<title>Alpha BookStore</title>
	</s:if><s:else>
		<title><s:property value="#title"/></title>
	</s:else>
	<meta charset="utf-8">
	
	<link rel="stylesheet" type="text/css" href="<s:url value='/static/css/bootstrap.min.css'/>">
	<link rel="stylesheet" type="text/css" href="<s:url value='/static/css/font-awesome.min.css'/>">
	<link rel="stylesheet" type="text/css" href="<s:url value='/static/css/style.css'/>">

	<style>
	</style>

	<script type="text/javascript">
		const base_url = '<%= basePath%>';
	</script>

</head>
<body>
	<div id="tip"></div>
	