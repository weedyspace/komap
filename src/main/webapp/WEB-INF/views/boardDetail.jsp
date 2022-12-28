<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page session="false" %>

<html>
  	<head>
  		<title>게시글 디테일</title>
  		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<link rel="stylesheet" type="text/css" href="./resources/css/home.css?ver=6354528939893">  <!-- css링크 -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">  <!-- bootstrap cdn링크 -->        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>  <!-- fontawesome cdn링크 -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
	</head>
	
    <body>
    
    	<%@ include file="./include/header-menu.jsp" %>
    	
<%--     	<div class="board-title-box">
    		<span class="board-title">디테일</span>
    	</div>

    	
  		<div>user index#: ${boardDetail.user_idx}</div> 
    	<div>user email: ${boardDetail.email}</div>
    	<div>user id: ${boardDetail.id}</div>
    	<div>user title: ${boardDetail.title}</div>
    	<div>user content: ${boardDetail.content}</div>
    	<div>board_idx: ${boardDetail.board_idx}</div>
    	

	    <div class="reply-reactions">
	    	<i class="far fa-thumbs-up main"></i>
	    	<span class="thumbs-up-count">${boardDetail.recomm_count}</span>
	    	<i class="far fa-thumbs-down main"></i>
	    	<span class="thumbs-down-count">${boardDetail.decomm_count}</span> 
	    </div>    	
    	<br/>
    	<br/> --%>
<%--     	<c:if test="${loginUser.id eq boardDetail.id}">
     		<button id="revise">수정</button>
    		<button id="delete">삭제</button> 
    	</c:if> --%>
    	
    	<!-- <input id="board-idx-inp" type="hidden" name="name" value="${boardDetail.board_idx}"> <!-- type를 hidden하는 대신에 style="display:none;" 을 적용시켜도 됨. 원래는 boardDetail.js에서 ajax구현시 board_idx 쿼리를 받아주기위해서 일부러 작성된 input문 이지만 url 주소창에서 받아오는 코드를 사용했기 때문에 생략했음 -->
    	
    	
    	<div class="whole-body-box">
    	
    	
    		<div class="body-box-line">
    		
     			<div class="board-top-box">
	    			<div class="board-box-inside" style="width:98%;">
	    				<div class="inside-box-left">자유게시판</div>
	    				<div class="inside-box-right">
	    					<input id="search-box"></input>
	    		        	<i class="far fa-search"></i>
	    				</div>
	    			</div>
    		    </div>
	    			
	 		    <div class="board-detail-box-top">
<!-- 	 		        <span>작성번호</span>
					<span class="board-detail-title">글제목</span>
					<span>아이디</span>
					<span>작성일</span>
					<span>조회수</span> -->
					
					<div style="width: 100%;">
	    				<div class="col-md-1 top-header first"><span class="board-idx">${boardDetail.board_idx}</span></div>
	    			 	<div class="col-md-7 top-header"><span>${boardDetail.title}</span></div>
	    			 	<div class="col-md-2 top-header"><span>${boardDetail.id}</span></div>
	    			 	<div class="col-md-1 top-header" style="font-size:10px; font-weight:500; padding:0 3px;"><span>조회수 ${boardDetail.click_count}</span></div>
	    			 	<div class="col-md-1 top-header" style="font-size:10px; font-weight:500; padding:0 3px;"><span class="unix">${boardDetail.unix}</span></div>
	    			</div>
	    			
	    			<div class="content" style="padding:40px 18px 0px 18px;">
	    				${boardDetail.content}
	    			</div>
	    			
					
		        </div>
		        
		        <div style="width:100%; border:1px solid #dedede; overflow:hidden; margin-top:30px; margin-bottom:15px;"></div>
		        
		    	
		    	<div style="display: flex; justify-content: space-between; margin-bottom:60px;">
		    		<span></span>
				    <div class="reply-reactions">			    
				    	<i class="far fa-thumbs-up main"></i>
				    	<span class="thumbs-up-count">${boardDetail.recomm_count}</span>
				    	<i class="far fa-thumbs-down main"></i>
				    	<span class="thumbs-down-count">${boardDetail.decomm_count}</span> 
				    	<c:if test="${loginUser.id eq boardDetail.id}">
				    		<span>
				    			<button id="revise" style="margin-right:5px; width:45px;">수정</button>
				    			<button id="delete" style="width:45px;">삭제</button>
				    		</span>	
			    		</c:if>	
			    		<c:if test="${loginUser.id ne boardDetail.id}">	<!-- ne = not equal(!=) -->
						    <span class="report">
						        <span><img src="./resources/images/report.png" style="width:40px; margin-bottom:10px; cursor:pointer;" /></span>
						        <p class="arrow_box">신고하기</p>
					    	</span>			    		
			    		</c:if>			    	
				    </div>
		    	</div>
		    		   

