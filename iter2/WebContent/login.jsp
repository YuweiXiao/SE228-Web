<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>	
<%@ taglib prefix="s" uri="/struts-tags" %>
	<%@include file="/template/header.jsp"%>



<body style="height:100%;width:100%">
	
	<div class="container-fiuld" style="height:10%">
		<div class="brand"><a class="brand" href="<%=path%>/index">Alpha BookStore</a></div>
	</div>	

	<div class="container-fiuld login-body">
		<div class="login-content">
			<div class="login-title">
				账户登录
			</div>
			<form class="login-form" method="post" action="<%=path %>/auth/action_login">
				<s:property value="tip"/>
				<div class="form-group">
					<label class="sr-only" for="username">username</label>
					<div class="input-group">
						<div class="input-group-addon"><i class="fa fa-user icon" style="font-size:18px"></i></div>
						<input type="text" class="form-control" name="user.username" id="username" placeholder="Username">
					</div>
				</div>

				<div class="form-group">
					<label class="sr-only" for="password">password</label>
					<div class="input-group">
						<div class="input-group-addon"><i class="fa fa-lock icon" style="font-size:20px"></i></div>
						<input type="password" class="form-control" name="user.password" id="password" placeholder="password">
					</div>
				</div>
				<button type="submit" class="btn btn-default">登陆</button>
				<button class="btn btn-default"><a href="<%=basePath%>register">免费注册</a></button>
			</form>
		</div>
	</div>
	<script src="/bookstore/static/js/jquery-1.12.2.min.js"></script>
	<script src="/bookstore/static/js/bootstrap.min.js"></script>

</body>
</html>