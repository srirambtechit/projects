<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SqlRunner - Login</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<div class='bg-overlay'>
		<div class="iwr-content-wrap">
			<div class="iwr-form-panel">

				<%
					String error = request.getParameter("error");
					if (error != null || !error.isEmpty()) {
				%>
				<div class="notify notify-red"><%=error%></div>
				<%
					}
				%>

				<input id="tab-1" type="radio" name="tab" class="sign-in" checked><label
					for="tab-1" class="tab">Sign In</label>

				<div class="iwr-ui-form">
					<form action="login?action=doLogin" method="post">
						<div class="group">
							<input id="user" type="text" class="input" name="username"
								placeholder="Username">
						</div>
						<div class="group">
							<input id="pass" type="password" class="input" name="password"
								data-type="password" placeholder="Password">
						</div>
						<div class="group">
							<input id="check" type="checkbox" class="check" checked>
							<label for="check"> Keep me Signed in</label>
						</div>
						<div class="group">
							<input type="submit" class="button" value="Sign In">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>