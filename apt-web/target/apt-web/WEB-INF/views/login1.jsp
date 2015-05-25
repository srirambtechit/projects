<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>
<title>User Login Page</title>
</head>
<body>
	<h1>User Login</h1>

	<c:url var="loginAction" value="/user/validate"></c:url>

	<form:form action="${loginAction}" commandName="user">
		<table>

			<tr>
				<td><form:label path="username">
						<spring:message text="Username" />
					</form:label></td>
				<td><form:input id="usernameId" path="username" maxlength="15" /></td>
			</tr>

			<tr>
				<td><form:label path="password">
						<spring:message text="Password" />
					</form:label></td>
				<td><form:password path="password" maxlength="15" /></td>
			</tr>

			<tr>
				<td colspan="2"><input type="submit"
					value="<spring:message text="Submit"/>" /></td>
			</tr>
		</table>
	</form:form>
</body>
</html>
