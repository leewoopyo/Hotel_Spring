//유효성 검사 체크 항목
var chk_name = false;
var chk_resv_date = true;
var chk_addr = false;
var chk_in_name = false;
var chk_telnum = false;

//이름의 유효성 검사
//한글만 가능하게
//공백 불가
//2글자 이상
//위 사항에 걸리면 해당 문구들이 생김
function check_name() {
	let name = document.getElementById("name").value;
	const validation_korean = /[a-z0-9ㄱ-ㅎㅏ-ㅣ]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"'\\]/g;
	let name_validation = document.getElementById("name_validation");

	if (name == '') {
		name_validation.innerHTML = "이름이 공백입니다.";
		chk_name = false;
	} else if (validation_korean.test(name)) {
		name_validation.innerHTML = "이름은 한글만 가능합니다.";
		chk_name = false;
	} else if (name.length <= 1) {
		name_validation.innerHTML = "이름은 2글자 이상 이어야 합니다.";
		chk_name = false;
	} else {
		name_validation.innerHTML = "";
		document.getElementById("in_name").value = name;
		//검사에 통과하면 체크하는 변수를 true로 한다.
		chk_name = true;
	}
}

//날짜의 유효성 검사
//비동기 요청으로 json 형식으로 데이터를 보내고
//만약 요청 보낸 날에 예약 잡힌 방이 있으면 해당 방은 안보여준다.
function check_date() {
	var Params = {
		"resv_date" : document.getElementById('resv_date').value
	//오늘 날짜
	};
	$.ajax({
		url : "./date_check",
		type : "get",
		data : Params,
		success : function(responsedata) {
			date_validation.innerHTML = responsedata.message;
			//console.log(responsedata.message);          
			//console.log(responsedata.deluxe);                
			//console.log(responsedata.suite);                
			//console.log(responsedata.royal); 

			let deluxe_div = document.getElementById("deluxe");
			let suite_div = document.getElementById("suite");
			let royal_div = document.getElementById("royal");

			if (responsedata.deluxe == 1) {
				deluxe_div.style.display = 'none';
			} else {
				deluxe_div.style.display = 'block';
			}
			if (responsedata.suite == 2) {
				suite_div.style.display = 'none';
			} else {
				suite_div.style.display = 'block';
			}
			if (responsedata.royal == 3) {
				royal_div.style.display = 'none';
			} else {
				royal_div.style.display = 'block';
			}

			if (responsedata.message == '') {
				//검사에 통과하면 체크하는 변수를 true로 한다.
				chk_resv_date = true;
			} else {
				//하니면 false로 한다.
				chk_resv_date = false;
			}
		},
		error : function(request, status, error) {
			console.log("AJAX_ERROR");
		}
	});
}

//주소 적을 시 daum주소 api를 활용하는 기능
function execDaumPostcode() {

	let addr_validation = document.getElementById("addr_validation");
	addr_validation.innerHTML = "";

	new daum.Postcode({
		oncomplete : function(data) {
			// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

			// 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
			// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
			var roadAddr = data.roadAddress; // 도로명 주소 변수
			var extraRoadAddr = ''; // 참고 항목 변수

			// 법정동명이 있을 경우 추가한다. (법정리는 제외)
			// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
			if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
				extraRoadAddr += data.bname;
			}
			// 건물명이 있고, 공동주택일 경우 추가한다.
			if (data.buildingName !== '' && data.apartment === 'Y') {
				extraRoadAddr += (extraRoadAddr !== '' ? ', '
						+ data.buildingName : data.buildingName);
			}
			// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
			if (extraRoadAddr !== '') {
				extraRoadAddr = ' (' + extraRoadAddr + ')';
			}

			// 우편번호와 주소 정보를 해당 필드에 넣는다.
			document.getElementById('postcode').value = data.zonecode;
			document.getElementById("roadAddress").value = roadAddr;
			document.getElementById("jibunAddress").value = data.jibunAddress;

			// 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
			if (roadAddr !== '') {
				document.getElementById("extraAddress").value = extraRoadAddr;
			} else {
				document.getElementById("extraAddress").value = '';
			}

			var guideTextBox = document.getElementById("guide");
			// 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
			if (data.autoRoadAddress) {
				var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
				guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
				guideTextBox.style.display = 'block';

			} else if (data.autoJibunAddress) {
				var expJibunAddr = data.autoJibunAddress;
				guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
				guideTextBox.style.display = 'block';
			} else {
				guideTextBox.innerHTML = '';
				guideTextBox.style.display = 'none';
			}
		}
	}).open();
}

