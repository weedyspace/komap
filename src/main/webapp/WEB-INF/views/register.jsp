<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page session="false" %>

<html>
  	<head>
  		<title>코맵 - 회원가입 | Komap.com</title>
  		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<link rel="stylesheet" type="text/css" href="./resources/css/home.css?ver=723092043">  <!-- css링크 -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">  <!-- bootstrap cdn링크 -->
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>  <!-- fontawesome cdn링크 -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
	</head>
	
    <body>
    	<%@ include file="./include/header-menu.jsp" %>
    	
    	<div class="cm-center-body">
    	
    		<div class="header-body">
    			<img class="top-logo" src="./resources/images/komap2.png"/>
    			<span class="top-name">KOMAP</span>
    		</div>	
    		
    		<div class="inner-box">
    			<div class="reg-box">
    				<span class="txt title"></span>
    				<span class="txt">아이디</span>
    				<div style="position:relative;">
	    				<input id="id" class="cm-inp">
	    				<button id="idCheck" style="width:50px; height:24px; position:absolute; top:5px; right:5px;">확 인</button>
    				</div>
    				<span class="txt">이메일</span>
    				<div style="position:relative;">
    					<input id="email" class="cm-inp">
    					<button id="emailCheck" style="width:50px; height:24px; position:absolute; top:5px; right:5px;">확 인</button>
    				</div>	
    				<span class="txt">비밀번호</span>
    				<input id="password" class="cm-inp password" type="password" placeholder="최소 6자리를 입력해주세요">
    				<span class="txt">비밀번호 확인</span>	
    				<input id="password1" class="cm-inp" type="password">
    				<span id="reg-btn">무료계정 등록</span>
    				<div class="reg-inbox">
    					<div class="reg-inline"></div>
    				</div>	
    				<div class="question-box">
    					<span class=question-txt>이미 회원 이신가요?</span>
    					<span class=login-txt>로그인</span>
    					<i class="fas fa-chevron-right"></i>
    				</div>
    			</div>
    		</div>
   
    	</div>
    	
    </body>
    
    <script src="./resources/js/register.js"></script>

</html>