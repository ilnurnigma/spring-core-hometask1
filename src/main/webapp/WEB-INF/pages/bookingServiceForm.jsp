<html>
<head></head>
<body>
<h1>Booking service form</h1>

<h2>Book ticket</h2>
<form method="post" action="bookTicket">
    User e-mail: <input type="text" name="userEmail" />	<br/>
  	Event name:  <input type="text" name="eventName" />	<br/>
  	Date and time:    <input type="datetime-local" name="dateTime" />	<br/>
  	Seat:  <input type="number" name="seat" />	<br/>
  	<input type="submit" value="Book" />
</form>

<h2>Find out tickets price</h2>
<form method="post" action="getTicketsPrice">
    User e-mail: <input type="text" name="userEmail" />	<br/>
  	Event name:  <input type="text" name="eventName" />	<br/>
  	Date and time:    <input type="datetime-local" name="dateTime" />	<br/>
  	Seat:  <input type="text" name="seats" />	<br/>
  	<input type="submit" value="Find out" />
</form>

<a href="/getBookedTickets">Get booked tickets (Only for booking managers)</a>

</br>
<a href=/>Main page</a>

</body>
</html>