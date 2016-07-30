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
	<link rel="stylesheet" type="text/css" href="<%=path %>/static/css/bootstrap.min.css">
	<style type="text/css">
		.axis path,
		.axis line {
		  fill: none;
		  stroke: #000;
		  shape-rendering: crispEdges;
		}

		#line-sales {
		  fill: none;
		  stroke: steelblue;
		  stroke-width: 1.5px;
		}

		.node {
		  /*fill: white;*/
		  /*stroke: steelblue;*/
		  stroke-width: 1.5px;
		}

		.tooltip {

		}
	</style>
</head>
<body style="width:100%">

<div>
	<nav>
	  <ul class="pagination">
	    <li><a href="<%=basePath%>admin/analysis_salesByDay">日</a></li>
	    <li><a href="<%=basePath%>admin/analysis_salesByMonth">月</a></li>
	    <li class="active"><a href="#">年</a></li>
	  </ul>
	</nav>
</div>


<script src="<%= path %>/static/js/jquery-1.12.2.min.js"></script>
<script src="<%= path %>/static/js/d3.min.js" charset="utf-8"></script>
<script src="<%=path %>/static/js/iframeResizer.contentWindow.min.js"></script>

<script type="text/javascript">
	
	$(function(){
		var margin = {top: 20, right: 30, bottom: 30, left: 50},
			width = window.innerWidth- margin.left - margin.right,
			height = 530 - margin.top - margin.bottom -20;
		
		var svg = d3.select("body").append("svg")
					.attr("width", width + margin.left + margin.right)
					.attr("height", height + margin.top + margin.bottom)
					.append("g")
					.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

		$.post({
			url:"<%= path %>/admin/data_getSalesByYear",
			success:function(data){
				console.log(data);

				var objData = [];
				for (var i = 0; i < data.date.length; i++) {
					objData.push({
						"date" : data.date[i],
						"sales": data.sales[i]/100.0
					})
				}
				
				objData.sort(function(a, b){
					if( a.date < b.date)
						return -1;
					else if( a.date > b.date)
						return 1;
					return 0;
				})
				
				console.log(objData);
				var parseTime = d3.time.format("%Y").parse;
				var xparseTime = d3.time.format("%Y");
				objData.forEach( function(element, index) {
					element.date = parseTime(element.date);
				});

				//objDate.date = parseTime(objDate.date);
				console.log(objData);
				//x轴坐标映射函数
				x = d3.time.scale().range([0,width])
							.domain(d3.extent(objData,function (d) {return d.date;}));
				
				//console.log(d3.min(data,function(d){return d.score;}));
				//console.log(d3.max(data, function(d){return d.score;}));
				
				//y1轴映射函数
				y1 = d3.scale.linear()
						  .domain([0, 
									d3.max(objData, function(d){return d.sales;})+100])
						  .range([height,0]);
				
				//折线
				var lineSales = d3.svg.line()
							 		.x(function(d,i) { console.log(x(d.date));return x(d.date); })
							 		.y(function(d,i) { return y1(d.sales); })
							 		.interpolate("monotone");
							 		//.interpolate("");
				//y1坐标轴
				var yAxis1 = d3.svg.axis()
								.scale(y1)
								.orient('left');
				//x坐标轴
				var xAxis = d3.svg.axis()
								.scale(x)
								.orient("bottom")
								.ticks(d3.time.year,1)
								.tickFormat(function(d){return xparseTime(d)});
								//.innerTickSize(-400).outerTickSize(0).tickPadding(10);

				svg.append("g")
				   .attr("class", "x axis")
				   .attr("transform", "translate(0," + height + ")")
				   .call(xAxis);
				   // .append('text')
				   // .attr('transform',"translate("+((width-margin.right-margin.left)/2-10)+"-10)")
				   // .text("date");

				svg.append("g")
					.attr("class", "y axis")
					.call(yAxis1)	
					.append('text')
					.text("销售额（元）");

				svg.append("path")
				   .datum(objData)
				   .attr("id", "line-sales")
				   .attr("d", lineSales);
				svg.selectAll('circle')
					.data(objData)
				    .enter()        
				    .append("svg:circle")                     
					.attr('cx', lineSales.x())
					.attr('cy', lineSales.y())
					.attr('r',2);



				var focus = svg.append("g")                                
				     		.style("display", "none");  
				var bisectDate = d3.bisector(function(d) { return d.date; }).left; // **


				// append the circle at the intersection               
			    focus.append("circle")                                 
			        .attr("class", "y")                                
			        .style("fill", "none")                             
			        .style("stroke", "blue")                           
			        .attr("r", 5);   
			    // append text of circle                                  
			    focus.append('text')
			    	.attr('class', "node-info");

			    // append the rectangle to capture mouse               
			    svg.append("rect")                                     
			        .attr("width", width)                              
			        .attr("height", height)                            
			        .style("fill", "none")                             
			        .style("pointer-events", "all")                    
			        .on("mouseover", function() { focus.style("display", null); })
			        .on("mouseout", function() { focus.style("display", "none"); })
			        .on("mousemove", mousemove);                       

			    function mousemove() {                                 
			        var x0 = x.invert(d3.mouse(this)[0]),              
			            i = bisectDate(objData, x0, 1),                   
			            d0 = objData[i - 1],                              
			            d1 = objData[i],                                  
			            d = x0 - d0.date > d1.date - x0 ? d1 : d0;     
			        var x1 = x(d.date);
			        if(x1 > width*2/3 )
			        	x1 -= 200;
			        focus.select("text.node-info")
			        	 .attr("transform",                             
			                  "translate(" + x1 + "," +         
			                                 y1(d.sales) + ")")
			             .html("日期：" + xparseTime(d.date)+'  <br/> 销售额:'+d.sales + " 元");                          
			        focus.select("circle.y")                           
			            .attr("transform",                             
			                  "translate(" + x(d.date) + "," +         
			                                 y1(d.sales) + ")");        
			    }             

			 //   var tooltip = d3.select("body")
			 //       .append("div")
			 //       .attr('class', 'tooltip')
			 //       .style("position", "absolute")
			 //       .style("z-index", "10")
			 //       .style("visibility", "hidden")
			 //       .text("a simple tooltip");

				// svg.selectAll("circle .node")
				//     .data(objData)
				//     .enter()
				//     .append("svg:circle")
				//     .attr("class", "node")
				//     .attr("cx", lineSales.x())
				//     .attr("cy", lineSales.y())
				//     .attr("r", 5)
				//     .on("mouseover", function(d){
				//     	// console.log(d);
				//     	tooltip.html("日期：" + xparseTime(d.date)+"  销售额:"+d.sales);
				//     	return tooltip.style("visibility", "visible");
				//     })
				//     .on("mousemove", function(){
				//     	return tooltip.style("top",(d3.event.pageY-10)+"px")
				//     				  .style("left",(d3.event.pageX+10)+"px");
				//     })
				//     .on("mouseout", function(){
				//     	return tooltip.style("visibility", "hidden");
				//     });
			}
		});	
	});
	

</script>

</body>
</html>