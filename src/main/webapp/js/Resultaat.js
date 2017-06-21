var role = window.sessionStorage.getItem("Role");
var aantalVragen = window.sessionStorage.getItem("aantalVragen");
var punten = window.sessionStorage.getItem("Punten")
var berekening = punten / aantalVragen * 10;
var cijfer = Math.round(berekening * 10) / 10;
var fouteVragen = JSON.parse(window.sessionStorage.getItem("fouteVragen"));
var currentDate = new Date();

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
	//zet de titel van hetzelfde als de quiznaam
	$("#title").text(window.sessionStorage.getItem("QuizNaam") + " resultaat")
	//laat het aantal punten zien en het aantal vragen
	$("#aantalGoed").val("Je hebt "+punten+" van de "+aantalVragen+" goed beantwoord.")
	//laat het cijfer izen
	$("#cijfer").val("CIJFER: "+cijfer);
	console.log(fouteVragen);
	//als de grootte van de arrayList groter is dan 1 doe dan het volgende
	if (fouteVragen.length >=1) {
		//laat een textarea zien met de volgende text
		$("#resultaat").append("<tr><td><textarea id='comment'>Deze vragen had je fout en zijn goede onderwerpen om nog eens beter naar te kijken</textarea></td></tr>");
		$.each(fouteVragen, function(i, vraag) {
			//laat voor elke vraag uit de arrayList een textarea zien
			$("#resultaat").append("<tr><td><textarea>"+vraag+"</textarea></td></tr>");
		});
	}
	//voeg de waardes toe aan een formulier
	$("#insertCijfer").val(cijfer);
	$("#leerlingnummer").val(window.sessionStorage.getItem("Leerlingnummer"));
	$("#quizNaam").val(window.sessionStorage.getItem("QuizNaam"));
	$("#datum").val(formatDate(currentDate));
	$("#insertQuizId").val(window.sessionStorage.getItem("ScoreQuizId"));
	window.sessionStorage.removeItem("aantalVragen");
	window.sessionStorage.removeItem("Punten");
	window.sessionStorage.removeItem("fouteVragen");
	insertScore();
}

function formatDate(date) {
	//haal de volgende dingen uit de datum
	var dag = date.getDate();
	var maand = '0' + date.getMonth()
	var jaar = date.getFullYear();
	
	//maak van de gegevens hierboven een datum die de database kan lezen
	return dag + '-' + maand + '-' + jaar;
}

function insertScore() {
	//defineer de url waar het formulier naar toe gestuurd moet worden
	var uri = "/quizschoolapp/restservices/scores";
	$.ajax({
		url: uri,
		type: "POST",
		//serialize het formulier om het te versturen
		data: $("#scoreInsert").serialize(),
		//stuur voor het formulier eerst de token om te kijken of de gebruiker dit wel mag
		beforeSend: function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
			console.log(token);
		},
		//als het gelukt is doe dan het volgende
		success: function(response) {
			$("#response").text(response);
		},
		//als het niet lukt doe dan het volgende
		error: function() {
			window.alert("Kan score niet opslaan!");
		}
	});
}

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

$("#ok").click(function() {
	window.location.href = 'quizOverzicht.html';
});

window.onload = initPage;