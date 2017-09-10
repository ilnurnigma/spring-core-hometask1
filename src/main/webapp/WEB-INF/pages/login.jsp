<form name='loginForm' action="/login" method='POST'>

<table>
<tr>
	<td>User:</td>
	<td><input type='text' name='username'></td>
</tr>
<tr>
	<td>Password:</td>
	<td><input type='password' name='password' /></td>
</tr>

<tr>
	<td></td>
	<td>Remember Me: <input type="checkbox" name="remember-me" /></td>
</tr>

<tr>
    <td colspan='2'><input name="submit" type="submit" value="submit" /></td>
</tr>

</table>

<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>