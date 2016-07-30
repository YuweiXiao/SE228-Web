<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>	
<%@ taglib prefix="s" uri="/struts-tags" %>
	<%@include file="template/header.jsp"%>
	<%@include file="template/first_nav.jsp"%>
	<%@include file="template/second_nav.jsp"%>


	<div class="container-fiuld item-content" style="margin-top:40px;margin-left: 15%;margin-right: 15%">
		<div class="row">
			<div class="col-md-4">
				<img class="img-show" id = "img-show" src="<%= path%>/image?filename=<s:property value='#book.imageID'/>" alt="<s:property value='#book.title' />">
			</div>
			<div class="col-md-2"></div>
			<div class="col-md-6 item-info">
				<h1>《<s:property value="#book.title"/>》</h1>
				<p><s:property value="#book.description"/></p>
				<dl>
					<dt>作者</dt>
					<dd><s:property value='#book.author'/></dd>
				</dl>
				<dl>
					<dt>单价</dt>
					<dd><s:property value='#book.price'/></dd>
				</dl>
				<dl>
					<dt>数量</dt>
					<dd class="amount">
			    		<span class="amount-widget">
			    			<input type="text" class="amount-input" value="1" maxlength="8" title="请输入购买量"/>
			    			<span class="amount-btn">
			                    <span class="fa fa-angle-up amount-increase" click="addBookNum()"></span>
			                    <span class="fa fa-angle-down amount-decrease"></span>
			    			</span>
			    			<span class="amount-unit">件</span>
			    		</span>
						<em class="inventory">库存<span id="inventory-value"><s:property value="#book.inventory"/></span>件</em>
					</dd>
				</dl>
				<button class="btn btn-default" onclick="add_to_cart(<s:property value='#book.bookID'/>)">加入购物车</button>
			</div>
		</div>
		<div class="other-info">
			<div class="description-and-detail">
				<h1>balabala</h1>
				<p>hhhhhhhhhhhhhhhhhhhhhhh</p>
			</div>
		</div>
	</div>

	<script src="<%= path %>/static/js/jquery-1.12.2.min.js"></script>
	<script src="<%= path %>/static/js/bootstrap.min.js"></script>
	<script src="<%= path %>/static/js/my.js"></script>

	<script type="text/javascript">
		
		// after document loaded, bind events
		$(function() {
			console.log("hello");
			$('.amount-increase').bind('click', addBookNum);
			$('.amount-decrease').bind('click', minusBookNum);
		});

	    function addBookNum()
	    {
	        amount = $('.amount-input').first().val();
	        amount = parseInt(amount) + 1;
	        // amount can not larger than inventory
	        if(amount > parseInt($('#inventory-value').html()))
	        	return ;
	        $('.amount-input').first().val(amount);
	    }

	    function minusBookNum()
	    {
	        amount = $('.amount-input').first().val();
	        if(amount > 1)
	            amount = parseInt(amount) - 1;
	        $('.amount-input').first().val(amount);
	    }

	    function add_to_cart(book_id)
	    {
	    	console.log(base_url+'item/action_cart_add');
	        $.post(
	            base_url+'item/action_cart_add',
	            {
	                'bookID':book_id,
	                'amount':$('.amount-input').first().val()
	            },
	            function(msg){
	                console.log(msg.success);
	                if(msg.success)
	                {
	                	$('#order').removeClass('disabled');
	                    showTip('成功加入购物车', 'success');
	                }
	                else if(msg == 1)
	                {
	                    showTip('you have to login','danger');
	                    setTimeout(function(){
	                        window.location.href="<?php echo site_url('login')?>"; 
	                    },1000);
	                }
	            });
	    }
	</script>

</body>
</html>