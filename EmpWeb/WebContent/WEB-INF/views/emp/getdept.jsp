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

String empIdStr = request.getParameter("empid");
String result=null ;
if (empIdStr != null){
	int empId = Integer.parseInt(empIdStr);
	result = dao.getDepartmentNameByEmployeeId(empId);
	out.print("<h1>"+empId+"번 사원의 부서 명: "+result+"<h1>");
}
%>
<h1> 사원번호: ${param.empid}</h1>
<h2> 부서명: ${getdept }</h2>
</body>
</html>