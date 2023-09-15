$(document).ready(function() {
	
	$('.click-detail').on('click', function() { //자유게시판의 게시물 제목 클릭시
	
		var idx = $(this).data('board-index'); //클릭한 제목의 인덱스 넘버 
		
		$.ajax({
			url: './updateBoardClickCount', //해당 게시물의 조회수 업데이트
			type: 'get',
			data: {
				board_idx: idx,
			},
			success: function(res) {
				console.log(res); //ok
				
			},
			error: function(error) {
				
			}
		});
		
			
		location.href = './boardDetail?board_idx=' + idx;
			
	});
		
	
	
	$('.writing-button').on('click', function() { //글쓰기 버튼 클릭시
		//location.href = './boardWrite';
		var content = $('input#test').val();
		if(content === '') { //null로 하면 안됨
			alert('로그인 해주세요');
			location.href = './login';
		} else {
			location.href = './boardWrite';
		} 
	});
	


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
					swal('검색어를 입력해 주세요').then(function() {
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
	
	
	
});