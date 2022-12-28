$(document).ready(function() {
	
	//var tata;
	//console.log(tata)
	
	$.ajax({
		url: './get_address_information',
		type: 'get',
		data: {
			
		},
		async: false, //비동기화 시켜주지 않기.즉 동기화. res값을 받을때까지 기다린후 밑으로 코드가 진행된다.
		success: function(res) {
			console.log(res);
			tata = res;

			//$.each(res, function(index, item) {
				
			//});			
		},
		error: function(error) {
			
		},
	});
	
	
	var tata;
	console.log(tata);
	
	
	var list = [];
	
	$.each(tata, function(i, item){ //item: list안에 들어있는 각각의 객체, i: 해당 객체의 인덱스 번호
		let map = {
			'lat':item.lat,
			'lng':item.lng
		}
		//console.log(item);
		//console.log(i);
		list.push(map);
	});
	console.log('====list===');
	console.log(list);

	
/*	$(tata).map(function(i, position) { // $.each(tata, function(i, item) {}); 과 동일한 코드 로직임. 선택사항
		console.log(position);
		console.log(i);
	}); */

 	

		

	/*var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	    mapOption = { 
	        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
	        level: 3 // 지도의 확대 레벨
	    };*/

    var map = new kakao.maps.Map(document.getElementById('map'), { // 지도를 표시할 div
        center : new kakao.maps.LatLng(36.2683, 127.6358), // 지도의 중심좌표
        level : 13 // 지도의 확대 레벨
    });




    // 마커 클러스터러를 생성합니다
    // 마커 클러스터러를 생성할 때 disableClickZoom 값을 true로 지정하지 않은 경우
    // 클러스터 마커를 클릭했을 때 클러스터 객체가 포함하는 마커들이 모두 잘 보이도록 지도의 레벨과 영역을 변경합니다
    // 이 예제에서는 disableClickZoom 값을 true로 설정하여 기본 클릭 동작을 막고
    // 클러스터 마커를 클릭했을 때 클릭된 클러스터 마커의 위치를 기준으로 지도를 1레벨씩 확대합니다
    var clusterer = new kakao.maps.MarkerClusterer({
        map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
        averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
        minLevel: 1, // 클러스터 할 최소 지도 레벨
        disableClickZoom: true // 클러스터 마커를 클릭했을 때 지도가 확대되지 않도록 설정한다
    });

	var markers = $(list).map(function(i, position) {
            return new kakao.maps.Marker({
                position : new kakao.maps.LatLng(position.lat, position.lng)
            });
        });

	console.log(markers);
	console.log(markers[0].n);
	
	// 클러스터러에 마커들을 추가합니다
	clusterer.addMarkers(markers);
	//클러스터를 만들기 위해 필요한 최소 마커 개수를 설정한다.
	clusterer.setMinClusterSize(1); 












	

    // 데이터를 가져오기 위해 jQuery를 사용합니다
    // 데이터를 가져와 마커를 생성하고 클러스터러 객체에 넘겨줍니다
   /* $.get("https://apis.map.kakao.com/download/web/data/chicken.json", function(data) {
        // 데이터에서 좌표 값을 가지고 마커를 표시합니다
        // 마커 클러스터러로 관리할 마커 객체는 생성할 때 지도 객체를 설정하지 않습니다
        var markers = $(data.positions).map(function(i, position) {
            return new kakao.maps.Marker({
                position : new kakao.maps.LatLng(position.lat, position.lng)
            });
        });

        // 클러스터러에 마커들을 추가합니다
        clusterer.addMarkers(markers);
    });*/

    // 마커 클러스터러에 클릭이벤트를 등록합니다
    // 마커 클러스터러를 생성할 때 disableClickZoom을 true로 설정하지 않은 경우
    // 이벤트 헨들러로 cluster 객체가 넘어오지 않을 수도 있습니다


   kakao.maps.event.addListener(clusterer, 'clusterclick', function(cluster) {
        // 현재 지도 레벨에서 1레벨 확대한 레벨
        var level = map.getLevel()-1;

        // 지도를 클릭된 클러스터의 마커의 위치를 기준으로 확대합니다
        map.setLevel(level, {anchor: cluster.getCenter()});
 


 




    });



/*'<li><label><input type="checkbox" id="btc" name="coin" value="btc">'+ '<img class="coinImage" src="./resources/images/BTC.svg" style="width:20px; height:20px; margin-left:4px;"/>' + ' Bitcoin</label>'+ '</li>' +
'<li><label><input type="checkbox" id="bch" name="coin" value="bch">'+ '<img class="coinImage" src="./resources/images/BCH.svg" style="width:20px; height:20px; margin-left:4px;"/>' + ' Bitcoin cash</label>'+ '</li>' +
'<li><label><input type="checkbox" id="eth" name="coin" value="eth">'+ '<img class="coinImage" src="./resources/images/ETH.svg" style="width:20px; height:20px; margin-left:3px;"/>' + '  Ethereum</label>'+ '</li>' +		
'<li><label><input type="checkbox" id="ada" name="coin" value="ada">'+ '  <img class="coinImage" src="./resources/images/ADA.svg" style="width:20px; height:20px;" margin-left:3px;/>' + '  Cardano</label>'+ '</li>' + 
'<li><label><input type="checkbox" id="mana" name="coin" value="mana">'+ ' <img class="coinImage" src="./resources/images/MANA.svg" style="width:18px; height:18px; margin-left:3px;"/>' + ' Decentraland</label>'+ '</li>' +
'<li><label><input type="checkbox" id="doge" name="coin" value="doge">'+ ' <img class="coinImage" src="./resources/images/DOGE.svg" style="width:18px; height:18px; margin-left:3px;"/>' + '  Dogecoin</label>'+ '</li>' +		
'<li><label><input type="checkbox" id="usdt" name="coin" value="usdt">'+ ' <img class="coinImage" src="./resources/images/USDT.svg" style="width:19px; height:19px; margin-left:3px;"/>' + '  USDT</label>'+ '</li>' +	
<span>' +decentraland+ '</span> <span>' +dogecoin+ '</span> <span>' +usdt+ '</span>*/

	$(tata).map(function(i, position) { //position: tata리스트안에 들어있는 각각의 객체, i: 해당 객체의 인덱스번호(0부터 시작)
		
		var marker = new kakao.maps.Marker({
		    map: map, 
		    position: new kakao.maps.LatLng(position.lat, position.lng)
		});

		console.log(i); //해당 객체의 인덱스번호(0부터 시작)
		console.log(position); //tata리스트안에 들어있는 각각의 객체
		var bitcoin;
		var bitcoin_cash;
		var ethereum;
		var cardano;
		var decentraland;
		var dogecoin;
		var usdt;
		
		
		
	    unix = Math.round(new Date().getTime() / 1000) - position.unix; 
		console.log(unix);

		if(unix < 60) {
			position.unix = unix + '초 전';
			console.log(position.unix);
		} else if(unix < 3600) {
			unix = Math.round(unix / 60);
			position.unix = unix + '분 전';
			console.log(position.unix);
		} else if(unix < 86400) {
			unix = Math.round(unix / 3600);
			position.unix = unix + '시간 전';
			console.log(position.unix);
		} else if(unix < 2592000) {
			unix = Math.round(unix / 86400);
			position.unix = unix + '일 전';
			console.log(position.unix);
		} else if(unix < 31536000) {
			unix = Math.round(unix / 2592000);
			position.unix = unix + '달 전';
		} else if(unix < 3153600000) {
			unix = Math.round(unix / 31536000);
			position.unix = unix + '년 전';
		} else if(unix < 31536000000) {
			unix = (unix / 315360000).toFix(1); //소수점 첫째자리 까지만 보여주고 만약 둘째자리가 5이상이면 반올림해주는 함수
			position.unix = unix + '백년 전';
		}
     	
		  
		
		
		//받아온 암호암폐기호를 이미지로 변환시키기			        
		if(position.bitcoin == 'btc') {
			bitcoin = '<i class="fa-brands fa-bitcoin" style="color:orange; font-size:18.5px; margin-bottom:-6.5px; margin-right:2px;"></i>'
			//bitcoin = (position.bitcoin).replace('btc', '<img class="coinImage" src="./resources/images/BTC.svg" style="width:18px; height:18px;/>');
		} else {
			bitcoin = '';
		}
		
		if(position.bitcoin_cash == 'bch') {
			bitcoin_cash='<img class="coinImage" src="./resources/images/BCH.svg" style="width:18px; height:18px;"/>';
			//bitcoin_cash = (position.bitcoin_cash).replace('bch', '<img class="coinImage" src="./resources/images/BCH.svg" style="width:18px; height:18px;"/>');
		} else {		
			bitcoin_cash = '';
		}
		
		if(position.ethereum == 'eth') {
			ethereum = '<img class="coinImage" src="./resources/images/ETH.svg" style="width:18px; height:18px;"/>'
			//ethereum = (position.ethereum).replace('eth', '<img class="coinImage" src="./resources/images/ETH.svg" style="width:18px; height:18px;"/>');
		} else {
			ethereum = '';
		}
		
		if(position.cardano == 'ada') {
			cardano = '<img class="coinImage" src="./resources/images/ADA.svg" style="width:18px; height:18px;"/>'
			//cardano = (position.cardano).replace('ada', '<img class="coinImage" src="./resources/images/ADA.svg" style="width:18px; height:18px;"/>');	
		} else {
			cardano = '';
		}
		
		if(position.decentraland == 'mana') {
			decentraland = '<img class="coinImage" src="./resources/images/MANA.svg" style="width:16px; height:16px;"/>'
			//decentraland = (position.decentraland).replace('mana', '<img class="coinImage" src="./resources/images/MANA.svg" style="width:16px; height:16px;"/>');
		} else {
			decentraland = '';
		}
		
		if(position.dogecoin == 'doge') {
			dogecoin = '<img class="coinImage" src="./resources/images/DOGE.svg" style="width:15px; height:15px;"/>'
			//dogecoin = (position.dogecoin).replace('doge', '<img class="coinImage" src="./resources/images/DOGE.svg" style="width:15px; height:15px;"/>');
		} else {
			dogecoin = '';
		}	
			
		if(position.usdt == 'usdt') {
			usdt = '<img class="coinImage" src="./resources/images/USDT.svg" style="width:17px; height:17px;"/>'
			//usdt = (position.usdt).replace('usdt', '<img class="coinImage" src="./resources/images/USDT.svg" style="width:17px; height:17px;"/>');
		} else {
			usdt = '';
		}		

		
		// 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
		kakao.maps.event.addListener(marker, 'click', function() {
			var content = '<div class="wrap">' + 
        '    <div class="info">' + 
        '        <div class="title">' + 
                     position.business_name + 
        '            <div class="close" onclick="closeOverlay()" title="닫기"></div>' + 
        '        </div>' + 
        '        <div class="body">' + 
  /*    '            <div class="img">' +
        '                <img src="https://s27389.pcdn.co/wp-content/uploads/2022/02/using-graph-technology-to-achieve-cryptocurrency-success.jpeg.optimal.jpeg" width="73" height="70">' +
        '            </div>' + */
        '            <div class="desc">' + 
		'                <div class="lat" style="display:none;">' + position.lat + '</div>' +
		'                <div class="lng" style="display:none;">' + position.lng + '</div>' +
        '                <div class="ellipsis"><i class="fa-solid fa-map-location-dot"></i>' + position.address + '</div>' + 
        '                <div class="jibun ellipsis"><i class="fa-solid fa-location-dot"></i>' + position.detail_address + '</div>' + 
		'                <div class="contact_number"><i class="fa-solid fa-phone"></i>' + position.contact_number + '</div>' + 
		'                <div class="email"><i class="fa-solid fa-envelope"></i>' + position.email + '</div>' +
		'                <div><i class="fa-brands fa-internet-explorer"></i><a href='+'"//' + position.website + '"'+'target="_blank" class="link">홈페이지</a></div>' + 
		'                <div class="instagram"><i class="fa-brands fa-square-instagram"></i>' + position.instagram + '</div>' +
		'                <div><i class="fa-sharp fa-solid fa-coins"></i><span>' + bitcoin + '</span>' + '<span>' + bitcoin_cash + '</span>' + ethereum + '</span> <span style="margin-left:-3px;">' + cardano + '</span> <span>' + decentraland + '</span> <span>' + dogecoin + '</span> <span>' + usdt + '</span> </div>' +
		'                <div class="information"><i class="fa-solid fa-circle-info"></i>' + position.information + '</div>' +
		'                <div style="display:flex; justify-content:space-between;">' +
		'				 	<div><i class="fa-solid fa-pen-to-square"></i>' + position.unix + ' by ' + position.id + '</div>' +		
		'                	<div class="revise" style="color:blue; margin-top:0px;">revise</div>' +	
        '                </div>' +
        '            </div>' + 
        '        </div>' + 
        '    </div>' +    
        '</div>';	
		
		//'                <div><i class="fa-sharp fa-solid fa-coins"></i><span>' + bitcoin + '</span> <span>' + bitcoin_cash + '</span>' + ethereum + '</span> <span style="margin-left:-3px;">' + cardano + '</span> <span>' + decentraland + '</span> <span>' + dogecoin + '</span> <span>' + usdt + '</span> </div>' +
							
			var overlay = new kakao.maps.CustomOverlay({
			    content: content,
			    map: map,
			    position: marker.getPosition(),
			});
			
			overlay.setMap(map);


			if(position.website === '') {
				$('.link').css('display', 'none');
			}	


			// 커스텀 오버레이를 닫기 위해 호출되는 함수입니다 
			function closeOverlay() {
			    overlay.setMap(null);  
			}		
	
	
			$('.close').on('click', function() {
				closeOverlay();
			});	
			
			
			
//----------------------------------정보수정하기--------------------------------------------------------------- 	
			$('.revise').on('click', function() {
				if($('.loginUser').html() === '') {
					swal('내용을 수정하려면 로그인 해주세요.');
					//alert('내용을 수정하려면 로그인 해주세요');
					return;
				}
				
				closeOverlay();
				var lat = position.lat;
				console.log(lat);
				var lng = position.lng;
				console.log(lng);
				var business_name = position.business_name;
				var address = position.address;
				console.log(address);
				var detail_address = position.detail_address;
				var contact_number = position.contact_number;
				var email = position.email;
				var website = position.website;
				var instagram = position.instagram;
				var information = position.information;
				
				
				// 마커를 표시할 위치입니다 
				var positions =  new kakao.maps.LatLng(lat, lng);
				
				// 마커를 생성합니다
				var marker = new kakao.maps.Marker({
				  position: positions,
				  clickable: true // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
				});
				
				// 아래 코드는 위의 마커를 생성하는 코드에서 clickable: true 와 같이
				// 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
				// marker.setClickable(true);
				
				// 마커를 지도에 표시합니다.
				marker.setMap(map);
				
				// 마커를 클릭했을 때 마커 위에 표시할 인포윈도우를 생성합니다
				var iwContent = '<div class="box-title re">' +
									'<div><i class="fa-solid fa-map-location-dot"></i><span style="margin-top:10px;">도로명주소<span></div>' + '<input class="business address" maxlength="30" value="' +position.address+ '"></input>' +
								'</div>' +
								'<div>' +
									'<div class="box-title re">' +
										'<div style="width:120px;"><i class="fa-solid fa-location-dot"></i><span class="question">상세주소</span></div>' + '<input class="business detailAddress" maxlength="30" value="' +detail_address+ '"></input>' +
									'</div>' +
									'<div class="box-title re">' +
										'<div><i class="fa-solid fa-briefcase"></i><span class="question">상호명*</span></div>' + '<input class="business name" maxlength="30" value="' +business_name+ '"></input>' +							
									'</div>' +
										'<div class="box-title re" style="margin-top:7px;">' +
										'<span class="question"><i class="fa-sharp fa-solid fa-coins"></i>코인</span>' +
									/*	'<form action="" method="post" style="width:228px;">' +
									        '<select name="telecom" id="telecom">' +
										        '<option>----- 선택하세요 -----</option>' +
										        '<option value="BTC">비트코인 (BTC)</option>' +
										        '<option value="ETH">이더리움 (ETH)</option>' +
										        '<option value="DOGE">도지코인 (DOGE)</option>' +
										    '</select>' +
										'</form>' +   */
										'<div class="dropDown" style="width:200px; height:30px;">' +
										    '<a href="#" class="btn_drop">선택</a>' +
										    '<div class="dropBox" style="width:200px;">' +
										        '<ul>' +
										            '<li><label><input type="checkbox" id="btc" name="coin" value="btc">'+ '<img class="coinImage" src="./resources/images/BTC.svg" style="width:20px; height:20px; margin-left:4px;"/>' + ' Bitcoin</label>'+ '</li>' +
										            '<li><label><input type="checkbox" id="bch" name="coin" value="bch">'+ '<img class="coinImage" src="./resources/images/BCH.svg" style="width:20px; height:20px; margin-left:4px;"/>' + ' Bitcoin cash</label>'+ '</li>' +
										            '<li><label><input type="checkbox" id="eth" name="coin" value="eth">'+ '<img class="coinImage" src="./resources/images/ETH.svg" style="width:20px; height:20px; margin-left:3px;"/>' + '  Ethereum</label>'+ '</li>' +		
										            '<li><label><input type="checkbox" id="ada" name="coin" value="ada">'+ '  <img class="coinImage" src="./resources/images/ADA.svg" style="width:20px; height:20px;" margin-left:3px;/>' + '  Cardano</label>'+ '</li>' + 
										            '<li><label><input type="checkbox" id="mana" name="coin" value="mana">'+ ' <img class="coinImage" src="./resources/images/MANA.svg" style="width:18px; height:18px; margin-left:3px;"/>' + ' Decentraland</label>'+ '</li>' +
										            '<li><label><input type="checkbox" id="doge" name="coin" value="doge">'+ ' <img class="coinImage" src="./resources/images/DOGE.svg" style="width:18px; height:18px; margin-left:3px;"/>' + '  Dogecoin</label>'+ '</li>' +		
										            '<li><label><input type="checkbox" id="usdt" name="coin" value="usdt">'+ ' <img class="coinImage" src="./resources/images/USDT.svg" style="width:19px; height:19px; margin-left:3px;"/>' + '  USDT</label>'+ '</li>' +									
										        '</ul>' +
										        '<a href="#" class="btn_save">Save</a>' +
										    '</div>' +
										'</div>' +																					
									'</div>' +									
									'<div class="box-title re">' +
										'<div><i class="fa-solid fa-phone"></i><span class="question">전화번호</span></div>' + '<input class="business number" maxlength="30" value="' +contact_number+ '"></input>' +								
									'</div>' +
									'<div class="box-title re">' +
										'<div><i class="fa-solid fa-envelope"></i><span class="question">이메일*</span></div>' + '<input class="business email" maxlength="30" value="' +email+ '"></input>' +		
									'</div>' +	
									'<div class="box-title re">' +
										'<div><i class="fa-brands fa-internet-explorer"></i><span class="question">웹사이트</span></div>' + '<input class="business website" maxlength="30" value="' +website+ '"></input>' +								
									'</div>' +	
									'<div class="box-title re">' +
										'<div><i class="fa-brands fa-square-instagram"></i><span class="question">인스타그램</span></div>' + '<input class="business instagram" style="text-overflow:ellipsis; white-space:nowrap; overflow:hidden;" value="' +instagram+ '"></input>' +							
									'</div>' +	
									'<div class="box-title re">' +																					
										'<div><i class="fa-solid fa-circle-info"></i><span class="question">도움 정보</span></div>' + '<div></div>' +							
									'</div>' +
									'<textarea rows="1" cols="80" class="business information" style="width:300px; margin-top:-3px;" maxlength="45">' +information+ '</textarea>' + 		
									'<div style="display: flex; justify-content: flex-end; margin-top:40px; margin-bottom:40px;">' +
										'<div class="cancel" style="cursor:pointer;">취소</div>' +
										'<div class="save" style="margin:0px 8px; cursor:pointer;">저장</div>' +							
									'</div>' +							
								'</div>'				 
				,
				    iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다
				
				
				
				// 인포윈도우를 생성합니다
				var infowindow = new kakao.maps.InfoWindow({
				    content : iwContent,
				    removable : iwRemoveable,
				});
				
				infowindow.open(map, marker);
				
				
				
				$( ".btn_drop" ).click(function() {
				     $(this).next().toggleClass("on");
				     return false;
				});
				$( ".btn_save,.btn_close,.btn_close_check" ).click(function() {
				     $(this).parent().removeClass("on");
				     return false;
				});
				$( ".dropBox ul li a" ).click(function() {
				     $(this).parent().parent().parent().removeClass("on").prev(".btn_drop").text($(this).text());
				     return false;
				});
				$(".dropBox ul li label").click(function() {
				    if($(this).children("input").is(':checked')) {
				        $(this).parent().addClass("on");
				    } else {
				        $(this).parent().removeClass("on");
				    }
				    if(!$(this).children("input").is(":checked")) {
				        $(this).parent().siblings("li.check_all").removeClass("on").find("input").prop("checked", false);
				    }
				    else {
				        var li_nav = $(this).parent().parent().children("li");
				        if(li_nav.not(".check_all").find("input:checked").size() == li_nav.size()-1) {
				            $(this).parent().siblings("li.check_all").addClass("on").find("input").prop("checked", true);
				        }
				    }
				});
				$(".dropBox ul li.check_all label").click(function() {
				    if($(this).children("input").is(":checked")) {
				        $(this).parent().siblings("li").addClass("on").find("input").prop("checked", true);
				    }
				    else {
				        $(this).parent().siblings("li").removeClass("on").find("input[type=checkbox]").prop("checked", false);
				    }
				});
				$( ".btn_save,.btn_close_check" ).click(function() {
				    if($(this).siblings("ul").find("li.check_all").children().children("input").is(":checked")) {
				        $(this).parent().prev(".btn_drop").text("All");
				    }
				    else {
				        var input_checked = $(this).siblings("ul").find("input:checked");
				        if((input_checked.size()) > 1){
				            $(this).parent().prev(".btn_drop").text(input_checked.first().parent().text()+" 외"+(input_checked.size()-1));
				        }
				        else if(($(this).siblings("ul").find("input:checked").size()) == 1){
				            $(this).parent().prev(".btn_drop").text(input_checked.first().parent().text());
				        }
				        else {
				            $(this).parent().prev(".btn_drop").text("Select(Checkbox)");
				         }
				    }
				    return false;
				});					
				
				
				
				$('.cancel').on('click', function() {
					infowindow.close();	
								
				});	
				
				
								
				
				// 마커에 클릭이벤트를 등록합니다
				kakao.maps.event.addListener(marker, 'click', function() {
				      // 마커 위에 인포윈도우를 표시합니다
				      infowindow.open(map, marker);  
				});	
				
					
							
				//인포창 저장 닫기 그리고 내용 db에 저장
				$('.save').on('click', function() { //save버튼클릭 
					var btc = null;
					var bch = null;
					var eth = null;
					var ada = null;
					var mana = null;
					var doge = null;
					var usdt = null;					
					
					$('input:checkbox[name=coin]').each(function() {
						if($(this).is(":checked")==true) {
						
							var coinName = $(this).val();
							
							if(coinName === 'btc') {
								btc = coinName;
							} 
							if(coinName === 'bch') {
								bch = coinName;
							}
							if(coinName === 'eth') {
								eth = coinName;
							}
							if(coinName === 'ada') {
								ada = coinName;
							}
							if(coinName === 'mana') {
								mana = coinName;
							}
							if(coinName === 'doge') {
								doge = coinName;
							}	
							if(coinName === 'usdt') {
								usdt = coinName;
							}																																															
						}

					});
					
					console.log(btc);
					console.log(bch);
					console.log(eth);	
					console.log(ada);
					console.log(mana);
					console.log(doge);
					console.log(usdt);					
									
					var idx = position.idx;
					console.log(idx);	
					var id = $('.loginUser').html();
					console.log(id);					
					var address = $('input.address').val();
					console.log(address);					
					var detail_address = $('input.detailAddress').val();
					console.log(detail_address);
					var business_name = $('input.name').val();
					console.log(business_name);
					var contact_number = $('input.number').val();
					console.log(contact_number);
					var email = $('input.email').val();
					console.log(email);
					var website = $('input.website').val();
					console.log(website);
					var instagram = $('input.instagram').val();
					console.log(instagram);
					var information = $('textarea.information').val();
					console.log(information);
					var unix = Math.round(new Date().getTime() / 1000); //1970년1월1일0시 기준 현재까지 초시간
					console.log(unix);
					
					if(business_name.length < 1 || email.length < 1) {
						if(business_name.length < 1) {
							swal('상호명을 기재해 주세요.').then(function() {
								$('input.name').focus();
							});							
							//alert('상호명을 기재해 주세요');
							//$('input.name').focus();
							return;
						} else if(email.length < 1) {
							swal('상호명을 기재해 주세요.').then(function() {
								$('input.email').focus();
							});							
							//alert('이메일을 기재해 주세요');
							//$('input.email').focus();
							return;
						}
					}
					
					$.ajax({ //db로 정보 업데이트
						url: './address_information_update',
						type: 'get',
						data: {
							idx: idx,
							id: id,
							lat: lat,
							lng: lng,
							address: address,
							detail_address: detail_address,
							business_name: business_name,
							contact_number: contact_number,
							email: email,
							website: website,
							instagram: instagram,
							bitcoin: btc,
							bitcoin_cash: bch,
							ethereum: eth,
							cardano: ada,
							decentraland: mana,
							dogecoin: doge,
							usdt: usdt,							
							information: information,
							unix: unix,
						},
						success: function(res) {
							console.log(res);
						},
						error: function(error) {
							
						}
					});	
					
					infowindow.close(); //인포인도우 없애기
					marker.setMap(''); //마커 없애기
					location.href = './intro'; 								

				});	
				
				
									
			});	
			
			


		});			

			
		
    });
	

	



	
	if (navigator.geolocation) {
					
	    // GeoLocation을 이용해서 접속 위치를 얻어옵니다
	    navigator.geolocation.getCurrentPosition(function(position) {
	        
	        var lat = position.coords.latitude, // 위도
	            lon = position.coords.longitude; // 경도

	        var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
	            message = '<div style="padding:5px;">여기에 계신가요?!</div>'; // 인포윈도우에 표시될 내용입니다
	        
	        // 마커와 인포윈도우를 표시합니다
	        displayMarker(locPosition, message);




			// 마커가 표시될 위치입니다 
			var markerPosition  = new kakao.maps.LatLng(lat, lon); 
			
			// 마커를 생성합니다
			var marker = new kakao.maps.Marker({
			    position: markerPosition
			});	
			console.log(marker);

		
			// 마커가 지도 위에 표시되도록 설정합니다
			marker.setMap(map);
			
			// 아래 코드는 지도 위의 마커를 제거하는 코드입니다
			//marker.setMap(null);    	
			
			
			// 마커를 클릭했을 때 마커 위에 표시할 인포윈도우를 생성합니다
			var iwContent = '<div style="padding:5px;">여기에 계신가요?!</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
			    iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다
			
			// 인포윈도우를 생성합니다
			var infowindow = new kakao.maps.InfoWindow({
			    content : iwContent,
			    removable : iwRemoveable
			});
			
			// 마커에 클릭이벤트를 등록합니다
			kakao.maps.event.addListener(marker, 'click', function() {
			      // 마커 위에 인포윈도우를 표시합니다
			      infowindow.open(map, marker);  
			});






			$('button#moveSmooth').on('click', function() {
				// 이동할 위도 경도 위치를 생성합니다 
			    var moveLatLon = new kakao.maps.LatLng(lat, lon);    
			    // 지도 중심을 부드럽게 이동시킵니다
			    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
			    map.panTo(moveLatLon); 
			});
	            

	      });
	    
	} else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다
	    
	    var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),    
	        message = 'geolocation을 사용할수 없어요..'
	        
	    displayMarker(locPosition, message);
	}
	
	// 지도에 마커와 인포윈도우를 표시하는 함수입니다
	function displayMarker(locPosition, message) {
	
	    // 마커를 생성합니다
	    var marker = new kakao.maps.Marker({  
	        map: map, 
	        position: locPosition
	    }); 
	    
	    var iwContent = message, // 인포윈도우에 표시할 내용
	        iwRemoveable = true;
	
	    // 인포윈도우를 생성합니다
	    var infowindow = new kakao.maps.InfoWindow({
	        content : iwContent,
	        removable : iwRemoveable
	    });
	    
	    // 인포윈도우를 마커위에 표시합니다 
	    infowindow.open(map, marker);
	    
	    // 지도 중심좌표를 접속위치로 변경합니다
	    map.setCenter(locPosition);      
	}    		
		
	
	
	
	
	$('button#moveSmooth').on('click', function() { //해당함수는 geolacation 함수안에 포함시켜 넣었음
		// 이동할 위도 경도 위치를 생성합니다 
	    var moveLatLon = new kakao.maps.LatLng(33.450580, 126.574942);
	    
	    // 지도 중심을 부드럽게 이동시킵니다
	    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
	    map.panTo(moveLatLon); 
	});
	
	
	
	
	
	
	
	
	// 지도를 생성합니다
	//var map = new kakao.maps.Map(mapContainer, mapOption);
	// 지도를 클릭한 위치에 표출할 마커입니다
	/*var marker = new kakao.maps.Marker({ 
	    // 지도 중심좌표에 마커를 생성합니다 
	    position: map.getCenter() 
	}); 
	// 지도에 마커를 표시합니다
	marker.setMap(map);
	
	// 지도에 클릭 이벤트를 등록합니다
	// 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
	kakao.maps.event.addListener(map, 'click', function(mouseEvent) {        
	    
	    // 클릭한 위도, 경도 정보를 가져옵니다 
	    var latlng = mouseEvent.latLng; 
	    
	    // 마커 위치를 클릭한 위치로 옮깁니다
	    marker.setPosition(latlng);
	    
	    var message = '클릭한 위치의 위도는 ' + latlng.getLat() + ' 이고, ';
	    message += '경도는 ' + latlng.getLng() + ' 입니다';
		
		var lat = latlng.getLat(); //위도
		console.log(lat);
		var lng = latlng.getLng(); //경도
		console.log(lng);
		
		$('.test').html('lat: ' + lat + ', lng: ' + lng);
		
		marker.setMap(map);		
		$('#cancel').on('click', function() {			
			$('.test').html('');
			marker.setMap(''); //마커 없애기 
		});
	    
	    var resultDiv = document.getElementById('clickLatlng'); 
	    resultDiv.innerHTML = message;
	    
	});		*/
	









	
	
	// 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
	var mapTypeControl = new kakao.maps.MapTypeControl();
	
	// 지도에 컨트롤을 추가해야 지도위에 표시됩니다
	// kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
	map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
	
	// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
	var zoomControl = new kakao.maps.ZoomControl()	
	
	map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);	
	
	
	
	

	// 지도 좌측상단에 지도 중심좌표에 대한 주소정보를 표출하는 함수입니다
	function displayCenterInfo(result, status) {
	    if (status === kakao.maps.services.Status.OK) {
	        var infoDiv = document.getElementById('centerAddr');
	
	        for(var i = 0; i < result.length; i++) {
	            // 행정동의 region_type 값은 'H' 이므로
	            if (result[i].region_type === 'H') {
	                infoDiv.innerHTML = result[i].address_name;
					console.log(infoDiv.innerHTML);
					$('#testing').html(infoDiv.innerHTML);
					console.log($('#testing').html());
	                break;
	            }
	        }
	    }    
	}

	


	
