<html>
<head></head><body>
	

	<div class="col-xs-12 col-sm-offset-3 col-sm-6">
		<h1><span class="handwriting">Welcome,</span> ${session?.user?.fullName}</h1>

		<hr>
		
		<form method="post" action="updateProfile">
			<div class="form-group">
				<label for="firstName">First Name</label>
				<input type="text" class="form-control" id="firstName" name="firstName" placeholder="First Name" value="${session?.user?.firstName}">
			</div>
			<div class="form-group">
				<label for="lastName">Last Name</label>
				<input type="text" class="form-control" id="lastName" name="lastName" placeholder="Last Name" value="${session?.user?.lastName}">
			</div>
			<div class="form-group">
				<label for="phoneNumber">Phone</label>
				<input type="text" class="form-control" id="phoneNumber" name="phoneNumber" placeholder="Phone Number" value="${session?.user?.phone}">
			</div>
			<div class="form-group">
				<label for="email">Email address</label>
				<input type="email" class="form-control" id="email" name="email" placeholder="Email" value="${session?.user?.email}">
			</div>
			<div class="form-group">
				<label for="password">Password</label>
				<input type="password" class="form-control" id="password" name="password" placeholder="Password" value="${session?.user?.password}">
			</div>
			<input type="submit" class="btn btn-default green-button" value="Update Profile" />
		</form>
	</div>


</body></html>