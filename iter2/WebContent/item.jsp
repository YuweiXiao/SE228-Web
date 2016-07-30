<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>	
<%@ taglib prefix="s" uri="/struts-tags" %>
	<%@include file="template/header.jsp"%>
	<%@include file="template/first_nav.jsp"%>
	<%@include file="template/second_nav.jsp"%>
	
	<style type="text/css">

		.amount-input {
			color: #666;
			font-size: 12px;
			margin: 0;
			padding: 3px 2px 0 3px;
			height: 26px;
			border: 1px solid #a7a6ac;
			width: 36px;
			line-height: 26px;
		}
		.amount-btn {
			display: inline-block;
			vertical-align: middle;	
			text-align: center;
		}
		.amount-unit {
			vertical-align: middle;
			margin-left: 5px;
		}
		.amount-increase {
			width: 16px;
		    height: 12px;
		    overflow: hidden;
		    cursor: pointer;
		    border: 1px solid #a7a6ab;
		    display: block;
		    line-height: 12px;
		    font-size: 16px;
		    margin-bottom: 3px;
		}
		.amount-decrease {
			width: 16px;
		    height: 12px;
		    overflow: hidden;
		    cursor: pointer;
		    border: 1px solid #a7a6ab;
		    display: block;
		    line-height: 12px;
		    font-size: 16px;
		    margin-bottom: 3px;
		}
		.inventory {
			font-size:12px;
		}

	</style>


	<div class="container-fiuld item-content">
		<div class="row">
			<div class="col-md-4">
				<img class="img-show" id = "img-show" src="/bookstore/static/image/4.jpg">
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
	    	console.log(base_url+'item/action_addToCart');
	        $.post(
	            base_url+'item/action_addToCart',
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