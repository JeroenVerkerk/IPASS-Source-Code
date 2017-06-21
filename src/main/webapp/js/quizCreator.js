var role = window.sessionStorage.getItem("Role");
function initPage() {
	//verberg een aantal menu opties aan de hand van de rol
	if (role == "leerling") {
		$("#quizCreator").hide();
		$("#docent").hide();
		$("#docentScore").hide();
		$("#accounts").hide();
	}
	else if (role == "docent") {
		$("#leerling").hide();
		$("#leerlingScore").hide();
		$("#lijstMaken").hide();
		$("#lijstOverzicht").hide();
		$("#openbareLijsten").hide();
		$("#accounts").hide();
	}
	
	//haal de docentnaam op uit de sessionStorage
	$("#gemaaktDoorInsert").val(window.sessionStorage.getItem("DocentNaam"));
}

$("#insertSubmit").click(function() {
	var modal = $('#modalSuccess').plainModal();
	var modal2 = $('#modalError').plainModal();
	//defineer de url waar het formulier naar toe gestuurd moet worden
	var uri = "/quizschoolapp/restservices/quizzes";
	$.ajax({
		url: uri,
		type: "POST",
		//serialize het formulier als de data
		data: $("#insertQuiz").serialize(),
		//stuur voor het formulier eerst de token om te kijken of de gebruiker dit wel mag
		beforeSend: function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
		},
		success: function() {
			modal.plainModal('open');
			//verberg het formulier en laat een ander formulier zien
			$("#quizForm").hide();
			$("#answerForm").show();
			$("#naamInsert").val('');
			$("#gemaaktDoorInsert").val('');
			$("#questionInsert").val('');
			$("#answerInsert").val('');
			$("#answerInsert2").val('');
			$("#questionInsert2").val('');
			$("#answerInsert3").val('');
		},
		error: function() {
			modal2.plainModal('open');
		}
	});
});

$("#submitAnswer").click(function() {
	var modal = $('#modalSuccess').plainModal();
	var modal2 = $('#modalError').plainModal();
	//defineer de url waar het formulier naar toe gestuurd moet worden
	var uri = "/quizschoolapp/restservices/quizzes/insertAnswer";
	$.ajax({
		type: "post",
		url: uri,
		//serialize het formulier om het te versturen
		data: $("#insertAnswer").serialize(),
		//stuur voor het formulier eerst de token om te kijken of de gebruiker dit wel mag
		beforeSend: function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
		},
		success: function(response) {
			window.alert("Quiz Opgeslagen");
			//ga terug naar het beginscherm
			window.location.href = 'home.html';
			$("#naamInsert").val('');
			$("#gemaaktDoorInsert").val('');
			$("#questionInsert").val('');
			$("#answerInsert").val('');
			$("#answerInsert2").val('');
			$("#questionInsert2").val('');
			$("#answerInsert3").val('');
		},
		error: function() {
			modal2.plainModal('open');
		}
	});
});

$("#submitQuestion").click(function() {
	var modal = $('#modalSuccess').plainModal();
	var modal2 = $('#modalError').plainModal();
	//defineer de url waar het formulier naar toe gestuurd moet worden
	var uri = "/quizschoolapp/restservices/quizzes/insertQuestion";
	$.ajax({
		type: "post",
		url: uri,
		//serialize het formulier om het te versturen
		data: $("#insertQuestion").serialize(),
		//stuur voor het formulier eerst de token om te kijken of de gebruiker dit wel mag
		beforeSend: function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
		},
		success: function(response) {
			window.alert("Quiz Opgeslagen");
			//ga terug naar het home scherm
			window.location.href = 'home.html';
			$("#naamInsert").val('');
			$("#gemaaktDoorInsert").val('');
			$("#questionInsert").val('');
			$("#answerInsert").val('');
			$("#answerInsert2").val('');
			$("#questionInsert2").val('');
			$("#answerInsert3").val('');
		},
		error: function() {
			modal2.plainModal('open');
		}
	});
});

$("#nextQuestion").click(function() {
	var modal = $('#modalSuccess').plainModal();
	var modal2 = $('#modalError').plainModal();
	//defineer de url waar het formulier naar toe gestuurd moet worden
	var uri = "/quizschoolapp/restservices/quizzes";
	$.ajax({
		type: "post",
		url: uri,
		//serialize het formulier om het te versturen
		data: $("#insertQuiz").serialize(),
		//stuur voor het formulier eerste de token om te kijken of de gebruiker dit wel mag
		beforeSend: function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
		},
		success: function(response) {
			modal.plainModal('open');
			$("#naamInsert").val('');
			$("#gemaaktDoorInsert").val('');
			$("#questionInsert").val('');
			$("#answerInsert").val('');
			$("#answerInsert2").val('');
			$("#questionInsert2").val('');
			$("#answerInsert3").val('');
			//verberg het formulier en laat een ander formulier zien
			$("#quizForm").hide();
			$("#questionForm").show();
		},
		error: function() {
			modal2.plainModal('open');
		}
	});
});

