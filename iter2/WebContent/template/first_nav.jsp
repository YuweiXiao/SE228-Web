<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<nav class="navbar  navbar-default">
	<div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#nav-collapse" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="<%=path%>/index">Alpha BookStore</a>
    </div>
	<div class="collapse navbar-collapse" id="nav-collapse">
	    <ul class="nav navbar-nav navbar-right">
	    	<s:if test="#session.user == null">
		    	<li><a href="<%=path %>/auth/action_login">登陆</a></li>    
		    	<li><a href="<%=basePath%>register">免费注册</a></li>
	    	</s:if> <s:else>
		    	<li><a>Hi~<s:property value="#session.user.username"/></a></li>
		    	<li><a style="color:black" href="<%=path%>/auth/action_logout">[退出]</a></li>
	    	</s:else>
	    	<li><a href="<%=path %>/order/action_myAllOrders">我的订单</a></li>    
	    	<li><a>我的收藏</a></li>    
	    </ul>
	</div><!-- /.navbar-collapse -->
</nav>