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
	
	
	
	$('.far.fa-search').on('click', function() { //search 이미지
		var searchContent = $('input#search-box').val();
		//console.log(searchContent);
		
		$.ajax({
			url: './faqBoardSearchResult',
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
				} else {location.href = './faqBoardSearchResult?&word=' + searchContent};
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
			url: './faqCountThumbsUp',
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
		
		console.log('board-idx: '+board_idx);
		console.log('decomm_count: '+decomm_count);
		
		$.ajax({
			url: './faqCountThumbsDown',
			type: 'get',
			data: {
				board_idx: board_idx,
				decomm_count: decomm_count,
			},
			success: function(res) {
				console.log(res);
				
			},
			error: function(error) {
				
			}
		});
		
	});	
	
	
//여기 아래 구현부분 중에서 굳이 url의 파라미터 값인 board_idx의 값을 가져오지 않아도 됨. 왜냐하면 윗줄에서 이미 board_idx를 먼저 가져와서 전역변수로 지정해놨기 때문에 그냥 써도 됨   	
	$('#revise').on('click', function() {
		//alert('revise!');
		var board_idx = getUrlParameter('board_idx');
		console.log(board_idx);
		
		location.href='./faqBoardRevise?&board_idx=' + board_idx ;
	});
	
	
	
	$('#delete').on('click', function() {
		//alert('delete!');	
		swal("해당글을 정말 삭제하시겠습니까?", {
		  buttons: {cancel: "취소", 네: true},
		}).then(function(value) {
			switch(value) {
				case "네":
				$.ajax({
					url: './faqBoardDeleteButton',
					type: 'get',
					data: {
						board_idx: board_idx,
					},
					success: function(res) {
						if(res === 'ok') {
							swal('삭제되었습니다.').then(function() {
								location.replace('./faq?start=0');
							});
						}
					},
					error: function(err) {
						
					}		
				});					
			}
		});
				
/*	    if(confirm('해당글을 정말 삭제하시겠습니까?')) {
			location.href='./faqBoardDeleteButton?&board_idx=' + board_idx;
			alert('삭제되었습니다');
			location.replace('./faq?start=0');
		} else {
			location.href='./faqBoardDetail?&board_idx=' + board_idx;
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
	
	$('.main.reply-button').on('click', function() { //가장 상단의 뎃글 버튼 클릭
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
								location.href = './faqBoardDetail?board_idx=' + board_idx;
							},
							error: function(error) {
								
							}
						});						
					} else{ //res의 값이 ok가 아닌 다른값일 경우(이경우 dup을 리턴받음): 즉, 이미 해당 아이디와 이메일로 가입되어져 있는 경우  
						console.log(res); //dup
						$.ajax({ //아이디와 이메일로 로그인
							url: './googleLogin',
							type: 'get',
							data: {
								id: user.displayName,
								email: user.email,
							},
							success: function(res) {
								console.log(res); //resultUser = {유저인덱스, 아이디, 이메일과 같은 해당 유저정보들}  
								alert(user.displayName + ' 님 환영합니다!');
								location.href = './faqBoardDetail?board_idx=' + board_idx;
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
				$('textarea.main.addReply').focus();				
			});
			//alert('내용을 입력해 주세요!'); 
			//var a = $(this).closest('.reply-box').find('.main.line-image') //먹히지가 않는다...
			//a.css('border-color', 'red');
			//$('textarea.main.addReply').focus(); 
			return;						
			} else { //로그인되어져있고 정규표현식도 모두 충족했을시 textarea의 내용을 db에 업로드
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

			$.ajax({ //데이터베이스에 유저인덱스와 콘텐츠를 넣어주기 
				url: './uploadFaqReply',
				type: 'get',
				data: {
					userIdx: userIdx,
					replyWritingIdx: replyWritingIdx, //해당 페이지 글의 board_idx 
					replyBoxContent: replaceBrTag(replyBoxContent),
				},
				success: function(res) {
					console.log(res); //ok
								
					location.href = './faqBoardDetail?board_idx=' + board_idx;
				},
				error: function(error) {
					
				}				
			});
			
			
		} 
		
	});
	
	
	

		

	$.ajax({ //faqReply 데이터베이스에 들어가있는 모든 유저인덱스와 콘텐츠를 가져오기
		url: './faqBoardDetail',
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
		
		



	/*$.ajax({ 
		url: './getFaqReReplyResult',
		type: 'get',
		data: {
			board_idx: board_idx,
			
		},
		success: function(res) {
		console.log(res);		
			
		},
		error: function(error) {
			
		}
	});	*/	
		
		

			



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
			url: './updateFaqReplyThumbsUp',
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
			return 'ok'			
			//alert('로그인 해주세요');
			//location.href = './login';
			//return 'ok';
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
			url: './updateFaqReReplyThumbsUp',
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
			return 'ok'			
		    //alert('로그인 해주세요');
			//location.href = './login';
			//return 'ok';
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
			url: './updateFaqReplyThumbsDown',
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
			return 'ok'			
			//alert('로그인 해주세요');
			//location.href = './login';
			//return 'ok';
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
			url: './updateFaqReReplyThumbsDown',
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
				//subLineImage.css('border-color', 'black'); //먹히지 않는 이유를 모르겠다...
				swal('내용을 입력해 주세요').then(function() {
					textareaSubAddReply.focus();
					subLineImage.css('border-color', 'black'); //이 함수 안에서는 이 코드가 먹힌다!!
				});
				//alert('내용을 입력해 주세요');
				//textareaSubAddReply.focus();
				console.log(subLineImage);
				return;
			} else {
				var reply_idx = $(this).data('reply-idx'); 
				console.log(reply_idx);
				var reply_box_content = textareaSubAddReply.val();
				console.log(reply_box_content);	
							
				$.ajax({
					url: './updateFaqReplyContent',
					type: 'get',
					data: {
						reply_idx: reply_idx,
						reply_box_content: replaceBrTag(reply_box_content),
					},
					success: function(res) {
						console.log(res);
						location.href = './faqBoardDetail?board_idx=' + board_idx;			
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
				swal('내용을 입력해 주세요!').then(function() {
					textAreaThird.focus();
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
					url: './updateFaqReReplyContent',
					type: 'get',
					data: {
						idx: reReply_idx,
						reReply_box_content: replaceBrTag(reReply_box_content),
					},
					success: function(res) {
						console.log(res);
						location.href = './faqBoardDetail?board_idx=' + board_idx;			
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

		swal("해당 댓글을 정말 삭제하시겠습니까?", {
		  buttons: {cancel: "취소", 네: true},
		}).then(function(value) {
			switch(value) {
				case "네":
				$.ajax({
					url: './faqReplyDelete',
					type: 'get',
					data: {
						reply_idx: reply_idx,
					},
					success: function(res) {
						console.log(res);
						swal('해당 댓글이 삭제 되었습니다!').then(function() {
							location.replace('./faqBoardDetail?board_idx=' + board_idx);
						});
						//alert('댓글이 삭제 되었습니다');
						//location.replace('./faqBoardDetail?board_idx=' + board_idx);			
					},
					error: function(error) {
						
					}
				});					
			}
		});			
	/*	var replyDelete = confirm('댓글을 정말 삭제하시겠습니까?');	 //yes면 코드가 계속 진행됨
		
		if(replyDelete) {
			$.ajax({
				url: './faqReplyDelete',
				type: 'get',
				data: {
					reply_idx: reply_idx,
				},
				success: function(res) {
					console.log(res);
					alert('댓글이 삭제 되었습니다');
					location.replace('./faqBoardDetail?board_idx=' + board_idx);			
				},
				error: function(error) {
					
				}
			});
					
		} */
	});
	
	
	
	
	$('.reply-revise.delete').on('click', function() {
		var idx = $(this).data('rereply-idx');
		console.log(idx);
		
		swal("해당 댓글을 정말 삭제하시겠습니까?", {
		  buttons: {cancel: "취소", 네: true},
		}).then(function(value) {
			switch(value) {
				case "네":
				$.ajax({
					url: './faqReReReplyDelete',
					type: 'get',
					data: {
						idx: idx,
					},
					success: function(res) {
						console.log(res);
						swal('해당 댓글이 삭제 되었습니다!').then(function() {
							location.replace('./faqBoardDetail?board_idx=' + board_idx);
						});
						//alert('댓글이 삭제 되었습니다');
						//location.replace('./faqBoardDetail?board_idx=' + board_idx);			
					},
					error: function(error) {
						
					}
				});					
			}
		});			
		
	/*	var reReplyDelete = confirm('댓글을 정말 삭제하시겠습니까?');	 //yes면 코드가 계속 진행됨 
		if(reReplyDelete) {
			$.ajax({
				url: './faqReReReplyDelete',
				type: 'get',
				data: {
					idx: idx,
				},
				success: function(res) {
					console.log(res);
					alert('댓글이 삭제 되었습니다');
					location.replace('./faqBoardDetail?board_idx=' + board_idx);			
				},
				error: function(error) {
					
				}
			});
					
		} */
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
			swal('내용을 입력해 주세요.').then(function() {
				$('textarea.sub.second.addReply').focus();
			});
			//alert('내용을 입력해 주세요');
		} else { //내용이 들어가 있다면
			var reReplyBoxContent = el.val();
			console.log(reReplyBoxContent); //작성내용
			console.log(userIdx); //로그인한 댓글작성자 고유 인덱스 번호
			console.log(replyWritingIdx); //자유게시판의 글작성 번호
			
			
			
			$.ajax({ //데이터베이스에 유저인덱스와 콘텐츠를 넣어주기 
				url: './insertFaqReReply',
				type: 'get',
				data: {
					userIdx: userIdx,
					replyIdx: replyIdx,
					replyWritingIdx: replyWritingIdx, //해당 페이지 글의 board_idx 
					replyBoxContent: replaceBrTag(reReplyBoxContent),
				},
				success: function(res) {
					console.log(res); //ok
								
					location.href = './faqBoardDetail?board_idx=' + board_idx;
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
	
	
	

	/*
	$('.report').on('click', function() {
		if($('.loginUser').html() === '') {
			alert('로그인 해주세요');
			location.href = "./login";
		} else if(confirm('해당 글을 신고하시겠습니까?')) {
			alert('신고되었습니다!!');
		}
	});
	*/

	
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
