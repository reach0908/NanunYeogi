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
<c:if test="${userId eq null}">
    <a href="https://kauth.kakao.com/oauth/authorize
            ?client_id=a863152a6c9a88819b4482a0b970723a
            &redirect_uri=http://nanunyeogi.paas-ta.org/kcallback
            &response_type=code">
        <img src="/img/kakao_account_login_btn_medium_wide_ov.png">
    </a>
</c:if>
<c:if test="${userId ne null}">
    <h1>로그인 성공입니다</h1>
    <input type="button" value="로그아웃" onclick="location.href='/logout'">
</c:if>
</body>

</html>
