<html>
<head></head>

<body>
<h1>Events</h1>
<#list events as event>
  <p>
  <#if event.name??>
    ${event.name}
  </#if>

  <#if event.basePrice??>
    ${event.basePrice}
  </#if>

  <#if event.rating??>
    ${event.rating}
  </#if>
</#list>

</br>
<p>
<a href=/>Main page</a>
</body>
</html>