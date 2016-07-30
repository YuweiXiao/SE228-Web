<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="modal fade" id="cart" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">Cart</h4>
            </div>
            <div class="modal-body">
                <table class="table table-striped">
                    <tr id="heading">    
                    	<th field="bookID" style="display:none">bookID</th>
                        <!-- <th field="isbn">isbn</th> -->
                        <th field="title" width="25%">书名</th>
                        <th field="author" width="15%" style="overflow: hidden;">作者</th>
                        <th field="press" width="25%">出版社</th>
                        <th field="price" width="8%">单价（元）</th>
                        <th filed="amount" width="15%">数量</th>
                        <th filed="total Price" width="8%">总金额（元）</th>
                        <th></th>
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <a class="btn btn-primary" id="order" role="button" href="<%=basePath%>order/action_createOrder">Order</a>
            </div>
        </div>
    </div>
</div>