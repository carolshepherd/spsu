// JavaScript Document

var url = '/getTopTen';
//var url = '/BeSetWeb/getTopTen';//local address

//This will load the top ten to highscores.html
$(document).ready(function(e) {
    $.ajax({
			url: url,
			method: 'Post',
			dataType: 'json',
			success: function(data){
				console.log(data);
				
				if(data.list.length == 0){
					$('#top5').append('<li>No one has recorded there score</li>')
				}
				else if(data.list.length < 5){
					for(var i=0; i< data.list.length; i++){
						//console.log(data.list[0].name);
						
						//loads the JSON to the correct spots
						$('.top5').append('<li>'+data.list[i].name+': '+data.list[i].score+'</li>');
					}
				}
				else{
					for(var i=0; i< 5; i++){
						//console.log(data.list[0].name);
						
						//loads the JSON to the correct spots
						$('.top5').append('<li>'+data.list[i].name+': '+data.list[i].score+'</li>');
					}
				}
			},
	});
});


$(document).ajaxStart(function() {
    console.log('start');
});

$(document).ajaxStop(function() {
    console.log('stopped');
});