<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page session="false" %>


<html>
  	<head>
  		<title>코맵 - 자유게시판 글쓰기 | Komap.com</title>
  		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<link rel="stylesheet" type="text/css" href="./resources/css/home.css?ver=2534534563544543">  <!-- css링크 -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">  <!-- bootstrap cdn링크 -->
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>  <!-- fontawesome cdn링크 -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
        
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script rel="stylesheet" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        
        <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet"> 
 		<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
 		<script src=" https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/lang/summernote-ko-KR.min.js"></script>
		
	</head>
	
    <body>
    	<%@ include file="./include/header-menu.jsp" %>
    	
    	<div>이미지 업로드</div>
    	<div>
    		<input id="img-file" type="file" />
    	</div>
    	<button id="img-upload-button">이미지 업로드</button>
		
		
		
    </body>
    
   
 <script src="https://www.gstatic.com/firebasejs/8.3.0/firebase-app.js"></script>
 <script src=" https://www.gstatic.com/firebasejs/8.3.0/firebase-storage.js"></script> 
 
 <script src="./resources/js/image-upload.js"></script>
 

</html>	