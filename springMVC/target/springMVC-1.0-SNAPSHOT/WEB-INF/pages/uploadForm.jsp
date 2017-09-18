<body>
<h1>Add user</h1>
<form name="upload" method="post" action="file" enctype="multipart/form-data">
    Select a file to upload: <input type="file" name="file" />	<br/>
  	<input type="submit" value="Submit" />
  	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

</br>
<a href=/>Main page</a>

</body>
</html>