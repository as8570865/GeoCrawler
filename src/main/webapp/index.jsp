<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring 4 MVC - HelloWorld Index Page</title>
</head>
<body>
	<center>
		<h1>GeoCrawler</h1>
		<form name="monitorForm" action="/monitor/worker" method="get">
			<table>
				<tr>
					<td>ID: <input type="text" id="id"
						name="id">
					</td>

				</tr>
				<tr>
					<td>Last Name: <input type="text" id="lastName"
						name="lastName">
					</td>
				</tr>
				<tr>
					<td>DOB: <input type="text" id="dob" name="dob">
					</td>
				</tr>
			</table>
			<input type="submit" value="Submit">
		</form>
	</center>
</body>
</html>