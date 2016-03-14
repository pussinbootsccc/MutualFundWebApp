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
                    <h1 class="page-header">Sell Fund</h1>
                </div>
            </div>
            <!-- /.row -->
			<div class="row">
                <div class="col-lg-12">
				<div class="panel panel-default">
                        <div class="panel-heading">
                            Funds to Sell
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-tickers">
                                    <thead>
                                        <tr>
                                            <th>Fund Name</th>
                                            <th>Ticker</th>
                                            <th>Available Shares</th>
                                            <th>Last Share Price ($)</th>
                                            <th>Sell</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach var="customerFund" items="${customerFunds}">
                                    		<tr class="odd gradeX">
                                    		<td>${customerFund.fundName}</td>
                                    		<td>${customerFund.fundTicker}</td>
                                            <td class="number">${customerFund.shares}</td>
                                            <td class="number">${customerFund.fundPrice}</td>
                                            <td>
												<form action="sell-fund.do" method="post">
													<input type="submit" class="btn btn-outline btn-danger" value="Sell">
													<input type="hidden" name="fund-id" value="${customerFund.fundId}">
												</form>
											</td>
                                        	</tr>
                                    	</c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- /.table-responsive -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
				</div>
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->
    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
    <!-- jQuery -->
    <script src="js/jquery.min.js"></script>

    <!-- DataTables JavaScript -->
    <script src="js/jquery.dataTables.min.js"></script>
    <script src="js/dataTables.bootstrap.min.js"></script>
    <script>
    $(document).ready(function() {
        $('#dataTables-tickers').DataTable({
                responsive: false
        });
    });
    </script>
</body>
</html>
