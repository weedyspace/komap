$(document).ready(function() {
	
	$('.writing-button-up').on('click', function() {
		var htmlStr = $('.summernote').summernote('code');
		console.log(htmlStr);
		
		var title = $('.writing-title-box').val();
		var content = $('.summernote').val();
		var unix = Math.round(new Date().getTime() / 1000); //1970년1월1일0시 기준 현재까지 초시간 
		console.log(unix);
		//var pic = $('.note-control-selection').val();
		//console.log(pic);
		//console.log(title);
		//console.log(content);
		
		if(title.length >= 1 && content.length >= 1) {
			$.ajax({
				url: './boardWriteButton',
				type: 'get',
				data: {
					title: title,
					content: content,
					unix: unix,
				},
				success: function(res) {
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