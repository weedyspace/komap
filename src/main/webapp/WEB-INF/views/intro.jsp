<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page session="false" %>

<html>
  	<head>
  		<title>코맵 - 코맵소개 | Komap.com</title>
  		<!-- <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"> -->
		<link rel="stylesheet" type="text/css" href="./resources/css/home.css?ver=2992093003">  <!-- css링크 -->
<!-- 	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">  bootstrap cdn링크  	-->		
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">  <!-- bootstrap cdn링크 -->
		<link rel="stylesheet" href="./resources/css/all.min.css"/>
 		<script src="https://code.jquery.com/jquery-3.6.1.min.js" integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
     	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d8606df5d21f589ec2dd66b7cb3d5e37&libraries=services,clusterer,drawing"></script>  <!-- 카카오맵 지도 api -->   
    </head>
	
    <body>
    	<%@ include file="./include/header-menu.jsp" %>
   <!-- <div>코맵소개</div> -->

<!--  <img src="./resources/images/USDT.svg" style="width:12px; height:12px;"/><i class="fa-solid fa-location-dot"></i><i class="fa-solid fa-map-location-dot"></i><i class="fa-solid fa-briefcase"></i><i class="fa-sharp fa-solid fa-coins"></i><i class="fa-solid fa-phone"></i><i class="fa-solid fa-envelope"></i><i class="fa-brands fa-internet-explorer"></i><i class="fa-brands fa-square-instagram"></i><i class="fa-solid fa-circle-info"></i> -->   	
<!--  <i class="fa-thin fa-calendar-days"></i><i class="fa-duotone fa-calendar-days"></i><i class="fa-light fa-calendar-days"></i><i class="fa-regular fa-calendar-days"></i><i class="fa-solid fa-calendar-days"></i><i class="fa-regular fa-pen-to-square"></i><i class="fa-solid fa-pen-to-square"></i>-->		 <!-- 지도를 표시할 div 입니다 -->
		<div class="map_wrap">		
		    <div id="map" style="width:100%;height:100%;position:relative;overflow:hidden;"></div>
		    <div class="hAddr">
		        <span class="title">지도중심기준 행정동 주소정보</span>
		        <span id="centerAddr"></span>
		    </div>
		</div>			
		
 
		
		<!-- <button id="moveSmooth">지도 중심좌표 부드럽게 이동시키기</button> --> 
		
   		<!-- <p id="result"></p>
    	<div id="clickLatlng"></div>
    	<div class="test"></div>
    	<button id="cancel">지우기</button>  -->	
    	<div id="testing"></div>
	
    	
	<!-- <div>
			<div class="layerPop" style="width=100px; height:100px; border: 1px solid black;"></div>
		</div> -->


	  <!-- Basic dropdown -->
<!-- <div class="dropdown">
  <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    Dropdown button
  </button>
  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
    <a class="dropdown-item" href="#">Action</a>
    <a class="dropdown-item" href="#">Another action</a>
    <a class="dropdown-item" href="#">Something else here</a>
  </div>
</div> -->
  	<!-- Basic dropdown -->		
		

		
  		<div class="loginUser" style="display: none;">${loginUser.id}</div>
  
  	
		<!-- <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script> -->
		<!-- <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"></script>  -->

    </body>

<script src="./resources/js/intro.js"></script>
     
</html>