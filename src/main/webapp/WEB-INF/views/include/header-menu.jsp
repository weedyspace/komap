<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page session="false" %>	


	
	<header class="header">
    	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">  <!-- bootstrap cdn링크 -->
    	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	
		 <div>
		 	<img class="logo one-menu" data-page="home" src="./resources/images/komap.png"/>
		 	<span class="one-menu <c:if test="${menu eq 'home'}">on</c:if>" data-page="home">KOMAP</span>
		 </div>
		 	
		 <div>
		 	<span class="one-menu <c:if test="${menu eq 'intro'}">on</c:if>" data-page="intro">코맵</span>
		 	<span class="one-menu <c:if test="${menu eq 'board'}">on</c:if>" data-page="board">자유게시판</span>
		 	<span class="one-menu <c:if test="${menu eq 'faq'}">on</c:if>" data-page="faq">FAQ</span>	
		 	
		 	<c:if test="${empty loginUser}">  <!-- 로그인 정보가 비워져 있을시 -->
		 	<span class="one-menu <c:if test="${menu eq 'register'}">on</c:if>" data-page="register">무료등록</span>		 	
		 	<span class="one-menu <c:if test="${menu eq 'login'}">on</c:if>" data-page="login">로그인</span>
		 	</c:if>
		 	
		 	<c:if test="${!empty loginUser}">  <!-- 로그인 정보가 들어가 있을시 -->	
		 	<span class="one-menu" data-page="logout">로그아웃</span>
		    <i class="far fa-user one-menu <c:if test="${menu eq 'account'}">on</c:if>" data-page="account"></i>	
		 	</c:if>
		 	
		 </div>
	</header>
	
    <input id="hiddenUserIdx" type="hidden" value="${loginUser.user_idx}"></input>
	<input id="hiddenUserImage" type="hidden" value="${checkedUser.user_image}"></input>

	
	<script src="./resources/js/header-menu.js"></script>