//---------------------------------------지도에서 마우스 오른쪽버튼 클릭시 주소정보 생성--------------------------------------------------------------- 
//$('#map').on('mousedown', function() { 
	//if(event.button == 2 || event.which == 3) {	
					
	// 주소-좌표 변환 객체를 생성합니다
	var geocoder = new kakao.maps.services.Geocoder();
	
	var marker = new kakao.maps.Marker(), // 클릭한 위치를 표시할 마커입니다
	    infowindow = new kakao.maps.InfoWindow({zindex:1}); // 클릭한 위치에 대한 주소를 표시할 인포윈도우입니다
	
	// 현재 지도 중심좌표로 주소를 검색해서 지도 좌측 상단에 표시합니다
	searchAddrFromCoords(map.getCenter(), displayCenterInfo);
	
	
	// 지도를 클릭했을 때 클릭 위치 좌표에 대한 주소정보를 표시하도록 이벤트를 등록합니다
	kakao.maps.event.addListener(map, 'mousedown', function(mouseEvent) { //지도에서 마우스 오른쪽 버튼 클릭시
		if(event.button == 2 || event.which == 3) {
			
			if($('.loginUser').html() === '') { //로그인이 되어있지 않은 상태일때
				swal('맵 등록을 위해서 로그인 해주세요.').then(function() {
					location.href = './login';
				});			
				//alert('맵 등록을 위해서 로그인 해주세요');
				//location.href = './login';
				return 'ok';
			}					
			
		// 지도에 클릭 이벤트를 등록합니다
		// 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
		//kakao.maps.event.addListener(map, 'click', function(mouseEvent) {        
		    
		    // 클릭한 위도, 경도 정보를 가져옵니다 
		    var latlng = mouseEvent.latLng; 
		    
		    // 마커 위치를 클릭한 위치로 옮깁니다
		    //marker.setPosition(latlng);
		    
		    var message = '클릭한 위치의 위도는 ' + latlng.getLat() + ' 이고, ';
		    message += '경도는 ' + latlng.getLng() + ' 입니다';
			
			var lat = latlng.getLat(); //위도
			console.log(lat);
			var lng = latlng.getLng(); //경도
			console.log(lng);
			
			$('.test').html('lat: ' + lat + ', lng: ' + lng);
			
			marker.setMap(map);		
			$('#cancel').on('click', function() { //cancel버튼 클릭시			
				$('.test').html('');
				marker.setMap(''); //마커 없애기 
			});
		    
		    var resultDiv = document.getElementById('clickLatlng'); 
		    //resultDiv.innerHTML = message;
		//});							
			
		    searchDetailAddrFromCoords(mouseEvent.latLng, function(result, status) {
		        if (status === kakao.maps.services.Status.OK) {
		            var detailAddr = !!result[0].road_address ? '<div>도로명주소 : ' + result[0].road_address.address_name + '</div>' : '';
		            detailAddr += '<div style="margin-left:5px;"><i class="fa-solid fa-map-location-dot"></i>지번주소 : ' + result[0].address.address_name + '</div>' + 
					'<div>' +
						'<div class="box-title">' +
							'<div><i class="fa-solid fa-location-dot"></i><span class="question">상세주소</span></div>' + '<input class="business detailAddress" maxlength="30"></input>' +
						'</div>' +
						'<div class="box-title">' +
							'<div><i class="fa-solid fa-briefcase"></i><span class="question">상호명*</span></div>' + '<input class="business name" maxlength="30"></input>' +							
						'</div>' +
											
						'<div class="box-title" style="margin-top:7px;">' +
							'<div><i class="fa-sharp fa-solid fa-coins"></i><span class="question">코인</span></div>' +
						/*	'<form action="" method="post" style="width:228px;">' +
						        '<select name="telecom" id="telecom">' +
							        '<option>----- 선택하세요 -----</option>' +
							        '<option value="BTC">비트코인 (BTC)</option>' +
							        '<option value="ETH">이더리움 (ETH)</option>' +
							        '<option value="DOGE">도지코인 (DOGE)</option>' +
							    '</select>' +
							'</form>' +   */
					
							'<div class="dropDown" style="width:200px;">' +
							    '<a href="#" class="btn_drop">선택</a>' +
							    '<div class="dropBox" style="width:400px;">' +
							        '<ul>' +
							            '<li><label><input type="checkbox" id="btc" name="coin" value="btc">'+ '<img class="coinImage" src="./resources/images/BTC.svg" style="width:20px; height:20px; margin-left:4px;"/>' + ' Bitcoin</label>'+ '</li>' +
							            '<li><label><input type="checkbox" id="bch" name="coin" value="bch">'+ '<img class="coinImage" src="./resources/images/BCH.svg" style="width:20px; height:20px; margin-left:4px;"/>' + ' Bitcoin cash</label>'+ '</li>' +
							            '<li><label><input type="checkbox" id="eth" name="coin" value="eth">'+ '<img class="coinImage" src="./resources/images/ETH.svg" style="width:20px; height:20px; margin-left:3px;"/>' + '  Ethereum</label>'+ '</li>' +		
							            '<li><label><input type="checkbox" id="ada" name="coin" value="ada">'+ '  <img class="coinImage" src="./resources/images/ADA.svg" style="width:20px; height:20px;" margin-left:3px;/>' + '  Cardano</label>'+ '</li>' + 
							            '<li><label><input type="checkbox" id="mana" name="coin" value="mana">'+ ' <img class="coinImage" src="./resources/images/MANA.svg" style="width:18px; height:18px; margin-left:3px;"/>' + ' Decentraland</label>'+ '</li>' +
							            '<li><label><input type="checkbox" id="doge" name="coin" value="doge">'+ ' <img class="coinImage" src="./resources/images/DOGE.svg" style="width:18px; height:18px; margin-left:3px;"/>' + '  Dogecoin</label>'+ '</li>' +		
							            '<li><label><input type="checkbox" id="usdt" name="coin" value="usdt">'+ ' <img class="coinImage" src="./resources/images/USDT.svg" style="width:19px; height:19px; margin-left:3px;"/>' + '  USDT</label>'+ '</li>' +		
										'<li><a href="#" class="btn_save">Save</a></li>' +		
							        '</ul>' +
							       
							    '</div>' +
							'</div>' + 												
																								
						'</div>' +	 						
						'<div class="box-title">' +
							'<div><i class="fa-solid fa-phone"></i><span class="question">전화번호</span></div>' + '<input class="business number" maxlength="30"></input>' +								
						'</div>' +						
						'<div class="box-title">' +
							'<div><i class="fa-solid fa-envelope"></i><span class="question">이메일*</span></div>' + '<input class="business email" maxlength="30"></input>' +		
						'</div>' +
						'<div class="box-title">' +
							'<div><i class="fa-solid fa-brands fa-internet-explorer"></i><span class="question">웹사이트</span></div>' + '<input class="business website" maxlength="30"></input>' +								
						'</div>' +	
						'<div class="box-title">' +
							'<div><i class="fa-solid fa-brands fa-square-instagram"></i><span class="question">인스타그램</span></div>' + '<input class="business instagram" style="text-overflow:ellipsis; white-space:nowrap; overflow:hidden;"></input>' +							
						'</div>' +	
						'<div class="box-title">' +
							'<div style=""><i class="fa-solid fa-circle-info"></i>도움 정보</div>' +	
						'</div>' +		
						'<textarea rows="1" cols="80" class="business information" style="width:335px; margin-top:-3px;" maxlength="45"></textarea>' +																															 		
						'<div style="display: flex; justify-content: flex-end; margin-top:40px; margin-bottom:40px;">' +
							'<div class="cancel" style="cursor:pointer;">취소</div>' +
							'<div class="save" style="margin:0px 8px; cursor:pointer;">저장</div>' +							
						'</div>' +							
					'</div>' 
	
		            
		            var content = '<div class="bAddr" style="width: 355px;">' +
										'<div style="display:flex; justify-content:space-between; margin-bottom:5px;">' +
	                  						 '<span class="title">주소정보</span>' + 
											 '<span style="cursor:pointer;"><i class="fa-sharp fa-solid fa-xmark"></i></span>'	+											
										'</div>' +
										 detailAddr +  
		                      	  '</div>';
				
				
					
												
		            // 마커를 클릭한 위치에 표시합니다 
		            marker.setPosition(mouseEvent.latLng);
		            marker.setMap(map);
		
		            // 인포윈도우에 클릭한 위치에 대한 법정동 상세 주소정보를 표시합니다
		            infowindow.setContent(content);
		            infowindow.open(map, marker);
	


					$( ".btn_drop" ).click(function() {
					     $(this).next().toggleClass("on");
					     return false;
					});
					$( ".btn_save,.btn_close,.btn_close_check" ).click(function() {
					     $(this).parent().removeClass("on");
					     return false;
					});
					$( ".dropBox ul li a" ).click(function() {
					     $(this).parent().parent().parent().removeClass("on").prev(".btn_drop").text($(this).text());
					     return false;
					});
					$(".dropBox ul li label").click(function() {
					    if($(this).children("input").is(':checked')) {
					        $(this).parent().addClass("on");
					    } else {
					        $(this).parent().removeClass("on");
					    }
					    if(!$(this).children("input").is(":checked")) {
					        $(this).parent().siblings("li.check_all").removeClass("on").find("input").prop("checked", false);
					    }
					    else {
					        var li_nav = $(this).parent().parent().children("li");
					        if(li_nav.not(".check_all").find("input:checked").size() == li_nav.size()-1) {
					            $(this).parent().siblings("li.check_all").addClass("on").find("input").prop("checked", true);
					        }
					    }
					});
					$(".dropBox ul li.check_all label").click(function() {
					    if($(this).children("input").is(":checked")) {
					        $(this).parent().siblings("li").addClass("on").find("input").prop("checked", true);
					    }
					    else {
					        $(this).parent().siblings("li").removeClass("on").find("input[type=checkbox]").prop("checked", false);
					    }
					});
					$( ".btn_save,.btn_close_check" ).click(function() {
					    if($(this).siblings("ul").find("li.check_all").children().children("input").is(":checked")) {
					        $(this).parent().prev(".btn_drop").text("All");
					    }
					    else {
					        var input_checked = $(this).siblings("ul").find("input:checked");
					        if((input_checked.size()) > 1){
					            $(this).parent().prev(".btn_drop").text(input_checked.first().parent().text()+" 외"+(input_checked.size()-1));
					        }
					        else if(($(this).siblings("ul").find("input:checked").size()) == 1){
					            $(this).parent().prev(".btn_drop").text(input_checked.first().parent().text());
					        }
					        else {
					            $(this).parent().prev(".btn_drop").text("Select(Checkbox)");
					         }
					    }
					    return false;
					});	
	
	
	
	

					
	
					//인포창 저장 닫기 그리고 내용 db에 저장
					$('.save').on('click', function() { //save버튼클릭 
						var btc = null;
						var bch = null;
						var eth = null;
						var ada = null;
						var mana = null;
						var doge = null;						
						var usdt = null;
						
						$('input:checkbox[name=coin]').each(function() {
							console.log($('input:checkbox[name=coin]').length); //checkbox 전체갯수 
							console.log($('input:checkbox[name=coin]:checked').length);	//input checkbox 에서 체크된 갯수
							
							if($(this).is(":checked")==true) {
							
								var coinName = $(this).val();
								
								if(coinName === 'btc') {
									btc = coinName;
								} 
								if(coinName === 'bch') {
									bch = coinName;
								}
								if(coinName === 'eth') {
									eth = coinName;
								}
								if(coinName === 'ada') {
									ada = coinName;
								}
								if(coinName === 'mana') {
									mana = coinName;
								}	
								if(coinName === 'doge') {
									doge = coinName;
								}																	
								if(coinName === 'usdt') {
									usdt = coinName;
								}										
							}
	
						});
						
						console.log(btc);
						console.log(bch);
						console.log(eth);	
						console.log(ada);
						console.log(mana);
						console.log(doge);						
						console.log(usdt);	
						
						var id = $('.loginUser').html();
						console.log(id);
						var address = $('#testing').html()
						console.log(address);					
						var detail_address = $('input.detailAddress').val();
						console.log(detail_address);
						var business_name = $('input.name').val();
						console.log(business_name);
						if(business_name.length < 1) {	
							swal('상호명을 기재해 주세요.').then(function() {
								$('input.name').focus();
							});			
							//alert('상호명을 기재해 주세요');
							//$('input.name').focus();
							return;					
						}	
											
						var contact_number = $('input.number').val();
						console.log(contact_number);
						var email = $('input.email').val();
						arr = [...email];
						if(!(arr.includes('@')) || email === '') {
							swal('이메일을 입력해 주세요.').then(function() {
								$('input.email').focus();
							});							
							//alert('이메일을 입력해 주세요');
							//$('input.email').focus();
							return;
						}
						
						console.log(business_name);
						console.log(email);
						var website = $('input.website').val();
						console.log(website);
						var instagram = $('input.instagram').val();
						console.log(instagram);
						var information = $('textarea.information').val();
						console.log(information);
						var unix = Math.round(new Date().getTime() / 1000); //1970년1월1일0시 기준 현재까지 초시간 

						
						$.ajax({ //db로 정보 넘기기
							url: './address_information',
							type: 'get',
							data: {
								id: id,
								lat: lat,
								lng: lng,
								address: address,
								detail_address: detail_address,
								business_name: business_name,
								contact_number: contact_number,
								email: email,
								website: website,
								instagram: instagram,
								bitcoin: btc,
								bitcoin_cash: bch,
								ethereum: eth,
								cardano: ada,
								decentraland: mana,
								dogecoin: doge,
								usdt: usdt,
								information: information,
								unix: unix,
							},
							success: function(res) {
								console.log(res);
							},
							error: function(error) {
								
							}
						});	
						
						infowindow.close(); //인포인도우 없애기
						marker.setMap(''); //마커 없애기
						
						location.href = './intro'; 								
	
					});	
					
					
					//인포창 닫기
					$('.cancel').on('click', function() { //취소 버튼클릭  
						infowindow.close(); //인포인도우 없애기
						marker.setMap(''); //마커 없애기 			
					});			
					
					
					//인포창 닫기
					$('.fa-sharp.fa-solid.fa-xmark').on('click', function() { //저장 버튼클릭  		
						infowindow.close(); //인포인도우 없애기
						marker.setMap(''); //마커 없애기 																			
					});	
																								
										 
					
		        }
	
	
	
	
	
	
			    $('textarea').on('keyup keypress', function() { //텍스트에 맞게 텍스트영역 높이 자동조절 
			        $(this).height(0);
			        $(this).height(this.scrollHeight);
			    });
	
	
				$('textarea').keyup(function() { //영역높이 제한
					var rows = $(this).val().split('\n').length;
					var maxRows = 2
					if(rows > maxRows) {
						modifiedText = $('textarea').val().split('\n').slice(0, maxRows);
						$('textarea').val(modifiedText.join('\n'));
					}
				});
				
								
	
	
	
				
		    });
	
	    }
	});
			
	
	// 중심 좌표나 확대 수준이 변경됐을 때 지도 중심 좌표에 대한 주소 정보를 표시하도록 이벤트를 등록합니다
	kakao.maps.event.addListener(map, 'idle', function() {
	    searchAddrFromCoords(map.getCenter(), displayCenterInfo);
	});
	
	function searchAddrFromCoords(coords, callback) {
	    // 좌표로 행정동 주소 정보를 요청합니다
	    geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);         
	}
	
	function searchDetailAddrFromCoords(coords, callback) {
	    // 좌표로 법정동 상세 주소 정보를 요청합니다
	    geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
	}
	
	/*
	// 지도 좌측상단에 지도 중심좌표에 대한 주소정보를 표출하는 함수입니다
	function displayCenterInfo(result, status) {
	    if (status === kakao.maps.services.Status.OK) {
	        var infoDiv = document.getElementById('centerAddr');
	
	        for(var i = 0; i < result.length; i++) {
	            // 행정동의 region_type 값은 'H' 이므로
	            if (result[i].region_type === 'H') {
	                infoDiv.innerHTML = result[i].address_name;
					console.log(infoDiv.innerHTML);
					$('#testing').html(infoDiv.innerHTML);
					console.log($('#testing').html());
	                break;
	            }
	        }
	    }    
	}		
	*/	
			
			
	        /*
			// 지도를 클릭한 위치에 표출할 마커입니다
			var marker = new kakao.maps.Marker({ 
				    // 지도 중심좌표에 마커를 생성합니다 
				    position: map.getCenter() 
				}); 
			// 지도에 마커를 표시합니다
			marker.setMap(map);
			
			// 지도에 클릭 이벤트를 등록합니다
			// 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
			kakao.maps.event.addListener(map, 'click', function(mouseEvent) {        
			    
			    // 클릭한 위도, 경도 정보를 가져옵니다 
			    var latlng = mouseEvent.latLng; 
			    
			    // 마커 위치를 클릭한 위치로 옮깁니다
			    marker.setPosition(latlng);
			    
			    var message = '클릭한 위치의 위도는 ' + latlng.getLat() + ' 이고, ';
			    message += '경도는 ' + latlng.getLng() + ' 입니다';
				
				var lat = latlng.getLat(); //위도
				console.log(lat);
				var lng = latlng.getLng(); //경도
				console.log(lng);
				
				$('.test').html('lat: ' + lat + ', lng: ' + lng);
				
				marker.setMap(map);		
				$('#cancel').on('click', function() {			
					$('.test').html('');
					marker.setMap(''); //마커 없애기 
				});
			    
			    var resultDiv = document.getElementById('clickLatlng'); 
			    resultDiv.innerHTML = message;
			});	
            */
		
		//}


