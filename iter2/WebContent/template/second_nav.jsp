<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="/template/cart_template.jsp"%>

<div class="container">
    <div class="main-logo">
        Alpha! Books.
    </div>
    <div class="main-search">
        <form class="search-form" role="search" method="get" action="<%=path %>/item/action_search">
            <div class="input-group" style="width:100%">
                <input type="text" name="title" value="<s:property value='title'/>" class="form-control" placeholder="Search Books" style="height:100%">
                <div class="input-group-addon nav-search-addon">
                    <span class="fa fa-search nav-search-icon" style="font-size:18px"></span>
                    <input class="nav-search-submit" type="submit" value="">
                </div>
            </div>
        </form>
    </div>
    <div class="main-cart">
        <a href="#" onclick="show_cart()">
            <span class="fa fa-shopping-cart cart-item1"></span>
            <span class="cart-item2">购物车</span>
        </a>
    </div>
</div>
<nav class="navbar second-navbar">
    <div class="collapse navbar-collapse">
        <ul class="nav navbar-nav">
            <li><a href="<%=path %>/item/action_listBookByCategory?categoryName=education">教育类</a></li>    
            <li><a href="<%=path %>/item/action_listBookByCategory?categoryName=literature">人文类</a></li>    
            <li><a href="<%=path %>/item/action_listBookByCategory?categoryName=computer">科技类</a></li>    
            <li><a href="<%=path %>/item/action_listBookByCategory?categoryName=novel">小说类</a></li>   
        </ul>
    </div><!-- /.navbar-collapse -->
</nav>

