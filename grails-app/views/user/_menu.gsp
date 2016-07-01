<div class="btn-group">
	<button type="button" class="btn btn-lg btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
		<span class="sr-only">Toggle Dropdown</span>
	</button>
	<ul class="dropdown-menu">
			<li><h4>${session.client.fullName}</h4></li>
			<li><a href="${createLink(controller:'user', action:'history')}">History <span class="glyphicon glyphicon-calendar" aria-hidden="true"></span></a></li>
			<li><a href="${createLink(controller:'user', action:'profile')}">Update Profile <span class="glyphicon glyphicon-user" aria-hidden="true"></span></a></li>
			<li><a href="${createLink(controller:'book', action:'chooseService')}">Book Appointment <span class="glyphicon glyphicon-time" aria-hidden="true"></span></a></li>
			<li role="separator" class="divider"></li>
			<li><a href="${createLink(controller:'access',action:'logout')}">Logout</a></li>

	</ul>
</div>