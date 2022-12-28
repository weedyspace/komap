$(document).ready(function() { //jquery방식

	firebase.initializeApp(firebaseConfig); //파이어베이스 연동에서 초기세팅을 위한 함수코드. 한번만 실행됨

	$('#reg-btn').on('click', function() {
		var email = $('.cm-inp.email').val();
		var password = $('.cm-inp.password').val();
		
		if(email === '' || !email.includes('@') ) {
			swal('이메일을 입력하세요.').then(function() {
				$('.cm-inp.email').focus();
			});
		} else if(password === '') {
			swal('비밀번호를 입력하세요.').then(function() {
				$('.cm-inp.password').focus();
			});
		} else { //이메일 비번 모두 입력시
			$.ajax({
				url: './checkWhenLogin',
				type: 'get',
				data: {
					email: email,
					password: password,
				},
				success: function(res) {
					console.log(res);
					 if(res === '') { //로그인 실패(이 경우 res에 해당하는 resultUser의 값이 null이 아닌 비워있는 String 값을 들고온다)											
						swal('이메일 혹은 비밀번호를 잘못 입력하셨습니다.');			
						//alert('올바른 정보를 입력하세요');
					} else { //로그인 성공
						//alert(res);
						//alert(res.user_image);
						swal(res.id + ' 님 환영합니다.').then(function() {
							location.replace('./');
						});
						//alert(res.id + ' 님 환영합니다');
					    //location.replace('./');
					}
				},
				error: function(error) {
					
				}
			}); 			
		}
		//console.log(email+password);
		
			//아래는 서버에 요청하는 ajax함수
	/*	$.ajax({
			url: './checkWhenLogin',
			type: 'get',
			data: {
				email: email,
				password: password,
			},
			success: function(res) {
				console.log(res);
				 if(res === '') { //로그인 실패(이 경우 res에 해당하는 resultUser의 값이 null이 아닌 비워있는 String 값을 들고온다)
					swal('올바른 정보를 입력하세요');			
					//alert('올바른 정보를 입력하세요');
				} else { //로그인 성공-- 즉, res값이 'ok' 가 됨 
					//alert(res);
					//alert(res.user_image);
					alert(res.id + ' 님 환영합니다');
				    location.replace('./');
				}
			},
			error: function(error) {
				
			}
		});  */
	});
	
		
	$('.cm-inp').on('keydown', function(key) { //키보드 버튼이 눌러질때 실행되는 jquery함수
		if(key.keyCode === 13) {  //13은 키보드상의 엔터키를 의미함
			//alert('enter');
			$('#reg-btn').trigger('click');  //강제 클릭이벤트 발생 함수. 3-30줄까지 복붙하면 되지만 비효율적이기 때문에 한줄 작성으로 로그인 버튼을 누른것과 동일한 효과를 대신 줬다. 
		}
	});
	
	
	$('#login-reg-btn').on('click', function() {
		location.href = './register';
	});
	
    //구글로그인
	$('#my-google-login-btn').on('click', function() {
		
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
			$.ajax({
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
						//alert('new');
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
							},
							error: function(error) {
								
							}
						});						
					} else{ //res의 값이 ok가 아닌 다른값일 경우(이경우 dup): 즉, 이미 해당 아이디와 이메일로 가입되어져 있는 경우  
						console.log(res);
						//alert('already');
						$.ajax({ //아이디와 이메일로 로그인
							url: './googleLogin',
							type: 'get',
							data: {
								id: user.displayName,
								email: user.email,
							},
							success: function(res) {
								console.log(res);  //dup
							    swal(user.displayName + '님 환영합니다!').then(function() {
									location.href = './';
								});
								//alert(user.displayName + '님 환영합니다');
								//location.href = './';
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

	});
	
}); 