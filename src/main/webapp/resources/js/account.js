$(document).ready(function() {
/*
const firebaseConfig = { //파이어베이스 komap-image
  apiKey: "AIzaSyAradkd8UwakjGctaRp0a7UpupcWH14JvE",
  authDomain: "komap-image-70853.firebaseapp.com",
  projectId: "komap-image-70853",
  storageBucket: "komap-image-70853.appspot.com",
  messagingSenderId: "276222561884",
  appId: "1:276222561884:web:bbe3cb0fdae0b1f14e2099",
  measurementId: "G-R28C79P0VM"
};*/
const firebaseConfig = { //파이어베이스 komap
  apiKey: "AIzaSyDF5vuLqNMYwWPxof6YO-JkBjmYscjga4g",
  authDomain: "komap-335012.firebaseapp.com",
  projectId: "komap-335012",
  storageBucket: "komap-335012.appspot.com",
  messagingSenderId: "1026064464164",
  appId: "1:1026064464164:web:ce60aecbfadcf3b376dbb1",
  measurementId: "G-YQE4MEKDMY"
};

firebase.initializeApp(firebaseConfig);

 // Get a reference to the storage service, which is used to create references in your storage bucket
 var storage = firebase.storage();

	var user_idx = $('#userIdx').html();
	//console.log(user_idx);
	//var user_image = $('#userImage').attr('src');  //현재 view에 나오는 프로필 이미지
	//console.log(user_image);
	
	$('img#user-img').on('click', function() {
		$('#img-file').trigger('click');
	});

	$('#img-file').on('change', function() {  //Choose File
		var file = $(this)[0].files[0]; //선택한 이미지 파일
		console.log(file);  
		var date =  Date.now();
		
		var storageRef = storage.ref().child('file-' + date); //선택한 이미지 파일에 랜덤이름을 생성해서 파이어베이스 스토리지의 자식에 해당하는 곳에 지정해주기
		storageRef.put(file).then(function(snapshot) {  //지정되어진 파이어베이스의 스토리지에 이미지 파일을 업로드 
 		console.log('Uploaded a blob or file!');;
					
		storageRef.getDownloadURL().then(function(url) { //파이어베이스의 스토리지에 업로드되어진 이미지 파일의 주소를 가져오기
			console.log(url); //firebase로 부터 가져온 이미지 주소
			
			//여기서부터 ajax 구현
			$('#img-upload-button').on('click', function() { //이미지 업로드 버튼 클릭
				//console.log('click!');
				$.ajax({  //ajax 실행
					url: './updateUserImage',
					type: 'get',
					data: {
						user_idx: user_idx,
						user_image: url,
					},
					success: function(res) {
						console.log(res);
						swal('프로필 사진이 업데이트 되었습니다.').then(function() {
							location.href='./updateUserImage?user_idx='+user_idx+'&user_image='+url;
						});						
						//alert('프로필 사진이 업데이트 되었습니다');
						//location.href='./updateUserImage?user_idx='+user_idx+'&user_image='+url;

					}
				})
			});
			
			$('#img-upload-button').trigger('click');
		})		
		
	 });
		
  });	



var id = $('#userId').html();
var email = $('#userEmail').html();

$('#withdrawal').on('click', function() {
	
	swal({
	  title: "정말 탈퇴하시겠습니까?",
	  text: "탈퇴시 작성하신 모든 기록과 내용들이 영구히 삭제되며 절대 되돌릴수 없을수 있습니다!",
	  icon: "warning",
	  buttons: true,
	  dangerMode: true,
	})
	
	.then((willDelete) => {	
	  if (willDelete) {
		$.ajax({ //계정삭제
			url: './accountDelete',
			type: 'get',
			data:{
				id: id,
				email: email,
			},
			success: function(res) {
			//console.log(res);
		    //alert('탈퇴되었습니다');	
				$.ajax({ //로그아웃
					url: './logout',
					type: 'get',
					data: {},
					success: function(res) {
						console.log(res);
					    swal("탈퇴되었습니다!", {
					      icon: "success",
					    }).then(function() {
						location.replace('./');
					});
						//location.replace('./');
					},
					error: function(err) {
					
					}		
				});		
			},			
			error: function(error) {
			
			}
			
		});		
		
	    swal("탈퇴되었습니다!", {
	      icon: "success",
	    });
	  } else {
	    //swal("Your imaginary file is safe!");
	  }
	});
			
				
/*	if(confirm('정말 탈퇴하시겠습니까? 탈퇴시 작성한 모든 기록과 내용들이 영구히 삭제되며 절대 되돌릴수 없습니다')) {
		
		$.ajax({ //계정삭제
			url: './accountDelete',
			type: 'get',
			data:{
				id: id,
				email: email,
			},
			success: function(res) {
			//console.log(res);
			alert('탈퇴되었습니다');
			
			$.ajax({ //로그아웃
				url: './logout',
				type: 'get',
				data: {},
				success: function(res) {
					console.log(res);
					location.replace('./');
				},
				error: function(err) {
				
				}		
			})
			

					
			},
			error: function(error) {
			
			}
		});
		
		} */
	

	});
	

			
});