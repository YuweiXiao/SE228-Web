<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>	
<%@ taglib prefix="s" uri="/struts-tags" %>
	<%@include file="template/header.jsp"%>
	<%@include file="template/first_nav.jsp"%>
	<%@include file="template/second_nav.jsp"%>

<div class="container index-content" ng-app="myModule" ng-controller="bookDetailControl">
	<p class="path-nav">
		<a href="<%=path%>/index">首页</a>
		<a>&nbsp&nbsp>&nbsp&nbsp<s:property value="#path_nav"/></a>
	</p>

	<s:if test="#books == null">
		<h1>OOPS, No Book Find.</h1>
	</s:if> <s:else>
	<table class="table table-striped">
	    <tr>    
	        <th>isbn</th>
	        <th>title</th>
	        <th>author</th>
	        <th>press</th>
	        <th>price</th>
	        <th></th>
	    </tr>
	    <s:iterator value="#books">
	        <tr>
	            <td><s:property value="isbn"/></td>
	            <td><s:property value="title"/></td>
	            <td><s:property value="author"/></td>
	            <td><s:property value="press"/></td>
	            <td><s:property value="price/100.0"/></td>
	            <td>
	            	<button class="btn btn-default btn-detail" ng-click="getDetail(<s:property value='bookID'/>)">
	            		Detail
                	</button>
                </td>
	        </tr>
		</s:iterator>
	</table>
	</s:else>
	<%@include file="/template/item_detail_template.jsp"%>
</div>

<script src="<%=path %>/static/js/jquery-1.12.2.min.js"></script>
<script src="<%=path %>/static/js/bootstrap.min.js"></script>
<script src="<%=path %>/static/js/angular.js"></script>
<script src="<%=path %>/static/js/app.js"></script>
<script src="<%=path %>/static/js/my.js"></script>