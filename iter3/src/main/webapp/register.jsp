<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>	
	<%@include file="/template/header.jsp"%>


<body style="height:100%;width:100%">
	
	<div class="container-fiuld" style="height:10%">
		<div class="brand"><a class="brand" style="margin-left:0px;text-align: center;" href="<%=path%>/index">Alpha BookStore</a></div>
	</div>	

	<div class="container-fiuld">
		<div class="register-content" style="">
			<h2 class="heading">
				账户注册
			</h2>
			<form class="register-form" id="register_form" method="post" action="<%=basePath%>auth/action_register">
			<!-- action="<%=path %>/auth/register"> -->
				<div class="form-group">
					<p>账号名</p>
					<input type="text" class="form-control" id="username" name="user.username" required>
				</div>

				<div class="form-group">
					<p>密码</p>
					<input type="password" class="form-control" name="user.password" id="password" required>
				</div>
				<div class="form-group">
					<p>再次输入密码</p>
					<input type="password" class="form-control" id="password_again" name="password-again">
					<div class="alert alert-warning hidden" id="password_again_warning" style="margin:3px 0;">两次密码不一致</div>
				</div>
				<div class="form-group">
					<p>邮箱地址</p>
					<input type="email" class="form-control" name="user.e_mail" id="email" required>
				</div>
				<!-- <div class="form-group">
					<p>手机号码</p>
					<input type="password" class="form-control" name="user.password" id="password">
				</div> -->
				<button type="submit" id="create" class="btn btn-primary" style="width:100%"><span>创建您的AlphaBookStore账户</span></button>
			</form>
		</div>
	</div>
	
	<script src="<%= path %>/static/js/jquery-1.12.2.min.js"></script>
	<script src="<%= path %>/static/js/bootstrap.min.js"></script>	
	<script src="http://jqueryvalidation.org/files/dist/jquery.validate.min.js"></script>

	<script type="text/javascript">
		$(function(){
			$('#password_again').bind('input',check_password);
			$("#create").click(function(check){ 
			    if(!check_password()){
			    	$('#password-again').focus();	
			    	check.preventDefault();
				}
			}); 

		});

		function check_password() {
			var password = $('#password').val();
			var again = $('#password_again').val();
			if(password !== again) {
				$('#password_again_warning').removeClass('hidden');
				return false;
			} else {
				$('#password_again_warning').addClass('hidden');
				return true;
			}
		}

	</script>

</body>
</html>