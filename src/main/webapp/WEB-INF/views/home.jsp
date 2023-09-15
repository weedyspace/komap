<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page session="false" %>

<html>
  <head>
      <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>  <!-- fontawesome cdn링크 -->
      <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
     <!--  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" 
	  integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous"> -->
	  <link type="text/css" rel="stylesheet" href="./resources/css/bootstrap.css"/>
  </head>
  
  <body>
  
  	<div class="input_text">
	  	<div>
	  		<input  class="signin_pass" id="licenseNumber" type="text" name="licenseNumber" title="라이센스 입력" placeholder="라이센스 입력해주세요">
			<input  class="signin_pass" id="phoneNumber" type="text" name="phoneNumber" title="전화번호 입력" placeholder="전화번호 입력해주세요">
	   		<input  class="signin_pass" type="button" value="입력" id="phoneChk"> // phoneChk 클릭시 함수 발동	  	
	  	</div>
	    
		<div>
		    <input  class="signin_pass" id="phone2" type="text" name="phone" title="전화번호 입력" placeholder="인증번호 입력해주세요">
		    <input  class="signin_pass" type="button" value="인증확인" id="phoneChk2"> // phoneChk 클릭시 함수 발동		
		</div>
	</div>
			
  </body>
  
  <script src="./resources/js/home.js"></script>
  
</html>