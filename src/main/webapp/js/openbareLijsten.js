var role= window.sessionStorage.getItem("Role");
function initPage() {
	//veberg een aantal menu opties aan de hand van de rol
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
	
	//stuur een request naar deze url
	$.get("/restservices/woordenlijsten/openbaar", function(data) {
		$.each(data, function(i, lijst) {
			var test = '"';
			var taal1 = lijst.Taal1;
			var taal2 = lijst.Taal2;
			var naam = lijst.Naam;
			//defineer de onclick die meegegeven wordt aan de button
			var link = "onclick='showLijst("+lijst.LijstId+","+test+naam+test+", "+test+taal1+test+", "+test+taal2+test+")'";
			//maak voor elke lijst die teruggeven wordt een nieuwe rij in de tabel aan
			$("#Lijsten").append("<tr><td>"+lijst.Naam+"</td><td>"+lijst.Taal1+"</td><td>"+lijst.Taal2+"</td><td><button "+link+">Overhoren</button></td></tr>");
		});
	});
}

function showLijst(lijstId, lijstNaam, taal1, taal2) {
	//sla de gegevens die meegestuurd worden op inde sessionStorage
	//zodat ze op de volgende pagina gebruikt kunnen worden
	window.sessionStorage.setItem("LijstId", lijstId);
	window.sessionStorage.setItem("LijstNaam", lijstNaam);
	window.sessionStorage.setItem("Taal1", taal1);
	window.sessionStorage.setItem("Taal2", taal2);
	window.location.href = 'lijstOverhoren.html';
}

function sortNaam() {
	  // maak de variabele aan
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("naam");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("Lijsten");
	  tr = table.getElementsByTagName("tr");

	  // Loop door de tabel en verberg de rijen die niet overeen komen met de input
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
	
function sortTaal1() {
	  // Declare variables 
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("taal1");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("Lijsten");
	  tr = table.getElementsByTagName("tr");

	  // Loop through all table rows, and hide those who don't match the search query
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
	
function sortTaal2() {
	// maak de variabele aan
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("taal2");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("Lijsten");
	  tr = table.getElementsByTagName("tr");

	// Loop through all table rows, and hide those who don't match the search query
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
