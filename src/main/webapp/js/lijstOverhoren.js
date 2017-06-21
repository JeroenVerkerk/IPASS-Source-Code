var fouteWoorden = [];
var count = 1;
var punten = 0;
var id = window.sessionStorage.getItem("LijstId");
var role = window.sessionStorage.getItem("Role");

//verberg een aantal menu opties aan de hand van de rol
function initPage() {
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
	
	//haal de talen uit de sessionStorage en wijs ze toe aan de buttons
	$("#taal1").text(window.sessionStorage.getItem("Taal1"));
	$("#taal2").text(window.sessionStorage.getItem("Taal2"));
	var taal1 = window.sessionStorage.getItem("Taal1");
	var taal2 = window.sessionStorage.getItem("Taal2");
	//geef de titel de waarde van de lijstnaam uit de sessionStorage
	$("#title").text(window.sessionStorage.getItem("LijstNaam"));
}

$("#taal1").click(function() {
	//zet de waarde van het invoerveld
	$("#taal").val(window.sessionStorage.getItem("Taal1") + " naar " + window.sessionStorage.getItem("Taal2"));
	//roep de volgende url aan met het id van de button
	var uri = "/quizschoolapp/restservices/woordenlijsten/woordenVersie1/" + id;
	$.get(uri, function(data) {
		//voeg de data toe aan een arraylist
		woorden = data;
	});
	//roep de functie start aan
	start();
});

$("#taal2").click(function() {
	//zet de waarde van het invoerveld
	$("#taal").val(window.sessionStorage.getItem("Taal2") + " naar " + window.sessionStorage.getItem("Taal1"));
	//roep de volgende url aan met het id van de button
	var uri2 = "/quizschoolapp/restservices/woordenlijsten/woordenVersie2/" + id;
	$.get(uri2, function(data) {
		//voeg de woorden aan een arraylist toe
		woorden = data;
	});
	//roep de functie start aan
	start();
});

function shuffle(woorden) {
	// voor de lengte van de arraylist krijgt ie een random nummer en wordt een woordt
	//daarna op die plek in de arraylist gezet
	//hierdoor is de volgorde van de lijst elke keer anders
    for (let i = woorden.length; i; i--) {
        let j = Math.floor(Math.random() * i);
        [woorden[i - 1], woorden[j]] = [woorden[j], woorden[i - 1]];
    }
}

function start() {
	$("#taalKeuze").hide();
	$("#startTable").show();
	$("#lijstNaam").val(window.sessionStorage.getItem("LijstNaam"));
}

$("#startButton").click(function() {
	//roep de functie shuffle aan met de arraylist woorden
	shuffle(woorden);
	$("#border").show();
	//voeg het eerste wordt uit de arraylist toe als waarde van het invoerveld
	$("#vraagWoord").val(woorden[0].Taal1Woord);
	$("#startTable").hide();
	$("#nextWoord").show();
});

function berekenPunten() {
	//geef de waarde van het invoerveld een variabele
	var answer = $("#invoerWoord").val();
	//als het ingevoerde woord gelijk is aan het wordt op positie count - (je wilt namelijk niet het aankomende wordt checken)
	//dan krijgt punten er één bij
 	if (answer === woorden[count - 1].Taal2Woord) {
 		punten = punten + 1;
 		$("#response").text("Correct!");
 	}
 	//als het wordt niet gelijk is dan wordt het goeie wordt en het wordt dat gevraagd werd in een arraylist gestopt
 	else {
 		var foutWoord = {Taal1Woord:woorden[count - 1].Taal1Woord, Taal2Woord:woorden[count - 1].Taal2Woord};
 		fouteWoorden.push(foutWoord);
 		$("#responseBad").text("Fout het juiste woord is: " + woorden[count - 1].Taal2Woord);
 	}
}

$("#nextWoord").click(function() {
	//roep de functie berekenPunten aan
	berekenPunten();
	//zet een timeOut zodat ze de response kunnen lezen
	setTimeout(function() {
		//zet de response text op niks
		$("#response").text("");
		$("#responseBad").text("");
		//als als count + 1(je wilt namelijk dat bij het laatste wordt de button veranderd wordt en anders heb je eigenlijk nog één wordt over)
		//net zo groot is als de lengte van de arrayList dan wordt er een andere button weergegeven
		if (woorden.length == count + 1) {
			$("#nextWoord").hide()
			$("#controleren").show();
			$("#vraagWoord").val(woorden[count].Taal1Woord);
			$("#invoerWoord").val("");
		}
		//anders blijf de button hetzelfde
		else {
			$("#vraagWoord").val(woorden[count].Taal1Woord);
			$("#invoerWoord").val("");
			count ++;
		}
	}, 2000);
});

$("#controleren").click(function() {
	//roep een andere berekenPunten aan dan normaal
	berekenPunten2();
	//zet een timeout zodat de gebruiker de response kan lezen
	setTimeout(function() {
		//sla de punten, fouteWoorden(als JsonArray), de lengte van de arrayList en hde lijstId op in de sessionStorage
		window.sessionStorage.setItem("Punten", punten);
		window.sessionStorage.setItem("fouteWoorden", JSON.stringify(fouteWoorden));
		window.sessionStorage.setItem("aantalWoorden", woorden.length);
		window.sessionStorage.setItem("ScoreLijstId", window.sessionStorage.getItem("LijstId"));
		window.location.href = 'woordenLijstResultaat.html';
	}, 2000);
});

function berekenPunten2() {
	var answer = $("#invoerWoord").val();
	//bij de laatste vraag wil je gewoon vegelijken met het woord dat getoond is dus hoef je niet count - 1 te doen
 	if (answer === woorden[count].Taal2Woord) {
 		//als het woord gelijk is aan het woord in de arrayList dan is punten + 1
 		punten = punten + 1;
 		$("#response").text("Correct!");
 	}
 	else {
 		//anders wordt het goede woord en het gevraagde woord opgeslagen in een ArrayList
 		var foutWoord = {Taal1Woord:woorden[count].Taal1Woord, Taal2Woord:woorden[count].Taal2Woord};
 		fouteWoorden.push(foutWoord);
 		$("#responseBad").text("Fout het juiste woord is: " + woorden[count].Taal2Woord);
 	}
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

window.onload = initPage();