//전화번호 입력 시 4글자 제한 및 포커스 이동 기능
function next_focus() {
	let telnum_validation = document.getElementById("telnum_validation");
	telnum_validation.innerHTML = "";

	let telnum2 = document.getElementById("telnum2");
	let telnum3 = document.getElementById("telnum3");

	//길이 제한
	//maxlength는 태그에서 설정이 되어 있다 (4)
	if (telnum2.value.length > telnum2.maxLength) {
		telnum2.value = telnum2.value.slice(0, telnum2.maxLength);
	}
	//길이 제한
	//maxlength는 태그에서 설정이 되어 있다 (4)
	if (telnum3.value.length > telnum3.maxLength) {
		telnum3.value = telnum3.value.slice(0, telnum3.maxLength);
	}
	//중간 번호 적는 곳에 길이가 4 이상이 되면 뒤 태그로 포커스가 이동한다.
	if (telnum2.value.length >= 4) {
		telnum3.focus();
	}
}

//입금자 이름의 유효성 검사
//한글만 가능하게
//공백 불가
//2글자 이상
//위 사항에 걸리면 해당 문구들이 생김
function check_in_name() {
	let in_name = document.getElementById("in_name").value;
	const validation_korean = /[a-z0-9ㄱ-ㅎㅏ-ㅣ]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"'\\]/g;
	let in_name_validation = document.getElementById("in_name_validation");

	if (in_name == '') {
		in_name_validation.innerHTML = "이름이 공백입니다.";
		chk_in_name = false;
	} else if (validation_korean.test(in_name)) {
		in_name_validation.innerHTML = "이름은 한글만 가능합니다.";
		chk_in_name = false;
	} else if (in_name.length <= 1) {
		in_name_validation.innerHTML = "이름은 2글자 이상 이어야 합니다.";
		chk_in_name = false;
	} else {
		in_name_validation.innerHTML = "";
		chk_in_name = true;
	}
}

