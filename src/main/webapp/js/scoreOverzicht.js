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
	
	//haal alle scores op die hetzelfde leerlingnummer hebben als het nummer in de sessionStorage
	$.get("/restservices/scores/leerling/"+window.sessionStorage.getItem("Leerlingnummer"), function(data) {
		$.each(data, function(i, score) {
			var naam = score.QuizNaam;
			var cijfer = score.Score;
			var datum = score.Datum;
			//maak voor elke score een nieuwe rij aan in de tabel
			$("#quizzes").append("<tr><td>" + naam + "</td><td>" + cijfer + "</td><td>" + datum + "</td></tr>");
		});
	});
	
	//haal alle scores op die hetzelfde leerlingnummer hebben als het nummer in de sessionStorage
	$.get("/restservices/scores/leerling/lijst/" + window.sessionStorage.getItem("Leerlingnummer"), function(data2) {
		$.each(data2, function(l, score2) {
			//maak voor elke score een nieuwe rij aan in de tabel
			$("#woordenlijsten").append("<tr><td>" + score2.LijstNaam + "</td><td>" + score2.Score + "</td><td>" + score2.Datum + "</td></tr>");
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
	  // maak de variabele aan
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("filterNaam");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("quizzes");
	  tr = table.getElementsByTagName("tr");

	  // Loop door de tabel en verberg alle rijen die niet hetzelfde zijn als de input
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
	
function sortDatum() {
	// maak de variabele aan
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("filterDatum");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("quizzes");
	  tr = table.getElementsByTagName("tr");

	// Loop door de tabel en verberg alle rijen die niet hetzelfde zijn als de input
	  for (i = 0; i < tr.length; i++) {
	    td = tr[i].getElementsByTagName("td")[2];
	    if (td) {
	      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
	    } 
	  }
}

function sortLijstNaam() {
	// maak de variabele aan
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("filterLijstNaam");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("woordenlijsten");
	  tr = table.getElementsByTagName("tr");

	// Loop door de tabel en verberg alle rijen die niet hetzelfde zijn als de input
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
	
function sortLijstDatum() {
	// maak de variabele aan
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("filterLijstDatum");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("woordenlijsten");
	  tr = table.getElementsByTagName("tr");

	// Loop door de tabel en verberg alle rijen die niet hetzelfde zijn als de input
	  for (i = 0; i < tr.length; i++) {
	    td = tr[i].getElementsByTagName("td")[2];
	    if (td) {
	      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
	    } 
	  }
}

window.onload = initPage;
