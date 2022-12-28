$(document).ready(function() {
	
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
	
	for(let i=0; i<boardList.length; i++) {
	    let unix = boardList[i].unix;
	    unix = Math.round(new Date().getTime() / 1000) - unix; 

		if(unix < 60) {
			boardList[i].unix = unix + '초 전';
		} else if(unix < 3600) {
			unix = Math.round(unix / 60);
			boardList[i].unix = unix + '분 전';
		} else if(unix < 86400) {
			unix = Math.round(unix / 3600);
			boardList[i].unix = unix + '시간 전';
		} else if(unix < 2592000) {
			unix = Math.round(unix / 86400);
			boardList[i].unix = unix + '일 전';
		} else if(unix < 31536000) {
			unix = Math.round(unix / 2592000);
			boardList[i].unix = unix + '달 전';
		} else if(unix < 3153600000) {
			unix = Math.round(unix / 31536000);
			boardList[i].unix = unix + '년 전';
		} else if(unix < 31536000000) {
			unix = (unix / 315360000).toFix(1); //소수점 첫째자리 까지만 보여주고 만약 둘째자리가 5이상이면 반올림해주는 함수
			boardList[i].unix = unix + '백년 전';
		}

	    //boardList[i].unix = unix;

		$('.board-row').append(
			'<div class="col-md-1 top-header-add first"><span>'+boardList[i].board_idx+'</span></div>' 
	+       '<div class="col-md-6 top-header-add"><span class="click-detail" data-board-index='+boardList[i].board_idx+'>'+boardList[i].title+'</span></div>'
	+		'<div class="col-md-2 top-header-add"><span>'+boardList[i].id+'</span></div>'
	+		'<div class="col-md-1 top-header-add"><span class="click-count" data-index='+boardList[i].board_idx+'>'+boardList[i].click_count+'</span></div>'
	+		'<div class="col-md-1 top-header-add"><span>'+boardList[i].recomm_count+'</span></div>'
    +		'<div class="col-md-1 top-header-add"><span class="unix" style="font-size: 10px; font-weight: 500;">'+boardList[i].unix+'</span></div>'
		);
	}
	
	console.log(boardList); 
	


//	$(document).on('click','#write-btn1',function(){
//		alert('123');
//	});
	$('.writing-button').on('click', function() { //글쓰기 버튼 클릭시
		//location.href = './boardWrite';
		var content = $('input#test').val();
		if(content === '') { //null로 하면 안됨
			swal('로그인 해주세요.').then(function() {
				location.href = './login';
			});
			//alert('로그인 해주세요');
			//location.href = './login';
		} else {
			location.href = './boardWrite';
		} 
	});
	
	
	
	
	$('.click-detail').on('click', function() { //자유게시판의 게시물 제목 클릭시
	
		var idx = $(this).data('board-index'); //클릭한 제목의 인덱스 넘버 
		
		$.ajax({
			url: './updateBoardClickCount', //해당 게시물의 조회수 업데이트
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
		
			
		location.href = './boardDetail?board_idx=' + idx;
			
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
					swal('검색어를 입력해 주세요.').then(function() {
						$('#search-box').focus();
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
	
	
	
	
	//url 파라미터의 값 가져오는 방법
	
	var _visiblePage = 5;
	var _countPerOnePage = 10;
	var nowStart = getUrlParameter('start'); //url의 파라미터값을 가져온다
	console.log(nowStart); 
	
	$.ajax({
		url:'./getBoardCount',
		type:'get',
		data:{},
		success:function(boardTotalCount){
			console.log(boardTotalCount);
			$('#pagination-demo').twbsPagination({
				initiateStartPageClick: false, 
				startPage : (nowStart/_countPerOnePage) + 1, // 0/10+1=1, 10/10+1=2, 20/10+1=3
		        totalPages: Math.ceil(boardTotalCount/ _countPerOnePage), 
		        visiblePages: _visiblePage,
 		        onPageClick: function (event, page) { 
		            //console.log(page);
					var newStart =(page-1)*_countPerOnePage;
					location.href='./board?start=' + newStart;
		        }
		     });
		},
		erorr:function(err){
			
		}
	})
	
	
});












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