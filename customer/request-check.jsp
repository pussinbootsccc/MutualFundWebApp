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
                    <h1 class="page-header">Request Check</h1>
                </div>
            </div>
            <!-- /.row -->
			<div class="row">
                
                <div class="col-lg-5">
					<span><p class="text-primary">Enter the Dollar Amount of Check</p></span>
				</div>
				<div class="col-lg-7">
					
				</div>
            </div>
            <div class="row">
            	<form action="request-check.do" method="post">
                <div class="col-lg-5">
					<div class="form-group input-group">
                        <span class="input-group-addon">$</span>
                        <input type="text" class="form-control" name="amount" required = "required" placeholder="100.00">
                        <!-- <span class="input-group-addon">.00</span> -->
                    </div>
				</div>
				<div class="col-lg-7">
					<input type="submit" class="btn btn-outline btn-success" name="button" value="Withdraw">
				</div>
				</form>
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

    <!-- jQuery -->
    <script src="js/jquery.min.js"></script>

</body>
</html>
