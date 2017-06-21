//haal de rol uit de sessionStorage
var role = window.sessionStorage.getItem("Role");

function initPage() {
	console.log(window.sessionStorage.getItem("DocentNaam"));
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
	
	//roep de volgende url aan met het docentnummer uit de sessionStorage dat bij het inloggen is opgeslagen
	$.get("/quizschoolapp/restservices/quizzes/byDocent/"+window.sessionStorage.getItem("DocentNaam"), function(data) {
		$.each(data, function(i, quiz) {
			var quizId = quiz.QuizId;
			var naam = quiz.Naam;
			var vak = quiz.Vak;
			var gemaaktDoor = quiz.Gemaakt_Door
			var niveau = quiz.Niveau;
			var test = '"';
			//maak de onclick aan voor die meegegeven worden aan de buttons
			var link = "onclick='showQuiz("+quiz.QuizId+","+test+naam+test+")'";
			var deleteQuiz = "onclick='deleteQuiz("+quizId+")'";
			//maak voor elke quiz een nieuwe rij aan in de tabel
			$("#quizzes").append("<tr><td>" + naam + "</td><td>" + vak + "</td><td>" + gemaaktDoor + "</td><td>" + niveau + "</td><td><button "+link+">Start Quiz</td><td><button "+deleteQuiz+"value="+quiz.QuizId+">Delete Quiz</button></td></tr>");
		});
	});
}

function showQuiz(quizId, quizNaam) {
	//sla het id en de naam op in de sesstionStorage
	window.sessionStorage.setItem("QuizId", quizId);
	window.sessionStorage.setItem("QuizNaam", quizNaam);
	//ga naar deze pagina
	window.location.href = 'quizMaken.html';
}

//zet de breedte van de navigaie op 250px
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
	//maak de variabele aan
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("filterNaam");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("quizzes");
	  tr = table.getElementsByTagName("tr");

	  // Loop door de tabel en verberg alle rijen  die niet overeen komen met de waarde van de input
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
	//maak de variabele aan
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("filterVak");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("quizzes");
	  tr = table.getElementsByTagName("tr");

	// Loop door de tabel en verberg alle rijen  die niet overeen komen met de waarde van de input
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
	//maak de variabele aan
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("filterDocent");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("quizzes");
	  tr = table.getElementsByTagName("tr");

	// Loop door de tabel en verberg alle rijen  die niet overeen komen met de waarde van de input
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
	//maak de variabele aan
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("filterNiveau");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("quizzes");
	  tr = table.getElementsByTagName("tr");

	// Loop door de tabel en verberg alle rijen  die niet overeen komen met de waarde van de input
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
	
function deleteQuiz(quizId) {
	//defineer de url met de het id dat vast zit aan de button
	var uri = "/quizschoolapp/restservices/quizzes/" + quizId;
	$.ajax(uri, {
		type: "DELETE",
		//voordat de request gestuurd wordt stuur eerst de token om te kijken of de gebruiker dit wel mag
		beforeSend: function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token)
			console.log(token);	
		},
		//als het lukt doe dan het volgende
		success : function() {
			window.location.href = 'docentQuizOverzicht.html';
		},
		//als het niet lukt doe dan het volgende
		error: function() {
			window.alert("Error kan quiz niet verwijderen! Er is een fout op de server of u bent niet bevoegd om dit te doen");
		}
	});
}

window.onload = initPage;