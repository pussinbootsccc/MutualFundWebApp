<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="resources/css/morris.css" rel="stylesheet">
<!-- jQuery -->
<script src="resources/js/jquery.min.js"></script>
<!-- Chart JavaScript -->
<script src="resources/js/raphael-min.js"></script>
<script src="resources/js/morris.min.js"></script>
</head>
<body>

	<div id="show">${fundHistory.date }</div>
	<c:forEach var="item" items="${fundViews}"  varStatus="loop">
				         {
				        	 period: <c:out value="${item.date}" />,
				        	 ${fundTicker}:parseFloat(${item.price})
				         } <c:if test="${!loop.last}">,</c:if>
	</c:forEach>


	<div id="morris-area-chart"></div>
	<script>
	
	$(function() {
		function data() {
			d = [];
			<c:forEach items="${fundViews}" var="item" varStatus="loop">
			d.push({period: "${item.date}",${fundTicker}:${item.price} }); 
	         </c:forEach>
			  return d;
			  
			}
		console.log(data());
		Morris.Area({
			element : 'morris-area-chart',
			data : data(),
			xkey : 'period',
			ykeys : [ "${fundTicker}" ],
			labels : [ "${fundTicker}"],
			pointSize : 2,
			hideHover : 'auto',
			resize : true
		});
	});

		</script>
</body>
</html>