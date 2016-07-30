<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="modal fade" id="item-detail" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">{{book.title}}</h4>
            </div>
            <div class="modal-body">
                <div class="container-fiuld item-content">
                	<div class="row">
                		<div class="col-md-6 book-image">
                			<img ng-src="<%= path%>/image?filename={{book.imageID}}" alt="{{book.title}}">
                		</div>
                		<div class="col-md-6 item-info">
                			<h1>《{{book.title}}》</h1>
                			<p>{{book.description}}</p>
                			<dl>
                				<dt>作者</dt>
                				<dd>{{book.author}}</dd>
                			</dl>
                			<dl>
                				<dt>单价(元)</dt>
                				<dd>{{book.price/100.0}}</dd>
                			</dl>
                			<dl>
                				<dt>数量</dt>
                				<dd class="amount">
                		    		<span class="amount-widget">
                		    			<input type="text" class="amount-input" value="1" maxlength="8" ng-model='amount' title="请输入购买量"/>
                		    			<span class="amount-btn">
                		                    <span class="fa fa-angle-up amount-increase" ng-click="addNum()"></span>
                		                    <span class="fa fa-angle-down amount-decrease" ng-click="minusNum()"></span>
                		    			</span>
                		    			<span class="amount-unit">件</span>
                		    		</span>
                					<em class="inventory">库存<span id="inventory-value">{{book.inventory}}</span>件</em>
                				</dd>
                			</dl>
                		</div>
                	</div>
                	<div class="other-info">
                		<div class="description-and-detail">
                			<h1>balabala</h1>
                			<p>hhhhhhhhhhhhhhhhhhhhhhh</p>
                		</div>
                	</div>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" ng-click="addToCart()">加入购物车</button>
            </div>
        </div>
    </div>
</div>
