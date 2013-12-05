/**
 * getTopTen.js 
 * written by Jacob Postema
 * 
 * Gets the top ten high scores form getTopTen.java servlet.
 * 
 */

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
					$('#topten').append('<td>No one has recorded there score</td>');
				}
				else{
					for(var i = data.list.length - 1 ; i > 0; i--){
						console.log(data.list[i].name);
						//loads the JSON to the correct spots
						$('#heading').after('<tr><td>'+(i)+'</td><td>'+data.list[i].name+'</td><td>'+data.list[i].score+'</td></tr>');
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