<html>
<head></head>

<body>
<h1>Users</h1>
<#list users as user>
  <p>
  <#if user.firstName??>
    ${user.firstName}
  </#if>

  <#if user.lastName??>
    ${user.lastName}
  </#if>

  ${user.email}
</#list>

</br>
<p>
<a href=/>Main page</a>
</body>
</html>