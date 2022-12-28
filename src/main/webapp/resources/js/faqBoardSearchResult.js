$(document).ready(function() {
	/*
	var searchContent = $('input#search-box').val(); 
		console.log(searchContent);
	$.ajax({
		url: './faqBoardSearchResultList', //해당 게시물의 조회수 업데이트
		type: 'get',
		async: false, //비동기화 시켜주지 않기.즉 동기화. res값을 받을때까지 기다린후 밑으로 코드가 진행된다.		
		data: {
			word: searchContent,
		},
		success: function(res) {
			console.log(res); //searchList
			searchList = res		
		},
		error: function(error) {
			
		}
	});	
	
	var searchList;
	console.log(searchList);
	
	for(let i=0; i<searchList.length; i++) {
	    let unix = searchList[i].unix;
	    unix = Math.round(new Date().getTime() / 1000) - unix; 

		if(unix < 60) {
			searchList[i].unix = unix + '초 전';
		} else if(unix < 3600) {
			unix = Math.round(unix / 60);
			searchList[i].unix = unix + '분 전';
		} else if(unix < 86400) {
			unix = Math.round(unix / 3600);
			searchList[i].unix = unix + '시간 전';
		} else if(unix < 2592000) {
			unix = Math.round(unix / 86400);
			searchList[i].unix = unix + '일 전';
		} else if(unix < 31536000) {
			unix = Math.round(unix / 2592000);
			searchList[i].unix = unix + '달 전';
		} else if(unix < 3153600000) {
			unix = Math.round(unix / 31536000);
			searchList[i].unix = unix + '년 전';
		} else if(unix < 31536000000) {
			unix = (unix / 315360000).toFix(1); //소수점 첫째자리 까지만 보여주고 만약 둘째자리가 5이상이면 반올림해주는 함수
			searchList[i].unix = unix + '백년 전';
		}

	    //faqList[i].unix = unix;

		if(searchList[i].id === '관리자') {
			$('.board-row').append(		
					'<div class="col-md-1 top-header-add first"><span style="color:red;">공지</span></div>' 
		+      		'<div class="col-md-6 top-header-add"><span style="font-weight:700;" class="click-detail" data-board-index='+searchList[i].board_idx+'>'+searchList[i].title+'</span></div>'
		+			'<div class="col-md-2 top-header-add"><span style="font-weight:700;">'+searchList[i].id+'</span></div>'
		+			'<div class="col-md-1 top-header-add"><span class="click-count" data-index='+faqList[i].board_idx+'>'+searchList[i].click_count+'</span></div>'
		+			'<div class="col-md-1 top-header-add"><span>'+searchList[i].recomm_count+'</span></div>'
		+			'<div class="col-md-1 top-header-add"><span class="unix" style="font-size: 10px; font-weight: 500;">'+searchList[i].unix+'</span></div>'
			);			
		} else if(searchList[i].id !== '관리자') {
			console.log(searchList[i].id);
			console.log(searchList[i].board_idx);
			$('.board-row1').append(		
					'<div class="col-md-1 top-header-add first"><span>'+searchList[i].board_idx+'</span></div>' 
		+      		'<div class="col-md-6 top-header-add"><span class="click-detail" data-board-index='+searchList[i].board_idx+'>'+searchList[i].title+'</span></div>'
		+			'<div class="col-md-2 top-header-add"><span>'+searchList[i].id+'</span></div>'
		+			'<div class="col-md-1 top-header-add"><span class="click-count" data-index='+searchList[i].board_idx+'>'+searchList[i].click_count+'</span></div>'
		+			'<div class="col-md-1 top-header-add"><span>'+searchList[i].recomm_count+'</span></div>'
		+			'<div class="col-md-1 top-header-add"><span class="unix" style="font-size: 10px; font-weight: 500;">'+searchList[i].unix+'</span></div>'
			);			
		}
				
	}
	*/
	
	
	
	
	
	
	$('.click-detail').on('click', function() { //faq게시판의 게시물 제목 클릭시
	
		var idx = $(this).data('board-index'); //클릭한 제목의 인덱스 넘버 
		//console.log(idx);
		
		$.ajax({
			url: './updateFaqBoardClickCount', //해당 게시물의 조회수 업데이트
			type: 'get',
			data: {
				board_idx: idx,
			},
			success: function(res) {
				//console.log(res); //ok
				
			},
			error: function(error) {
				
			}
		});
		
			
		location.href = './faqBoardDetail?board_idx=' + idx;
			
	});
	
	
	
	
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
					swal('검색어를 입력해 주세요').then(function() {
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
	
	
	
	
	$('.writing-button').on('click', function() { //글쓰기 버튼 클릭시
		//location.href = './boardWrite';
		console.log('!!!!!!!!!!!');
		var content = $('input#test').val();
		if(content === '') { //null로 하면 안됨
			alert('로그인 해주세요');
			location.href = './login';
		} else {
			location.href = './faqWrite';
		} 
	});	
	
	
		
	
});