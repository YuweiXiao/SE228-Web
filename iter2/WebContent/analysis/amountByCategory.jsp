<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>	
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
   String path = request.getContextPath();
   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<title></title>
	<style type="text/css">
		.MyText{
			fill: white;
			text-anchor: middle;
			font-size:1.5em;
		}
	</style>
</head>
<body >


<script src="<%= path %>/static/js/jquery-1.12.2.min.js"></script>
<script src="<%= path %>/static/js/d3.min.js" charset="utf-8"></script>
<script src="<%=path %>/static/js/iframeResizer.contentWindow.min.js"></script>

<script type="text/javascript">
	$(function(){
		var margin = {top: 20, right: 30, bottom: 30, left: 50},
			padding = {top: 0, right: 0, bottom: 0, left: 0},
			width = window.innerWidth- margin.left - margin.right - 100,
			height = 530 - margin.top - margin.bottom -20;
		
		var svg = d3.select("body").append("svg")
					.attr("width", width + margin.left + margin.right)
					.attr("height", height + margin.top + margin.bottom)
					.append("g")
					.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

		$.post({
			url:"/bookstore/test_categoryAnalysis",
			success:function(data){
				console.log(data);

				data.amount = data.amount.map(function(d){
					return parseInt(d);
				})

				var xScale = d3.scale.ordinal()
								.rangeRoundBands([0, width - padding.left - padding.right])
								.domain(data.categoryName);
				
				var yScale = d3.scale.linear()
							   .range([height, 0])
							   .domain([0,d3.max(data.amount)]);

				var xAxis = d3.svg.axis()
							  .scale(xScale)
							  .orient('bottom');

				var yAxis = d3.svg.axis()
								.scale(yScale)
								.orient("left");

				var rectPadding = 4;
				var rects = svg.selectAll(".MyRect")
					        .data(data.amount)
					        .enter()
					        .append("rect")
					        .attr("class","MyRect")
					        .attr("transform","translate(" + padding.left + "," + padding.top + ")")
					        .attr("x", function(d,i){
					            return xScale(data.categoryName[i]) + rectPadding/2;
					        } )
					       	.attr('y',function(d){
					        	return yScale(yScale.domain()[0]);
					        })
					        .attr('height', 0)
					        .attr("width", xScale.rangeBand() - rectPadding )
					        .attr('fill', 'steelblue')					        
					        .transition()
					        .delay(function(d,i){
					        	return i*200;
					        })
					        .duration(3000) 
					        .ease('bounce')
					        .attr("y",function(d){
					            return yScale(d);
					        })
					        .attr("height", function(d){
					            return height - padding.top - padding.bottom - yScale(d);
					        });
					        
				var texts = svg.selectAll(".MyText")
				        .data(data.amount)
				        .enter()
				        .append("text")
				        .attr("class","MyText")
				        .attr("transform","translate(" + padding.left + "," + padding.top + ")")
				        .attr("dx",function(){
				            return (xScale.rangeBand() - rectPadding)/2;
				        })
				        .attr("dy",function(d){
				            return 20;
				        })
				        .text(function(d){
				            return d;
				        })
				        .attr("x", function(d,i){
				            return xScale(data.categoryName[i]) + rectPadding/2;
				        } )
				        .attr("y",function(d){
				            var min = yScale.domain()[0];
				            return yScale(min);
				        })
				        .transition()
				        .delay(function(d,i){
				            return i * 200;
				        })
				        .duration(3000)
				        .ease("bounce")
				        .attr("y",function(d){
				            return yScale(d);
				        });

				//添加x轴
				svg.append("g")
				  .attr("class","axis")
				  .attr("transform","translate(" + padding.left + "," + (height - padding.bottom) + ")")
				  .call(xAxis); 

       			//添加y轴
				svg.append("g")
				  .attr("class","axis")
				  .attr("transform","translate(" + padding.left + "," + padding.top + ")")
				  .call(yAxis)
				  .append('text')
				  .text('销售册数（本）');
			}

		})
	});

</script>
	