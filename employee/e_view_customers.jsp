<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="/WEB-INF/imports.jsp"%>
</head>
<body>
    <div id="wrapper">
        <!-- Navigation -->
        <%@include file="/WEB-INF/employee/e_navbar.jsp"%>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header">View Customer Accounts</h1>
                </div>
            </div>
			<div class="row">
				<div class="col-lg-12">	
					<div class="panel panel-default">
                        <div class="panel-heading">
                            Customers
                        </div>
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-tickers">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>UserName</th>
                                            <th>First Name</th>
                                            <th>Last Name</th>
                                            <th>Details</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <!-- Remove java -->
                                    
                                    <c:forEach var="item" items="${customerList}" varStatus ="loop">

											<tr class="odd gradeX">
												<td>${loop.index + 1}</td>
												<td>${item.userName}</td>
												<td>${item.firstName}</td>
												<td>${item.lastName}</td>
												<td>
													<form action="e_view_customer_detail.do" method="post">
													<input type="hidden" name="customerId" value="${item.customerId}">
														<input type="submit" class="btn btn-primary" value="Detail">
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
				</div>
			</div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="js/jquery.min.js"></script>
    <!-- Chart JavaScript -->
    <script src="js/raphael-min.js"></script>
    <script src="js/morris.min.js"></script>
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
