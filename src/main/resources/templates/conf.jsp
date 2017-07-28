<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org"
	xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
<title>Hello LMoon!</title>

</head>
<body>
	<div th:if="${#lists.isEmpty(list)}">Hello LMoon!Failed to get ss account...</div>
	<div th:if="${not #lists.isEmpty(list)}">
		<div th:each="entry:${list}">
			<a th:attr="id=${entry.id}" th:href="${entry.url}" th:text="${entry.server}">Hello LMoon!</a>
		</div>
	</div>
	<script language="javascript" type="text/javascript"> 
	var abc = document.getElementById('ids');
	if(abc){
		abc.click();
	}
		
	</script>
</body>
</html>
