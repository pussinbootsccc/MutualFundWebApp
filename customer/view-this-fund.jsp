<%@ page import="java.util.List" import="java.text.SimpleDateFormat" import="databeans.FundHistoryView"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="/WEB-INF/imports.jsp"%>
</head>
<body>
    <div id="wrapper">
        <!-- Navigation -->
        <%@include file="/WEB-INF/customer/navbar.jsp"%>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Research Fund</h1>
					<br>
                </div>
                <!-- /.col-lg-12 -->
            </div>
			<div class="row">
				<div class="col-lg-7">
					<H3>Share Price History of ${fundName}</H3>
				</div>
				<div class="col-lg-5">
				</div>
			</div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-7">
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div id="morris-area-chart"></div>
                    </div>
                    <!-- /.panel-body -->                 
                </div>
                <div class="col-lg-5">
				</div>
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

   	<script>

	$(function() {
		function data() {
			d = [];
			<c:forEach items="${history}" var="item" varStatus="loop">
			d.push({period: "${item.date}", ${fundTicker}:${item.price} }); 
	         </c:forEach>
			  return d;
			  
			}
		//console.log(data());
		Morris.Line({
			element : 'morris-area-chart',
			data : data(),
			xkey : 'period',
			ykeys : [ "${fundTicker}" ],
			labels : [ "${fundTicker}"],
			pointSize : 2,
			hideHover : 'auto',
			resize : true,
			smooth: false,
			xLabels: "day"
		});
	});

		
	</script>
</body>
</html>