//});
	
	


	$(document).click(function(e){
		if(!$(e.target).hasClass('layerPop')){
	    	console.log('레이어팝업 외의 영역입니다');
	    }
	});
	

    $('textarea').on('keyup keypress', function() {
        $(this).height(0);
        $(this).height(this.scrollHeight);
    });
		














	$( ".btn_drop" ).click(function() {
	     $(this).next().toggleClass("on");
	     return false;
	});
	$( ".btn_save,.btn_close,.btn_close_check" ).click(function() {
	     $(this).parent().removeClass("on");
	     return false;
	});
	$( ".dropBox ul li a" ).click(function() {
	     $(this).parent().parent().parent().removeClass("on").prev(".btn_drop").text($(this).text());
	     return false;
	});
	$(".dropBox ul li label").click(function() {
	    if($(this).children("input").is(':checked')) {
	        $(this).parent().addClass("on");
	    } else {
	        $(this).parent().removeClass("on");
	    }
	    if(!$(this).children("input").is(":checked")) {
	        $(this).parent().siblings("li.check_all").removeClass("on").find("input").prop("checked", false);
	    }
	    else {
	        var li_nav = $(this).parent().parent().children("li");
	        if(li_nav.not(".check_all").find("input:checked").size() == li_nav.size()-1) {
	            $(this).parent().siblings("li.check_all").addClass("on").find("input").prop("checked", true);
	        }
	    }
	});
	$(".dropBox ul li.check_all label").click(function() {
	    if($(this).children("input").is(":checked")) {
	        $(this).parent().siblings("li").addClass("on").find("input").prop("checked", true);
	    }
	    else {
	        $(this).parent().siblings("li").removeClass("on").find("input[type=checkbox]").prop("checked", false);
	    }
	});
	$( ".btn_save,.btn_close_check" ).click(function() {
	    if($(this).siblings("ul").find("li.check_all").children().children("input").is(":checked")) {
	        $(this).parent().prev(".btn_drop").text("All");
	    }
	    else {
	        var input_checked = $(this).siblings("ul").find("input:checked");
	        if((input_checked.size()) > 1){
	            $(this).parent().prev(".btn_drop").text(input_checked.first().parent().text()+" 외"+(input_checked.size()-1));
	        }
	        else if(($(this).siblings("ul").find("input:checked").size()) == 1){
	            $(this).parent().prev(".btn_drop").text(input_checked.first().parent().text());
	        }
	        else {
	            $(this).parent().prev(".btn_drop").text("Select(Checkbox)");
	         }
	    }
	    return false;
	});
	
	




	


	
	
	
});