<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page session="false" %>

<html>
  <head>
	  <title>코맵 - 대한민국 대표 암호화폐 오픈맵 | Komap.com</title>
	  <link rel="stylesheet" type="text/css" href="./resources/css/home.css?ver=276522343323">  <!-- css링크 -->
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">  <!-- bootstrap cdn링크 -->
      <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>  <!-- fontawesome cdn링크 -->
      <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
     <!--  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" 
	  integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous"> -->
	  <link type="text/css" rel="stylesheet" href="./resources/css/bootstrap.css"/>
  </head>
  <body>
	<%-- <header class="header">
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
		 	<i class="far fa-user one-menu <c:if test="${menu eq 'account'}">on</c:if>" data-page="account"></i>  <!-- fontawesome cdn링크를 통해서 이미지를 가져옴 -->
		 	</c:if>
		 	
		 </div>
	</header> --%>
	<%@ include file="./include/header-menu.jsp" %>
	
		 
	<div class="main-image-body">
		<img src="./resources/images/komap1.png"/>
		<div class="black-container">
			<span class="txt1">대한민국 대표 오픈소스 코인등록 맵</span>
		 	<span class="txt2">코맵에서 무료로 해당 사업장에서</span>
		 	<span class="txt2">코인을 받는지 알아보거나 등록하세요</span>
		 	<div class="button">VIEW MAP</div>
		</div>
	</div>
	
	
	<div class="contents-body white">
	    <div class="wrapper">
			<div class="row">
			
				<div class="col-md-4" style="padding:10px;">
					<div class="car-card">
						<div style="height:400px; background-color: #FFFFFF; overflow:hidden; box-sizing: border-box; border: 1px solid #56667F; border-radius: 4px; text-align: right; line-height:14px; font-size: 12px; font-feature-settings: normal; text-size-adjust: 100%; box-shadow: inset 0 -20px 0 0 #56667F; padding: 0px; margin: 0px; width: 100%;">
						<div style="height:200px; padding:0px; margin:0px; width: 100%;">
						<iframe src="https://widget.coinlib.io/widget?type=full_v2&theme=light&cnt=6&pref_coin_id=1505&graph=yes" width="100%" height="409px" scrolling="auto" marginwidth="0" marginheight="0" frameborder="0" border="0" style="border:0;margin:0;padding:0;"></iframe>
						</div></div>
					</div>
			    </div>	
					    
			    
	            <div class="col-md-4" style="padding:10px;">
		            <div class="car-card">
		            <script>!function(){var e=document.getElementsByTagName("script"),t=e[e.length-1],n=document.createElement("script");function r(){var e=crCryptocoinPriceWidget.init({base:"KRW,USD,CNY,EUR",items:"BTC,BAT,BCH,LINK,MANA,ETH,ETC,FIL,ZEN,LTC,LPT,XLM,ZEC",backgroundColor:"FFFFFF",streaming:"1",striped:"1",rounded:"1",boxShadow:"1",border:"1"});t.parentNode.insertBefore(e,t)}n.src="https://co-in.io/widget/pricelist.js?items=BTC%2CBAT%2CBCH%2CLINK%2CMANA%2CETH%2CETC%2CFIL%2CZEN%2CLTC%2CLPT%2CXLM%2CZEC",n.async=!0,n.readyState?n.onreadystatechange=function(){"loaded"!=n.readyState&&"complete"!=n.readyState||(n.onreadystatechange=null,r())}:n.onload=function(){r()},t.parentNode.insertBefore(n,null)}();</script>
		            <a href="https://currencyrate.today/" rel="noopener" target="_blank"></a>
		            </div>
	            </div>	
			    
			    	
                <div class="col-md-4" style="padding:10px;">
		            <div class="car-card3">
		                <div style="width: 250px; height:335px; background-color: #FAFAFA; overflow:hidden; box-sizing: border-box; border: 1px solid #56667F; border-radius: 4px; text-align: right; line-height:14px; block-size:335px; font-size: 12px; font-feature-settings: normal; text-size-adjust: 100%; box-shadow: inset 0 -20px 0 0 #56667F;margin: 0;width: 250px;padding:1px;padding: 0px; margin: 0px;">
			            <div style="height:315px; padding:0px; margin:0px; width: 100%;">
		                <iframe src="https://widget.coinlib.io/widget?type=converter&theme=light" width="250" height="325px" scrolling="auto" marginwidth="0" marginheight="0" frameborder="0" border="0" style="border:0;margin:0;padding:0;"></iframe>
		                </div><div style="color: #FFFFFF; line-height: 14px; font-weight: 400; font-size: 11px; box-sizing: border-box; padding: 2px 6px; width: 100%; font-family: Verdana, Tahoma, Arial, sans-serif;">
		                <a href="https://coinlib.io" target="_blank" style="font-weight: 500; color: #FFFFFF; text-decoration:none; font-size:11px"></a></div></div>
	   	            </div>   	
                </div> 
            </div>
        </div>
    </div>
    
    
    <div class="contents-body black">
    	<div class="wrapper">
    		<div class="text3">KOMAP 뉴스레터</div>
    		<div class="line"></div>
    	</div>
    	
    	<div class="text4">새로 등록되는 글들과 이벤트 소식을 듣고 싶으신가요?</div>
    	
    	<div class="btn-box">
    		<input type="text" id="newsletter" placeholder="이메일을 입력해주세요">
            <div class="sign-up-button">구 독</div>
    	</div>
    	
    	<div>
    		<i class="fab fa-facebook"></i>
    		<i class="fab fa-twitter"></i>
    		<i class="fab fa-instagram"></i>
    		<i class="fab fa-reddit"></i>
    	</div>
    	    	
    </div>
    
    <input id="hiddenUserIdx" type="hidden" value="${checkedUser.user_idx}"></input>
	<input id="hiddenUserImage" type="hidden" value="${checkedUser.user_image}"></input>
	 
    <div style="height:62px; background-color: #1D2330; overflow:hidden; box-sizing: border-box; border: 1px solid #282E3B; border-radius: 4px; text-align: right; line-height:14px; block-size:45px; font-size: 12px; font-feature-settings: normal; text-size-adjust: 100%; box-shadow: inset 0 -20px 0 0 #262B38;padding:1px;padding: 0px; margin: 0px; width: 100%;"><div style="height:40px; padding:0px; margin:0px; width: 100%;"><iframe src="https://widget.coinlib.io/widget?type=horizontal_v2&theme=dark&pref_coin_id=1520&invert_hover=no" width="100%" height="36px" scrolling="auto" marginwidth="0" marginheight="0" frameborder="0" border="0" style="border:0;margin:0;padding:0;"></iframe></div><div style="color: #626B7F; line-height: 14px; font-weight: 400; font-size: 11px; box-sizing: border-box; padding: 2px 6px; width: 100%; font-family: Verdana, Tahoma, Arial, sans-serif;"><a href="https://coinlib.io" target="_blank" style="font-weight: 500; color: #626B7F; text-decoration:none; font-size:11px"></a>&nbsp;</div></div>
			
  </body>
  
  <script src="./resources/js/home.js"></script>
  
</html>