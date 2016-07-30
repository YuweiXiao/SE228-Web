<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>	
<%@ taglib prefix="s" uri="/struts-tags" %>
	<%@include file="/template/header.jsp"%>
	<%@include file="/template/first_nav.jsp"%>
	<%@include file="/template/second_nav.jsp"%>

	
	<div id="carousel-index-generic" class="carousel slide" data-ride="carousel">
		<!-- Indicators -->
		<ol class="carousel-indicators">
			<li data-target="#carousel-index-generic" data-slide-to="0" class="active"></li>
			<li data-target="#carousel-index-generic" data-slide-to="1"></li>
			<li data-target="#carousel-index-generic" data-slide-to="2"></li>
		</ol>

		<!-- Wrapper for slides -->
		<div class="carousel-inner" role="listbox">
			<div class="item active">
				<img src="/bookstore/static/image/1.jpg" alt="...">
				<div class="carousel-caption">
					...
				</div>
			</div>
			<div class="item">
				<img src="/bookstore/static/image/2.jpg" alt="...">
				<div class="carousel-caption">
				...
				</div>
			</div>
			<div class="item">
				<img src="/bookstore/static/image/3.jpg" alt="...">
				<div class="carousel-caption">
				...
				</div>
			</div>
		</div>

		<!-- Controls -->
		<a class="left carousel-control" href="#carousel-index-generic" role="button" data-slide="prev">
			<span class="fa fa-chevron-left pointer-previous" aria-hidden="true"></span>
			<span class="sr-only">Previous</span>
		</a>
		<a class="right carousel-control" href="#carousel-index-generic" role="button" data-slide="next">
			<span class="fa fa-chevron-right pointer-next" aria-hidden="true"></span>
			<span class="sr-only">Next</span>
		</a>
	</div>
	
	<div class="container index-content">
		<div class="container">
			<h2 class="heading">
				热销书籍
			</h2>
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
		                <td><button class="btn btn-default btn-detail"><a href="<%=path%>/item/action_showBookByID?bookID=<s:property value="bookID"/>">Detail</a></button></td>
		            </tr>
				</s:iterator>
	    	</table>
		</div>

		<div class="container">
			<h2 class="heading">
				新书上架
			</h2>
			<table class="table table-striped">
		        <tr>    
		            <th>isbn</th>
		            <th>title</th>
		            <th>author</th>
		            <th>press</th>
		            <th>price</th>
		            <th></th>
		        </tr>
		        <s:iterator value="#newbooks">
		            <tr>
		                <td><s:property value="isbn"/></td>
		                <td><s:property value="title"/></td>
		                <td><s:property value="author"/></td>
		                <td><s:property value="press"/></td>
		                <td><s:property value="price/100.0"/></td>
		                <td><button class="btn btn-default btn-detail"><a href="<%=path%>/item/action_showBookByID?bookID=<s:property value="bookID"/>">Detail</a></button></td>
		            </tr>
				</s:iterator>
	    	</table>
		</div>

	</div>


	<script src="<%=path %>/static/js/jquery-1.12.2.min.js"></script>
	<script src="<%=path %>/static/js/bootstrap.min.js"></script>
	<script src="<%=path %>/static/js/my.js"></script>

</body>
</html>