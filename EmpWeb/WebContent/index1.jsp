<%@page import="kr.kosa.web.MyElClass"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix = "mytag" uri = "/WEB-INF/MyTagLib.tld" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%pageContext.setAttribute("myClass", new MyElClass()); %>

</head>
<body>
인스턴스 메소드 호출
${myClass.instMethod("홍길동") }<br>
클래스 메소드 호출
${MyElClass.statMethod("홍길서") }<br>
tld 이용 메소드 호출
${mytag:statMethod("홍길남")}
</body>
</html>