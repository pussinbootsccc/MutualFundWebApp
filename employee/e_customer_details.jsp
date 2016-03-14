<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="databeans.Customer"%>
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
                    <h2 class="page-header">Customer Details</h1>
                </div>
            </div>

			<div class="row">
				<div class="col-lg-12">	
					<div class="panel panel-default">
                        <div class="panel-heading">
                       
                             ${customer.firstName } ${customer.lastName } (<strong>${customer.userName }</strong>)
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <!-- Nav tabs -->
                            <ul class="nav nav-tabs">
                                <c:choose>
                                <c:when test="${messageType == null}">
                                    <li class="active"><a href="#general" data-toggle="tab" aria-expanded="true">General</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class=""><a href="#general" data-toggle="tab" aria-expanded="true">General</a>
                                    </li>
                                </c:otherwise>
                                </c:choose>

                                <c:choose>
                                <c:when test="${messageType == 'passwordError' || messageType == 'passwordSuccess'}">
                                <li class="active"><a href="#password" data-toggle="tab" aria-expanded="false">Password</a>
                                </li>
                                </c:when>
                                <c:otherwise>
                                    <li class=""><a href="#password" data-toggle="tab" aria-expanded="false">Password</a>
                                    </li>
                                </c:otherwise>
                                </c:choose>

                                <li class=""><a href="#history" data-toggle="tab" aria-expanded="false">History</a>
                                </li>
                                <c:choose>
                                <c:when test="${messageType == 'despositCheckError' || messageType == 'depositCheckSuccess'}">
                                    <li class="active"><a href="#check" data-toggle="tab" aria-expanded="false">Deposit Check</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class=""><a href="#check" data-toggle="tab" aria-expanded="false">Deposit Check</a>
                                    </li>
                                </c:otherwise>
                                </c:choose>
                            </ul>

                            <!-- Tab panes -->
                            <div class="tab-content">
                                <c:choose>
                                <c:when test="${messageType == null}">
                                <div class="tab-pane fade active in" id="general">
                                </c:when>
                                <c:otherwise>
                                <div class="tab-pane fade" id="general">
                                </c:otherwise>
                                </c:choose>
									<div class="row">
										<div class="col-lg-12">
											</br>
											<strong>Available Cash</strong>
											<p>${balance}</p>
											<strong><i class="fa fa-calendar fa-fw"></i> Last Trading Day </strong>
											<p>${lastTransactionDay}</p>
											<address>
												<strong>Address</strong>
												<br>${customer.addr_line1}
												    ${customer.addr_line2}
										        <br>${customer.city}, ${customer.state} ${customer.zip}
												<br>
												
											</address>
										</div>
									</div>
									<div class="row">
										<div class="col-lg-12">
											<div class="panel panel-default">
												<div class="panel-heading">
													Shares Bought
												</div>
												<!-- /.panel-heading -->
												<div class="panel-body">
													<div class="dataTable_wrapper">
														<table class="table table-striped table-bordered table-hover" id="dataTables-example">
															<thead>
																<tr>
																	<th>Fund</th>
																	<th>Shares Number</th>
																	<th>Share Price ($)</th>
																	<th>Shares Value ($)</th>
																</tr>
															</thead>
															<tbody>
															<c:forEach var="customerFund" items="${customerFunds}">
																<tr class="odd gradeX">
																	<td>${customerFund.fundName}</td>
																	<td class="number">${customerFund.shares}</td>
																	<td class="number">${customerFund.fundPrice}</td>
																	<td class="number">${customerFund.total}</td>
																</tr>
															</c:forEach>
															</tbody>
														</table>
													</div>
												</div>
												<!-- /.panel-body -->
											</div>
										</div>
									</div>
                                </div>
                                <c:choose>
                                <c:when test="${messageType == 'passwordError' || messageType == 'passwordSuccess'}">
                                <div class="tab-pane fade active in" id="password">
                                </c:when>
                                <c:otherwise>
                                <div class="tab-pane fade" id="password">
                                </c:otherwise>
                                </c:choose>
                                <form action="e_change_customer_password.do" method="POST">
									<div class="row">
										<div class="col-lg-4">
											<div class="form-group has-success">
											<br/>
											<p class="text-success">Enter New Password*</p>
												<input type="password" class="form-control" id="inputSuccess" name="newPassword" placeholder="password" required = "required" >
												<br/>
												<p class="text-success">Confirm New Password*</p>
												<input type="password" class="form-control" id="inputSuccess" name="confirmPassword" placeholder="password" required = "required" >
											</div>
											<input type="hidden" class="btn btn-success" name="userName" value="${customer.userName}">
											<input type="submit" class="btn btn-success" value="Change Password">
										</div>
									</div>	
									</form>
									<div class="row">
              							<div class="col-lg-4">
			                    			</br>
											<c:if test="${(not empty message) && (messageType =='passwordSuccess') }">
												<div class="alert alert-success alert-dismissable">
													<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
													<font color="green"><b>${message}</b></font>
												</div>
											</c:if>
											<c:if test="${messageType =='passwordError'}">
											<c:forEach var="error" items="${errors}">
								               	<div class="alert alert-danger alert-dismissable">
								               		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
													<font color="red"><b>${error}</b></font>
												</div>
											</c:forEach>
											
											</c:if>
											
                						</div>
            						</div>
                                </div>
                                <div class="tab-pane fade" id="history">
                                     <!-- /.row -->
									 </br>
									<div class="row">
										<div class="col-lg-12">
										<div class="panel panel-default">
												<div class="panel-heading">
													Transaction History
												</div>
												<!-- /.panel-heading -->
												<div class="panel-body">
													<div class="dataTable_wrapper">
														<table class="table table-striped table-bordered table-hover" id="dataTables-tickers">
															<thead>
																<tr>
																	<th>Date</th>
																	<th>Operation</th><!--buy, sell, request check, deposit!-->
																	<th>Fund</th>
																	<th>Shares Number</th>
																	<th>Share Price ($)</th>
																	<th>Amount ($)</th>
																	<th>Status</th> <!--pending or not -->
																</tr>
															</thead>
															<tbody>
															<c:forEach var="transaction" items="${transactions}">
																<tr class="odd gradeX">
																	<td>${transaction.date}</td>
																	<td>${transaction.type}</td>
																	<td>${transaction.fundName}</td>
																	<td class="number">${transaction.shares}</td>
																	<td class="number">${transaction.price}</td>
																	<td class="number">${transaction.amount}</td>
																	<td>${transaction.status}</td>
																</tr>
															</c:forEach>
															</tbody>
														</table>
													</div>
													<!-- /.table-responsive -->
												</div>
												<!-- /.panel-body -->
											</div>
											<!-- /.panel -->
										</div>
									</div>
									<!-- /.row -->
                                </div>
                                <c:choose>
                                <c:when test="${messageType == 'depositCheckError' || messageType == 'depositCheckSuccess'}">
                                <div class="tab-pane fade active in" id="check">
                                </c:when>
                                <c:otherwise>
                                <div class="tab-pane fade" id="check">
                                </c:otherwise>
                                </c:choose>
                                    <div class="row">
										<div class="col-lg-5">
										</br>
											<span><p class="text-success">Enter the Dollar Amount of Check</p></span>
										</div>
										<div class="col-lg-7">
										</div>
									</div>
									<div class="row">
									<form action="e_deposit_check.do" method="POST">
										<div class="col-lg-5">
											<div class="form-group input-group">
												<span class="input-group-addon">$</span>
												<input type="text" class="form-control" name="cash" placeholder="100.00" required = "required">
												<input type="hidden" class="form-control" name="userName" value="${customer.userName}">

											</div>
										</div>
										<div class="col-lg-7">
											<input type="submit" class="btn btn-success" value="Deposit">
										</div>
										</form>
										
									</div>
									<div class="row">
            						    <div class="col-lg-4">
											</br>
											<c:if test="${(not empty message) && (messageType =='depositCheckSuccess') }">
												<div class="alert alert-success alert-dismissable">
													<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
													<font color="green"><b>${message}</b></font>
												</div>
											</c:if>
											<c:if test="${messageType =='depositCheckError'}">
											<c:forEach var="error" items="${errors}">
								               	<div class="alert alert-danger alert-dismissable">
								               		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
													<font color="red"><b>${error}</b></font>
												</div>
											</c:forEach>
											</c:if>
               							 </div>
          							  </div>
                                </div>
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
				</div>
			</div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script>
    $(document).ready(function() {
        $('#dataTables-tickers').DataTable({
        		"aaSorting": [],
                responsive: false
        });
    });
    
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
        		"aaSorting": [],
                responsive: false
        });
    });
    </script>

</body>
</html>
