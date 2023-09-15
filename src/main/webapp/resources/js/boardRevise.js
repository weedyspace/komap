$(document).ready(function() {
	
	$('.writing-button-up').on('click', function() {
		
		var title = $('.writing-title-box').val();
		var content = $('.summernote').val();
		
		var board_idx = getUrlParameter('board_idx');
		
		
		//console.log(title);
		//console.log(content);
		//console.log(board_idx);
		
		if(title.length >= 1 && content.length >= 1) {
			$.ajax({
			url: './boardWriteButtonRevised',
			type: 'get',
			data: {
				title: title,
				content: content,
				board_idx: board_idx,
			},
			success: function(res) {
				//alert(res)
				if(res === "ok") {
					swal('작성되었습니다.').then(function() {
						location.href = './board?start=0';
					});
					//alert('작성되었습니다');
					//location.href = './board?start=0'; 		
				}
			},
			error: function(error) {
				
			}
		});
		} else if (title.length === 0 && content.length >= 1) {
			swal('제목을 입력 해주세요.');
			//alert('제목을 입력 해주세요');
		} else if (title.length >= 1 && content.length === 0 ) {
			swal('내용을 입력 해주세요.');
			//alert('내용을 입력 해주세요');
		} else {
			swal('제목과 내용을 입력 해주세요.');
			//alert('제목과 내용을 입력 해주세요');
		}
	});
	
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