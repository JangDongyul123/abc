<%@page import="java.util.List"%>
<%@ page import="kr.kosa.emp.EmpDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%
// String url = application.getInitParameter("OracleURL");
// String id = application.getInitParameter("OracleId");
// String pw = application.getInitParameter("OraclePwd");
// EmpDao dao = new EmpDao(url,id,pw);
%>`
<body>
<h1> 하이</h1>
<%
EmpDao dao = new EmpDao();
int result ; 
String deptStr=request.getParameter("deptno");
if(deptStr ==null ){
	result = dao.getEmpCount(); 
}else{
	int deptno = Integer.parseInt(deptStr);
	result = dao.getEmpCount(deptno);
}




%>
<h1>사원의 수 : <%=result %></h1>

</body>
</html>