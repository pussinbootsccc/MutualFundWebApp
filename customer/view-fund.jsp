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
                </div>
            </div>
            <!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<!-- /.panel-heading -->
					<div class="panel panel-default">
                        <div class="panel-heading">
                            Available Funds
                        </div>
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-tickers">
                                    <thead>
                                        <tr>
                                            <th>Fund Name</th>
                                            <th>Ticker</th>
                                            <th>Last Share Price</th>
                                            <th>View</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach var="fund" items="${fundList}">
                                    		<tr class="odd gradeX">
                                    		<td>${fund.fundName}</td>
                                            <td>${fund.fundTicker}</td>
                                            <td class="number">${fund.fundPrice}</td>
                                            <td>
												<form action="view-fund.do" method="post">
													<c:choose>
														<c:when test="${fund.fundPrice eq '-'}">
							        						<input type="submit" class="btn btn-outline btn-info" value="View" disabled>
							    						</c:when>
							    						<c:otherwise>
							        						<input type="submit" class="btn btn-outline btn-info" value="View">
							    						</c:otherwise>
													</c:choose>
													<input type="hidden" name="fund-id" value="${fund.fundId}">
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
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

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