<%-- 		    <c:if test="${loginUser.id != boardDetail.id || empty loginUser}">
			    	<div style="display: flex; justify-content: space-between; margin-bottom:60px;">
			    		<span></span>
					    <div class="reply-reactions">
					    	<i class="far fa-thumbs-up main"></i>
					    	<span class="thumbs-up-count">${boardDetail.recomm_count}</span>
					    	<i class="far fa-thumbs-down main"></i>
					    	<span class="thumbs-down-count">${boardDetail.decomm_count}</span> 
						    <span class="report">
						        <span><img src="./resources/images/report.png" style="width:40px; margin-bottom:10px; cursor:pointer;" /></span>
						        <p class="arrow_box">신고하기</p>
					    	</span>					    	
					    </div>		    			    		
		    		</div>
		    	</c:if>	 --%>
		    		    			    	     
		        
		        
		        <div class="reply-area">
		        
 		        	<c:if test="${empty loginUser}">
		        		<img class="profile-image" src="https://firebasestorage.googleapis.com/v0/b/komap-335012.appspot.com/o/file-1659928121956?alt=media&token=422eada4-a9b0-48b5-88f7-2ab644c4bb58" /> <!-- 구글 파이어베이스에 저장되어져있는 이미지파일의 주소 -->
		        	</c:if>
	       		    <c:if test="${!empty loginUser}">
 	 		   			<img class="profile-image" src="${loginUser.user_image}" />
	 		        </c:if>
 		        	    
		        	<div class="reply-box">
		        		<textarea class="main addReply" rows="1" cols="88" placeholder="댓글추가.."></textarea>
		        		<div class="main line-image"></div>
		        		<div class="main reply-selections" style="display: none;">
		       				<span class="main cancel-button"><span>취소</span></span>
		       				<span class="main reply-button"><span>댓글</span></span>		
		       		    </div>
		       		</div>
		       		
		        </div>
		        
		        
		        
	        	<c:forEach var="writing" items="${replyList}">
		        	<div class="reply-area sub-result">		        
   			        <img class="profile-image" src="${writing.user_image}" />
			        	<div class="reply-box">
			        		<div class="reply-box-top">
			        		    <div>
			        				<span id="reply-id" style="font-size: 12px; font-weight:700;">${writing.id}</span>
			        				<span class="reply-unix" style="color: #7B7D80; font-size: 11px;">${writing.reply_reg_date.substring(0,16)}</span>
			        			</div>
			        			
			        			<c:if test="${empty loginUser}">
				        			<div class="dropdown first" style="display: none;">
				        				<i class='fal fa-ellipsis-v fa-1x' id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true" style='color:#000000;'></i>
										<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">										
									    	<li class="empty" role="presentation"><a role="menuitem" tabindex="-1" href="#"></a></li>
									    </ul>			        				
				        			</div>			        				
			        			</c:if>
			        			
			        		 	<c:if test="${!empty loginUser && (loginUser.user_idx != writing.reply_user_idx)}">
				        			<div class="dropdown first" style="margin-top: 4px;">
				        				<i class='fal fa-ellipsis-v fa-1x' id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true" style='color:#000000;'></i>
										<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
											<li class="report" style="display:flex; align-items:center;" role="presentation">
												<img src="./resources/images/report.png" style="width:30px; height:30px; margin-bottom:0px; margin-left:10px;" />
												<a style="padding:3px 20px;" role="menuitem" tabindex="-1" href="#">신고</a>
											</li>

									   	 </ul>			        				
				        			</div>			        				
			        			</c:if>		        			
			        			
			        			<c:if test="${!empty loginUser && loginUser.user_idx == writing.reply_user_idx}">				        			
					    			<div class="dropdown first" style="margin-top: 5px;">
				        				<i class='fal fa-ellipsis-v fa-1x' id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true" style='color:#000000;'></i>
										<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
									    	<li class="reply-revise first" style="display:flex; align-items:center;" role="presentation"><img src="./resources/images/pen.png" style="width:20px; height:20px; margin-bottom:0px; margin-left:10px;" /><a role="menuitem" tabindex="-1" href="#">수정</a></li>
									    	<li class="reply-delete" data-reply-idx="${writing.reply_idx}" style="display:flex;" role="presentation"><img src="./resources/images/bin.png" style="width:20px; height:20px; margin-top:3px; margin-left:10px;" /><a role="menuitem" tabindex="-1" href="#">삭제</a></li>								 
									   	</ul>			        				
				        			</div>	 	        				
			        			</c:if>			        			

			  
			        		</div>
			        		
			        		<div class="reply-first-writing">${writing.reply_box_content}</div>
			        		<textarea class="sub addReply first" rows="1" cols="88" placeholder="댓글추가.." style="display:none;"></textarea>
			        		<div class="sub line-image first" style="display: none;"></div>
			        	
			        		<div class="reply-reactions first">
						    	<i class="far fa-thumbs-up sub" data-thumbsup-idx="${writing.reply_idx}"></i>
						    	<span class="reply-thumbs-up-count">${writing.reply_recomm_count}</span>
						    	<i class="far fa-thumbs-down sub" data-thumbsdown-idx="${writing.reply_idx}"></i>
						    	<span class="reply-thumbs-down-count">${writing.reply_decomm_count}</span>
			       				<span class="re-reply-button first" style="cursor:pointer;"><span>답글</span></span>
			       		    </div>
			       		    
			       		    
			        		<div class="sub reply-selections first" style="display: none;">
			       				<span class="sub cancel-button first"><span>취소</span></span>
			       				<span class="sub reply-button first" data-reply-idx="${writing.reply_idx}"><span>저장</span></span>		
			       		    </div>			       		    
			       		    
			       		    
			       		    
			       		    
					       		    
		  					<div class="re-reply-area first" style="display: none;"> <!-- 답글추가 창 -->
					        
				       		    <c:if test="${!empty loginUser}">
			 	 		   			<img class="profile-image" style="width:40px; height:40px;" src="${loginUser.user_image}" />
				 		        </c:if>
			 		        	    
					        	<div class="re-reply-box" style="width: 89%;">
					        		<textarea class="sub second addReply" rows="1" cols="88" placeholder="답글추가.."></textarea>
					        		<div class="second line-image first"></div>
					        		<div class="reply-selections">
					       				<span class="re cancel-button"><span>취소</span></span>
					       				<span class="re reply-button" data-reply-idx="${writing.reply_idx}"><span>답글</span></span>
					       		    </div>
					       		</div>
					       		
					        </div>	
			       		    
			       		    
			       		    
			       		    
			       		    			        	
 			        		<c:forEach var="reWriting" items="${reReplyList}">
	 			        			<c:if test="${writing.reply_idx eq reWriting.reReply_idx}">
							        	<div class="reply-area third-result" style="margin-bottom: 0px;">		        
					 			        	<img class="profile-image" src="${reWriting.user_image}" />
								        	<div class="reply-box">
								        		<div class="reply-box-top">
								        			<div>
								        				<span id="reply-id" style="font-size: 12px; font-weight:700;">${reWriting.id}</span>
								        				<span style="color: #7B7D80; font-size: 11px;">${reWriting.reReply_reg_date.substring(0,16)}</span>
								        			</div>
								        			
								        			<c:if test="${empty loginUser}">
									        			<div class="dropdown second" style="display: none;">
									        				<i class='fal fa-ellipsis-v fa-1x' id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true" style='color:#000000;'></i>
															<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
														    	<li class="empty" role="presentation"><a role="menuitem" tabindex="-1" href="#"></a></li>
														    </ul>			        				
									        			</div>			        				
								        			</c:if>
								        			
								        		 	<c:if test="${!empty loginUser && (loginUser.user_idx != reWriting.reReply_user_idx)}">
									        			<div class="dropdown second" style="margin-top: 4px;">
									        				<i class='fal fa-ellipsis-v fa-1x' id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true" style='color:#000000;'></i>
																<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
														    	<li class="report" style="display:flex; align-items:center;" role="presentation">
														    		<img src="./resources/images/report.png" style="width:30px; height:30px; margin-bottom:0px; margin-left:10px;" />
														    		<a role="menuitem" tabindex="-1" href="#">신고</a>
														    	</li>
														    </ul>			        				
									        			</div>			        				
								        			</c:if>		        			
								        			
								        			<c:if test="${!empty loginUser && loginUser.user_idx == reWriting.reReply_user_idx}">				        			
										    			<div class="dropdown second" style="margin-top: 5px;">
									        				<i class='fal fa-ellipsis-v fa-1x' id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true" style='color:#000000;'></i>
																<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
														    	<li class="reply-revise second" style="display:flex; align-items:center;" role="presentation"><img src="./resources/images/pen.png" style="width:20px; height:20px; margin-bottom:0px; margin-left:10px;" /><a role="menuitem" tabindex="-1" href="#">수정</a></li>
														    	<li class="reply-revise delete" data-rereply-idx="${reWriting.idx}" style="display:flex;" role="presentation"><img src="./resources/images/bin.png" style="width:20px; height:20px; margin-top:3px; margin-left:10px;" /><a role="menuitem" tabindex="-1" href="#">삭제</a></li>								 
														    </ul>			        				
									        			</div>	 	        				
								        			</c:if>			        			
					
								  
								        		</div>
								        		<div class="reply-writing">${reWriting.reReply_box_content}</div>
								        		<textarea class="sub third addReply" data-rereply-idx="${reWriting.idx}" rows="1" cols="88" placeholder="댓글추가.." style="display:none;"></textarea>
								        		<div class="sub line-image" style="display: none;"></div>
								        	
								        		<div class="reply-reactions">
											    	<i class="far fa-thumbs-up third" data-thumbsup-idx="${reWriting.idx}"></i>
											    	<span class="reply-thumbs-up-count">${reWriting.reReply_recomm_count}</span>
											    	<i class="far fa-thumbs-down third" data-thumbsdown-idx="${reWriting.idx}"></i>
											    	<span class="reply-thumbs-down-count">${reWriting.reReply_decomm_count}</span>
								       				<span class="re-reply-button second" style="cursor:pointer;"><span><!-- 답글 --></span></span>
								       		    </div>
								       		    
								       		    
								        		<div class="sub reply-selections" style="display: none;">
								       				<span class="sub cancel-button second"><span>취소</span></span>
								       				<span class="sub reply-button second" data-rereply-idx="${reWriting.idx}"><span>저장</span></span>		
								       		    </div>			       		    
								       		    
								       		    
								       		    
								       		    
										       		    
							  					<div class="re-reply-area third" style="display: none;"> <!-- 답글추가 창 -->
										        
									       		    <c:if test="${!empty loginUser}">
								 	 		   			<img class="profile-image" style="width:40px; height:40px;" src="${loginUser.user_image}" />
									 		        </c:if>
								 		        	    
										        	<div class="re-reply-box" style="width: 89%;">
										        		<textarea class="sub fourth addReply" rows="1" cols="88" placeholder="답글추가.."></textarea>
										        		<div class="third line-image"></div>
										        		<div class="reply-selections">
										       				<span class="re cancel-button"><span>취소</span></span>
										       				<span class="re reply-button" data-reply-idx="${reWriting.reReply_idx}"><span>답글</span></span>
										       		    </div>
										       		</div>
										       		
										        </div>	
								       		    
								       		    
								       		    
								       		    
								       		</div>
								       		
								       		
								       		
								       		
								        </div>							      
									</c:if>  	   	
							</c:forEach>
						       		    
			       		    
			       		    
			       		    
			       		</div> 
			       					       		
			       		       					       		
			       		
			       		
			        </div>	
	        	</c:forEach>	        
		        				           		
	    	</div>
	    	
	    	
    	</div>
    	
    	
<!-- 		<div class="dropdown">
		  <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true">
		    Dropdown
		    <span class="caret"></span>
		  </button>
		  <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
		    <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Action</a></li>
		    <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Another action</a></li>
		    <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Something else here</a></li>
		    <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Separated link</a></li>
		  </ul>
		</div> -->
    	
    	
		<div class="loginUser" style="display: none;">${loginUser}</div>
		

    </body>
   
    <script src="https://www.gstatic.com/firebasejs/7.23.0/firebase-app.js"></script>  <!-- 파이어베이스의 전반적인 기능을 쓸수있게 해주는 라이브러리 -->
	<script src="https://www.gstatic.com/firebasejs/7.23.0/firebase-auth.js"></script> <!-- 파이어베이스를 통한 구글인증 라이브러리 -->
    <script src="./resources/js/config.js"></script>
    <!-- <script src="./resources/js/login.js"></script> -->   
    <script src="./resources/js/boardDetail.js"></script>
    
</html>