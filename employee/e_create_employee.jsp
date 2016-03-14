<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="/WEB-INF/imports.jsp"%>
</head>
<body>
    <div id="wrapper">
        <!-- Navigation -->
        <%@include file="/WEB-INF/employee/e_navbar.jsp"%>
        
		<form action="e_create_employee.do" method="POST">
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header">Create Employee Account</h1>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-3">
					<p class="text-success">Enter Employee Username*</p>
                </div>
                <div class="col-lg-3">
					<input type="text" class="form-control" id="inputSuccess" name="userName" required="required" placeholder="username" value="${form.userName}">
				</div>
				<div class="col-lg-7">
				</div>
            </div>
             <br/>
			<div class="row">
                <div class="col-lg-3">
					<p class="text-success">Enter Employee Password*</p>
                </div>
                <div class="col-lg-3">
					<input type="password" class="form-control" id="inputSuccess" name="password" required="required" placeholder="password">
				</div>
				<div class="col-lg-7">
				</div>
            </div>
             <br/>
            <div class="row">
                <div class="col-lg-3">
					<p class="text-success">Confirm Password*</p>
                </div>
                <div class="col-lg-3">
					<input type="password" class="form-control" id="inputSuccess" name="confirmPassword" required="required" placeholder="password" >
				</div>
				<div class="col-lg-7">
				</div>
            </div>
			 <br/>
			<div class="row">
				<div class="col-lg-3">	
					<p class="text-success">Enter Employee First Name*</p>
				</div>	
				<div class="col-lg-3">	
					<input type="text" class="form-control" id="inputSuccess" name="firstName" required="required" placeholder="Donald" value="${form.firstName}">
				</div>
				<div class="col-lg-7">	
				</div>
			</div>
			 <br/>
			<div class="row">
				<div class="col-lg-3">	
					<p class="text-success">Enter Employee Last Name*</p>
				</div>	
				<div class="col-lg-3">	
					<input type="text" class="form-control" id="inputSuccess" name="lastName" required="required" placeholder="Trump" value="${form.lastName}">
				</div>
				<div class="col-lg-7">	
				</div>
			</div>
			 <br/>
			<div class="row">
				<div class="col-lg-3">	
					
				</div>	
				<div class="col-lg-3">	
				</br>
					<input type="submit" class="btn btn-success" name="create" value="Create"/>
				</div>
				<div class="col-lg-7">	
				</div>
			</div>
			</br>
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
            <!-- /.row -->
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
