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
                    <h2 class="page-header">Transition Day</h1>
                </div>
            </div>
            <form action="e_transition_day.do" method="post">
            <div class="row">
				<div class="col-lg-7">
					<div class="alert alert-success">
                        Last Transition Day:  <strong>${lastTransitionDay}</strong>
                    </div>
				</div>
				<div class="col-lg-5">
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<p> Enter the Transition Date </p>
					<div id="datetimepicker" class="input-append date">
					  <input type="text" name="date" value="${newDay}">
					  <span class="add-on">
						<i data-time-icon="fa fa-clock-o" data-date-icon="fa fa-calendar" style="margin-top: -20px; margin-left: 180px;"></i>
					  </span>
					</div>
				</div>
				</br></br></br></br>
			</div>
			<div class="row">
				<div class="col-lg-7">
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
                                            <th>Fund Ticker</th>
                                            <th>Current Price ($)</th>
                                            <th>Closing Price ($)</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach var="fund" items="${fundList}">
                                    		<tr class="odd gradeX">
                                    		<td>${fund.fundName}</td>
                                            <td>${fund.fundTicker}</td>
                                            <td class="number">${fund.fundPrice}</td>
                                            <td>
												<input type="text" class="form-control" name="fundPrices" required="required" placeholder="0.01-10000.00">
												<input type="hidden" name="fundIds" value="${fund.fundId}">
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
				<div class="col-lg-5">
				</div>
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-3">
					<input type="submit" class="btn btn-info" name="button" value="Update Transition Day">
				</div>
				<div class="col-lg-9">
				</div>
				</br></br></br></br>
			</div>
			</form>
			<div class="row row-content">
				<div class="col-md-4">
					<c:forEach var="error" items="${errors}">
                        	<div class="alert alert-danger alert-dismissable">
                        		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
								<font color="red"><b>${error}</b></font>
							</div>
					</c:forEach>
					<c:if test="${not empty message }">
						<div class="alert alert-success alert-dismissable">
							<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
							<font color="green"><b>${message}</b></font>
						</div>
					</c:if>
				</div>
			</div>
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
	    <!-- DataTables JavaScript -->
    <script src="js/jquery.dataTables.min.js"></script>
    <script src="js/dataTables.bootstrap.min.js"></script>
    <script>
    $(document).ready(function() {
		
        $('#dataTables-tickers').DataTable({
                responsive: false
        });
		$('.dataTables_filter').hide();
    });
	</script>
	<script type="text/javascript" src="http://tarruda.github.com/bootstrap-datetimepicker/assets/js/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript" src="http://tarruda.github.com/bootstrap-datetimepicker/assets/js/bootstrap-datetimepicker.pt-BR.js"></script>
	<script type="text/javascript">
	  var picker = $('#datetimepicker').datetimepicker({
		//defaultDate: "2016/01/05",
		format: 'yyyy-MM-dd',
		pickTime: false,
		language: 'en-US',
		pickSeconds: false,
	  });
	</script>
</body>
</html>
