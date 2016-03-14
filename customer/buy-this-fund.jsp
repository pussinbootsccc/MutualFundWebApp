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
                    <h1 class="page-header">Buy ${fund.fundName}(${fund.fundTicker}) Shares</h1>
                </div>
            </div>
            <!-- /.row -->
			<div class="row">
                <div class="col-lg-12">
                <form action="buy-fund.do" method="post">
				<div class="panel panel-info">
                        <div class="panel-heading">
                            Fund to sell
                        </div>
						<div class="panel-body">
							<div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th>Fund</th>
                                            <th>Available Cash ($)</th>
                                            <th>Last Share Price ($)</th>
                                            <th>Amount to Buy ($)</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>${fund.fundTicker}</td>
                                            <td class="number">${availableCash}</td>
                                            <c:choose>
                                            	<c:when test="${lastSharePrice eq '0.00'}">
                                            		<td class="number">-</td>
                                            	</c:when>
                                            	<c:otherwise>
                                            		<td class="number">${lastSharePrice}</td>
                                            	</c:otherwise>
                                            </c:choose>
                                            <td><input type="text" class="form-control" id="inputSuccess" name="amount" required = "required" placeholder="100.00"></input></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
						</div>
						<div class="panel-footer">
							<!-- why??? error!!!! -->
							<input type="hidden" name="fund-id" value="${fundId}">
							<c:choose>
								<c:when test="${availableCash eq '0.00'}">
								<div data-toggle="tooltip" title="Buy button does not work, because you do not have available cash amount to buy." >
							        <input type="submit" class="btn btn-primary btn-lg btn-block" name="button" value="Buy" disabled>
							    </div>
							    </c:when>
							    <c:otherwise>
							        <input type="submit" class="btn btn-primary btn-lg btn-block" name="button" value="Buy">
							    </c:otherwise>
							</c:choose>
							
						</div>
                    </div>
                    <!-- /.panel -->
                </form>
				</div>
            </div>
            <!-- show message or errors -->
            <div class="row row-content">
				<div class="col-md-4">
						<c:if test="${not empty message }">
							<div class="alert alert-success alert-dismissable">
								<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
								<font color="green"><b>${message}</b></font>
							</div>
						</c:if>
						<c:forEach var="error" items="${errors}">
                        	<div class="alert alert-danger alert-dismissable">
                        		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
								<font color="red"><b>${error}</b></font>
							</div>
						</c:forEach>
					
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