$("#nextQuestion2").click(function() {
	var modal = $('#modalSuccess').plainModal();
	var modal2 = $('#modalError').plainModal();
	//defineer de url waar het formulier naar toe gestuurd moet worden
	var uri = "/quizschoolapp/restservices/quizzes/insertAnswer";
	$.ajax({
		type: "post",
		url: uri,
		//serialize het formulier om het te versturen
		data: $("#insertAnswer").serialize(),
		//stuur voor het formulier eerst de token om te kijken of de gebruiker dit wel mag
		beforeSend: function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
		},
		success: function(response) {
			modal.plainModal('open');
			//verberg het formulier en laat een ander formulier zien
			$("#answerForm").hide();
			$("#questionForm").show();
			$("#naamInsert").val('');
			$("#gemaaktDoorInsert").val('');
			$("#questionInsert").val('');
			$("#answerInsert").val('');
			$("#answerInsert2").val('');
			$("#questionInsert2").val('');
			$("#answerInsert3").val('');
		},
		error: function() {
			modal2.plainModal('open');
		}
	});
});

$("#nextAnswer").click(function() {
	var modal = $('#modalSuccess').plainModal();
	var modal2 = $('#modalError').plainModal();
	//defineer de url waar het formulier naar toe gestuurd moet worden
	var uri = "/quizschoolapp/restservices/quizzes/insertAnswer";
	$.ajax({
		type: "post",
		url: uri,
		//serialize het formulier om het te versturen
		data: $("#insertAnswer").serialize(),
		//stuur voor het  formulier eerst de token om te kijken of de gebruiker dit wel mag
		beforeSend: function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
			console.log(token);
		},
		success: function(response) {
			modal.plainModal('open');
			//leeg de velden voor een nieuwe vraag
			$("#naamInsert").val('');
			$("#gemaaktDoorInsert").val('');
			$("#questionInsert").val('');
			$("#answerInsert").val('');
			$("#answerInsert2").val('');
			$("#questionInsert2").val('');
			$("#answerInsert3").val('');
		},
		error: function() {
			modal2.plainModal('open');
		}
	});
});

$("#nextQuestion3").click(function() {
	var modal = $('#modalSuccess').plainModal();
	var modal2 = $('#modalError').plainModal();
	//defineer de url waar het formulier naar toe gestuurd moet worden
	var uri = "/quizschoolapp/restservices/quizzes/insertQuestion";
	$.ajax({
		type: "post",
		url: uri,
		//serialize het formulier om het te versturen
		data: $("#insertQuestion").serialize(),
		//stuur voor het formulier eerst de token om te kijken of de gebruiker dit wel mag
		beforeSend: function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
			console.log(token);
		},
		success: function(response) {
			modal.plainModal('open');
			$("#naamInsert").val('');
			$("#gemaaktDoorInsert").val('');
			$("#questionInsert").val('');
			$("#answerInsert").val('');
			$("#answerInsert2").val('');
			$("#questionInsert2").val('');
			$("#answerInsert3").val('');
		},
		error: function() {
			modal2.plainModal('open');
		}
	});
});

$("#nextAnswer2").click(function() {
	var modal = $('#modalSuccess').plainModal();
	var modal2 = $('#modalError').plainModal();
	//defineer de url waar het formulier naar toe gestuurd moet worden
	var uri = "/quizschoolapp/restservices/quizzes/insertQuestion";
	$.ajax({
		type: "post",
		url: uri,
		//serialize het formulier zodat het verstuurt kan worden
		data: $("#insertQuestion").serialize(),
		//stuur voor het formulier eerst de token om te kijken of de gebruiker dit wel mag
		beforeSend: function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
			console.log(token);
		},
		success: function(response) {
			modal.plainModal('open');
			//verberg het formulier en laat een ander formulier zien
			$("#questionForm").hide();
			$("#answerForm").show();
			$("#naamInsert").val('');
			$("#gemaaktDoorInsert").val('');
			$("#questionInsert").val('');
			$("#answerInsert").val('');
			$("#answerInsert2").val('');
			$("#questionInsert2").val('');
			$("#answerInsert3").val('');
		},
		error: function() {
			modal2.plainModal('open');
		}
	});
});

$("#okClose").click(function() {
	var modal = $('#modalSuccess').plainModal();
	//sluit het modal
	modal.plainModal('close');
});

$("#okClose2").click(function() {
	var modal = $('#modalError').plainModal();
	//sluit het modal
	modal.plainModal('close');
});

//zet de breedte van de navigatie op 250px
function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
    document.body.style.backgroundColor = "rgba(0,0,0,0.4)";
}

//zet de breedte van de navigatie op 0
function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
    document.body.style.backgroundColor = "white";
}

window.onload = initPage();