//보내기 버튼 클릭 시 입력이 잘 되었나 체크 한 담에 submit을 하는 기능
function sendparam(str) {
	//이름 값
	let name = document.getElementById("name").value;	//이름 값
	const validation_korean = /[a-z0-9ㄱ-ㅎㅏ-ㅣ]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"'\\]/g;	//한글 정규식
	let name_validation = document.getElementById("name_validation");	//유효성 검사 문구 출력되는 부분
	//예약일 값
	let resv_date = document.getElementById("resv_date").value;	//예약일 값
	//방 정보 값
	let room = document.getElementsByName("room");
	let room_value; // 여기에 선택된 radio 버튼의 값이 담기게 된다.
	for (var i = 0; i < room.length; i++) {
		if (room[i].checked) {
			room_value = room[i].value;
		}
	}
	//주소 값
	let postcode = document.getElementById("postcode").value;
	let roadAddress = document.getElementById("roadAddress").value;
	let jibunAddress = document.getElementById("jibunAddress").value;
	let extraAddress = document.getElementById("extraAddress").value;
	let detailAddress = document.getElementById("detailAddress").value;
	//위 값들이 조합되어  한 문장이 된다.
	let addr_value = postcode + ' /' + roadAddress + ' /' + jibunAddress + ' /'
			+ extraAddress + ' /' + detailAddress;
	//유효성 검사 문구 출력하는 부분
	let addr_validation = document.getElementById("addr_validation");
	//조합된 문장이 value값으로 input태그에 저장된다.
	document.getElementById("addr").value = addr_value;

	//전화번호 값
	let telnum0 = document.getElementById("telnum1");

	//각 적힌 전화번호 값들을 가져오고
	let telnum1 = telnum0.options[telnum0.selectedIndex].value;
	let telnum2 = document.getElementById("telnum2").value;
	let telnum3 = document.getElementById("telnum3").value;
	//각 문자를 조합한다. 
	let telnum_value = telnum1 + '-' + telnum2 + '-' + telnum3;
	//유효성 검사 문구 
	let telnum_validation = document.getElementById("telnum_validation");
	//조합된 전화번호를 input태그에 value값으로 저장한다.
	document.getElementById("telnum").value = telnum_value;

	//입금자 값
	let in_name = document.getElementById("in_name").value;
	let in_name_validation = document.getElementById("in_name_validation");
	//코맨트 값
	let comment = document.getElementById("comment").value;
	//등록일자
	let write_date = document.getElementById("write_date").value;
	//진행상황
	let processing = document.getElementById("processing").value;

	//이름 유효성 검사
	if (name == '') {
		name_validation.innerHTML = "이름이 공백입니다.";
		chk_name = false;
	} else if (validation_korean.test(name)) {
		name_validation.innerHTML = "이름은 한글만 가능합니다.";
		chk_name = false;
	} else if (name.length <= 1) {
		name_validation.innerHTML = "이름은 2글자 이상 이어야 합니다.";
		chk_name = false;
	} else {
		name_validation.innerHTML = "";
		document.getElementById("in_name").value = name;
		chk_name = true;
	}

	//주소 유효성 검사
	if (postcode == '') {
		addr_validation.innerHTML = "주소를 입력하세요.";
		chk_addr = false;
	} else {
		addr_validation.innerHTML = "";
		chk_addr = true;
	}

	//입금자 유효성 검사
	if (in_name == '') {
		in_name_validation.innerHTML = "이름이 공백입니다.";
		chk_in_name = false;
	} else if (validation_korean.test(in_name)) {
		in_name_validation.innerHTML = "이름은 한글만 가능합니다.";
		chk_in_name = false;
	} else if (in_name.length <= 1) {
		in_name_validation.innerHTML = "이름은 2글자 이상 이어야 합니다.";
		chk_in_name = false;
	} else {
		in_name_validation.innerHTML = "";
		chk_in_name = true;
	}

	//전화번호 유효성 검사
	if (telnum.value.length != 13) {
		telnum_validation.innerHTML = "전화번호를 올바르게 입력하세요";
		chk_telnum = false;
	} else {
		telnum_validation.innerHTML = "";
		chk_telnum = true;
	}

//	console.log(name);
//	console.log(resv_date);
//	console.log(room_value);
//	console.log(addr.value);
//	console.log(telnum.value);
//	console.log(in_name);
//	console.log(comment);
//	console.log(write_date);
//	console.log(processing);
//
//	console.log(chk_name);
//	console.log(chk_resv_date);
//	console.log(chk_addr);
//	console.log(chk_telnum);
//	console.log(chk_in_name);

	//form정보 가져옴
	var post_param = document.post_param;
	//데이터 삽입, 수정, 삭제중 function에 매개값으로 들어온 문자에 따라 action 값이 바뀐다.
	if (str == 'i') {
		post_param.action = './insert_logic';
	} else if (str == 'u') {
		post_param.action = './admin_updateDB';
	} else if (str == 'd') {
		post_param.action = './admin_deleteDB';
	}
	
	//유효성 검사시마다 체크한 체크 변수가 모두 true일때만 submit한다.
	if (chk_name == true) {
		if (chk_resv_date == true) {
			if (chk_addr == true) {
				if (chk_telnum == true) {
					if (chk_in_name == true) {
						post_param.submit();
					}
				}
			}
		}
	}
}
