<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="true"%>
<html>
<head>
<title>Question - Answer Page</title>
</head>
<body>
	<h1>Examination</h1>
	<form:form method="post" action="saveAnswer" modelattribute="questionForm">
		<table>
		    <c:forEach items="${questionForm.questions}" var="question"  varStatus="status">
			    <tr>
				    <td align="center">${status.count}</td>
				    <%-- <td><input name="questions[${status.index}].question" value="${question.question}"/></td> --%>
				    <td><form:label path="${question.question}" /></td>
				    <td><form:input path="questions[${status.index}].question" value="${question.question}" /></td>
			        <form:hidden path="questions[${status.index}].id" />
			    </tr>
		    </c:forEach>
		    <tr><td><input value="Save" type="submit"></td></tr>
	    </table>
	</form:form>
</body>
</html>
