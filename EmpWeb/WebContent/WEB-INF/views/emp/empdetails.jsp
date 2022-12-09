<%@page import="kr.kosa.emp.EmpVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
table{ border:1}
</script>
</head>
<%-- <% EmpVo empvo = request.getAttribute("empvo");%> --%>
<body>
<table border=1>
<tr> 
<th>사원번호</th>
<th>
사원 이름
</th>
<th>사원 이메일</th> 
<th>사원 전화번호</th>
<th>사원 입사일 </th>
<th>사원 직무아이디</th>
<th>사원 급여 </th>
<th>사원 보너스율 </th>
<th>사원 매니저아이디 </th>
<th>사원 부서아이디 </th>
</tr>
<tr>
<c:if test="${not empty emp.employeeId }">

<td>${emp.employeeId }</td>
<td>${emp.firstName } ${empvo.lastName }</td>
<td>${emp.email }</td>
<td> ${emp.phoneNumber }</td>
<td>${emp.hireDate }</td>
<td>${emp.jobId }</td>
<td>${emp.salary }</td>
<td>${emp.commissionPct }</td>
<td>${emp.managerId }</td>
<td>${emp.departmentId }</td>
</c:if>
</tr>
</table>
<a href = "EmpUpdate.do?empid=${emp.employeeId }">사원정보 수정</a>
<a href = "EmpDelete.do?empid=${emp.employeeId }">사원정보 삭제</a>
<c:if test="${empty emp.employeeId}">
사원 정보가 없습니다
</c:if>
</body>
</html>