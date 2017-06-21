var role = window.sessionStorage.getItem("Role");
function initPage() {
	if (role == "leerling") {
		$("#quizCreator").hide();
		$("#docent").hide();
		$("#docentScore").hide();
	}
	else if (role == "docent") {
		$("#leerling").hide();
		$("#leerlingScore").hide();
	}
	
	$("#gemaaktDoorInsert").val(window.sessionStorage.getItem("DocentNaam"));
}

$("#insertSubmit").click(function() {
	var modal = $('#modalSuccess').plainModal();
	var modal2 = $('#modalError').plainModal();
	var uri = "/restservices/quizzes";
	$.ajax({
		url: uri,
		type: "POST",
		data: $("#insertQuiz").serialize(),
		beforeSend: function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
			console.log(token);
		},
		success: function() {
			modal.plainModal('open');
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
	var uri = "/restservices/quizzes/insertAnswer";
	$.ajax({
		type: "post",
		url: uri,
		data: $("#insertAnswer").serialize(),
		beforeSend: function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
			console.log(token);
		},
		success: function(response) {
			modal.plainModal('open');
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
	var uri = "/restservices/quizzes/insertQuestion";
	$.ajax({
		type: "post",
		url: uri,
		data: $("#insertQuestion").serialize(),
		beforeSend: function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
			console.log(token);
		},
		success: function(response) {
			modal.plainModal('open');
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
	var uri = "/restservices/quizzes";
	$.ajax({
		type: "post",
		url: uri,
		data: $("#insertQuiz").serialize(),
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
	var uri = "/restservices/quizzes/insertAnswer";
	$.ajax({
		type: "post",
		url: uri,
		data: $("#insertAnswer").serialize(),
		beforeSend: function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
			console.log(token);
		},
		success: function(response) {
			modal.plainModal('open');
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
	var uri = "/restservices/quizzes/insertAnswer";
	$.ajax({
		type: "post",
		url: uri,
		data: $("#insertAnswer").serialize(),
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

$("#nextQuestion3").click(function() {
	var modal = $('#modalSuccess').plainModal();
	var modal2 = $('#modalError').plainModal();
	var uri = "/restservices/quizzes/insertQuestion";
	$.ajax({
		type: "post",
		url: uri,
		data: $("#insertQuestion").serialize(),
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
	var uri = "/restservices/quizzes/insertQuestion";
	$.ajax({
		type: "post",
		url: uri,
		data: $("#insertQuestion").serialize(),
		beforeSend: function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
			console.log(token);
		},
		success: function(response) {
			modal.plainModal('open');
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
	modal.plainModal('close');
});

$("#okClose2").click(function() {
	var modal = $('#modalError').plainModal();
	modal.plainModal('close');
});

/* Set the width of the side navigation to 250px */
function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
    document.body.style.backgroundColor = "rgba(0,0,0,0.4)";
}

/* Set the width of the side navigation to 0 */
function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
    document.body.style.backgroundColor = "white";
}

window.onload = initPage();
