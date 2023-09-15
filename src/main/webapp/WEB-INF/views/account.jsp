<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page session="false" %>

<html>
  	<head>
  		<title>코맵 - 사용자 계정 | Komap.com</title>
  		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<link rel="stylesheet" type="text/css" href="./resources/css/home.css?ver=2420979323">  <!-- css링크 -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">  <!-- bootstrap cdn링크 -->
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>  <!-- fontawesome cdn링크 -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
	</head>
	
    <body>
    	<%@ include file="./include/header-menu.jsp" %>
    	
    	<div id="account-card">
	    	<div class="account-inbox">
	    		<div class="img-box">
	    			<img id="user-img" src="${checkedUser.user_image}" />
	    		</div>
		    	
		    	<input id="img-file" type="file" style="display: none;" />
		    	<div style="font-weight:700; font-size:18px; margin-top:20px; margin-bottom:20px;">회원 정보</div>
		    	<div style="margin-bottom:7px; font-weight:500;">유저인덱스번호 <span id="userIdx">${loginUser.user_idx}</span></div>
		    	<div style="margin-bottom:7px; font-weight:500;">아이디 <span id="userId">${checkedUser.id}</span></div>
		    	<div style="margin-bottom:7px; font-weight:500;">이메일 주소 <span id="userEmail">${checkedUser.email}</span></div>
		    	<div style="margin-bottom:30px; font-weight:500;">가입일 <span>${checkedUser.reg_date.substring(0,10)}</span></div>
		    	<button id="img-upload-button" style="display: none;">이미지 업로드</button>
		    	<div style="display:flex; justify-content:end;"><button id="withdrawal">탈퇴하기</button></div>     	
	    	</div>  
  	
    	</div>


<%--     	<c:if test="${empty loginUser.user_img}">
    		<div>
    			<input id="img-file" type="file" />
    		</div>
    		<button id="img-upload-button">이미지 업로드</button>
    	</c:if> --%>
    </body>
    
    
<script src="https://www.gstatic.com/firebasejs/8.3.0/firebase-app.js"></script>
<script src=" https://www.gstatic.com/firebasejs/8.3.0/firebase-storage.js"></script> 
    
<script src="./resources/js/account.js"></script>
    

</html>	