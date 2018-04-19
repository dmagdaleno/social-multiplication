/**
 * 
 */

function updateMultiplication(){
	$.ajax({
		url: "http://localhost:8080/multiplications/random"
	}).then(function(data){
		// cleans the form
		$('#attempt-form').find('input[name="result-attempt"]').val('');
		$('#attempt-form').find('input[name="user-alias"]').val('');
		
		// gets a random challenge from the api and loads the data in the html
		$('.multiplication-a').empty().append(data.factorA);
		$('.multiplication-b').empty().append(data.factorB);
	});
}

function updateStats(alias){
	$.ajax({
		url: "http://localhost:8080/results/from/" + alias,
	}).then(function(data){
		$('#stats-body').empty();
		data.forEach(function(row){
			$('#stats-body').append(
				'<tr>' + 
					'<td>' + row.id + '</td>' +
					'<td>' + row.multiplication.factorA + ' x ' + row.multiplication.factorB + '</td>' +
					'<td>' + row.resultAttempt + '</td>' +
					'<td>' + (row.correct === true ? 'YES' : 'NO') + '</td>' + 
				'</tr>'
			);
		});
	});
}

$(document).ready(function(){
	updateMultiplication();
	
	$('#attempt-form').submit(function(event){
		// dont submit the form normally
		event.preventDefault();
		
		// get some values from elements on the page
		var a = $('.multiplication-a').text();
		var b = $('.multiplication-b').text();
		var $form = $(this), 
			attempt = $form.find('input[name="result-attempt"]').val(),
			userAlias = $form.find('input[name="user-alias"]').val();
		
		// compose the data in the format that the api is expecting
		var data = {
			user: {alias: userAlias},
			multiplication: {factorA: a, factorB: b},
			resultAttempt: attempt
		};
		
		// send the data using post
		$.ajax({
			url: '/results',
			type: 'POST',
			data: JSON.stringify(data),
			contentType: 'application/json; charset=utf-8',
			dataType: 'json',
			success: function(result){
				if(result.correct){
					$('.result-message').empty().append('The result is correct! Congrats!');
				} else {
					$('.result-message').empty().append('Oops, that is not correct! But keep trying!');
				}
			}
		});
		
		updateMultiplication();
		updateStats(userAlias);
	});
});