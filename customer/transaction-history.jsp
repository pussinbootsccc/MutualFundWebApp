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
                    <h1 class="page-header">Transaction History</h1>
                </div>
            </div>
            <!-- /.row -->
			<div class="row">
                <div class="col-lg-12">
				<div class="panel panel-default">
                        <div class="panel-heading">
                            Transactions
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
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->
    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
    <!-- jQuery -->
    <script src="js/jquery.min.js"></script>

    <!-- DataTables JavaScript -->
    <script src="js/jquery.dataTables.min.js"></script>
    <script src="js/dataTables.bootstrap.min.js"></script>
    <script>
    $(document).ready(function() {
        $('#dataTables-tickers').DataTable({
        		"aaSorting": [],
                responsive: false
        });
    });
    </script>
</body>
</html>
