<nav class="navbar navbar-default navbar-static-top" role="navigation"
	style="margin-bottom: 0">
	<div class="navbar-header">
		<a class="navbar-brand" href="employee-home.do">Employee Account
			(${employee.userName})</a>
	</div>
	<!-- /.navbar-header -->

	<div class="navbar-default sidebar" role="navigation">
		<div class="sidebar-nav navbar-collapse">
			<ul class="nav" id="side-menu">
				<li><a><i class="fa fa-user fa-fw"></i> Profile <span
						class="fa arrow"></span></a>
					<ul class="nav nav-second-level">
						<li><a href="e_create_employee.do"><i
								class="fa fa-history fa-fw"></i>Create Employee Account</a></li>
						<li><a href="e_change_password.do"><i
								class="fa fa-gear fa-fw"></i>Change Password</a></li>
						<li><a href="logout.do"><i class="fa fa-unlock-alt fa-fw"></i>Logout</a>
						</li>
					</ul> <!-- /.nav-second-level --></li>
				<li><a><i class="fa fa-sliders fa-fw"></i>
						Operations <span class="fa arrow"></span></a>
					<ul class="nav nav-second-level">
						<li><a href="e_create_customer.do"><i
								class="fa fa-plus-circle fa-fw"></i>Create Customer Account</a></li>
						<li><a href="e_view_customer_list.do"><i
								class="fa fa-eye fa-fw"></i>View Customer Account</a></li>
						<li><a href="e_create_fund.do"><i
								class="fa fa-bank fa-fw"></i>Create Fund</a></li>
						<li><a href="e_transition_day.do"><i
								class="fa fa-calendar fa-fw"></i>Transition Day</a></li>

					</ul> <!-- /.nav-second-level --></li>
			</ul>
		</div>
		<!-- /.sidebar-collapse -->
	</div>
	<!-- /.navbar-static-side -->
</nav>
