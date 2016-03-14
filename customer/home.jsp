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
                    <h1 class="page-header">${customer.firstName} ${customer.lastName}</h1>
					<h4> 
					${customer.addr_line1}<br>
					${customer.addr_line2}<br>
					${customer.city}, ${customer.state} ${customer.zip}<br>
					</h4>
					<br>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-3 col-md-6" data-toggle="tooltip" title="This is the amount in your account as of the end of last trading day.">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-dollar fa-3x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge"><i class=""></i>${total}</div>
                                    
                                </div>
                            </div>
                        </div>
                        <a>
                            <div class="panel-footer">
                                <span class="pull-right">Balance</span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
				
                <div class="col-lg-3 col-md-6" data-toggle="tooltip" title="This is the amount available for immediate withdrawal from your account.">
                    <div class="panel panel-green">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-dollar fa-3x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge"><i class=""></i>${available}</div>
                                    
                                </div>
                            </div>
                        </div>
                        <a>
                            <div class="panel-footer">
                                <span class="pull-right">Available Cash</span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
               
                <div class="col-lg-4 col-md-6" data-toggle="tooltip" title="This is the day when your last transaction was made.">
                    <div class="panel panel-yellow">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-calendar-o fa-3x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">${lastDate}</div>
                                </div>
                            </div>
                        </div>
                        <a>
                            <div class="panel-footer">
                                <span class="pull-right">Last Trading Date</span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-10">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Position
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>Fund</th>
                                            <th>Available Shares</th>
                                            <th>Last Share Price ($)</th>
                                            <th>Value ($)</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                       <c:forEach var="customerFund" items="${customerFunds}">
                                   		<tr class="odd gradeX">
                                   		<td>${customerFund.fundTicker}</td>
                                           <td class="number">${customerFund.shares}</td>
                                           <td class="number">${customerFund.fundPrice}</td>
                                           <td class="number">${customerFund.total}</td>
                                       	</tr>
                                   	  </c:forEach>
                                    </tbody>
                                </table>
                            </div>
<!--                             /.table-responsive
							<div class="well">
                                <h4>Some info</h4>
                                <p>Some text </p>
                            </div> -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->                    
                </div>
                <!-- /.col-lg-8 -->
                <div class="col-lg-5">
					<!-- <div class="panel panel-default">
                        <div class="panel-heading">
                            Shares pending
                        </div>
                        /.panel-heading
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>Fund</th>
                                            <th>Value</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr class="odd gradeX">
                                            <td>GOOG</td>
                                            <td class="center">$3000</td>
                                        </tr>
                                        <tr class="even gradeX">
                                            <td>GOOG</td>
                                            <td class="center">$3000</td>
                                        </tr>
										<tr class="odd gradeX">
                                            <td>GOOG</td>
                                            <td class="center">$3000</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            /.table-responsive
							<div class="well">
                                <h4>Some info</h4>
                                <p>Some text </p>
                            </div>
                        </div>
                        /.panel-body
                    </div> -->
                    <!-- /.panel -->
				</div>
                <!-- /.col-lg-4 -->
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
    <script>
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
                responsive: false
        });
    });
    
    $(document).ready(function(){
        $('[data-toggle="tooltip"]').tooltip();   
    });
    </script>
</body>
</html>
