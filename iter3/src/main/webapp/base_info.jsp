<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>	
<%@ taglib prefix="s" uri="/struts-tags" %>
	<%@include file="/template/header.jsp"%>
	<%@include file="/template/first_nav.jsp"%>
	<%@include file="/template/second_nav.jsp"%>

<div class="container index-content me-page">
	<p class="path-nav">
		<a href="<%=path%>/index">首页</a>
		<a>&nbsp&nbsp>&nbsp&nbsp<s:property value="#path_nav"/></a>
	</p>
	<div class="user-navigation">
		<p class="title" style="background-image: url(<%=path%>/static/image/ucenter.png);"></p>
		<div class="user-thumb">
			<div class="thumb-bg">
				<img src="<%=path%>/imageByID?filename=<s:property value='#user.imageID'/>" alt="hello">
			</div>
		</div>

	</div>


	<div class="me-main">
		<div class="userinfo-edit" style="">
			<h2 class="title" style="background-image: url(<%=path%>/static/image/title-informations.png)"></h2>
			<div class="edit-box" style="">
				<div class="user-personal-info">
					<h2 class="sub-title">
						会员信息
					</h2>
					<form class="form-horizontal" method="post" action="<%=path%>/user/updateProfile">
						<div class="form-group">
							<label for="usernate" class="col-sm-2 control-label">账号</label>
							<div class="col-sm-4">
								<p class="form-control"><s:property value="#session.user.username"/></p>
							</div>
						</div>
						<div class="form-group">
							<label for="name" class="col-sm-2 control-label">真实姓名</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" value="<s:property value='#user.name'/>" id="name" name="user.name">
							</div>
						</div>
						<div class="form-group">
							<label for="gender" class="col-sm-2 control-label">性别</label>
							<div class="col-sm-4">
								<input type="text"  class="form-control" id="gender" value="<s:property value='#user.gender'/>" name="user.gender">
							</div>
						</div>
						<div class="form-group">
							<label for="e_mail" class="col-sm-2 control-label">电子邮件</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" id="e_mail" value="<s:property value='#user.e_mail'/>" name="user.e_mail">
							</div>
						</div>
						<div class="form-group">
							<label for="birthday" class="col-sm-2 control-label">生日</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" id="birthday" value="<s:property value='#user.birthday'/>" name="user.birthday">
							</div>
						</div>
						<div class="form-group">
							<label for="job" class="col-sm-2 control-label">职业</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" id="job" value="<s:property value='#user.job'/>" name="user.job">
							</div>
						</div>
						<div class="form-group">
							<label for="income" class="col-sm-2 control-label">收入情况</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" id="income" value="<s:property value='#user.income'/>" name="user.income" >
							</div>
						</div>
						<div class="col-sm-offset-2">
							<button type="submit" id="" class="btn btn-primary">
								<span>修改</span>
							</button>
						</div>
					</form>
				</div>
				<div class="user-image-info">
					<h2 class="sub-title">
						会员头像
					</h2>
					<div class="user-image">
						<img class="img-show" src="<%=path%>/imageByID?filename=<s:property value='#user.imageID'/>" alt="hello">
					</div>
					<form class="form-horizontal" method="post" action="<%=path%>/user/uploadImage" enctype="multipart/form-data">
						<div class="form-group">
							<label for="myFile" class="col-sm-2 control-label">File input</label>
							<div class="col-sm-4">
								<input type="file" name="myFile"/>
								<p class="help-block">上传您的新头像</p>
							</div>
						</div>
						<div class="col-sm-offset-2">
							<button type="submit" id="" class="btn btn-primary">
								<span>修改</span>
							</button>
						</div>
					</form>
				</div>

			</div>
		</div>

	</div>
