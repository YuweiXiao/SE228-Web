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
<body style="width:100%;min-height: 300px;">
<div style="width: 200px;margin-top: 10px;margin-left: 50px;">
	<div class="form-group">
		<label>userID:</label>
		<select class="form-control" id = "userID" name="userID">
		  <option>1</option>
		  <option>7</option>
		</select>
	</div>
	<button class="btn btn-default" onclick="show()">show</button>
</div>

<div id="pie" style="margin-left: auto;margin-right: auto;width: 590px;">
	

</div>

<div id="lineChart" style="margin-left: auto;margin-right: auto;width:600px;">
	

</div>

<script src="<%= path %>/static/js/jquery-1.12.2.min.js"></script>
<script src="<%= path %>/static/js/d3.min.js" charset="utf-8"></script>
<script src="<%= path %>/static/js/d3pie.js" charset="utf-8"></script>
<script src="<%= path %>/static/js/moment.js" charset="utf-8"></script>
<script src="<%=path %>/static/js/iframeResizer.contentWindow.min.js"></script>

<script type="text/javascript">
	
	var margin = {top: 20, right: 30, bottom: 30, left: 50},
				width = 600 - margin.left - margin.right,
				height = 530 - margin.top - margin.bottom -20;
	function show(){
		$.post({
			url:"<%= path %>/admin/data_getUserBehaviorOfBookCategory",
			data: {	'userID': $('#userID').val() },
			success:function(data){
				 console.log(data);
				 $('#pie svg').remove();
				 var content = [];
				 var color = d3.scale.category20c();
				 for(var i = 0; i < data.amount.length; ++i) {
				 	content.push({
				 		'value':parseInt(data.amount[i]),
				 		'label':data.categoryName[i],
				 		'color': color[i]
				 	})
				 }
				 var pie = new d3pie("pie", {
										"header": {
											"title": {
												"text": "用户购买书籍种类统计",
												"fontSize": 24,
												"font": "open sans"
											},
											"subtitle": {
													"text": "单位（本）",
													"color": "#999999",
													"fontSize": 12,
													"font": "open sans"
											},
											"titleSubtitlePadding": 9
										},
										"size": {
											"canvasWidth": 590,
											"pieOuterRadius": "90%"
										},
										"data": {
											"sortOrder": "amount-desc",
											"content": content
										},
										"labels": {
											"outer": {
												"pieDistance": 32
											},
											"inner": {
												"format":"value",
												"hideWhenLessThanPercentage": 3
											},
											"mainLabel": {
												"fontSize": 11
											},
											"percentage": {
												"color": "#ffffff",
												"decimalPlaces": 0
											},
											"value": {
												"color": "#adadad",
												"fontSize": 11
											},
											"lines": {
												"enabled": true
											},
											"truncation": {
												"enabled": true
											}
										},
										"effects": {
											"pullOutSegmentOnClick": {
												"effect": "linear",
												"speed": 400,
												"size": 8
											}
										},
										"misc": {
											"gradient": {
												"enabled": true,
												"percentage": 100
											}
										}
									});
			}
		});	


		$.post({
			url:"<%= path %>/admin/data_getUserRecentSales",
			data: {	'userID': $('#userID').val() },
			success:function(data){
				 console.log(data);

				 $('#lineChart svg').remove();
				 var svg = d3.select("#lineChart").append("svg")
					.attr("width", width + margin.left + margin.right)
					.attr("height", height + margin.top + margin.bottom)
					.append("g")
					.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

				var objData = [];							// make date consistant
				var p = 0;
				var now = new Date();
				var tDate = new Date(now.getTime() - 7 * 24 * 3600 * 1000);
				tDate = new Date(tDate.toLocaleDateString());
				now = new Date(now.toLocaleDateString());
				
				while( tDate <= now ) {
					console.log(tDate);
					console.log(tDate.getTime());
					console.log(moment(data.date[p]).toDate());
					console.log(moment(data.date[p]).toDate().getTime());
					if( p < data.date.length && tDate.getTime() == moment(data.date[p]).toDate().getTime() ) {
						console.log("here" + parseInt(data.sales[p])/100.0);
						objData.push({
							"date" : data.date[p],
							"sales": parseInt(data.sales[p])/100.0
						});
						p++;
					} else {
						var t = tDate.getFullYear() + "-" + (tDate.getMonth()+1) + "-" + tDate.getDate();
						objData.push({
							"date" : t,
							"sales": 0
						});
					}

					tDate.setDate(tDate.getDate() + 1);
				}
				
				console.log(objData);

				var parseTime = d3.time.format("%Y-%m-%d").parse;
				var xparseTime = d3.time.format("%b %d");
				var yparseTime = d3.time.format("%Y %b %d");
				objData.forEach( function(element, index) {
					element.date = parseTime(element.date);
				});

				
				//x轴坐标映射函数
				x = d3.time.scale().range([0,width])
							.domain(d3.extent(objData,function (d) {return d.date;}));
				
				
				//y1轴映射函数
				y1 = d3.scale.linear()
						  .domain([-10, 
									d3.max(objData, function(d){return d.sales;})+100])
						  .range([height,0]);
				
				//折线
				var lineSales = d3.svg.line()
							 		.x(function(d,i) { return x(d.date); })
							 		.y(function(d,i) { return y1(d.sales); })
							 		.interpolate("monotone");
				
				//y1坐标轴
				var yAxis1 = d3.svg.axis()
								.scale(y1)
								.orient('left');
				//x坐标轴
				var xAxis = d3.svg.axis()
								.scale(x)
								.orient("bottom")
								.ticks(d3.time.day)
								.tickFormat(function(d){return xparseTime(d)});

				svg.append("g")
				   .attr("class", "x axis")
				   .attr("transform", "translate(0," + height + ")")
				   .call(xAxis);

				svg.append("g")
					.attr("class", "y axis")
					.call(yAxis1)	
					.append('text')
					.text("日均消费额（元）");

				svg.append("path")
				   .datum(objData)
				   .attr("id", "line-sales")
				   .attr("d", lineSales);

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
			             .html("日期：" + yparseTime(d.date)+'  <br/> 总消费:'+d.sales + " 元");                          
			        focus.select("circle.y")                           
			            .attr("transform",                             
			                  "translate(" + x(d.date) + "," +         
			                                 y1(d.sales) + ")");        
			    }             
			}
		});	
	}
	

</script>

</body>
</html>