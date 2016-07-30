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

<body style="min-height:600px;width:100%">

	<div class="container-fiuld">
		<div style="width: 30%;min-width: 400px;margin-left: auto;margin-right: auto;">
			<h2 class="heading text-center" >
				创建新书
			</h2>
			<form class="form-horizontal" method="post" action="<%=path %>/admin/books_saveBook" enctype="multipart/form-data">
			<!-- action="<%=path %>/auth/register"> -->
				<div class="form-group">
					<label for="isbn" class="col-sm-4 control-label">ISBN</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="isbn" name="book.isbn" required>
					</div>
				</div>

				<div class="form-group">
					<label for="title" class="col-sm-4 control-label">title</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="title" name="book.title" required>
					</div>
				</div>

				<div class="form-group">
					<label for="price" class="col-sm-4 control-label">price(单位:分)</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="price" name="book.price" required>
					</div>
				</div>

				<div class="form-group">
					<label for="author" class="col-sm-4 control-label">author</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="author" name="book.author" required>
					</div>
				</div>

				<div class="form-group">
					<label for="press" class="col-sm-4 control-label">press</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="press" name="book.press" required>
					</div>
				</div>

				<div class="form-group">
					<label for="inventory" class="col-sm-4 control-label">inventory</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="inventory" name="book.inventory" required>
					</div>
				</div>
				
				<div class="form-group">
					<label for="category" class="col-sm-4 control-label">category</label>
					<div class="col-sm-8">
						<s:iterator value="#category">
						<div class="checkbox">
							<label>
								<input type="checkbox" name="category" value="<s:property value='categoryID'/>"><s:property value="name"/>
							</label>
						</div>
 						</s:iterator>
					</div>
				</div>

				 

				<div class="form-group">
					<label for="myFile" class="col-sm-4 control-label">File input</label>
					<div class="col-sm-8">
						<input type="file" name="myFile">
						<p class="help-block">The image of this new book</p>
					</div>
				</div>
				
				<button type="submit" id="create" class="btn btn-primary" style="width:100%"><span>创建</span></button>
			</form>
		</div>
	</div>
	
	<script src="<%= path %>/static/js/jquery-1.12.2.min.js"></script>
	<script src="<%=path %>/static/js/iframeResizer.contentWindow.min.js"></script>

</body>
</html>