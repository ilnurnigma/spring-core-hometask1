<html>
<head></head>
<body>
<h1>User service form</h1>

<h2>Save user</h2>
<form name="saveUser" method="post" action="save">
    Firstname: <input type="text" name="firstName" />	<br/>
  	Lastname:  <input type="text" name="lastName" />	<br/>
  	E-mail:    <input type="text" name="email" />	<br/>
  	<input type="submit" value="Save" />
</form>

<h2>Get user by email</h2>
<form method="post" action="email">
    E-mail: <input type="text" name="email" />	<br/>
  	<input type="submit" value="Find" />
</form>

<h2>Delete user by email</h2>
<form method="post" action="delete">
    E-mail: <input type="text" name="email" />	<br/>
  	<input type="submit" value="Delete" />
</form>

<a href="all">Get all users</a>
</br>
<a href="/">Main page</a>

</body>
</html>