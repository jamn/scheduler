<div class="btn-group">
	<button type="button" class="btn btn-lg btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
		<span class="sr-only">Toggle Dropdown</span>
	</button>
	<ul class="dropdown-menu">
			<li><h4>${session.client.fullName}</h4></li>
			<li><a href="#">History</a></li>
			<li><a href="${createLink(controller:'user', action:'profile')}">Update Profile</a></li>
			<li role="separator" class="divider"></li>
			<li><a href="${createLink(controller:'access',action:'logout')}">Logout</a></li>

	</ul>
</div>