//아이디와 비밀번호를 json 형식으로 넣어서 로그인 체크하는 곳에 보내고
//일치할때와 비일치 할 때 다른 기능을 가지는 비동기 요청
function send_param(){
	 var Params = {                    
	            "id" : document.getElementById('id').value, 	//아이디
	            "password" : document.getElementById('password').value 	//비밀번호
	    };
    $.ajax({
        url         :   "./login_check",
        type        :   "get",
        data        :   Params,
        success     :   function(responsedata){
        	//받은 메시지가 공백이면 서밋시키고
	        if(responsedata.message == ''){
	    		let frm = document.getElementById("post_param");
	    		frm.submit();
	    	//아니면 메시지를 출력한다.
		    }else{
			    let message = document.getElementById("message");
			    message.innerHTML = responsedata.message;
			    message.style.display = 'block';
			}
        },
        error       :   function(request, status, error){
            console.log("AJAX_ERROR");
        }
    });
}