var leerlingnummer = window.sessionStorage.getItem("Leerlingnummer");
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
	
	//roep de volgende url aan met het leerlingnummer uit de sessionStorage
	$.get("/restservices/woordenlijsten/" + leerlingnummer, function(data) {
		$.each(data, function(i, lijst) {
			var test = '"';
			var taal1 = lijst.Taal1;
			var taal2 = lijst.Taal2;
			var naam = lijst.Naam;
			//defineer de onclicks die worden meegegeven aan de buttons
			var link = "onclick='showLijst("+lijst.LijstId+","+test+naam+test+", "+test+taal1+test+", "+test+taal2+test+")'";
			var deleteLijst = "onclick='deleteLijst("+lijst.LijstId+")'"
			var showWoorden = "onclick='showWoorden("+lijst.LijstId+")'"
			var showInsert =  "onclick='showInsert("+test+taal1+test+", "+test+taal2+test+")'";
			//als het scherm kleiner is dan 1024 dan willen we sommige buttons niet zien en bij een groot scherm wel
			if ($(window).width() < 1024) {
				//maak voor elke lijst een nieuwe rij in de tabel aan
				$("#priveLijsten").append("<tr><td>"+lijst.Naam+"</td><td>"+lijst.Taal1+"</td><td>"+lijst.Taal2+"</td><td><button "+link+">Overhoren</button</td></tr>");
			}
			else {
				//maak voor elke lijst een nieuwe rij in de tabel aan
				$("#priveLijsten").append("<tr><td>"+lijst.Naam+"</td><td>"+lijst.Taal1+"</td><td>"+lijst.Taal2+"</td><td><button "+link+">Overhoren</button</td><td><button "+showInsert+">Woorden Toevoegen</button></td><td><button "+showWoorden+">Delete Woorden</button</td><td><button "+deleteLijst+">Lijst Verwijderen</button></td></tr>");
			}
		});
	});
}

function showLijst(lijstId, lijstNaam, taal1, taal2) {
	//sla de gegevens die van de button afkomen op in de sessionStorage
	window.sessionStorage.setItem("LijstId", lijstId);
	window.sessionStorage.setItem("LijstNaam", lijstNaam);
	window.sessionStorage.setItem("Taal1", taal1);
	window.sessionStorage.setItem("Taal2", taal2);
	window.location.href = 'lijstOverhoren.html';
}

function deleteLijst(lijstId) {
	//vraag de volgende url aan met het id dat van de button afkomt
	var uri = "/restservices/woordenlijsten/" + lijstId;
	$.ajax(uri, {
		type: "DELETE",
		//stuur voor de request de token om te kijken of de gebruiker dit mag doen
		beforeSend: function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token)
			console.log(token);	
		},
		//als het gelukt is doe dan het volgende
		success : function() {
			window.location.href = 'lijstOverzicht.html';
		},
		//als het niet lukt doe dan het volgende
		error: function() {
			window.alert("Error kan lijst niet verwijderen!");
		}
	});
}

function showWoorden(lijstId) {
	//roep de volgende url aan en zet de waarde van de table headers
	$.get("/restservices/woordenlijsten/lijst/" + lijstId, function(data) {
		$("#taal1").text(data.Taal1);
		$("#taal2").text(data.Taal2);
	});
	//verberg de tabel en laat een andere tabel zien
	$("#priveLijsten").hide();
	$("#deleteWoorden").show();
	//roep de volgende url aan met het lijstId dat van de button afkomt
	$.get("/restservices/woordenlijsten/woorden/" + lijstId, function(woorden) {
		$.each(woorden, function(i, woord) {
			//defineer de onclick die meegegeven wordt aan de buttons
			var deleteWoord = "onclick='deleteWoord("+woord.WoordId+", "+lijstId+")'"
			//voor elk woord wordt een nieuwe rij aangemaakt in de tabel
			$("#deleteWoorden").append("<tbody id='deleteWoordenBody'><tr><tr><td>"+woord.Taal1Woord+"</td><td>"+woord.Taal2Woord+"</td><td><button "+deleteWoord+">Delete Woord</button></td></tr></tbody>")
		});
	});
}

function deleteWoord(woordId, lijstId) {
	//roep de volgende url aan met het id dat van de button afkomt
	var uri = "/restservices/woordenlijsten/deleteWoord/" + woordId;
	$.ajax(uri, {
		type: "DELETE",
		//voor de request gestuurd wordt stuur eerst de token om te kijken of de gebruiker dit wel mag
		beforeSend: function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token)
			console.log(token);	
		},
		//als het gelukt is doe dan het volgende
		success : function() {
			window.location.href = 'lijstOverzicht.html';
		},
		//als het niet gelukt is doe dan het volgende
		error: function() {
			window.alert("Error kan woord niet verwijderen!")
		}
	});
}

function showInsert(taal1, taal2) {
	//geef de labels de waarde die meegegeven zijn
	$("#taal1WoordInsert2Text").text(taal1 + ": ");
	$("#taal2WoordInsert2Text").text(taal2 + ": ");
	$("#insertWoord").show();
	$("#volgendeWoord").show();
	$("#opslaan").show();
	$("#priveLijsten").hide();
	$("#filters").hide();
}

$("#volgendeWoord").click(function() {
	//defineer de url waar het formulier naar toe gestuurd moet worden
	var uri = "/restservices/woordenlijsten/insertWoord";
	$.ajax({
		type: "POST",
		url: uri,
		//serialize het formulier
		data: $("#insertWoord").serialize(),
		//stuur voor het formulier eerst de token om te kijken of de gebruiker dit wel mag
		beforeSend: function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
			console.log(token);
		},
		//als het gelukt is doe dan het volgende
		success: function(response) {
			//leeg de velden voor een nieuwe invoer
			$("#taal1WoordInsert2").val("");
			$("#taal2WoordInsert2").val("");
			$("#response").text("Woorden opgeslagen");
		},
		//als het niet lukt doe dan het volgende
		error: function() {
			$("#responseBad").text("Error: Kan lijst niet opslaan! Mogelijk is er een fout op de server of u bent niet bevoegd om dit uit te voeren.")
		}
	});
});

$("#opslaan").click(function() {
	//defineer de url waar het formulier naar toe gestuurd moet worden
	var uri = "/restservices/woordenlijsten/insertWoord";
	$.ajax({
		type: "POST",
		url: uri,
		//serialize het formulier
		data: $("#insertWoord").serialize(),
		//stuur voor het formulier eerst de token om te kijken of de gebruiker dit wel mag
		beforeSend: function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
			console.log(token);
		},
		//als het lukt doe dan het volgende
		success: function(response) {
			window.location.href = 'lijstOverzicht.html';
		},
		//als het niet lukt doe dan het volgende
		error: function() {
			$("#responseBad").text("Error: Kan lijst niet opslaan! Mogelijk is er een fout op de server of u bent niet bevoegd om dit uit te voeren.")
		}
	});
});

function sortNaam() {
	  //maak de variabele aan
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("naam");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("priveLijsten");
	  tr = table.getElementsByTagName("tr");

	  // Loop door de tabel en verberg alle rijen die niet overeen komen met de ingevoerde waarde
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
	 //maak de variabele aan
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("taal1");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("priveLijsten");
	  tr = table.getElementsByTagName("tr");

	  // Loop door de tabel en verberg alle rijen die niet overeen komen met de ingevoerde waarde
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
	//maak de variabele aan
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("taal2");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("priveLijsten");
	  tr = table.getElementsByTagName("tr");

	// Loop door de tabel en verberg alle rijen die niet overeen komen met de ingevoerde waarde
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

window.onload = initPage;
