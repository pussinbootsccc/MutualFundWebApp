<%@ page isErrorPage="true"%>
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
                    <h2 class="page-header">Whoops!</h1>
                    <p>We're sorry. The Web address you entered is not a functioning page on our site.</p>
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

					<c:choose>
    					<c:when test="${not empty sessionScope.customer}">
	 			            <a href="customer-home.do">Back to Customer Home Page</a>
						</c:when>
						<c:when test="${not empty sessionScope.employee}">
						    <a href="employee-home.do">Back to Employee Home Page</a>
						</c:when>
						<c:otherwise>
						    <a href="login.do">Back to Login Page</a>
						</c:otherwise>
					</c:choose>					
				</div>
			</div>
			</div>
	</div>
</body>
</html>