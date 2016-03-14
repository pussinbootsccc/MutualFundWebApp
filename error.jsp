<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="/WEB-INF/imports.jsp"%>
</head>
<body>
    <div id="wrapper">
    <c:choose>
    	<c:when test="${not empty sessionScope.customer}">
            <%@include file="/WEB-INF/customer/navbar.jsp"%>
		</c:when>
		<c:when test="${not empty sessionScope.employee}">
		    <%@include file="/WEB-INF/employee/e_navbar.jsp"%>
		</c:when>
	</c:choose>    
     <div id="page-wrapper">
     <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header">Error Notification</h1>
                </div>
            </div>
			<div class="row row-content">
				<div class="col-md-4">
					<c:forEach var="error" items="${errors}">
                        	<div class="alert alert-danger alert-dismissable">
                        		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
								<font color="red"><b>${error}</b></font>
							</div>
					</c:forEach>
                    <%--<c:if test="${not empty message }">
						<div class="alert alert-success alert-dismissable">
							<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
							<font color="green"><b>${message}</b></font>
						</div>
					</c:if> --%>
					<c:choose>
    					<c:when test="${not empty sessionScope.customer}">
	 			            <a href="customer-home.do">Back to customer home</a>
						</c:when>
						<c:when test="${not empty sessionScope.employee}">
						    <a href="employee-home.do">Back to employee home</a>
						</c:when>
						<c:otherwise>
						    <a href="login.do">Back to login page</a>
						</c:otherwise>
					</c:choose>					
				</div>
			</div>
			</div>
	</div>
</body>
</html>