<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>	
<%@ taglib prefix="s" uri="/struts-tags" %>
	<%@include file="/template/header.jsp"%>
	<%@include file="/template/first_nav.jsp"%>
	<%@include file="/template/second_nav.jsp"%>

<div class="container index-content">
	<p class="path-nav">
		<a href="<%=path%>/index">首页</a>
		<a>&nbsp&nbsp>&nbsp&nbsp我的订单</a>
	</p>


	<s:if test="#orders == null">
		<h1>No Order</h1>
	</s:if> <s:else>

	<s:iterator value="#orders" var = "od" status="order">
		<div class="container content-wrapper order" style="width:100%">
			<ul>
				<li><strong>OrderID:</strong><s:property value="orderID"/></li>
				<li><strong>UserID:</strong><s:property value="user.userID"/></li>
				<li><strong>UserName:</strong><s:property value="user.name"/></li>
				<li><strong>IsDeal:</strong><s:property value="isDeal"/></li>
	        </ul>
	        <table class="table table-striped">
        		<tr>
        			<th field="isbn" width="10%">isbn</th>
        			<th field="title" width="20%">title</th>
        			<th field="author" width="10%">author</th>
        			<th field="press" width="20%">press</th>
        			<th field="amount" width="10%">amount</th>
        			<th field="unit_price" width="15%">unit Price</th>
        			<th field="total_price" width="15%">Price</th>
        		</tr>
        		<s:iterator value="books" var="ordersbook" status="book">
		       		<tr>
		       			<td><s:property value="#ordersbook.book.isbn"/></td>
		       			<td><s:property value="#ordersbook.book.title"/></td>
		       			<td><s:property value="#ordersbook.book.author"/></td>
		       			<td><s:property value="#ordersbook.book.press"/></td>
		       			<td>
		       				<span class = "amount">
		       					<s:property value="#ordersbook.amount"/>
		       				</span>
		       			</td>
		       			
		       			<td><s:property value="#ordersbook.book.price/100.0"/></td>
		       			<td class="price"><s:property value="#ordersbook.book.price*#ordersbook.amount/100.0"/></td>
		       			<td>
		       			
		       			</td>
		       		</tr>
		       </s:iterator>
	        </table>
	        <p>
	            <i class="fa fa-clock-o" aria-hidden="true"></i>
	            <strong>Order time:</strong><s:property value="orderTime"/>
	        </p>
	        <s:if test="#od.isDeal == 'N'">
	        	<a role="button" class="btn btn-default" href="<%=path%>/order/action_closeOrder?orderID=<s:property value='#od.orderID'/>">
		        	取消订单
		        </a>
		        <a role="button" class="btn btn-primary" href="<%=path%>/order/action_pay?orderID=<s:property value='#od.orderID'/>">
		        	确认支付
		        </a>
		    </s:if> <s:else>
		    	<button class="btn btn-default" onclick="destroyOrder(<s:property value='#order.index'/>, <s:property value='orderID'/>)" >删除订单</button>
		    </s:else>
		</div>
	</s:iterator>
	</s:else>
	<div id="tip"></div>
</div>

<script src="<%=path %>/static/js/jquery-1.12.2.min.js"></script>
<script src="<%=path %>/static/js/bootstrap.min.js"></script>
<script src="<%=path %>/static/js/my.js"></script>
<script type="text/javascript">
	
	function destroyOrder (order_id, orderID) {
		 $.post({
		 	url:base_url+"admin/orders_destroyOrder",
		 	data: {
		 		'orderID':orderID,
		 	},
		 	success:function(msg){
		 		console.log(msg);
		 		if(msg.success)	
		 		{
			 		showTip('success', 'success');
					$('.order:eq('+order_id+')').css('display','none');	 	
			 	}
			 	else
			 	{
			 		showTip('some thing go wrong','danger');
			 	}
		 	}
		 });
	}

</script>