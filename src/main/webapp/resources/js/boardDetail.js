$(document).ready(function() {
	firebase.initializeApp(firebaseConfig); //파이어베이스 연동에서 초기세팅을 위한 함수코드. 한번만 실행됨

	var getUrlParameter = function getUrlParameter(sParam) { //JQUERY URL GET PARAMETER 가져오기 로 구글에서 검색하면 찾을수 있음
	    var sPageURL = window.location.search.substring(1),
	        sURLVariables = sPageURL.split('&'),
	        sParameterName,
	        i;
	
	    for (i = 0; i < sURLVariables.length; i++) {
	        sParameterName = sURLVariables[i].split('=');
	
	        if (sParameterName[0] === sParam) {
	            return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
	        }
	    }
	    return false;
	};








	//ajax로 mainController에 RequestMapping을 하나 만들어줘서 아래의 boardList를 res로 받아온다
	//List<Board> boardList = boardDao.getAll(start);
    //받아온 list의 unix를 알맞게 가공시켜준다==> each반복문으로 인덱스에 접근. 해당 인덱스 객체의 unix 값에 접근 (boardList.unix). Math.round(new Date.getTime() / 1000) 마이너스 그 값
	//boardList.unix.replace(위에 계산한 값); 
    //append를 활용해 jsp에 붙여준다	
	$.ajax({
		url: './boardList',
		type: 'get',
		data: {
			start: getUrlParameter('start')
		},
		async: false, //비동기화 시켜주지 않기.즉 동기화. res값을 받을때까지 기다린후 밑으로 코드가 진행된다.
		success: function(res) {
			console.log(res);
			boardList = res;	
		},
		error: function(err) {
			
		}		
	});		
	
	var boardList;
	console.log(boardList);
	
	
	
    let unix = Math.round($('.unix').html()); //jsp에서 가져오기 => unix = 1667154403 => 1667154
	console.log(unix); 
    unix = Math.floor(new Date().getTime() / 1000) - unix;
	console.log(unix);
	
	if(unix < 60) {
		$('.unix').html(unix + '초 전');
	} else if(unix < 3600) {
		unix = Math.round(unix / 60);
		$('.unix').html(unix + '분 전');
	} else if(unix < 86400) {
		unix = Math.round(unix / 3600);
		$('.unix').html(unix + '시간 전');
	} else if(unix < 2592000) {
		unix = Math.round(unix / 86400);
		$('.unix').html(unix + '일 전');
	} else if(unix < 31536000) {
		unix = Math.round(unix / 2592000);
		$('.unix').html(unix + '달 전');
	} else if(unix < 3153600000) {
		unix = Math.round(unix / 31536000);
		$('.unix').html(unix + '년 전');
	} else if(unix < 31536000000) {
		unix = (unix / 315360000).toFix(1); //소수점 첫째자리 까지만 보여주고 만약 둘째자리가 5이상이면 반올림해주는 함수
		$('.unix').html(unix + '백년 전');
	}

	  
		

	
	
	








	$('.far.fa-search').on('click', function() { //search 이미지
		var searchContent = $('input#search-box').val();
		//console.log(searchContent);
		
		$.ajax({
			url: './boardSearchResult',
			type: 'get',
			data: {
				word: searchContent,
			},
			success: function(res) {
				console.log(res);
				if(res.length === 0) {
					alert('검색결과가 없습니다');
				} else if ($('#search-box').val() === '') {
					swal('검색어를 입력해 주세요.').then(function() {
						$('input#search-box').focus();
					});
					//alert('검색어를 입력해 주세요');
				} else {location.href = './boardSearchResult?&word=' + searchContent};
			},
			error: function(error) {
				
			}
		});
	});
	
	
	
	$('#search-box').on('keydown', function(key) { //키보드 버튼이 눌러질때 실행되는 jquery함수
		if(key.keyCode === 13) {  //13은 키보드상의 엔터키를 의미함
			//alert('enter');
			$('.far.fa-search').trigger('click');  //강제 클릭이벤트 발생 함수. 
		}
	});




	$('.fal').css('display', 'none');
	
	var board_idx = getUrlParameter('board_idx'); //url로 부터 board_idx 가져오기 
	//var board_idx = $('input#board-idx-inp').val(); //board.jsp의 input값으로 부터 board_idx 가져오기  
		console.log(board_idx);
	
	
	
	$('i.far.fa-thumbs-up.main').one('click', function() {
		if($('.loginUser').html() === '') {
			swal('로그인 해주세요.').then(function() {
				location.href = './login';
			});
			return 'ok';	
			//alert('로그인 해주세요');
			//location.href = './login';
			//return 'ok';
		}
		//location.href = './';
		/*var thumbsUpCount = parseInt($('.far.fa-thumbs-up').html()) + 1; //jsp 뷰에서 찍혀나오는 문자형 숫자를 int로 parsing 시켜주고 1을 더해줌
		$('.far.fa-thumbs-up').html(thumbsUpCount);
		console.log($('.far.fa-thumbs-up').html());
		var recomm_count = $('.far.fa-thumbs-up').html();
		console.log(recomm_count);*/
		//var thumbsUpCount = parseInt($('.thumbs-up-count').html()) + 1; //jsp 뷰에서 찍혀나오는 문자형 숫자를 int로 parsing 시켜주고 1을 더해줌
		var thumbsUpCount = parseInt($(this).next().html()) + 1;
		//$('.thumbs-up-count').html(thumbsUpCount);
		$(this).next().html(thumbsUpCount);
		//console.log($('.thumbs-up-count').html());
		var recomm_count = $('.thumbs-up-count').html();
		//console.log(recomm_count);
		
		
		console.log('board-idx: '+board_idx);
		console.log('recomm_count: '+recomm_count);
		
		
		$.ajax({
			url: './countThumbsUp',
			type: 'get',
			data: {
				board_idx: board_idx,
				recomm_count: recomm_count,
			},
			success: function(res) {
				console.log(res);
				
			},
			error: function(error) {
				
			}
		});
			
	});
	



	$('i.far.fa-thumbs-down.main').one('click', function() {
		if($('.loginUser').html() === '') {
			swal('로그인 해주세요.').then(function() {
				location.href = './login';
			});
			return 'ok';
			//alert('로그인 해주세요');
			//location.href = './login';
			//return 'ok';			
		}
		//alert('down');
		//var thumbsDownCount = parseInt($('.thumbs-down-count').html()) + 1;
		var thumbsDownCount = parseInt($(this).next().html()) + 1;
		//$('.thumbs-down-count').html(thumbsDownCount);
		$(this).next().html(thumbsDownCount);
		//console.log($('.far.fa-thumbs-down').html());
		var decomm_count = $('.thumbs-down-count').html();
		//console.log(decomm_count);
		
		$.ajax({
			url: './countThumbsDown',
			type: 'get',
			data: {
				board_idx: board_idx,
				decomm_count: decomm_count,
			},
			success: function(res) {
				//console.log(res);
				
			},
			error: function(error) {
				
			}
		});
		
	});	
	
	
//여기 아래 구현부분 중에서 굳이 url의 파라미터 값인 board_idx의 값을 가져오지 않아도 됨. 왜냐하면 이미 3번줄에서 board_idx를 먼저 가져왔기 때문에 그냥 써도 됨   	
	$('#revise').on('click', function() {
		//alert('revise!');
		var board_idx = getUrlParameter('board_idx');
		console.log(board_idx);
		
		location.href='./boardRevise?&board_idx=' + board_idx ;
	});
	
	
	
	$('#delete').on('click', function() {
		//alert('delete!');	
		swal("해당글을 정말 삭제하시겠습니까?", {
		  buttons: {cancel: "취소", 네: true},
		}).then(function(value) {
			switch(value) {
				case "네":
				$.ajax({
					url: './boardDeleteButton',
					type: 'get',
					data: {
						board_idx: board_idx,
					},
					success: function(res) {
						if(res === 'ok') {
							swal('삭제되었습니다.').then(function() {
								location.replace('./board?start=0');
							});								
						}
					},
					error: function(err) {
						
					}		
				});									
			}
		});	
				
	  /* if(confirm('해당글을 정말 삭제하시겠습니까?')) {
			location.href='./boardDeleteButton?&board_idx=' + board_idx;
			alert('삭제되었습니다');
			location.replace('./board?start=0');
		} else {
			location.href='./boardDetail?&board_idx=' + board_idx;
		}	*/	
	});
	
	
	
	
	
    var userIdx = $('#hiddenUserIdx').val(); //로그인되었을때 유저 고유인덱스 넘버
	console.log(userIdx);
	
	$('textarea.main.addReply').on('click', function() {
		if(userIdx.length === 0) { //유저 고유인덱스 넘버가 없을때 ==> 로그인되지 않았을시
			swal('로그인 해주세요.').then(function() {
				$('.main.reply-button').trigger('click'); //구글 로그인과 연계된 리플버튼 강제클릭 함수실행
			});		
			//alert('로그인 해주세요');
			//$('.main.reply-button').trigger('click'); //구글 로그인과 연계된 리플버튼 강제클릭 함수실행
		} else { //로그인 되어있는 상태일때
		$('.main.reply-selections').css('display', '');
		$('.main.line-image').css('border-color', 'black');
	
		/*$(document).click(function(e){ 
	    	if (!$(e.target).is('textarea.main.addReply')) { 
	    		$('.main.line-image').css('border-color', '#dedede');
	   	 }

		});*/	
				
		}
					
	});
	
	
	
	
	
	
	var replyWritingIdx = $('.board-idx').html();
	console.log(replyWritingIdx);
	
	$('.main.reply-button').on('click', function() { //뎃글 버튼 클릭
		if(userIdx.length === 0) { //유저 고유인덱스를 받아오지 않는다면 => 즉 로그인되어있지 않는 상태라면 구글로그인으로 이동함

		var provider = new firebase.auth.GoogleAuthProvider();
		
		firebase.auth()
		  .signInWithPopup(provider) //구글로그인 팝업창 
		  .then((result) => {  //내 로그인 이메일주소와 비번을 서버로 전달함
		    /** @type {firebase.auth.OAuthCredential} */
		    var credential = result.credential;
			
		    // This gives you a Google Access Token. You can use it to access the Google API.
		    var token = credential.accessToken;
		    // The signed-in user info.
		    var user = result.user;  //내 구글 어카운트 정보를 서버로부터 받아옴
		    // ...
			console.log(user); //내 구글 어카운트
			console.log(user.displayName);
			console.log(user.email);
			console.log(user.photoURL);
		
			//ajax로 사용자 정보를 데이터베이스로 넘겨주기
			$.ajax({ //사용자계정 새로가입 
				url: './googleLoginNewUser',
				type: 'get',
				data: {
					id: user.displayName,
					email: user.email,
					user_image: user.photoURL,
				},
				success: function(res) {
					console.log(res);
					if(res === 'ok') {  //데이터베이스에 아이디와 이메일 그리고 유저이미지를 넣어 가입되었음을 의미 
						$.ajax({  //아이디와 이메일로 로그인
							url: './googleLogin',
							type: 'get',
							data: {
								id: user.displayName,
								email: user.email,
							},
							success: function(res) {
								console.log(res);
								alert(user.displayName + '님의 가입을 환영합니다');
								location.href = './boardDetail?board_idx=' + board_idx;
							},
							error: function(error) {
								
							}
						});						
					} else{ //res의 값이 ok가 아닌 다른값일 경우(이경우 dup): 즉, 이미 해당 아이디와 이메일로 가입되어져 있는 경우  
						console.log(res);
						$.ajax({ //아이디와 이메일로 로그인
							url: './googleLogin',
							type: 'get',
							data: {
								id: user.displayName,
								email: user.email,
							},
							success: function(res) {
								console.log(res);  //dup
								alert(user.displayName + ' 님 환영합니다!');
								location.href = './boardDetail?board_idx=' + board_idx;
							},
							error: function(error) {
								
							}
						});
					}
				},
				error: function(error) {
					
				}
			});
			
		  }).catch((error) => {
		    // Handle Errors here.
		    var errorCode = error.code;
		    var errorMessage = error.message;
		    // The email of the user's account used.
		    var email = error.email;
		    // The firebase.auth.AuthCredential type that was used.
		    var credential = error.credential;
		    // ...
		  });
		

		} else if(userIdx.length > 0 && $('textarea.main.addReply').val().replace(/\s| /gi, "").length == 0) { //이미 로그인되어있는 상태이고 글을 작성했을시 띄어쓰기 무시(정규표현식) --> 문자열의 모든 문자를 대소문자를 무시하고 검색하되, 문자열 내의 모든 공백을 제거(""로 대체)했을때의 텍스트 길이가 0이라면, 공백 외의 내용이 없다는  
			swal('내용을 입력해 주세요!').then(function() {
				$('.main.line-image').css('border-color','black');
				$('.main.addReply').focus(); //이상하게도 바로 위에 코드는 실행이 안된다.  
			});
			//alert('내용을 입력해 주세요!');  
			//$('textarea.main.addReply').focus();
			//$('.main.line-image').css('border','1px solid black');
			//$('.main.line-image').css('border-color','black');
			//$('.line-image').css('background', 'black'); //먹히지가 않는다...
			//$('.line-image').css('border-color', 'black'); //먹히지가 않는다...
			//$('#test-line').css('border-color', 'black'); //먹히지가 않는다...
			//$('#test-line').css('height','1px');
			//$('#test-line').css('background','black');
	
			return;
		} else {  //로구인되어져있고 정규표현식도 모두 충족했을시 textarea의 내용을 db에 업로드
			var replyBoxContent = $('textarea.main.addReply').val(); //textarea의 리플 콘텐츠를 받아서
			console.log(userIdx);
			console.log(replyBoxContent);
			
			/*$('.reply-area').prepend(
				'<img class="profile-image" src="${writing.user_image}" />' +
	        	'<div class="reply-box">' +
	        		'<div>' +
	        			'<span id="reply-id" style="font-size: 12px;font-weight:700;">' + "${writing.id}" + '</span>' +
	        			'<span style="color: #7B7D80; font-size: 11px;">' + "${writing.reply_reg_date.substring(0,16)}" + '</span>' +
	        		'</div>' +
	        		'<div class="reply-writing">${writing.reply_box_content}</div>' +
	        	
	        		'<div class="reply-reactions">' +
				    	'<i class="far fa-thumbs-up" data-thumbsup-idx="${writing.reply_idx}"></i>' +
				    	'<span class="reply-thumbs-up-count">${writing.reply_recomm_count}</span>' +
				    	'<i class="far fa-thumbs-down" data-thumbsdown-idx="${writing.reply_idx}"></i>' +
				    	'<span class="reply-thumbs-down-count">${writing.reply_decomm_count}</span>' +
	       				'<span id="re-reply-button"><span>답글</span></span>' +
	       		    '</div>' +
	       		'</div>'	
			);*/
			
			unix = Math.round(new Date().getTime() / 1000); //1970년1월1일0시 기준 현재까지 초시간
			console.log(unix);
			
			$.ajax({ //데이터베이스에 유저인덱스와 댓글 콘텐츠를 넣어주기 
				url: './uploadReply',
				type: 'get',
				data: {
					userIdx: userIdx,
					replyWritingIdx: replyWritingIdx, //해당 페이지 글의 board_idx 
					replyBoxContent: replaceBrTag(replyBoxContent),
					unix: unix,
				},
				success: function(res) {
					console.log(res); //ok
								
					location.href = './boardDetail?board_idx=' + board_idx;
				},
				error: function(error) {
					
				}				
			});
			
			
			
/*			$.ajax({ // 해당 게시글에 딸린 댓글들 가져오기 
				url: './boardReplyList',
				type: 'get',
				async: false, //비동기화 시켜주지 않기.즉 동기화. res값을 받을때까지 기다린후 밑으로 코드가 진행된다.
				data: {
					replyWritingIdx: replyWritingIdx, //해당 페이지 글의 board_idx 
				},
				success: function(res) {
					console.log(res); //replyList
					boardReplyList = res;			
					//location.href = './boardDetail?board_idx=' + board_idx;
				},
				error: function(error) {
					
				}				
			});			
			
			
			var boardReplyList;
			console.log(boardReplyList);
			
			for(let i=0; i<boardReplyList.length; i++) {
				unix = Math.floor(new Date().getTime() / 1000) - boardReplyList[i].unix;
				console.log(unix);
				
				if(unix < 60) {
					console.log(boardReplyList[i].unix);
					boardReplyList[i].unix = unix + '초 전';
				} else if(unix < 3600) {
					unix = Math.round(unix / 60);
					boardReplyList[i].unix = unix + '분 전';
				} else if(unix < 86400) {
					unix = Math.round(unix / 3600);
					boardReplyList[i].unix = unix + '시간 전';
				} else if(unix < 2592000) {
					unix = Math.round(unix / 86400);
					boardReplyList[i].unix = unix + '일 전';
				} else if(unix < 31536000) {
					unix = Math.round(unix / 2592000);
					boardReplyList[i].unix = unix + '달 전';
				} else if(unix < 3153600000) {
					unix = Math.round(unix / 31536000);
					boardReplyList[i].unix = unix + '년 전';
				} else if(unix < 31536000000) {
					unix = (unix / 315360000).toFix(1); //소수점 첫째자리 까지만 보여주고 만약 둘째자리가 5이상이면 반올림해주는 함수
					boardReplyList[i].unix = unix + '백년 전';
				}
				
				$('.reply-box-top').append(
					'<div>'
+						'<span id="reply-id" style="font-size: 12px; font-weight:700;">' + boardReplyList[i].id + '</span>'
+						'<span class="reply-unix" style="color: #7B7D80; font-size: 11px;">' + boardReplyList[i].unix + '</span>'
+					'</div>'					
				);	
				
							
				
			}	*/					
				
					
				
		} 
				
	});
	
	
	
	


	$.ajax({ // 해당 게시글에 딸린 댓글들 가져오기 
		url: './boardReplyList',
		type: 'get',
		async: false, //비동기화 시켜주지 않기.즉 동기화. res값을 받을때까지 기다린후 밑으로 코드가 진행된다.
		data: {
			replyWritingIdx: replyWritingIdx, //해당 페이지 글의 board_idx 
		},
		success: function(res) {
			console.log(res); //replyList
			boardReplyList = res;			
		},
		error: function(error) {
			
		}				
	});			
	
	
	var boardReplyList;
	console.log(boardReplyList);	
	
	
	
	$.ajax({ // 해당 게시글에 딸린 댓글의 댓글들 가져오기 
		url: './boardReReplyList',
		type: 'get',
		async: false, //비동기화 시켜주지 않기.즉 동기화. res값을 받을때까지 기다린후 밑으로 코드가 진행된다.
		data: {
			replyWritingIdx: replyWritingIdx, //해당 페이지 글의 board_idx 
		},
		success: function(res) {
			console.log(res); //
			reReplyList = res;			
		},
		error: function(error) {
			
		}				
	});	
	
	var reReplyList;
	console.log(reReplyList);
	
	
	for(let i=0; i<boardReplyList.length; i++) {
		unix = Math.floor(new Date().getTime() / 1000) - boardReplyList[i].unix;
		console.log(unix);
		
		if(unix < 60) {
			console.log(boardReplyList[i].unix);
			boardReplyList[i].unix = unix + '초 전';
			console.log(boardReplyList[i].unix);
		} else if(unix < 3600) {
			unix = Math.round(unix / 60);
			boardReplyList[i].unix = unix + '분 전';
			console.log(boardReplyList[i].unix);
		} else if(unix < 86400) {
			unix = Math.round(unix / 3600);
			boardReplyList[i].unix = unix + '시간 전';
			console.log(boardReplyList[i].unix);
		} else if(unix < 2592000) {
			unix = Math.round(unix / 86400);
			boardReplyList[i].unix = unix + '일 전';
			console.log(boardReplyList[i].unix);
		} else if(unix < 31536000) {
			unix = Math.round(unix / 2592000);
			boardReplyList[i].unix = unix + '달 전';
			console.log(boardReplyList[i].unix);
		} else if(unix < 3153600000) {
			unix = Math.round(unix / 31536000);
			boardReplyList[i].unix = unix + '년 전';
			console.log(boardReplyList[i].unix);
		} else if(unix < 31536000000) {
			unix = (unix / 315360000).toFix(1); //소수점 첫째자리 까지만 보여주고 만약 둘째자리가 5이상이면 반올림해주는 함수
			boardReplyList[i].unix = unix + '백년 전';
			console.log(boardReplyList[i].unix);
		}
			
	    console.log(boardReplyList[i].user_image);	
		console.log(boardReplyList[i].unix);	
/*
		$('.reply-area.sub-result').append(
           '<img class="profile-image" src='+boardReplyList[i].user_image+ '/>'
+          '<div class="reply-box">'
+        		'<div class="reply-box-top">'
+	        	    '<div>'
+        				'<span id="reply-id" style="font-size: 12px; font-weight:700;">'+boardReplyList[i].id+'</span>'
+        				'<span class="reply-unix" style="color: #7B7D80; font-size: 11px;">'+boardReplyList[i].unix+'</span>'
+        			'</div>' 
+        			
+        			'<c:if test="${empty loginUser}">'
+	        			'<div class="dropdown first" style="display: none;">'
+	        				'<i class="fal fa-ellipsis-v fa-1x" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true" style="color:#000000;"></i>'
+							'<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">'										
+						    	'<li class="empty" role="presentation"><a role="menuitem" tabindex="-1" href="#"></a></li>'
+						    '</ul>'			        				
+	        			'</div>'			        				
+        			'</c:if>'
+        			
+        		 	'<c:if test=${!empty loginUser && (loginUser.user_idx !=' +boardReplyList[i].reply_user_idx+')}>'
+	        			'<div class="dropdown first" style="margin-top: 4px;">'
+	        				'<i class="fal fa-ellipsis-v fa-1x" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true" style="color:#000000;"></i>'
+							'<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">'
+								'<li class="report" style="display:flex; align-items:center;" role="presentation">'
+									'<img src="./resources/images/report.png" style="width:30px; height:30px; margin-bottom:0px; margin-left:10px;" />'
+									'<a style="padding:3px 20px;" role="menuitem" tabindex="-1" href="#">신고</a>'
+								'</li>'
+
+						   	 '</ul>'		        				
+	        			'</div>'		        				
+       			'</c:if>'		        			
+        			
+        			'<c:if test="${!empty loginUser && loginUser.user_idx ==' +boardReplyList[i].reply_user_idx+"}>"				        			
+		    			'<div class="dropdown first" style="margin-top: 5px;">'
+	        				'<i class="fal fa-ellipsis-v fa-1x" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true" style="color:#000000;"></i>'
+							'<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">'
+						    	'<li class="reply-revise first" style="display:flex; align-items:center;" role="presentation"><img src="./resources/images/pen.png" style="width:20px; height:20px; margin-bottom:0px; margin-left:10px;" /><a role="menuitem" tabindex="-1" href="#">수정</a></li>'
+						    	'<li class="reply-delete" data-reply-idx='+boardReplyList[i].reply_idx+ 'style="display:flex;" role="presentation"><img src="./resources/images/bin.png" style="width:20px; height:20px; margin-top:3px; margin-left:10px;" /><a role="menuitem" tabindex="-1" href="#">삭제</a></li>	'							 
+						   	'</ul>'			        				
+	        			'</div>'	 	        				
+       			'</c:if>'			        			
+
+  
+        		'</div>'
+        		
+       		'<div class="reply-first-writing">'+boardReplyList[i].reply_box_content+'</div>'
+        		'<textarea class="sub addReply first" rows="1" cols="88" placeholder="댓글추가.." style="display:none;"></textarea>'
+       		'<div class="sub line-image first" style="display: none;"></div>'
+        	
+        		'<div class="reply-reactions first">'
+			    	'<i class="far fa-thumbs-up sub" data-thumbsup-idx='+boardReplyList[i].reply_idx+'></i>'
+			    	'<span class="reply-thumbs-up-count">'+boardReplyList[i].reply_recomm_count+'</span>'
+			    	'<i class="far fa-thumbs-down sub" data-thumbsdown-idx='+boardReplyList[i].reply_idx+'></i>'
+			    	'<span class="reply-thumbs-down-count">'+boardReplyList[i].reply_decomm_count+'</span>'
+      				'<span class="re-reply-button first" style="cursor:pointer;"><span>답글</span></span>'
+       		'</div>'
+       		    
+       		    
+        		'<div class="sub reply-selections first" style="display: none;">'
+       			'<span class="sub cancel-button first"><span>취소</span></span>'
+       			'<span class="sub reply-button first" data-reply-idx="${writing.reply_idx}"><span>저장</span></span>'	
+       		'</div>'			       		    
+       		    
+      		    
+       		    
+       		    
+		       		    
+				'<div class="re-reply-area first" style="display: none;">' //<!-- 답글추가 창 -->
+		        
+	       		    '<c:if test="${!empty loginUser}">'
+ 	 		   			'<img class="profile-image" style="width:40px; height:40px;" src="${loginUser.user_image}" />'
+	 		        '</c:if>'
+ 		        	    
+		        	'<div class="re-reply-box" style="width: 89%;">'
+		        		'<textarea class="sub second addReply" rows="1" cols="88" placeholder="답글추가.."></textarea>'
+		        		'<div class="second line-image first"></div>'
+		        		'<div class="reply-selections">'
+		       				'<span class="re cancel-button"><span>취소</span></span>'
+		       				'<span class="re reply-button" data-reply-idx='+boardReplyList[i].reply_idx+'><span>답글</span></span>'
+		       		    '</div>'
+		       		'</div>'
+		       		
+		        '</div>'
+
+				'<div class="list-div">'
+				'</div>'      		    
+
      			$.each(reReplyList, function(index, reWriting) {
					$('.list-div').append(
+	        			'<c:if test='+boardReplyList[i]+ 'eq' +reWriting.reReply_idx+'>'
+				        	'<div class="reply-area third-result" style="margin-bottom: 0px;">'		        
+		 			        	'<img class="profile-image" src='+reWriting.user_image+ '/>'
+					        	'<div class="reply-box">'
+					        		'<div class="reply-box-top">'
+					        			'<div>'
+					        				'<span id="reply-id" style="font-size: 12px; font-weight:700;">'+reWriting.id+'</span>'
+					        				'<span style="color: #7B7D80; font-size: 11px;">'+reWriting+'</span>'
+					        			'</div>'
					        			
+					        			'<c:if test="${empty loginUser}">'
+						        			'<div class="dropdown second" style="display: none;">'
+						        				'<i class=""fal fa-ellipsis-v fa-1x" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true" style="color:#000000;"></i>'
+												'<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">'
+											    	'<li class="empty" role="presentation"><a role="menuitem" tabindex="-1" href="#"></a></li>'
+											    '</ul>'			        				
+						        			'</div>'			        				
+					        			'</c:if>'
					        			
+					        		 	'<c:if test="${!empty loginUser && (loginUser.user_idx !=' +reWriting.reReply_user_idx+')}>'
+						        			'<div class="dropdown second" style="margin-top: 4px;">'
+						        				'<i class="fal fa-ellipsis-v fa-1x" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true" style="color:#000000;"></i>'
+													'<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">'
+											    	'<li class="report" style="display:flex; align-items:center;" role="presentation">'
+											    		'<img src="./resources/images/report.png" style="width:30px; height:30px; margin-bottom:0px; margin-left:10px;" />'
+											    		'<a role="menuitem" tabindex="-1" href="#">신고</a>'
+											    	'</li>'
+											    '</ul>'			        				
+						        			'</div>'			        				
+					        			'</c:if>'		        			
					        			
+					        			'<c:if test="${!empty loginUser && loginUser.user_idx ==' +reWriting.reReply_user_idx+'}>'				        			
+							    			'<div class="dropdown second" style="margin-top: 5px;">'
+						        				'<i class="fal fa-ellipsis-v fa-1x" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true" style="color:#000000;"></i>'
+													'<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">'
+											    	'<li class="reply-revise second" style="display:flex; align-items:center;" role="presentation"><img src="./resources/images/pen.png" style="width:20px; height:20px; margin-bottom:0px; margin-left:10px;" /><a role="menuitem" tabindex="-1" href="#">수정</a></li>'
+											    	'<li class="reply-revise delete" data-rereply-idx=+reWriting.idx+ style="display:flex;" role="presentation"><img src="./resources/images/bin.png" style="width:20px; height:20px; margin-top:3px; margin-left:10px;" /><a role="menuitem" tabindex="-1" href="#">삭제</a></li>'								 
+											    '</ul>'			        				
+						        			'</div>'	 	        				
+					        			'</c:if>'			        			
		
					  
+					        		'</div>'
+					        		'<div class="reply-writing">'+reWriting.reReply_box_content+'</div>'
+					        		'<textarea class="sub third addReply" data-rereply-idx="${reWriting.idx}" rows="1" cols="88" placeholder="댓글추가.." style="display:none;"></textarea>'
+					        		'<div class="sub line-image" style="display: none;"></div>'
					        	
+					        		'<div class="reply-reactions">'
+								    	'<i class="far fa-thumbs-up third" data-thumbsup-idx='+reWriting.idx+'></i>'
+								    	'<span class="reply-thumbs-up-count">'+reWriting.reReply_recomm_count+'</span>'
+								    	'<i class="far fa-thumbs-down third" data-thumbsdown-idx='+reWriting.idx+'></i>'
+								    	'<span class="reply-thumbs-down-count">'+reWriting.reReply_decomm_count+'</span>'
+					       				'<span class="re-reply-button second" style="cursor:pointer;"><span><!-- 답글 --></span></span>'
+					       		    '</div>'
					       		    
					       		    
+					        		'<div class="sub reply-selections" style="display: none;">'
+					       				'<span class="sub cancel-button second"><span>취소</span></span>'
+					       				'<span class="sub reply-button second" data-rereply-idx='+reWriting.idx+'><span>저장</span></span>'		
+					       		    '</div>'			       		    
					       		    
					       		    
					       		    
					       		    
							       		    
+				  					'<div class="re-reply-area third" style="display: none;"> <!-- 답글추가 창 -->'
							        
+						       		    '<c:if test="${!empty loginUser}">'
+					 	 		   			'<img class="profile-image" style="width:40px; height:40px;" src="${loginUser.user_image}" />'
+						 		        '</c:if>'
					 		        	    
+							        	'<div class="re-reply-box" style="width: 89%;">'
+							        		'<textarea class="sub fourth addReply" rows="1" cols="88" placeholder="답글추가.."></textarea>'
+							        		'<div class="third line-image"></div>'
+							        		'<div class="reply-selections">'
+							       				'<span class="re cancel-button"><span>취소</span></span>'
+							       				'<span class="re reply-button" data-reply-idx='+reWriting.reReply_idx+'><span>답글</span></span>'
+							       		    '</div>'
+							       		'</div>'
							       		
+							        '</div>'	
					       		    
					       		    
					       		    
					       		    
+					       		'</div>'
					       		
					       		
					       		
					       		
+					        '</div>'						      
+						'</c:if>'						
					)//656줄 append			
				}) //655줄 each
		) //569줄 append */
	} //532줄 반복문		
	
	
	
       		    /*		        	
+        		'<c:forEach var="reWriting" items='+reReplyList+'>'
+	        			'<c:if test='+writing.reply_idx+ 'eq' +reWriting.reReply_idx+'>'
+				        	'<div class="reply-area third-result" style="margin-bottom: 0px;">'		        
+		 			        	'<img class="profile-image" src='+reWriting.user_image+ '/>'
+					        	'<div class="reply-box">'
+					        		'<div class="reply-box-top">'
+					        			'<div>'
+					        				'<span id="reply-id" style="font-size: 12px; font-weight:700;">'+reWriting.id+'</span>'
+					        				'<span style="color: #7B7D80; font-size: 11px;">'+reWriting.reReply_reg_date.substring(0,16)+'</span>'
+					        			'</div>'
					        			
+					        			'<c:if test="${empty loginUser}">'
+						        			'<div class="dropdown second" style="display: none;">'
+						        				'<i class=""fal fa-ellipsis-v fa-1x" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true" style="color:#000000;"></i>'
+												'<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">'
+											    	'<li class="empty" role="presentation"><a role="menuitem" tabindex="-1" href="#"></a></li>'
+											    '</ul>'			        				
+						        			'</div>'			        				
+					        			'</c:if>'
					        			
+					        		 	'<c:if test="${!empty loginUser && (loginUser.user_idx !=' +reWriting.reReply_user_idx+')}>'
+						        			'<div class="dropdown second" style="margin-top: 4px;">'
+						        				'<i class="fal fa-ellipsis-v fa-1x" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true" style="color:#000000;"></i>'
+													'<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">'
+											    	'<li class="report" style="display:flex; align-items:center;" role="presentation">'
+											    		'<img src="./resources/images/report.png" style="width:30px; height:30px; margin-bottom:0px; margin-left:10px;" />'
+											    		'<a role="menuitem" tabindex="-1" href="#">신고</a>'
+											    	'</li>'
+											    '</ul>'			        				
+						        			'</div>'			        				
+					        			'</c:if>'		        			
					        			
+					        			'<c:if test="${!empty loginUser && loginUser.user_idx ==' +reWriting.reReply_user_idx+'}>'				        			
+							    			'<div class="dropdown second" style="margin-top: 5px;">'
+						        				'<i class="fal fa-ellipsis-v fa-1x" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true" style="color:#000000;"></i>'
+													'<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">'
+											    	'<li class="reply-revise second" style="display:flex; align-items:center;" role="presentation"><img src="./resources/images/pen.png" style="width:20px; height:20px; margin-bottom:0px; margin-left:10px;" /><a role="menuitem" tabindex="-1" href="#">수정</a></li>'
+											    	'<li class="reply-revise delete" data-rereply-idx=+reWriting.idx+ style="display:flex;" role="presentation"><img src="./resources/images/bin.png" style="width:20px; height:20px; margin-top:3px; margin-left:10px;" /><a role="menuitem" tabindex="-1" href="#">삭제</a></li>'								 
+											    '</ul>'			        				
+						        			'</div>'	 	        				
+					        			'</c:if>'			        			
		
					  
+					        		'</div>'
+					        		'<div class="reply-writing">'+reWriting.reReply_box_content+'</div>'
+					        		'<textarea class="sub third addReply" data-rereply-idx="${reWriting.idx}" rows="1" cols="88" placeholder="댓글추가.." style="display:none;"></textarea>'
+					        		'<div class="sub line-image" style="display: none;"></div>'
					        	
+					        		'<div class="reply-reactions">'
+								    	'<i class="far fa-thumbs-up third" data-thumbsup-idx='+reWriting.idx+'></i>'
+								    	'<span class="reply-thumbs-up-count">'+reWriting.reReply_recomm_count+'</span>'
+								    	'<i class="far fa-thumbs-down third" data-thumbsdown-idx='+reWriting.idx+'></i>'
+								    	'<span class="reply-thumbs-down-count">'+reWriting.reReply_decomm_count+'</span>'
+					       				'<span class="re-reply-button second" style="cursor:pointer;"><span><!-- 답글 --></span></span>'
+					       		    '</div>'
					       		    
					       		    
+					        		'<div class="sub reply-selections" style="display: none;">'
+					       				'<span class="sub cancel-button second"><span>취소</span></span>'
+					       				'<span class="sub reply-button second" data-rereply-idx='+reWriting.idx+'><span>저장</span></span>'		
+					       		    '</div>'			       		    
					       		    
					       		    
					       		    
					       		    
							       		    
+				  					'<div class="re-reply-area third" style="display: none;"> <!-- 답글추가 창 -->'
							        
+						       		    '<c:if test="${!empty loginUser}">'
+					 	 		   			'<img class="profile-image" style="width:40px; height:40px;" src="${loginUser.user_image}" />'
+						 		        '</c:if>'
					 		        	    
+							        	'<div class="re-reply-box" style="width: 89%;">'
+							        		'<textarea class="sub fourth addReply" rows="1" cols="88" placeholder="답글추가.."></textarea>'
+							        		'<div class="third line-image"></div>'
+							        		'<div class="reply-selections">'
+							       				'<span class="re cancel-button"><span>취소</span></span>'
+							       				'<span class="re reply-button" data-reply-idx='+reWriting.reReply_idx+'><span>답글</span></span>'
+							       		    '</div>'
+							       		'</div>'
							       		
+							        '</div>'	
					       		    
					       		    
					       		    
					       		    
+					       		'</div>'
					       		
					       		
					       		
					       		
+					        '</div>'						      
+						'</c:if>'  	   	
+				'</c:forEach>' 
			       		    
       		    
       		    
       		    
+       		'</div>'					
		);	
		
					
		
	}	
*/	
	
	
	
	
	
	
	

			
/*		
	$.ajax({ //reply 데이터베이스에 들어가있는 모든 유저인덱스와 콘텐츠를 가져오기
		url: './boardDetail',
		type: 'get',
		data: {
			board_idx: board_idx,
			
		},
		success: function(res) {
		//console.log(res);	
		console.log(board_idx);	

		//location.href = './boardDetail?board_idx=' + board_idx;
			
		},
		error: function(error) {
			
		}
	});	
*/		
		
		
		
/*
	$.ajax({ 
		url: './getReReplyResult',
		type: 'get',
		data: {
			board_idx: board_idx,
			
		},
		//async: false, //비동기화 시켜주지 않기.즉 동기화. res값을 받을때까지 기다린후 밑으로 코드가 진행된다.
		success: function(res) {
		console.log(res);		
			
		},
		error: function(error) {
			
		}
	});
	*/	

			



	/*$.ajax({ //reply 데이터베이스에 들어가있는 모든 유저인덱스와 콘텐츠를 가져오기
		url: './getReply',
		type: 'get',
		data: {},
		success: function(res) {
			
			if(res === 'ok') {
				//location.href = './boardDetail?board_idx=' + board_idx;
			} 
			
		},
		error: function(error) {
			
		}
	});*/	
	
	
	
	
	$('.main.cancel-button').on('click', function() {
		if($('textarea.main.addReply').val().length !== 0) { //댓글창에 글자가 들어가 있으면
			console.log($('textarea.main.addReply').val());
			$('textarea.main.addReply').val(''); //댓글창 비워주기 
			console.log($('textarea.main.addReply').val());
		}
		$('.main.reply-selections').css('display', 'none');
		$('.main.line-image').css('border-color', '#dedede');				
	});
	
	
	
	$('.re.cancel-button').on('click', function() { //답글 취소버튼 클릭
		if($('textarea.sub.second.addReply').val().length !== 0) { //댓글창에 글자가 들어가 있으면
			console.log($('textarea.sub.second.addReply').val());
			$('textarea.sub.second.addReply').val(''); //댓글창 비워주기 
			console.log($('textarea.sub.second.addReply').val());
		}

	});
	
	

	
	
	/*$('.inc-btn').on('click',function(){
		var parent = $(this).closest('.reply-reactions');
		var el = parent.find('.reply-thumbs-up-count');
		
		var count = el.html();
		el.html(Number(count)+1);
		
		
	});*/
	
	$('i.far.fa-thumbs-up.sub').one('click', function() {
		if($('.loginUser').html() === '') {
			swal('로그인 해주세요.').then(function() {
				location.href = './login';
			});
			return 'ok';		
			//alert('로그인 해주세요');
			//location.href = './login';
			//return 'ok';
		}
		
		//var replyThumbsUpIdx = $('.far.fa-thumbs-up').data('thumbsup-idx');
		var replyThumbsUpIdx = $(this).data('thumbsup-idx');
		console.log(replyThumbsUpIdx);
		
		var replyThumbsUpCount = parseInt($(this).next().html()) + 1; 
		console.log(replyThumbsUpCount);
		//var replyThumbsUpCount = parseInt($('.reply-thumbs-up-count').html()) + 1;
		//console.log(replyThumbsUpCount);
		//$('.reply-thumbs-up-count').html(replyThumbsUpCount);
		$(this).next().html(replyThumbsUpCount);
		//var totalReplyThumbsUpCount = $('.reply-thumbs-up-count').html();
		var totalReplyThumbsUpCount = $(this).next().html();
		console.log(totalReplyThumbsUpCount);
		
		$.ajax({
			url: './updateReplyThumbsUp',
			type: 'get',
			data: {
				reply_idx: replyThumbsUpIdx,
				reply_recomm_count: totalReplyThumbsUpCount,
			},
			success: function(res) {
				console.log(res);			
			},
			error: function(error) {
				
			}
		}); 
		
		
	});
	
	
	
	
	
	
	$('i.far.fa-thumbs-up.third').one('click', function() {
		if($('.loginUser').html() === '') {
			swal('로그인 해주세요.').then(function() {
				location.href = './login';
			});			
			//alert('로그인 해주세요');
			//location.href = './login';
			return 'ok';
		}
		
		//var reReplyThumbsUpIdx = $('.far.fa-thumbs-up').data('thumbsup-idx');
		var reReplyThumbsUpIdx = $(this).data('thumbsup-idx');
		console.log(reReplyThumbsUpIdx);
		
		var reReplyThumbsUpCount = parseInt($(this).next().html()) + 1; 
		console.log(reReplyThumbsUpCount);
		//var reReplyThumbsUpCount = parseInt($('.reply-thumbs-up-count').html()) + 1;
		//console.log(reReplyThumbsUpCount);
		//$('.reply-thumbs-up-count').html(reReplyThumbsUpCount);
		$(this).next().html(reReplyThumbsUpCount);
		
		$.ajax({
			url: './updateReReplyThumbsUp',
			type: 'get',
			data: {
				idx: reReplyThumbsUpIdx,
				reReply_recomm_count: reReplyThumbsUpCount,
			},
			success: function(res) {
				console.log(res);			
			},
			error: function(error) {
				
			}
		}); 
		
		
	});

	
	
	
	
	
	
	
	$('i.far.fa-thumbs-down.sub').one('click', function() {
		if($('.loginUser').html() === '') {
			swal('로그인 해주세요.').then(function() {
				location.href = './login';
			});			
		    //alert('로그인 해주세요');
			//location.href = './login';
			return 'ok';
		}		
		
		//var replyThumbsUpIdx = $('.far.fa-thumbs-up').data('thumbsup-idx');
		var replyThumbsDownIdx = $(this).data('thumbsdown-idx');
		console.log(replyThumbsDownIdx);
		
		var replyThumbsDownCount = parseInt($(this).next().html()) + 1;
		console.log(replyThumbsDownCount);
		//var replyThumbsUpCount = parseInt($('.reply-thumbs-up-count').html()) + 1;
		//console.log(replyThumbsUpCount);
		//$('.reply-thumbs-up-count').html(replyThumbsUpCount);
        $(this).next().html(replyThumbsDownCount);
		//var totalReplyThumbsUpCount = $('.reply-thumbs-up-count').html();
		var totalReplyThumbsDownCount = $(this).next().html();
		console.log(totalReplyThumbsDownCount);
		
		
		$.ajax({
			url: './updateReplyThumbsDown',
			type: 'get',
			data: {
				reply_idx: replyThumbsDownIdx,
				reply_decomm_count: totalReplyThumbsDownCount,
			},
			success: function(res) {
				console.log(res);			
			},
			error: function(error) {
				
			}
		}); 
		
		
	});
	
	
	
	
	
	$('i.far.fa-thumbs-down.third').one('click', function() {
		if($('.loginUser').html() === '') {
			swal('로그인 해주세요.').then(function() {
				location.href = './login';
			});			
			//alert('로그인 해주세요');
			//location.href = './login';
			return 'ok';
		}
		
		//var reReplyThumbsDownIdx = $('.far.fa-thumbs-down').data('thumbsdown-idx');
		var reReplyThumbsDownIdx = $(this).data('thumbsdown-idx');
		console.log(reReplyThumbsDownIdx);
		
		var reReplyThumbsDownCount = parseInt($(this).next().html()) + 1; 
		console.log(reReplyThumbsDownCount);
		//var reReplyThumbsDownCount = parseInt($('.reply-thumbs-down-count').html()) + 1;
		//console.log(reReplyThumbsDownCount);
		//$('.reply-thumbs-down-count').html(reReplyThumbsDownCount);
		$(this).next().html(reReplyThumbsDownCount);
		
		$.ajax({
			url: './updateReReplyThumbsDown',
			type: 'get',
			data: {
				idx: reReplyThumbsDownIdx,
				reReply_decomm_count: reReplyThumbsDownCount,
			},
			success: function(res) {
				console.log(res);			
			},
			error: function(error) {
				
			}
		}); 
		
		
	});
	
	

	$('.reply-box-top').hover(function() { //댓글 옵션기능 hoover 적용 
			var pointing = $(this).find('.fal');
			pointing.css('display', '');
		}, function() {
			$('.fal').css('display', 'none');
		});
	
	

	
	
	$('.reply-revise.first').on('click', function() {

		var parent = $(this).closest('.reply-area.sub-result');
		var replyWriting = parent.find('.reply-first-writing');
		var replyBoxContent = replyWriting.html(); //댓글창 글자 가져와서 저장
		replyBoxContent=replyBoxContent.replaceAll('<br>','\n');
		console.log(replyWriting.html());
		console.log(replyWriting.text());
		
		replyWriting.css('display', 'none'); //댓글창 댓글 없애기
		var textareaSubAddReply = parent.find('textarea.sub.addReply.first');		
		textareaSubAddReply.css('display', '');  //수정할 새 댓글창 생기게 하기
		textareaSubAddReply.text(replyBoxContent); //수정할 새 댓글창에 저장했던 글자 넣기
		textareaSubAddReply.height(1).height(textareaSubAddReply.prop('scrollHeight')-10); //textarea 높이 자동조절
		textareaSubAddReply.focus();
		var subLineImage = parent.find('.sub.line-image.first'); //수정할 새 댓글창의 언더라인
		subLineImage.css('display', ''); //수정할 새 댓글창의 언더라인 보이게 하기
		subLineImage.css('border-color', 'black'); //수정할 새 댓글창의 언더라인 진하게표시 
		
		
		var replyReactions = parent.find('.reply-reactions.first')
		replyReactions.css('display', 'none');
		var subReplySelections = parent.find('.sub.reply-selections.first');
		subReplySelections.css('display', '');
		
		var dropdown = parent.find('.dropdown.first');
		dropdown.css('display', 'none');
		/*$(document).click(function(e){ 
	    	if (!$(e.target).is(textareaSubAddReply)) { 
	    		subLineImage.css('border-color', '#dedede');
	   	 }
		});*/
		
		var subCancelButton = parent.find('.sub.cancel-button.first');
		subCancelButton.on('click', function() { //댓글수정 취소 버튼
			replyWriting.css('display', '');
			textareaSubAddReply.css('display', 'none');
			subLineImage.css('display', 'none');
			replyReactions.css('display', '');
			subReplySelections.css('display', 'none');
			dropdown.css('display', '');
			
			textareaSubAddReply.val(replyBoxContent); //기존의 콘텐츠로 유지 
		});	
		
			
					
		
		
		var subReplyButton = parent.find('.sub.reply-button.first');
		subReplyButton.on('click', function() {
			console.log(textareaSubAddReply.val());
			//console.log(textareaSubAddReply.text());
			//console.log(textareaSubAddReply.html());
			if(textareaSubAddReply.val().replace(/\s| /gi, "").length == 0) {
			    swal('내용을 입력해 주세요.').then(function() {
					subLineImage.css('border-color', 'black'); //먹히지 않는 이유를 모르겠다...
					textareaSubAddReply.focus();						
				});
				//alert('내용을 입력해 주세요');
				//subLineImage.css('border-color', 'black'); //먹히지 않는 이유를 모르겠다...
				//textareaSubAddReply.focus();
				console.log(subLineImage);
				return;
			} else {
				var reply_idx = $(this).data('reply-idx'); 
				console.log(reply_idx);
				var reply_box_content = textareaSubAddReply.val();
				console.log(reply_box_content);	
							
				$.ajax({
					url: './updateReplyContent',
					type: 'get',
					data: {
						reply_idx: reply_idx,
						reply_box_content: replaceBrTag(reply_box_content),
					},
					success: function(res) {
						console.log(res);
						location.href = './boardDetail?board_idx=' + board_idx;			
					},
					error: function(error) {
						
					}
				})				
			}
			
			
		});
		
		
		textareaSubAddReply.on('click', function() {
			subLineImage.css('display', '');
			subLineImage.css('border-color', 'black');
		});					
				
				
						
		$('.re-reply-area').css('display', 'none');
		$('.sub.cancel-button').on('click', function() {
			$('textarea.sub.second.addReply').css('display', '');
			$('.second.line-image').css('display', '');
			
		});
		
		
		$('textarea.sub.second.addReply').on('click', function() {
			$('.sub.line-image').css('display', 'none');
		});
		
		
	});
	
	
	
	
	
	
	
	
	
		$('.reply-revise.second').on('click', function() {

		var parent = $(this).closest('.reply-area.third-result');
		var replyWriting = parent.find('.reply-writing');
		var replyBoxContent = replyWriting.html(); 
		replyBoxContent=replyBoxContent.replaceAll('<br>','\n');
		console.log(replyWriting.html());
		console.log(replyWriting.text());
		
		replyWriting.css('display', 'none');
		var textareaSubAddReply = parent.find('textarea.sub.third.addReply');		
		textareaSubAddReply.css('display', ''); 
		textareaSubAddReply.focus();
		textareaSubAddReply.text(replyBoxContent);
		textareaSubAddReply.height(1).height(textareaSubAddReply.prop('scrollHeight')-10);
		//textareaSubAddReply.focus();
		var subLineImage = parent.find('.sub.line-image');
		subLineImage.css('display', '');
		subLineImage.css('border-color', 'black');
		
		//parent.find('textarea.sub.third.addReply');
		
		
		var replyReactions = parent.find('.reply-reactions')
		replyReactions.css('display', 'none');
		var subReplySelections = parent.find('.sub.reply-selections');
		subReplySelections.css('display', '');
		
		var dropdown = parent.find('.dropdown.second');
		dropdown.css('display', 'none');
		/*$(document).click(function(e){ 
	    	if (!$(e.target).is(textareaSubAddReply)) { 
	    		subLineImage.css('border-color', '#dedede');
	   	 }
		});*/
		
		
		var subCancelButton = parent.find('.sub.cancel-button.second');
		subCancelButton.on('click', function() { //댓글수정 취소 버튼
			replyWriting.css('display', '');
			textareaSubAddReply.css('display', 'none');
			subLineImage.css('display', 'none');
			replyReactions.css('display', '');
			subReplySelections.css('display', 'none');
			dropdown.css('display', '');
			
			textareaSubAddReply.val(replyBoxContent); //기존의 콘텐츠로 유지 
		});		
		
		
		var subCancelButton = parent.find('.sub.cancel-button');
		subCancelButton.on('click', function() {
			replyWriting.css('display', '');
			textareaSubAddReply.css('display', 'none');
			subLineImage.css('display', 'none');
			replyReactions.css('display', '');
			subReplySelections.css('display', 'none');
			dropdown.css('display', '');
		});	
		
		
		/*var subReplyButtonFirst = parent.find('.sub.reply-button.first');
		subReplyButtonFirst.on('click', function() {
			console.log(textareaSubAddReply.val());
			//console.log(textareaSubAddReply.text());
			//console.log(textareaSubAddReply.html());
			if(textareaSubAddReply.val().replace(/\s| /gi, "").length == 0) {
				alert('내용을 입력해 주세요');
			}
			
			//ajax로 db에 업뎃시켜주기 
			var reply_idx = $(this).data('reply-idx'); 
			console.log(reply_idx);
			var reply_box_content = textareaSubAddReply.val();
			console.log(reply_box_content);
			
			$.ajax({
				url: './updateReplyContent',
				type: 'get',
				data: {
					reply_idx: reply_idx,
					reply_box_content: replaceBrTag(reply_box_content),
				},
				success: function(res) {
					console.log(res);
					location.href = './boardDetail?board_idx=' + board_idx;			
				},
				error: function(error) {
					
				}
			});
		
			
		});*/
		
		
		
	$('.sub.reply-button.second').on('click', function() { //저장버튼 클릭
			console.log($(this).data('rereply-idx'));
			var replyArea = $(this).closest('.reply-area.third-result');
			var textAreaThird = replyArea.find('textarea.sub.addReply.third');
			console.log(textAreaThird);
			console.log(textAreaThird.val());
			//console.log(textareaSubAddReplyThird.text());
			//console.log(textareaSubAddReplyThird.html());
			if(textAreaThird.val().replace(/\s| /gi, "").length == 0) {
				swal('내용을 입력해 주세요.').then(function() {					
					textAreaThird.focus();
					//replyArea.find('.sub.line-image').css('border-line', 'black'); //안먹힌다;;
				});
				//alert('내용을 입력해 주세요');	
				//textAreaThird.focus();
				return;
			} else {
				//ajax로 db에 업뎃시켜주기 
				var reReply_idx = textAreaThird.data('rereply-idx');
				console.log(reReply_idx);
				var reReply_box_content = textAreaThird.val();
				console.log(reReply_box_content);
				
				$.ajax({
					url: './updateReReplyContent',
					type: 'get',
					data: {
						idx: reReply_idx,
						reReply_box_content: replaceBrTag(reReply_box_content),
					},
					success: function(res) {
						console.log(res);
						location.href = './boardDetail?board_idx=' + board_idx;			
					},
					error: function(error) {
						
					}
				});
						
			}
			
			
		});		
		
		
		
		
		
		
		textareaSubAddReply.on('click', function() {
			subLineImage.css('display', '');
			subLineImage.css('border-color', 'black');
		});							
				
				
						
		$('.re-reply-area').css('display', 'none');
		$('.sub.cancel-button').on('click', function() {
			$('textarea.sub.second.addReply').css('display', '');
			$('.second.line-image').css('display', '');
		});
		
		$('textarea.sub.second.addReply').on('click', function() {
			$('.sub.line-image').css('display', 'none');
		});
		
	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	$('.reply-delete').on('click', function() {
		var reply_idx = $(this).data('reply-idx');
		console.log(reply_idx);
		
		swal("댓글을 정말 삭제하시겠습니까?", {
		  buttons: {cancel: "취소", 네: true},
		}).then(function(value) {
			switch(value) {
				case "네":
				$.ajax({
					url: './replyDelete',
					type: 'get',
					data: {
						reply_idx: reply_idx,
					},
					success: function(res) {
						console.log(res);
						swal('댓글이 삭제 되었습니다.').then(function() {
							location.replace('./boardDetail?board_idx=' + board_idx);
						});
						//alert('댓글이 삭제 되었습니다');
						//location.replace('./boardDetail?board_idx=' + board_idx);			
					},
					error: function(error) {
						
					}
				});					
			}
		});			
		
	/*	var replyDelete = confirm('댓글을 정말 삭제하시겠습니까?');	 //yes면 코드가 계속 진행됨 
		if(replyDelete) {
			$.ajax({
				url: './replyDelete',
				type: 'get',
				data: {
					reply_idx: reply_idx,
				},
				success: function(res) {
					console.log(res);
					alert('댓글이 삭제 되었습니다');
					location.replace('./boardDetail?board_idx=' + board_idx);			
				},
				error: function(error) {
					
				}
			});
					
		} */
	});
	
	
	
	
	$('.reply-revise.delete').on('click', function() {
		swal("댓글을 정말 삭제하시겠습니까?", {
		  buttons: {cancel: "취소", 네: true},
		}).then(function(value) {
			switch(value) {
				case "네":
			$.ajax({
				url: './reReReplyDelete',
				type: 'get',
				data: {
					idx: idx,
				},
				success: function(res) {
					console.log(res);
					alert('댓글이 삭제 되었습니다');
					location.replace('./boardDetail?board_idx=' + board_idx);			
				},
				error: function(error) {
					
				}
			});				
			}
		});	
		
		var idx = $(this).data('rereply-idx');
		console.log(idx);				
	/*	var reReplyDelete = confirm('댓글을 정말 삭제하시겠습니까?');	 //yes면 코드가 계속 진행됨 
		
		if(reReplyDelete) {
			$.ajax({
				url: './reReReplyDelete',
				type: 'get',
				data: {
					idx: idx,
				},
				success: function(res) {
					console.log(res);
					alert('댓글이 삭제 되었습니다');
					location.replace('./boardDetail?board_idx=' + board_idx);			
				},
				error: function(error) {
					
				}
			});
					
		}*/
	});	
	
	
	
	
	
	
	
	$('textarea.sub.addReply').on('click', function() {
		var subAddReplyParent = $(this).closest('.re-reply-area');
		var subLineImage = subAddReplyParent.find('.sub.line-image');
		
		subLineImage.css('border-color', 'black');
		
		var subTextareaBox = subAddReplyParent.find('textarea.sub.addReply');	
		/*$(document).click(function(e){ 
	    	if (!$(e.target).is(subTextareaBox)) { 
				subLineImage.css('border-color', '#dedede');
	   	 }

		});*/		
		
	});		
	
	
	
	
	
	$('.addReply').on('focusout',function(){
		$('.line-image').css('border-color','#dedede');
	});		
	

	
	$('.addReply').on('focus',function(){
		$('.line-image').css('border-color','#dedede');
		var parent = $(this).closest('.re-reply-box');
		var borderHtml = parent.find('.line-image');
		borderHtml.css('border-color','black');
	});
			
		
	
	
	
	$('.re-reply-button.first').on('click', function() { //만약 on대신 one을 사용하면 이벤트함수 한번만 적용
		//alert('click!');	
		$(this).closest('reply-area.sub-result').find('second.line-image.first').css('border-color','#dedede');		
		//$('.line-image').css('border-color','#dedede');
			
		if($('.loginUser').html() === '') { //로그인 되어있지 않다면
		 	swal('로그인 해주세요.').then(function() {
				location.href = './login';
		});
			//alert('로그인 해주세요');
			//location.href = './login';
		} else { //로그인 되어 있으면
			var reParent = $(this).closest('.reply-area.sub-result');
			console.log(reParent);
			var reEl = reParent.find('.re-reply-area.first');
			reEl.css('display', '');			
			var secondLineImage = reParent.find('.second.line-image.first');
			console.log(secondLineImage);
			secondLineImage.css('border-color', 'black');
			
			
			var reReplyTextarea = reParent.find('textarea.sub.second.addReply');
			console.log(reReplyTextarea);
			reReplyTextarea.focus(); //input이나 textarea에 포커스 주기 
			//reReplyTextarea.blur(); //포커스를 벗어나는 항수는 blur()
			
			var secondLineImage = reParent.find('.second.line-image.first');	
			console.log(secondLineImage);
				
			/*$(document).click(function(e){ 
		    	if (!$(e.target).is(reReplyTextarea)) { 
		    		secondLineImage.css('border-color', '#dedede');                  
		   	 	}
			});	*/
						
			
			/*var reParent = $(this).closest('.reply-area.sub-result');
			console.log(reParent);
			var reEl = reParent.find('.reply-reactions');
			console.log(reEl);
			
			reEl.after(
				
	    			'<div class="re-reply-area">' +
			        
		       		    '<c:if test="${!empty loginUser}">' +
	 	 		   			'<img class="profile-image" src="${loginUser.user_image}" />' +
		 		        '</c:if>' +
	 		        	    
			        	'<div class="re-reply-box">' +
			        		'<textarea class="addReply" rows="1" cols="79" placeholder="답글추가.."></textarea>' +
			        		'<div class="line-image"></div>' +
			        		'<div class="reply-selections">' +
			       				'<span class="re cancel-button"><span>취소</span></span>' +
			       				'<span class="re reply-button"><span>댓글</span></span>	' +
			       		    '</div>' +
			       		'</div>' +
			       		
			        '</div>'			
				
			);*/
			$('.re.cancel-button').on('click', function() { //취소버튼 누르면 댓글창 전부 사라지게하기	
				var cancelParent = $(this).closest('.re-reply-area.first');
				var subSecondAddReply = cancelParent.find('.sub.second.addReply');
				console.log(subSecondAddReply);
				subSecondAddReply.val(''); //textarea는 html()이 아닌 val()로 해야 텍스트에 접근 가능함
				cancelParent.css('display', 'none');
				
			});	
		}
		
		

	});
	
	
	
	
	
	
	
	$('.re-reply-button.second').on('click', function() { //만약 on대신 one을 사용하면 이벤트함수 한번만 적용
		//alert('click!');
		//var parent = $(this).closest('reply-area.third-result');
		//var child = parent.find('.third.line-image');
		//child.css('border-color','#dedede');	
		$('third.line-image').css('border-color','black');

		
			
		if($('.loginUser').html() === '') { //로그인 되어있지 않다면 
			swal('로그인 해주세요.').then(function() {
				location.href = './login';
			});		
			//alert('로그인 해주세요');
			//location.href = './login';
		} else { //로그인 되어 있으면
			var reParent = $(this).closest('.reply-area.third-result');
			console.log(reParent);
			var reEl = reParent.find('.re-reply-area.third');
			reEl.css('display', '');			
			var thirdLineImage = reParent.find('.third.line-image');
			console.log(thirdLineImage);
			thirdLineImage.css('border-color', 'black');
			
			var reReplyTextarea = reParent.find('textarea.sub.fourth.addReply');
			console.log(reReplyTextarea);
			reReplyTextarea.focus();					
		
				
			$('.re.cancel-button').on('click', function() { //취소버튼 누르면 댓글창 전부 사라지게하기	
				var cancelParent = $(this).closest('.re-reply-area.third');
				var subThirdAddReply = cancelParent.find('.sub.fourth.addReply');
				console.log(subThirdAddReply);
				subThirdAddReply.val(''); //textarea는 html()이 아닌 val()로 해야 텍스트에 접근 가능함
				console.log(subThirdAddReply.val(''));
				cancelParent.css('display', 'none');
				thirdLineImage.css('border-color', '#dedede');
			});	
			
		}
		

	});


	
	
	

	
	
	
	$('.re.reply-button').on('click', function() { //댓글버튼
		var parent = $(this).closest('.reply-area.sub-result');
		console.log(parent);
		var el = parent.find('textarea.sub.second.addReply');
		console.log(el);
		var replyIdx = $(this).data('reply-idx');
		console.log(replyIdx);
		
		if(el.val().replace(/\s| /gi, "").length == 0) {
			alert('내용을 입력해 주세요');
		} else { //내용이 들어가 있다면
			var reReplyBoxContent = el.val();
			console.log(reReplyBoxContent); //작성내용
			console.log(userIdx); //로그인한 댓글작성자 고유 인덱스 번호
			console.log(replyWritingIdx); //자유게시판의 글작성 번호
			unix = Math.floor(new Date().getTime() / 1000) - unix;
			
			
			
			$.ajax({ //데이터베이스에 유저인덱스와 콘텐츠를 넣어주기 
				url: './insertReReply',
				type: 'get',
				data: {
					userIdx: userIdx,
					replyIdx: replyIdx,
					replyWritingIdx: replyWritingIdx, //해당 페이지 글의 board_idx 
					replyBoxContent: replaceBrTag(reReplyBoxContent),
					unix: unix,
				},
				success: function(res) {
					console.log(res); //ok
								
					location.href = './boardDetail?board_idx=' + board_idx;
				},
				error: function(error) {
					
				}				
			});
			
			
						
		}
				
	});
	
	
	$('textarea').on('keyup', function() {
		//$(this).css('height', 'auto');
		//$(this).height(this.scrollHeight);
		$(this).height(1).height($(this).prop('scrollHeight')-10);
	});
	
	
	
	$('.report').on('click', function() {
		if($('.loginUser').html() === '') {
			swal('로그인 해주세요.').then(function() {
				location.href = "./login";
			});
			//alert('로그인 해주세요');
			//location.href = "./login";
		} else {
			swal("정말 신고 하시겠습니까?", {
			  buttons: {cancel: "취소", 네: true},
			}).then(function(value) {
				switch(value) {
					case "네":
				    	swal("신고되었습니다!");
				        break;										
				}
			});			
		}
		
	/*	else if(confirm('해당 글을 신고하시겠습니까?')) {
			alert('신고되었습니다!!');
		}*/
	});
	

	
});






function replaceBrTag(str){
            if (str == undefined || str == null)
            {
                return "";
            }

            str = str.replace(/\r\n/ig, '<br>');
            str = str.replace(/\\n/ig, '<br>');
            str = str.replace(/\n/ig, '<br>');
			
            return str;
        }
