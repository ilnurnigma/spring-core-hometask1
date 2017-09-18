<html>
<head></head>

<body>
<h1>Booked tickets</h1>
<#list tickets as ticket>
  <p>Event name: ${ticket.event.name}. User email: ${ticket.user.email}
</#list>

</br>
<a href=/>Main page</a>
</body>
</html>