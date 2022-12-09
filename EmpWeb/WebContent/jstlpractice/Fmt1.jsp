<%@page import="java.util.TimeZone"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h4>숫자 포맷 설정</h4>
	<c:set var="number1" value="12345" />
	콤마 O :
	<fmt:formatNumber value="${number1 }" />
	<br> 콤마 X :
	<fmt:formatNumber value="${number1 }" groupingUsed="false" />
	<br>
	<fmt:formatNumber value="${number1 }" type="currency" var="printNum2" />
	통화기호 : ${printNum2 }
	<br>
	<fmt:formatNumber value="0.03" type="percent" var="printNum2" />
	퍼센트 : ${printNum2 }
	<br>

	<hr>

	<h4>문자열을 숫자로 변경</h4>
	<c:set var="number2" value="6,789.01" />
	기본값: ${number2 }
	<br>
	<fmt:parseNumber value="${number2 }" pattern="00,000.00"
		var="printNum3" />
	소수점까지 :${printNum3 }
	<br>
	<fmt:parseNumber value="${number2 }" integerOnly="true" var="printNum4" />
	정수 부분만:${printNum4 }


	<hr>
	<c:set var="today" value="<%=new java.util.Date()%>" />
	${today }
	<br> formatDate로 출력되는 시간 형식 변환:
	<br> type="date"
	<br> dateStyle="full":
	<fmt:formatDate value="${today }" type="date" dateStyle="full" />
	<br> dateStyle="short":
	<fmt:formatDate value="${today }" type="date" dateStyle="short" />
	<br> dateStyle="long":
	<fmt:formatDate value="${today }" type="date" dateStyle="long" />
	<br> dateStyle="default":
	<fmt:formatDate value="${today }" type="date" dateStyle="default" />
	<br>
	<hr>

	formatDate로 출력되는 시간 형식 변환:
	<br> type="time""
	<br> timeStyle="full":
	<fmt:formatDate value="${today }" type="time" timeStyle="full" />
	<br> timeStyle="short":
	<fmt:formatDate value="${today }" type="time" timeStyle="short" />
	<br> timeStyle="long":
	<fmt:formatDate value="${today }" type="time" timeStyle="long" />
	<br> timeStyle="default":
	<fmt:formatDate value="${today }" type="time" timeStyle="default" />
	<br>
	<hr>
	formatDate로 출력되는 시간 형식 변환:
	<br> type="both"
	<br> pattern 이용하기
	<br>
	<fmt:formatDate value="${today }" type="both"
		pattern="yyyy. M. d. hh:mm:ss" />
	<br>
	<fmt:formatDate value="${today }" type="both"
		pattern="yyyy-MM-dd hh:mm:ss" />
	<br>

	<hr>


	<fmt:timeZone value="American/Chicago">
	</fmt:timeZone>

	<hr>
	setLocale value값에 맞춰서 원하는 나라 별 시간과 통화 기호 사용
	<br>
	<br>
	<fmt:setLocale value="ko_kr" />
	<fmt:formatNumber value="${number1 }" type="currency" />
	<fmt:formatDate value="${today }" />
	<hr>
	<fmt:setLocale value="ja_jp" />
	<fmt:formatNumber value="${number1 }" type="currency" />
	<fmt:formatDate value="${today }" />
	<hr>
	<fmt:setLocale value="en_US" />
	<fmt:formatNumber value="${number1 }" type="currency" />
	<fmt:formatDate value="${today }" />
	<hr>



</body>
</html>