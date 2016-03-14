<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="/WEB-INF/imports.jsp"%>
</head>
<style>
h2 {text-align: center;}
h3 {text-align: center;}
</style>

<body>
    <div id="wrapper">
        <!-- Navigation -->
		 <div class="container">
        <div class="row">
            <div class="col-lg-4 col-lg-offset-4">
            
                <div class="login-panel panel panel-default">
                	<div class="alert alert-success">
                	
                    	<h2>ACE Mutual Fund</h2>
                    </div>
                    <div class="panel-heading">
                        <h3 class="panel-title">Please Sign In</h3>
                    </div>
                    <div class="panel-body">
                        <form role="form" action="login.do" method="post">
                            <fieldset>
								<div class="form-group">
                                    <label class="radio-inline">
                                        <input type="radio" name="user-group" id="optionsRadiosInline1" value="customer">Login as Customer
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="user-group" id="optionsRadiosInline2" value="employee">Login as Employee
                                    </label>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="User Name" required = "required" name="userName" type="text">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" required = "required" name="password" type="password" value="">
                                </div>
                                
                                	<input type="submit" class="btn btn-lg btn-success btn-block" name="button" value="login"/>
                            </fieldset>
                        </form>
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
        </div>
    </div>
    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="js/jquery.min.js"></script>

</body>
</html>
