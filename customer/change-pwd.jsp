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
                    <h1 class="page-header">Change Password</h1>
                </div>
            </div>
            <!-- /.row -->
			<div class="row">
<!--                 <div class="col-lg-2">
					<span><p class="text-primary">Enter Old Password</p></span>
                </div> -->
                <div class="col-lg-3">
					<span><p class="text-primary">Enter New Password*</p></span>
				</div>
				<div class="col-lg-3">
					<span><p class="text-primary">Confirm New Password*</p></span>
				</div>
				<div class="col-lg-6">
					
				</div>
            </div>
            <form role="form" action="change-pwd.do" method="post">
            <div class="row">
<!--                 <div class="col-lg-2">
					<div class="form-group input-group">
						<input type="password" class="form-control">
                    </div>
                </div> -->
                <div class="col-lg-3">
					<div class="form-group input-group">
                        <input type="password" name="newPassword" required="required" placeholder="password" class="form-control">
                    </div>
				</div>
				<div class="col-lg-3">
					<div class="form-group input-group">
                        <input type="password" name="confirmPassword" required="required" placeholder="password" class="form-control">
                    </div>
				</div>
				<div class="col-lg-6">
					<input type="submit" class="btn btn-outline btn-success" name="button" value="Change Password">
				</div>
				<div class="col-lg-6">
				</div>
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
    <script src="js/morris-data.js"></script>

</body>
</html>
