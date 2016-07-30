<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>

<%
   String path = request.getContextPath();
   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
	<meta charset="UTF-8">
	<title>order</title>

	<link rel="stylesheet" type="text/css" href="<%=path %>/static/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=path %>/static/css/style.css">
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
	
	
	<style type="text/css">
		body {
			height: initial;
			min-height:300px;
		}
	</style>
	
</head>


<body>
<!-- 	<?php if (count($rows) === 0): ?>
		<h1>No order</h1>
	<?php else:?>
 -->
 	<div id="tip"></div>

	<s:iterator value="#orders" status="order">
		<div class="container content-wrapper order">
			<ul>
				<li><strong>OrderID:</strong><s:property value="orderID"/></li>
				<li><strong>UserID:</strong><s:property value="user.userID"/></li>
				<li><strong>UserName:</strong><s:property value="user.name"/></li>
				<li><strong>IsDeal:</strong><s:property value="isDeal"/></li>
	        </ul>
	        <table class="table table-striped">
        		<tr>
        			<th field="isbn" width="15%">isbn</th>
        			<th field="title" width="20%">title</th>
        			<th field="author" width="15%">author</th>
        			<th field="press" width="15%">press</th>
        			<th field="amount" width="10%">amount</th>
        			<th field="unit_price" width="15%">unit Price</th>
        			<th field="total_price" width="15%">Price</th>
        			<!-- <th field="total_price" width="10%"> </th> -->
        		</tr>
        		<s:iterator value="books" var="ordersbook" status="book">
		       		<tr>
		       			<td><s:property value="#ordersbook.book.isbn"/></td>
		       			<td><s:property value="#ordersbook.book.title"/></td>
		       			<td><s:property value="#ordersbook.book.author"/></td>
		       			<td><s:property value="#ordersbook.book.press"/></td>
		       			<td>
		       				<!-- <button class="btn btn-xs modify"  
		       						onclick="minusBookNum(<s:property value='#order.index'/>, <s:property value='#book.index'/>, <s:property value='#ordersbook.book.price'/>)">
			       				<i class="fa fa-minus" aria-hidden="true"></i>
		       				</button>  -->
		       				<span class = "amount">
		       					<s:property value="#ordersbook.amount"/>
		       				</span>
		       				<!-- <button class="btn btn-xs modify"  
		       						onclick="addBookNum(<s:property value='#order.index'/>, <s:property value='#book.index'/>,<s:property value='#ordersbook.book.price'/>)">
		       					<i class="fa fa-plus" aria-hidden="true"></i>
		       				 --></button> 
		       			</td>
		       			
		       			<td><s:property value="#ordersbook.book.price/100.0"/></td>
		       			<td class="price"><s:property value="#ordersbook.book.price*#ordersbook.amount/100.0"/></td>
		       			<td>
		       			
		       			<!-- <button class="btn btn-default btn-xs modify" 
		       					onclick="removeBook( <s:property value='#order.index'/>,  <s:property value='#book.index'/>, <s:property value='orderID'/>, <s:property value='#ordersbook.book.bookID'/>)">
		       				<i class="fa fa-times" aria-hidden="true"></i>
		       			</button> -->
		       			</td>
		       		</tr>
		       </s:iterator>
	       </table>
	        <p>
	            <i class="fa fa-clock-o" aria-hidden="true"></i>
	            <strong>Order time:</strong><s:property value="orderTime"/>
	        </p>

            <button class="btn btn-default" onclick="destroyOrder(<s:property value='#order.index'/>, <s:property value='orderID'/>)" >Delete Order</button>

		</div>
	</s:iterator>
	<div class="container">
		<ul class="pagination">
	        <li>
	            <a href="<%=path%>/admin/orders_listOrder?pageno=1">First</a>
	        </li>
	        <s:if test="#page.hasPreviousPage">
		        <li>
		            <a href="<%=path%>/admin/orders_listOrder?pageno=<s:property value='#page.prePage'/>">
		            Previous
		            </a>
		        </li>
	        </s:if>
	        <s:iterator value="#page.list" id = "pageindex">
	        	<s:if test="#pageindex == #page.currentPage">
	        		<li class="active">
	        			<a "#"><s:property value="pageindex"/></a>
	        		</li>  
	        	</s:if> <s:else>
	        		<li>
	        			<a href="<%=path%>/admin/orders_listOrder?pageno=<s:property value='pageindex'/>"><s:property value="pageindex"/></a>
	        		</li>
	        	</s:else>
	            		  
	        </s:iterator>
	                   
	        <s:if test="#page.hasNextPage">
		        <li>
		            <a href="<%=path%>/admin/orders_listOrder?pageno=<s:property value='#page.nextPage'/>">
		            Next
		            </a>
		        </li>
	        </s:if>
	        <li>
	            <a href="<%=path%>/admin/orders_listOrder?pageno=<s:property value='#page.totalPage'/>">Last</a>
	        </li>
	    </ul>
	</div>


	<script src="<%=path %>/static/js/jquery-1.12.2.min.js"></script>
	<script src="<%=path %>/static/js/bootstrap.min.js"></script>
	<script src="<%=path %>/static/js/iframeResizer.contentWindow.min.js"></script>

	<script type="text/javascript">

		var base_url = "<%=basePath%>";

		function showTip(tip, type) {
		    var $tip = $('#tip');
		    $tip.attr('class', 'alert alert-' + type).text(tip).css('margin-left', - $tip.outerWidth() / 2).fadeIn(500).delay(1000).fadeOut(500);
		}

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
</body>
</html>