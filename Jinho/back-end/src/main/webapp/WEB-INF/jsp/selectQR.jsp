<%--
  Created by IntelliJ IDEA.
  User: jungjihoons
  Date: 2020-11-29
  Time: 오전 5:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>사용하실 QR코드를 선택해주세요</h1>
<ul>
    <li>
<%--        <input type="button" value="NaverQR" onclick="location.href='/locations/{uid}">--%>
       <a href="https://nid.naver.com/login/privacyQR">
            <input type="button" value="뒤로가기" onclick="location.href='selectQR'">
        NAVER QR코드
        </a>
    </li>
    <li>
        <a href="https://accounts.kakao.com/qr_check_in#page-qr-check-in">
            KAKAO QR코드
        </a>
    </li>
<%--    <li>--%>
<%--        <a href="https://nid.naver.com/login/privacyQR">--%>
<%--            PASS QR코드--%>
<%--        </a>--%>
<%--    </li>--%>
</ul>

<br>
<input type="button" value="MyPage" onclick="location.href=''">
<br><br>
<input type="button" value="지도보기" onclick="location.href=''">
<br><br>
<a href="https://kauth.kakao.com/oauth/logout?client_id=e54cdcf9334ea492db2e01c8ebacd5ee&logout_redirect_uri=http://localhost:8080/logout&response_type=code">
    <input type="button" value="로그아웃" onclick="location.href='/logout'">
</a>
</body>
</html>
