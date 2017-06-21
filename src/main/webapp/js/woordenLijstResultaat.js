var aantalWoorden = window.sessionStorage.getItem("aantalWoorden");
var punten = window.sessionStorage.getItem("Punten")
var berekening = punten / aantalWoorden * 10;
var cijfer = Math.round(berekening * 10) / 10;
var fouteWoorden = JSON.parse(window.sessionStorage.getItem("fouteWoorden"));
var role = window.sessionStorage.getItem("Role");
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
	
	//zet de titel hetzelfde als de lijstnaam
	$("#title").text(window.sessionStorage.getItem("LijstNaam") + " resultaat")
	//laat het aantal punten en het aantal woorden zien
	$("#aantalGoed").val("Je hebt "+punten+" van de "+aantalWoorden+" goed beantwoord.")
	//laat het cijfer zien
	$("#cijfer").val("CIJFER: "+cijfer);
	if (fouteWoorden.length >=1) {
		//als de array groter is dan 1 laat dan een textarea zien met de volgende text
		$("#resultaat").append("<tr><td><textarea id='comment'>Deze vragen had je fout en zijn goede onderwerpen om nog eens beter naar te kijken</textarea></td></tr>");
		$.each(fouteWoorden, function(i, woord) {
			//laat voor elk fout woord een input zien met het woord als waarde
			$("#resultaat").append("<input type='text' value="+woord.Taal1Woord+" /><input type='text' value="+woord.Taal2Woord+" />");
		});
	}
	//voeg de waardes toe aan het formulier
	$("#insertCijfer").val(cijfer);
	$("#leerlingnummer").val(window.sessionStorage.getItem("Leerlingnummer"));
	$("#lijstNaam").val(window.sessionStorage.getItem("LijstNaam"));
	$("#datum").val(formatDate(currentDate));
	insertScore();
}

function formatDate(date) {
	//haal de volgende waarden uit de datum
	var dag = date.getDate();
	var maand = '0' + date.getMonth()
	var jaar = date.getFullYear();
	
	//maak van de gegevens een datum die de database kan lezen
	return jaar + '-' + maand + '-' + dag;
}

function insertScore() {
	//defineer de url waar het formulier naar toe gestuurd moet worden
	var uri = "/restservices/scores/lijst";
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
		success: function(response) {
		},
		error: function() {
		}
	});
}

$("#ok").click(function() {
	window.location.href= 'lijstOverzicht.html';
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
