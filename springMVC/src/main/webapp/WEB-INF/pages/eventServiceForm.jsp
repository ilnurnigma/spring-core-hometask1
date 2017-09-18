<html>
<head></head>
<body>
<h1>Event service form</h1>

<h2>Save event</h2>
<form method="post" action="save">
    Name: <input type="text" name="name" />	<br/>
  	Base price:  <input type="number" name="basePrice" />	<br/>
  	Rating:    <input type="text" name="rating" />	<br/>
  	<input type="submit" value="Save" />
</form>

<h2>Get event by name</h2>
<form method="post" action="name">
    Name: <input type="text" name="name" />	<br/>
  	<input type="submit" value="Find" />
</form>

<h2>Delete event by name</h2>
<form method="post" action="delete">
    Name: <input type="text" name="name" />	<br/>
  	<input type="submit" value="Delete" />
</form>

<h2>Get events for a date range</h2>
<form method="post" action="date">
    E-mail: <input type="date" name="from"/>	<br/>
    E-mail: <input type="date" name="to"/>	<br/>
  	<input type="submit" value="Find" />
</form>

<h2>Get next events</h2>
<form method="post" action="next">
    E-mail: <input type="datetime-local" name="to"/>	<br/>
  	<input type="submit" value="Find" />
</form>

<a href="all">Get all events</a>
</br>

<a href="/">Main page</a>

</body>
</html>