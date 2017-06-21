//haal de rol uit de sessionStorage
var role = window.sessionStorage.getItem("Role");
//verberg een aantal menu opties gebaseerd op de rol
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
	
	//roep de volgende url aan met de docentnaam uit de ssionStorage
	$.get("/quizschoolapp/restservices/quizzes/byDocent/"+window.sessionStorage.getItem("DocentNaam"), function(data) {
		$.each(data, function(i, quiz) {
			var quizId = quiz.QuizId;
			
			//roep de functie getScores aan en geef het quizId mee
			getScores(quizId);
		});
	});
}

function getScores(quizId) {
	//roep de volgende url aan met het meegegeven quizId
	$.get("/quizschoolapp/restservices/scores/docent/"+quizId, function(data) {
		$.each(data, function(i, score) {
			var naam = score.QuizNaam;
			var leerlingnummer = score.Leerlingnummer;
			var cijfer = score.Score;
			var datum = score.Datum
			//maak voor elke score een nieuwe rij aan in de tabel
			$("#quizzes").append("<tr><td>"+ naam +"</td><td>" + leerlingnummer + "</td><td>" + cijfer + "</td><td>" + datum + "</td></tr>");
		});
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


	function sortQuizNaam() {
		//maak de variabelen aan
		var input, filter, table, tr, td, i;
		input = document.getElementById("filterNaam");
		filter = input.value.toUpperCase();
		table = document.getElementById("quizzes");
		tr = table.getElementsByTagName("tr");

		// Loop door de tabel en verberg alle rijen die niet overeen komen met de input
		for (i = 0; i < tr.length; i++) {
			td = tr[i].getElementsByTagName("td")[0];
			if (td) {
				if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
			}
		}
	}

	function sortLeerlingnummer() {
		//maak de variabelen aan
		var input, filter, table, tr, td, i;
		input = document.getElementById("filterLeerlingnummer");
		filter = input.value.toUpperCase();
		table = document.getElementById("quizzes");
		tr = table.getElementsByTagName("tr");

		// Loop door de tabel en verberg alle rijen die niet overeen komen met de input
		for (i = 0; i < tr.length; i++) {
			td = tr[i].getElementsByTagName("td")[1];
			if (td) {
				if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
			}
		}
	}

	function sortDatum() {
		//maak de variabelen aan
		var input, filter, table, tr, td, i;
		input = document.getElementById("filterDatum");
		filter = input.value.toUpperCase();
		table = document.getElementById("quizzes");
		tr = table.getElementsByTagName("tr");

		// Loop door de tabel en verberg alle rijen die niet overeen komen met de input
		for (i = 0; i < tr.length; i++) {
			td = tr[i].getElementsByTagName("td")[3];
			if (td) {
				if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
			}
		}
	}

//roep bij het laden van de pagina de volgende functie aan
window.onload = initPage;