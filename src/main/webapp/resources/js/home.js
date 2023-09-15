$(function(){
    var license = ""; 
    var phone = "";
    
	//휴대폰 번호 인증var code2 = "";
	$("#phoneChk").click(function(){
	    alert('인증번호신청');
	    license = $("#licenseNumber").val();
	    phone = $("#phoneNumber").val();
	    console.log(phone);
	    $.ajax({
	        type:"GET", 
	        url:"./pilot_login_verification_process", // controller 위치
	        data: {
	        	LicenseNumber: license,
	        	PhoneNumber: phone,
	        }, // 전송할 ㅔ이터값
	        success: function(res){
	        	console.log(res);
	            if(res == 101){ //실패시 
	                alert("라이센스 번호 및 휴대폰 번호가 올바르지 않습니다.")
	            } else {            //성공시        
	            	alert("휴대폰 전송이  됨.")
	                var res = Math.floor(Math.random() * 9000) + 1000; // 1000부터 9999까지의 4자리 난수 생성
	                code2 = res; // 성공하면 데이터저장
	                console.log(code2);
	                
	                //4자리숫자인 code2를 해당 전화번호 문자메시지로 날려주기
//	        	    $.ajax({
//	        	        type: "GET", 
//	        	        url: "http://biz.moashot.com/EXT/URLASP/mssendUTF.asp", 
//	        	        data: {
//	        	        	uid: "luckycookie7",
//	        	        	pwd: "1a1b1c1d!",
//	        	        	sendType: 3,
//	        	        	toNumber: phone,
//	        	        	fromNumber: 01037495369,
//	        	        	content: code2 + "를 입력해주세요",
//	        	        }, 
//	        	        success: function(res){
//	        	        	console.log(res);
//	        	           
//	        	        }      
//	        	    });	                
	                
	            }
	        }      
	    });
	    
	});
		 
		 
	//4자리숫자인 code2를 해당 전화번호 문자메시지로 날려주기 
		
		
		 
	//휴대폰 인증번호 대조
	$("#phoneChk2").click(function(){
		if($("#phone2").val() == code2){ // 위에서 저장한값을 비교함
			alert('인증성공')
			
			//ajax 로그인 인증로직 작성
		    $.ajax({
		        type:"GET", 
		        url:"./pilot_login", // controller 위치
		        data: {
		        	LicenseNumber: license,
		        	PhoneNumber: phone,
		        }, // 전송할 ㅔ이터값
		        success: function(res){
		        	console.log(res);  // "ok"	 
		        }      
		    });
			
		}else{
			alert('인증실패')
		}
	});	
	
});