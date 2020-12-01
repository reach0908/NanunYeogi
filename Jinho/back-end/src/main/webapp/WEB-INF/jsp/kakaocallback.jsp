<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>카카오콜백</title>
</head>


<body>
<%--<c:if test="${userId eq null}">--%>
<%--    <a href="https://kauth.kakao.com/oauth/authorize--%>
<%--            ?client_id=e54cdcf9334ea492db2e01c8ebacd5ee--%>
<%--            &redirect_uri=http://localhost:8000/kcallback--%>
<%--            &response_type=code">--%>
<%--    </a>--%>
<%--</c:if>--%>
<%--<c:if test="${userId ne null}">--%>
    <h1>로그인 성공입니다</h1>
<input type="button" value="QR코드 선택하기" onclick="location.href='/selectQR'">
<br><br>
<a href="https://kauth.kakao.com/oauth/logout?client_id=a863152a6c9a88819b4482a0b970723a&logout_redirect_uri=http://localhost:8080&response_type=code">
    <input type="button" value="로그아웃" onclick="location.href='/Login'">
</a>
<%--</c:if>--%>
</body>

</html>
