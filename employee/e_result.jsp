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
                    <h2 class="page-header">Notification</h1>
                </div>
            </div>
			<div class="row">
                <div class="col-lg-6">
				 <c:if test="${not empty message}">
						<div class="alert alert-success alert-dismissable">
							<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
							<font color="green"><b>${message}</b></font>
						</div>
					</c:if>
                </div>
                <div class="col-lg-6">
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
		$('.dataTables_filter').hide();
    });
	</script>

</body>
</html>
