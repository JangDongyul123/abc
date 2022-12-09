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
		String deptStr = request.getParameter("deptno");
		EmpDao dao = new EmpDao();
		int deptno = 0;
		double result = 0.0;
		if (deptStr == null) {
			result = dao.getAverageSalaryByDepartment(deptno);
			out.print("<h1>"+"회사 평균 급여: "+result+"</h1>");
	%>
	
	<%
		} else {
			deptno = Integer.parseInt(deptStr);
			result = dao.getAverageSalaryByDepartment(deptno);
	%>
	<h1><%=deptno%>번 부서의 평균 급여:
		<%=result%></h1>
	<%
		}
	%>


</body>
</html>