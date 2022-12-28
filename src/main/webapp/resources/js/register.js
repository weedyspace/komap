$(document).ready(function() { //jquery방식

	$('#idCheck').on('click', function() { //아이디 확인 버튼 클릭시
		let id = $('input#id').val().trim(); //trim()함수를 사용시 앞뒤 공백을 모두 제거해준다
		console.log(id);
		if(id === '') { 
			swal('아이디를 입력해 주세요.').then(function() {
				$('#id.cm-inp').focus();
			});
			//alert('아이디를 입력해 주세요');
			return;
		} 
		
		$.ajax({
			url: './selectId',
			type: 'get',
			data: {
				id: id, 
			},

			success: function(res) { 
				console.log(res); // selectUserById
				
				if(res === '') {
					swal('사용가능한 아이디 입니다.');
					//alert('사용가능한 아이디 입니다');
				} else if(res.id === id) { //입력한 아이디가 이미 있다면
					swal('이미 존재하는 아이디 입니다.').then(function() {
						$('#id.cm-inp').focus();
					});
					//alert('이미 존재하는 아이디 입니다');
				}

			},
			error: function(error) {
				
			}
		});			
	});
	
	
	$('#emailCheck').on('click', function() { //아이디 확인 버튼 클릭시
		let email = $('input#email').val();
		console.log(email);
		if(email === '' || !email.includes('@') ) {
			swal('이메일을 입력해 주세요.').then(function() {
				$('#email.cm-inp').focus();
			});
			//alert('이메일을 입력해 주세요');
			return;
		}
		if(email.includes(' ')) {
			swal('이메일에는 공백을 포함하지 않습니다.').then(function() {
				$('#email.cm-inp').focus();
			});
			//alert('이메일에는 공백을 포함하지 않습니다');
			return;
		} 		
		
		$.ajax({
			url: './selectEmail',
			type: 'get',
			data: {
				email: email,
			},

			success: function(res) { 
				console.log(res); // selectUserByEmail
				
				if(res === '') { //입력한 이메일이 비어 있다
					swal('사용가능한 이메일주소 입니다.');
					//alert('사용가능한 이메일주소 입니다');
				} else if(res.email === email) {
					swal('이미 존재하는 이메일 입니다.').then(function() {
						$('#email.cm-inp').focus();
					});
					//alert('이미 존재하는 이메일 입니다');
				} 

			},
			error: function(error) {
				
			}
		});			
	});	
	

	$('#reg-btn').on('click', function() {
		
		var id = $('#id').val();
		var email = $('#email').val();
		var password = $('#password').val();
		var password1 = $('#password1').val();
		var user_image = 'https://firebasestorage.googleapis.com/v0/b/komap-335012.appspot.com/o/file-1659928121956?alt=media&token=422eada4-a9b0-48b5-88f7-2ab644c4bb58'
		
		//console.log(id+email+password+password1);
		
		if(id === '') { 
			swal('아이디를 입력해 주세요.').then(function() {
				$('#id.cm-inp').focus();
			});		
			//alert('아이디를 입력해 주세요');
		} else if(email === '' || !email.includes('@')) {
			swal('이메일을 입력해 주세요.').then(function() {
				$('#email.cm-inp').focus();
			});
		} else if(password !== password1 || password.length < 6) {
			swal('6자리 이상의 동일한 비밀번호를 입력해 주세요');
			//alert('6자리 이상의 동일한 비밀번호를 입력해 주세요');
		} else { //비번동일 && 비번6자리 이상   //else if(password === password1 && password.length >= 6)
			//아래는 서버에 요청하는 ajax함수
		/*if((password !== password1) || password.length > 6) {
				alert('6자리 이상의 동일한 비밀번호를 입력해 주세요');
				return 'ok';
		}*/
			$.ajax({
				url: './addUser',
				type: 'get',
				data: {
					id: id,
					email: email,
					password: password,
					password1: password1,
					user_image: user_image,
				},
				async: false, //비동기화 시켜주지 않기.즉 동기화. res값을 받을때까지 기다린후 밑으로 코드가 진행된다.

				success: function(res) { //
					console.log(res); //
					console.log(res.email);
					if(res.email === email) { //입력한 이메일이 이미 있다면
						alert('이미 존재하는 이메일주소 입니다');
					} else if (res.id === id) {
					    alert('이미 존재하는 아이디 입니다');
					} else if(res === '') {		
						$.ajax({
							url: './insertUser',
							type: 'get',
							data: {
								id: id,
								email: email,
								password: password,
								password1: password1,
								user_image: user_image,
							},
							success: function(res) { //
								console.log(res); //
								
								if(res === 'ok') {
									swal("가입을 환영합니다!", "").then(function() {
										location.replace('./login');	
									});
									//alert('가입을 환영합니다');
									//location.href = './';  //이 코드 사용시 웹상에서 뒤로가기를 하면 회원등록 페이지로 다시 이동하게 된다. 그래서 바로 아래 코드로 대체시켜준다 
									//location.replace('./login');									
								}							
				
							},
							error: function(error) {
								
							}
						});										
		
					} 
	
				},
				error: function(error) {
					
				}
			});		
			
			
		}

 		
		 
		
	});
	
	
	$('.cm-inp').on('keydown', function(key) { //키보드 버튼이 눌러질때 실행되는 jquery함수
		if(key.keyCode === 13) {  //13은 키보드상의 엔터키를 의미함
			//alert('enter');
			$('#reg-btn').trigger('click');  //강제 클릭이벤트 발생 함수. 3-30줄까지 복붙하면 되지만 비효율적이기 때문에 한줄 작성으로 로그인 버튼을 누른것과 동일한 효과를 대신 줬다. 
		}
	});
	
	
	$('.login-txt, .fas.fa-chevron-right').on('click', function() {
		location.href = './login';
	});
	
	//$('.fas.fa-chevron-right').on('click', function() {
	//	location.href = './login';
	//});
	
}); 