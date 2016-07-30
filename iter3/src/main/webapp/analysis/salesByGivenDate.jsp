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
		<label>起始日期</label>
		<input class="form-control" type="date" id="beginDate" name="beginDate" required />
	</div>
	<div class="form-group">
		<label>结束日期</label>
		<input class="form-control" type="date" id="endDate" name="endDate" required />
	</div>
	<button class="btn btn-default" onclick="show()">show</button>
</div>


<script src="<%= path %>/static/js/jquery-1.12.2.min.js"></script>
<script src="<%= path %>/static/js/d3.min.js" charset="utf-8"></script>
<script src="<%=path %>/static/js/iframeResizer.contentWindow.min.js"></script>

<script type="text/javascript">
	
	var margin = {top: 20, right: 30, bottom: 30, left: 50},
				width = window.innerWidth- margin.left - margin.right,
				height = 530 - margin.top - margin.bottom -20;
	function show(){
		var beginDate = $('#beginDate').val();
		var endDate = $('#endDate').val();
		if(beginDate == "" || endDate == "")
			return;
		$.post({
			url:"<%= path %>/admin/data_getSalesByGivenDate",
			data: {	'beginDate': beginDate,
					'endDate'  : endDate
			},
			success:function(data){
				 console.log(data);

				 $('svg').remove();
				 var svg = d3.select("body").append("svg")
					.attr("width", width + margin.left + margin.right)
					.attr("height", height + margin.top + margin.bottom)
					.append("g")
					.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

				var objData = [];							// make date consistant
				var p = 0;
				var tDate = new Date(beginDate);
				while( tDate.getTime() < Date.parse(endDate) ) {
					if( p < data.date.length && tDate.getTime() == Date.parse(data.date[p]) ) {
						objData.push({
							"date" : data.date[p],
							"sales": data.sales[p]/100.0
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
								.tickFormat(function(d){return xparseTime(d)});

				svg.append("g")
				   .attr("class", "x axis")
				   .attr("transform", "translate(0," + height + ")")
				   .call(xAxis);

				svg.append("g")
					.attr("class", "y axis")
					.call(yAxis1)	
					.append('text')
					.text("销售额（元）");

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
			             .html("日期：" + yparseTime(d.date)+'  <br/> 销售额:'+d.sales + " 元");                          
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