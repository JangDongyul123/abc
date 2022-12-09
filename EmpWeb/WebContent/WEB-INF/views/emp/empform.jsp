<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="kr.kosa.emp.EmpVo"%>
<%@page import="java.util.List"%>
<%@page import="kr.kosa.emp.EmpDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
	<%
		// 		EmpDao emp = new EmpDao();
		// 		List<Map<String, Object>> jobIdList = new ArrayList<Map<String, Object>>();
		// 		jobIdList = emp.getJobIdList();
	%>
	<h1>사원 정보 입력 양식</h1>
	<form action="EmpInsert.do" method="post">

		<table border="1">
			<tr>
				<th>사원아이디</th>
				<td><input type="number" name="employeeId" min="${maxEmpId }" max="1000" step="1"></td>
			</tr>
			<tr>
				<th>이름</th>
				<td><input type="text" name="firstName"></td>
			</tr>
			<tr>
				<th>성</th>
				<td><input type="text" name="lastName"></td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><input type="text" name="email"></td>
			</tr>
			<tr>
				<th>전화번호</th>
				<td><input type="text" name="phoneNumber"></td>
			</tr>
			<tr>
				<th>입사일</th>
				<td><input type="date" name="hireDate"></td>
			</tr>

			<tr>
				<th>직무아이디</th>
				<!-- 				<td><input type="text" name="jobId"></td> -->

				<td><select name="jobId">
						<c:forEach var="job" items="${jobIdList}">
							<option value="${job.jobId}">${job.jobTitle}</option>
						</c:forEach>
				</select></td>

			</tr>

			<tr>
				<th>급여</th>
				<td><input type="number" name="salary"></td>
			</tr>
			<tr>
				<th>보너스율</th>
				<td><input type="number" name="commissionPct" min="0"
					max="0.99" step="0.01"></td>
			</tr>
			<tr>
				<th>매니저아이디</th>
				
				<td><select name="managerId">
						<c:forEach var="emp" items="${empIdList }">
							<option value="${emp.empId}">${emp.name} (${emp.empId})</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<th>부서아이디</th>
				<!-- 				<td><input type="number" name="departmentId"></td> -->
				<td><select name="departmentId">

						<c:forEach var="dept" items="${deptIdList }">
							<option value= "${dept.deptId }">${dept.deptName} (${dept.deptId })</option>
						</c:forEach>
				</select></td>
			</tr>

			<tr colspan="2">
				<td><input type="submit" value="저장"> <input
					type="reset" value="취소"></td>
			</tr>



		</table>

	</form>
</body>
</html>