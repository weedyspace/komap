$(document).ready(function() {

	$('.one-menu').on('click', function() {
		//location.href = './'; 
		var page = $(this).data('page');
		
		if (page === 'home') {
			location.href = './';
		} else if (page === 'intro') {
			location.href = './intro';
		} else if (page === 'board') {
			location.href = './board?start=0';			
		} else if (page === 'faq') {
			location.href = './faq?start=0';
		} else if (page === 'register') {
			location.href = './register';
		} else if (page === 'login') {
			location.href = './login';
		} else if (page === 'logout') {
		
		swal("정말 로그아웃 하시겠습니까?", {
		  buttons: {cancel: "취소", 네: true},
		}).then(function(value) {
			switch(value) {
				case "네":
				$.ajax({
					url: './logout',
					type: 'get',
					data: {},
					success: function(res) {
						if(res === 'ok') {
							location.replace('./login');
						}
					},
					error: function(err) {
						
					}		
				});					
			}
		});	
/*		swal("정말 로그아웃 하시겠습니까?", {
		  buttons: {cancel: "취소", defeat: true},
		}).then((value) => {
			switch(value) {
				case "defeat":
				$.ajax({
					url: './logout',
					type: 'get',
					data: {},
					success: function(res) {
						if(res === 'ok') {
							location.replace('./login');
						}
					},
					error: function(err) {
						
					}		
				});					
			}
			
		});		*/	
		
			
		//var con = confirm('정말 로그아웃 하시겠습니까?');
		if(con) {
			$.ajax({
				url: './logout',
				type: 'get',
				data: {},
				success: function(res) {
					if(res === 'ok') {
						location.replace('./login');
					}
				},
				error: function(err) {
					
				}		
			});	
		}	
		} else if (page === 'account') {
			var user_idx = $('#hiddenUserIdx').val();
			console.log(user_idx);
			//location.href = 'account';
			location.href = './account?user_idx='+user_idx;
		}
	 	
	});
});