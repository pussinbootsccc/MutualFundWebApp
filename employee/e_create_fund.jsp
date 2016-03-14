<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="/WEB-INF/imports.jsp"%>
</head>
<body>
    <div id="wrapper">
        <!-- Navigation -->
        <%@include file="/WEB-INF/employee/e_navbar.jsp"%>

<form action="e_create_fund.do" method="POST">
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header">Create Fund</h1>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-5">
                    <p class="text-success">Enter Fund Name*</p>
                    
                </div>
                <div class="col-lg-3">
                    <p class="text-success">Enter Ticker*</p>
                </div>
                <div class="col-lg-4">
                    
                </div>
            </div>
            <div class="row">
                <div class="col-lg-5">  
                    <input type="text" class="form-control" name="fundName" required="required" placeholder="GOOGLE" value="${form.fundName}">
                </div>  
                <div class="col-lg-3">  
                    <input type="text" class="form-control" name="fundTicker" required="required" placeholder="GOOG" value="${form.fundTicker}">
                </div>
                <div class="col-lg-4">  
                    <input type="submit" class="btn btn-success" name="action" Value="Add"/>
                </div>
            </div>
            <!-- /.row -->
                <div class="row row-content">
            <div class="col-md-4">
                        <!--Error messages show here -->
                        </br>
                        <c:forEach var="error" items="${errors}">
                        	<div class="alert alert-danger alert-dismissable">
                        		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
								<font color="red"><b>${error}</b></font>
							</div>
						</c:forEach>
            </div>
        </div>
        </div>
        <!-- /#page-wrapper -->
        
</form>

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
