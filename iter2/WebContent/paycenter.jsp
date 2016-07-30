<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>	
<%@ taglib prefix="s" uri="/struts-tags" %>
	<%@include file="/template/header.jsp"%>
	<%@include file="/template/first_nav.jsp"%>


<style>


</style>

<div class="paycenter">
	
	<h1>PAY CENTER</h1>
	<div class="inner">
		<div class="titlebar">
			
			<ul class="nav">
				<li>
					总金额: <span class="price">￥<s:property value="#totalPrice/100.0"/></span>	
				</li>
				<li style="margin-left:16em;">
					订单编号：<span class="orderID"><s:property value="#orderID"/></span>
				</li>
			</ul>
		</div>
		<div class="wrapper">
			<h2>商品信息</h2>
			<table class="table table-striped">
		        <tr>    
		            <th width="15%">isbn</th>
		            <th width="15%">title</th>
		            <th width="15%">author</th>
		            <th width="15%">单价(元)</th>
		            <th width="15%">数量</th>
		            <th width="15%">总价(元)</th>
		            <th></th>
		        </tr>
		        <s:iterator value="#books">
		            <tr>
		                <td><s:property value="book.isbn"/></td>
		                <td><s:property value="book.title"/></td>
		                <td><s:property value="book.author"/></td>
		                <td><s:property value="book.price/100.0"/></td>
		                <td><s:property value="amount"/></td>
		                <td><s:property value="amount*book.price/100.0"/></td>
		                <td><button class="btn btn-default btn-detail"><a href="<%=path%>/item/action_showBookByID?bookID=<s:property value='book.bookID'/>">Detail</a></button></td>
		            </tr>
				</s:iterator>
	    	</table>
		</div>
		<div class="bottombar">
			<a role="button" class="btn btn-primary btn-lg" href="<%=path%>/order/action_pay?orderID=<s:property value='#orderID'/>">
				确认支付
			</a>
		</div>

	</div>

</div>
	
