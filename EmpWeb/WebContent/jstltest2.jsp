<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:set var="number1" value="12345" />
<h1>${number1 }</h1>
<h1><fmt:formatNumber value="${number1 }" /></h1> 
<h1><fmt:formatNumber value="${number1 }" groupingUsed="false"/></h1>
<h1><fmt:formatNumber value="${number1 }" type ="currency" /></h1>
<h1><fmt:formatNumber value="${number1 }" type ="currency" currencyCode="GBP"/></h1>
<h1><fmt:formatNumber value="${number1 }" type ="currency" currencyCode="EUR"/></h1>
<h1><fmt:formatNumber value="${number1 }" type ="currency" currencyCode="JPY"/></h1>
<h1><fmt:formatNumber value="${number1 }" type ="currency" currencyCode="CAD"/></h1>
<h1><fmt:formatNumber value="${number1 }" type ="currency" currencyCode="CNY"/></h1>
<h1><fmt:formatNumber value="${number1 }" type ="currency" currencyCode="CHF"/></h1>
<h1><fmt:formatNumber value="${number1 }" type ="currency" currencyCode="KRW"/></h1>
<h1><fmt:formatNumber value="${number1 }" type ="currency" currencySymbol="@"/></h1>
<h1><fmt:formatNumber value="${number1 }" type ="currency" currencySymbol="!"/></h1>
<h1><fmt:formatNumber value="${number1 }" type ="currency" currencySymbol="*"/></h1>
<c:set var="number2" value="6,789.01"/>
<fmt:parseNumber value="${number2}" pattern="00,000.00"/>
<fmt:parseNumber value="${number2}" integerOnly="true"/>
</body>
</html>