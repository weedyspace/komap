<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page session="false" %>

<html>
  	<head>
  		<title>코맵 - 로그인 | Komap.com</title>
		<link rel="stylesheet" type="text/css" href="./resources/css/home.css?ver=344324353">  <!-- css링크 -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">  <!-- bootstrap cdn링크 -->
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>  <!-- fontawesome cdn링크 -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<!--         <meta name ="google-signin-client_id" content="140702220863-2930n1vru2nmujq6epthhvf54ik8skq1.apps.googleusercontent.com">     -->
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
    				<span class="txt title">로그인</span>
    				<span class="txt email">이메일</span>
    				<input class="cm-inp email">
    				<span class="txt password">비밀번호</span>
    				<input class="cm-inp password" type="password">
    				
    				<span id="reg-btn">로그인</span>
    				<!-- <button id="my-google-login-btn">구글 로그인</button> -->
    				<img id="my-google-login-btn" src="./resources/images/googleLoginIcon.png" />
    				<div class="reg-inbox new">
    					<span class="login-inline"></span>
    					<span style="margin: 0px 7px;">코맵에 처음 이신가요?</span>
    					<span class="login-inline"></span>
    				</div>	
    				<span id="login-reg-btn">무료계정 만들기</span>
    				
    				
    				
    				
    				<!-- <ul>
 						<li id="GgCustomLogin">
  							<a href="javascript:void(0)">
  								<span>Login with Google</span>
  							</a>
 						</li>
					</ul>
    				<script>
    				function init() {
    					gapi.load('auth2', function() {
    						gapi.auth2.init();
    						options = new gapi.auth2.SigninOptionsBuilder();
    						options.setPrompt('select_account');
    				        // 추가는 Oauth 승인 권한 추가 후 띄어쓰기 기준으로 추가
    						options.setScope('email profile openid https://www.googleapis.com/auth/user.birthday.read');
    				        // 인스턴스의 함수 호출 - element에 로그인 기능 추가
    				        // GgCustomLogin은 li태그안에 있는 ID, 위에 설정한 options와 아래 성공,실패시 실행하는 함수들
    						gapi.auth2.getAuthInstance().attachClickHandler('GgCustomLogin', options, onSignIn, onSignInFailure);
    					})
    				}

    				function onSignIn(googleUser) {
    					var access_token = googleUser.getAuthResponse().access_token
    					$.ajax({
    				    	// people api를 이용하여 프로필 및 생년월일에 대한 선택동의후 가져온다.
    						url: 'https://people.googleapis.com/v1/people/me'
    				        // key에 자신의 API 키를 넣습니다.
    						, data: {personFields:'birthdays', key:'AIzaSyB4zACSHoKLIZJzSbx5s_39glK-53YS5Wg', 'access_token': access_token}
    						, method:'GET'
    					})
    					.done(function(e){
    				        //프로필을 가져온다.
    						var profile = googleUser.getBasicProfile();
    						console.log(profile)
    					})
    					.fail(function(e){
    						console.log(e);
    					})
    				}
    				function onSignInFailure(t){		
    					console.log(t);
    				}
    				</script>	 -->
    				
   	
    					
    			</div>
    		</div>
    	</div>
   
    	
   </body>
    
    <script src="https://www.gstatic.com/firebasejs/7.23.0/firebase-app.js"></script>  <!-- 파이어베이스의 전반적인 기능을 쓸수있게 해주는 라이브러리 -->
	<script src="https://www.gstatic.com/firebasejs/7.23.0/firebase-auth.js"></script> <!-- 파이어베이스를 통한 구글인증 라이브러리 -->
	<script src="./resources/js/config.js"></script>
    <script src="./resources/js/login.js"></script>
<!--     <script src="https://apis.google.com/js/platform.js?onload=init" async defer></script>     -->
</html>