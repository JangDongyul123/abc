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

<h1>사원 정보 입력 양식</h1>
	<form action="EmpUpdate.do" method="post">

		<table border="1">
			<tr>
				<th>사원아이디</th>
				<td><input type="number" name="employeeId" min="${maxEmpId }" value=${emp.employeeId } readonly></td>
			</tr>
			<tr>
				<th>이름</th>
				<td><input type="text" name="firstName" value=${emp.firstName } readonly></td>
			</tr>
			<tr>
				<th>성</th>
				<td><input type="text" name="lastName" value= ${emp.lastName } readonly></td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><input type="text" name="email" value=${emp.email }></td>
			</tr>
			<tr>
				<th>전화번호</th>
				<td><input type="text" name="phoneNumber" value=${emp.phoneNumber}></td>
			</tr>
			<tr>
				<th>입사일</th>
				<td><input type="date" name="hireDate" value=${emp.hireDate } readonly></td>
			</tr>

			<tr>
				<th>직무아이디</th>
				<!-- 				<td><input type="text" name="jobId"></td> -->

				<td><select name="jobId">
						<c:forEach var="job" items="${jobIdList}">
							<option value="${job.jobId}"
							<c:if test= "${emp.jobId==job.jobId }">
							selected</c:if>
							>${job.jobTitle}</option>
						</c:forEach>
				</select></td>

			</tr>

			<tr>
				<th>급여</th>
				<td><input type="number" name="salary" value=${emp.salary }></td>
			</tr>
			<tr>
				<th>보너스율</th>
				<td><input type="number" name="commissionPct" min="0"
					max="0.99" step="0.01" value=${emp.commissionPct}></td>
			</tr>
			<tr>
				<th>매니저아이디</th>
				
				<td><select name="managerId">
						<c:forEach var="mgr" items="${empIdList }">
							<option value="${mgr.empId}"
							<c:if test="${emp.managerId == mgr.empId }">
							selected </c:if>
							>${mgr.name} (${mgr.empId})</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<th>부서아이디</th>
				<!-- 				<td><input type="number" name="departmentId"></td> -->
				<td><select name="departmentId">

						<c:forEach var="dept" items="${deptIdList }">
							<option value= "${dept.deptId }"
							<c:if test="${emp.departmentId==dept.deptId }">
							selected</c:if>
							>${dept.deptName} (${dept.deptId })</option>
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