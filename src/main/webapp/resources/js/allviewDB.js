let date = document.getElementsByName("date");

for(let i = 0;i < date.length; i++){
	if(date[i].innerHTML.search('토') != -1){
	date[i].style.color = 'red';
}else if(date[i].innerText.search('일') != -1){
	date[i].style.color = 'red';
	}
} 