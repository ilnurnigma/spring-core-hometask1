<html>
<head></head>
<body>
<h1>User service form</h1>

<h2>Save user</h2>
<form name="saveUser" method="post" action="saveUser">
    Firstname: <input type="text" name="firstName" />	<br/>
  	Lastname:  <input type="text" name="lastName" />	<br/>
  	E-mail:    <input type="text" name="email" />	<br/>
  	<input type="submit" value="Save" />
</form>

<h2>Get user by email</h2>
<form method="post" action="getUserByEmail">
    E-mail: <input type="text" name="email" />	<br/>
  	<input type="submit" value="Find" />
</form>

<h2>Delete user by email</h2>
<form method="post" action="deleteUserByEmail">
    E-mail: <input type="text" name="email" />	<br/>
  	<input type="submit" value="Delete" />
</form>

<a href=getAllUsers>Get all users</a>
</br>
<a href=/>Main page</a>

</body>
</html>