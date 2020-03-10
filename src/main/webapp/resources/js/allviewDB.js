//전체 출력 시 요일이 '토' 나 '일' 이면 폰트 색이 빨간색으로 나옴
let date = document.getElementsByName("date");

for(let i = 0;i < date.length; i++){
	if(date[i].innerHTML.search('토') != -1){
	date[i].style.color = 'red';
}else if(date[i].innerText.search('일') != -1){
	date[i].style.color = 'red';
	}
} 