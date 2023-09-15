<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page session="false" %>

<html>
  	<head>
  		<title>자유게시판</title>
  		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<link rel="stylesheet" type="text/css" href="./resources/css/home.css?ver=13454353">  <!-- css링크 -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">  <!-- bootstrap cdn링크 -->
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>  <!-- fontawesome cdn링크 -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script> <!-- jquery cdn링크 -->
        <script src="./resources/js/jquery.twbsPagination.min.js"></script> <!-- 페이지내이션 --> 
	</head>
	
	<style>
		.board-row:nth-child(even) > div{
			background: #FCFCFC;
		}
	</style>
	
    <body>
    
    	<%@ include file="./include/header-menu.jsp" %> 
    	<div class="board-top-box">
    		<div class="board-box-inside">
    			<div class="inside-box-left">자유게시판</div>
    			<div class="inside-box-right">
    				<input id="search-box"></input>
    		        <i class="far fa-search"></i>
    			</div>
    		</div>
    	</div>
    	<div class="board-box">
    	
    		<div class="board-box-row">
    		  
    			<div>
    				<div class="col-md-1 top-header first"><span>No</span></div>
    			 	<div class="col-md-6 top-header"><span>제목</span></div>
    			 	<div class="col-md-2 top-header"><span>아이디</span></div>
    			 	<div class="col-md-1 top-header"><span>조회수</span></div>
    			 	<div class="col-md-1 top-header"><span>추천수</span></div>
    			 	<div class="col-md-1 top-header"><span>등록일</span></div>
    			</div>
    			
     			<div class="bigBox">
     				<c:forEach var="writing" items="${searchList}"> 
    					<div class="board-row">
    						<div class="col-md-1 top-header-add first"><span>${writing.board_idx}</span></div>
    			 			<div class="col-md-6 top-header-add"><span class="click-detail" data-board-index="${writing.board_idx}">${writing.title}</span></div>
    			 			<div class="col-md-2 top-header-add"><span>${writing.id}</span></div>
    			 			<div class="col-md-1 top-header-add"><span class="click-count" data-index="${writing.board_idx}">${writing.click_count}</span></div>
    			 			<div class="col-md-1 top-header-add"><span>${writing.recomm_count}</span></div>
    			 			<div class="col-md-1 top-header-add"><span style="font-size: 10px; font-weight: 500;">${writing.reg_date.substring(0,16)}</span></div>
    					</div>
    				</c:forEach>
    				
    				
    			</div>
   			 
    		    <ul id="pagination-demo" class="pagination-sm"></ul>
    						
    			<div>
    				<div class="col-md-1 botton"><span> </span></div>
    			 	<div class="col-md-6 botton"><span> </span></div>
    			 	<div class="col-md-2 botton"><span> </span></div>
    			 	<div class="col-md-1 botton"><span> </span></div>
    			 	<div class="col-md-1 botton"><span> </span></div>
    			 	<div class="col-md-1 writing-button"><span style="font-color:blue;">글쓰기</span></div>
    			</div>
    			
    			
    		</div>
    		
    	</div>
    	

    	<input id="test" type="hidden" name="name" value="${loginUser.id}"> <!-- type를 hidden하는 대신에 style="display:none;" 을 적용시켜도 됨 -->
    	
    </body>
    
    <script src="./resources/js/boardSearchResult.js"></script>
    	
</html>