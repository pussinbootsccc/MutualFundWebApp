<!DOCTYPE html>
<html lang="en">
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/WEB-INF/imports.jsp"%>
</head>
<body>
    <div id="wrapper">
        <!-- Navigation -->
        <%@include file="/WEB-INF/employee/e_navbar.jsp"%>
        
<form method="post" action="e_create_customer.do">
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header">Create Customer Account</h1>
                </div>
            </div>
			<div class="row">
                <div class="col-lg-3">
					<p class="text-success">Enter Customer Username*</p>
                </div>
                <div class="col-lg-3">
					<input type="text" class="form-control" id="inputSuccess" name="userName" required = "required" placeholder="username" value="${form.userName}">
				</div>
				<div class="col-lg-7">
				</div>
            </div>
            <br/>
			<div class="row">
                <div class="col-lg-3">
					<p class="text-success">Enter Customer Password*</p>
                </div>
                <div class="col-lg-3">
					<input type="password" class="form-control" id="inputSuccess" name="password" required = "required" placeholder="password">
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
					<input type="password" class="form-control" id="inputSuccess" name="confirmPassword" required = "required" placeholder="password">
				</div>
				<div class="col-lg-7">
				</div>
            </div>
			 <br/>
			<div class="row">
				<div class="col-lg-3">	
					<p class="text-success">Enter Customer First Name*</p>
				</div>	
				<div class="col-lg-3">	
					<input type="text" class="form-control" id="inputSuccess" name="firstName" required="required" placeholder="Harry" value="${form.firstName}">
				</div>
				<div class="col-lg-7">	
				</div>
			</div>
			 <br/>
			<div class="row">
				<div class="col-lg-3">	
					<p class="text-success">Enter Customer Last Name*</p>
				</div>	
				<div class="col-lg-3">	
					<input type="text" class="form-control" id="inputSuccess" name="lastName" required="required" placeholder="Potter" value="${form.lastName}">
				</div>
				<div class="col-lg-7">	
				</div>
			</div>
			 <br/>
			<div class="row">
				<div class="col-lg-3">	
					<p class="text-success">Enter Customer Address Line 1*</p>
				</div>	
				<div class="col-lg-3">	
					<input type="text" class="form-control" id="inputSuccess" name="addressLine1" required="required" placeholder="5000 Forbes Ave" value="${form.addressLine1}">
				</div>
				<div class="col-lg-7">	
				</div>
			</div>	
			 <br/>
			<div class="row">
				<div class="col-lg-3">	
					<p class="text-success">Enter Customer Address Line 2</p>
				</div>	
				<div class="col-lg-3">	
					<input type="text" class="form-control" id="inputSuccess" name="addressLine2" placeholder="Apt 66" value="${form.addressLine1}">
				</div>
				<div class="col-lg-7">	
				</div> 
			</div>	
			 <br/>
			<div class="row">
				<div class="col-lg-3">	
					<p class="text-success">Enter Customer City*</p>
				</div>	
				<div class="col-lg-3">	
					<input type="text" class="form-control" id="inputSuccess" name="city" required="required" placeholder="Pittsburgh" value="${form.city}">
				</div>
				<div class="col-lg-7">	
				</div>
			</div>	
			 <br/>
			<div class="row">
				<div class="col-lg-3">	
					<p class="text-success">Enter Customer State*</p>
				</div>	
				<div class="col-lg-3">	
					<%-- <input type="text" class="form-control" id="inputSuccess" name="state" required="required" placeholder="PA" value="${form.state}"> --%>
					<select class="form-control" id="state-dropdown" name="state" required>
						<option value="AK">Alaska</option>
						<option value="AL">Alabama</option>
						<option value="AR">Arkansas</option>
						<option value="AZ">Arizona</option>
						<option value="CA">California</option>
						<option value="CO">Colorado</option>
						<option value="CT">Connecticut</option>
						<option value="DC">District of Columbia</option>
						<option value="DE">Delaware</option>
						<option value="FL">Florida</option>
						<option value="GA">Georgia</option>
						<option value="HI">Hawaii</option>
						<option value="IA">Iowa</option>
						<option value="ID">Idaho</option>
						<option value="IL">Illinois</option>
						<option value="IN">Indiana</option>
						<option value="KS">Kansas</option>
						<option value="KY">Kentucky</option>
						<option value="LA">Louisiana</option>
						<option value="MA">Massachusetts</option>
						<option value="MD">Maryland</option>
						<option value="ME">Maine</option>
						<option value="MI">Michigan</option>
						<option value="MN">Minnesota</option>
						<option value="MO">Missouri</option>
						<option value="MS">Mississippi</option>
						<option value="MT">Montana</option>
						<option value="NC">North Carolina</option>
						<option value="ND">North Dakota</option>
						<option value="NE">Nebraska</option>
						<option value="NH">New Hampshire</option>
						<option value="NJ">New Jersey</option>
						<option value="NM">New Mexico</option>
						<option value="NV">Nevada</option>
						<option value="NY">New York</option>
						<option value="OH">Ohio</option>
						<option value="OK">Oklahoma</option>
						<option value="OR">Oregon</option>
						<option value="PA">Pennsylvania</option>
						<option value="PR">Puerto Rico</option>
						<option value="RI">Rhode Island</option>
						<option value="SC">South Carolina</option>
						<option value="SD">South Dakota</option>
						<option value="TN">Tennessee</option>
						<option value="TX">Texas</option>
						<option value="UT">Utah</option>
						<option value="VA">Virginia</option>
						<option value="VT">Vermont</option>
						<option value="WA">Washington</option>
						<option value="WI">Wisconsin</option>
						<option value="WV">West Virginia</option>
						<option value="WY">Wyoming</option>
					</select>
				</div>
				<div class="col-lg-7">	
				</div>
			</div>		
			 <br/>	
			<div class="row">
				<div class="col-lg-3">	
					<p class="text-success">Enter Customer Zip Code*</p>
				</div>	
				<div class="col-lg-3">	
					<input type="text" class="form-control" id="inputSuccess" name="zip" required="required" placeholder="15213" value="${form.zip}">
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
        </form>
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
    
    $(document).ready(function() {
    	var nodes = document.getElementById("state-dropdown").childNodes;     
    	for (i = 0; i < nodes.length; i++) {
    		if ('${form.state}' == '') {
    			if (nodes[i].value == 'PA') {
        			nodes[i].selected = true;
        		} else {
        			nodes[i].selected = false;
        		}
    		} else {
    			if (nodes[i].value == '${form.state}') {
        			nodes[i].selected = true;
        		} else {
        			nodes[i].selected = false;
        		}
    		}
    	}
    });
	</script>

</body>
</html>
