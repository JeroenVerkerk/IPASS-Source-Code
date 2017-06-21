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
	//als het scherm kleiner is dan 1024 laat dan een andere div zien
	if ($(window).width() < 1024) {
		$("#quizzes").hide();
		$("#small").show();
	}
	else {
		//haal alle quizzen op
		$.get("/restservices/quizzes", function(data) {
			$.each(data, function(i, quiz) {
				var quizId = quiz.QuizId;
				var naam = quiz.Naam;
				var vak = quiz.Vak;
				var gemaaktDoor = quiz.Gemaakt_Door
				var niveau = quiz.Niveau;
				var test = '"';
				//defineer de onclick die meegegeven wordt aan de button
				var link = "onclick='showQuiz("+quiz.QuizId+","+test+naam+test+")'";
				//maak voor elke quiz een nieuwe rij in de tabel aan
				$("#quizzes").append("<tr><td>" + naam + "</td><td>" + vak + "</td><td>" + gemaaktDoor + "</td><td>" + niveau + "</td><td><button "+link+">Start Quiz</td></tr>");
			});
		});
	}
}

function showQuiz(quizId, quizNaam) {
	//sla de gegevens die meegegeven worden op in de sessionStorage
	window.sessionStorage.setItem("QuizId", quizId);
	window.sessionStorage.setItem("QuizNaam", quizNaam);
	window.location.href = 'quizMaken.html';
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
	
function sortVak() {
	// maak de variabele aan 
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("filterVak");
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
	
function sortDocent() {
	// maak de variabele aan 
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("filterDocent");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("quizzes");
	  tr = table.getElementsByTagName("tr");

	// Loop door de tabel en verberg alle rijen die niet overeen komen met de input
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
	
function sortNiveau() {
	// maak de variabele aan 
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("filterNiveau");
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
	
$("#selecteer").click(function() {
	var vak = $("#vak").val();
	var niveau = $("#niveau").val();
	$("#small").hide();
	
	//haal de quizzen op die het vak en niveau hebben dat geselecteerd is
	$.get("/restservices/quizzes/overzicht/"+vak+"/"+niveau, function(data) {
		$.each(data, function(i, quiz) {
			var naam = quiz.Naam;
			var vak = quiz.Vak;
			var gemaaktDoor = quiz.Gemaakt_Door
			var niveau = quiz.Niveau;
			var test = '"';
			//defineer de onclick die meegegeven wordt aan de button
			var link = "onclick='showQuiz("+quiz.QuizId+","+test+naam+test+")'";
			//maak voor elke quiz een nieuwe rij in de tabel aan
			$("#quizzes").append("<tr><td>" + naam + "</td><td>" + vak + "</td><td>" + gemaaktDoor + "</td><td>" + niveau + "</td><td><button "+link+">Start Quiz</td></tr>")
		});
	});
	$("#zoekDiv").show();
	$("#quizzes").show();
});

$("#zoek").click(function() {
	//verberg de div en laat de tabel zien
	$("#zoekDiv").hide();
	$("#quizzes").hide();
	$("#quizzes").empty();
	$("#small").show();
});

window.onload = initPage;
