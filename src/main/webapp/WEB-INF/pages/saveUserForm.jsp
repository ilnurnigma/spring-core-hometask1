<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<html>
<head></head>
<body>
<h1>Add user</h1>
<form name="saveUser" method="post" action="save">
    Firstname: <input type="text" name="firstName" />	<br/>
  	Lastname: <input type="text" name="lastName" />	<br/>
  	<input type="submit" value="   Save   " />
</form>
<c:if test="${not empty firstName}">${firstName} ${lastName} saved</c:if>
</body>
</html>