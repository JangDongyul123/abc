<%@page import="java.util.ArrayList"%>
<%@page import="kr.kosa.emp.EmpVo"%>
<%@page import="java.util.List"%>
<%@page import="kr.kosa.emp.EmpDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		EmpDao emp = new EmpDao();
		List<EmpVo> EmpList = new ArrayList<>();
		EmpList = emp.getAllEmpsDistinct();
	%>
	<c:set var="emp" value="<%=EmpList %>"/>
	${emp }
</body>
</html>