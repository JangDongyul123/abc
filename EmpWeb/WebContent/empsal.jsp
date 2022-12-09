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
EmpDao dao = new EmpDao();

String empIdStr = request.getParameter("empId");

if (empIdStr != null){
	int empId = Integer.parseInt(empIdStr);
	double result = dao.getSalaryByEmployeeId(empId);
	
	out.print("<h1>"+empId+"번 사원의 급여: "+result+"<h1>");
}
%>
</body>
</html>