<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>카카오로그인</title>
</head>
<body>
    <a href="https://kauth.kakao.com/oauth/authorize?client_id=34b92c6aafabb13e147ab7f1d1ea468f&redirect_uri=http://localhost:8080/callback&response_type=code">
            <%--        <img src="kakao_login.png">--%>
        <p>카카오 login</p>
    </a>
</body>
</html>
