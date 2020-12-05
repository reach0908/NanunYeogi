<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.math.BigInteger" %>
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
<%--<a href="https://kauth.kakao.com/oauth/authorize?client_id=34b92c6aafabb13e147ab7f1d1ea468f&redirect_uri=http://localhost:8080/callback&response_type=code">--%>
<%--&lt;%&ndash;            <img src="/jsp/kakao_login.png">&ndash;%&gt;--%>
<%--    <p>카카오 login</p>--%>
<%--</a>--%>

<c:if test="${userId eq null}">
    <a href="https://kauth.kakao.com/oauth/authorize?client_id=a863152a6c9a88819b4482a0b970723a&redirect_uri=http://nanunyeogi.paas-ta.org/kcallback&response_type=code">
            <%--            <img src="/jsp/kakao_login.png">--%>
        <p>카카오 login</p>
    </a>
</c:if>
<c:if test="${userId ne null}">
    <h1>로그인 성공입니다</h1>
    <input type="button" value="로그아웃" onclick="location.href='/logout'">
</c:if>

<%
    String clientId = "iqeUzjHk6QcI3kT_Wn2i";//애플리케이션 클라이언트 아이디값";
    String redirectURI = URLEncoder.encode("http://nanunyeogi.paas-ta.org/ncallback", "UTF-8");
    SecureRandom random = new SecureRandom();
    String state = new BigInteger(130, random).toString();
    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
    apiURL += "&client_id=" + clientId;
    apiURL += "&redirect_uri=" + redirectURI;
    apiURL += "&state=" + state;
    session.setAttribute("state", state);
%>
<a href="<%=apiURL%>"><img height="50"/>네이버 로그인</a>

<%--<a href="<%=apiURL%>"><img height="50" src="http://static.nid.naver.com/oauth/small_g_in.PNG"/></a>--%>
</body>
</html>
