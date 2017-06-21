$("#docentOverzicht").click(function() {
	//verberg div en laat de tabellen zien
	$("#choose").hide();
	$("#docentFilters").show();
	$("#docentOverzichtTable").show();
	
	//roep de volgende url aan
	$.get("http://localhost:4711/quizschoolapp/restservices/docenten", function(data) {
		$.each(data, function(i, docent) {
			var naam = docent.Naam+' '+docent.Achternaam;
			//defineer de onlcick functies die worden meegegeven aan de buttons
			var deleteDocent = "onclick='getDocent("+docent.Docentnummer+")'";
			var updateDocent = "onclick='getUpdateDocent("+docent.Docentnummer+")'";
			//maak voor elke docent een nieuwe rij aan in de tabel
			$("#docentOverzichtTable").append("<tr><td>" + docent.Docentnummer + "</td><td>" + naam + "</td><td>" + docent.Niveau + "</td><td>"+ docent.Woonplaats+"</td><td><button "+updateDocent+"</button>Update Docent</td><td><button "+deleteDocent+">Delete Docent</td></tr>");
		});
	});
});

function getDocent(docentnummer) {
	//roep de volgende url aan met het docentnummer dat hierboven is verkregen
	$.get("http://localhost:4711/quizschoolapp/restservices/docenten/docent/"+docentnummer, function(data){
		var inlognaam = data.Inlognaam;
		//roep de functie deleteDocent aan
		deleteDocent(docentnummer, inlognaam)
	});
}

function deleteDocent(docentnummer, inlognaam) {
	//defineer de url met de inlognaam en docentnummer die hierboven zijn meegestuurd
	var uri = "http://localhost:4711/quizschoolapp/restservices/accounts/docent/"+inlognaam+"/"+docentnummer;
	$.ajax(uri, {
		type: "DELETE",
		//voordat de request wordt verstuurd stuur eerst de token om te kijken of de gebruiker dit wel mag uitvoeren
		beforeSend: function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token)
			console.log(token);	
		},
		//als het gelukt is doe dan het volgende
		success : function() {
			window.location.href = 'accounts.html';
		},
		//als het niet lukt doe dan het volgende
		error: function() {
			window.alert('error');
		}
	});
}

function getUpdateDocent(docentnummer) {
	//roep de volgende url aan met het docent nummer dat in het begin is meegegeven
	$.get("http://localhost:4711/quizschoolapp/restservices/docenten/docent/"+docentnummer, function(data) {
		//sla alle data op in variabelen
		var niveau = data.Niveau;
		var naam = data.Naam;
		var achternaam = data.Achternaam;
		var postcode = data.Postcode;
		var woonplaats = data.Woonplaats;
		var postcode = data.Postcode;
		var geboortedatum = data.Geboortedatum;
		var inlognaam = data.Inlognaam;
		
		//roep de volgende url aan met de inlognaam die hierboven verkregen is
		$.get("http://localhost:4711/quizschoolapp/restservices/accounts/"+inlognaam, function(account) {
			var role= account.Rol
			var wachtwoord = account.Wachtwoord;
			
			//roep de functie showUpdateDocent aan met de gegevens die hierboven aangegeven zijn
			showUpdateDocent(docentnummer, niveau, naam, achternaam, postcode, woonplaats, geboortedatum, inlognaam, role, wachtwoord);
		});
	});
}

function showUpdateDocent(docentnummer, niveau, naam, achternaam, postcode, woonplaats, geboortedatum, inlognaam, role, wachtwoord) {
	//verberg de tabellen en laat het formulier zien met vooraf ingevulde gegevens die hierboven zijn meegegeven
		$("#docentFilters").hide();
		$("#docentOverzichtTable").hide();
		$("#insertDocent").show();
		$("#docentUpdateButton").show();
		$("#insertInlognaam2").prop('readonly', true);
		$("#insertDocentnummer").prop('readonly', true);
		$("#insertInlognaam2").val(inlognaam);
		$("#insertRole2").val(role);
		$("#insertWacthwoord2").val(wachtwoord);
		$("#insertDocentnummer").val(docentnummer);
		$("#insertNiveau2").val(niveau);
		$("#insertNaam2").val(naam);
		$("#insertAchternaam2").val(achternaam);
		$("#insertGeboortedatum2").val(geboortedatum);
		$("#insertPostcode2").val(postcode);
		$("#insertWoonplaats2").val(woonplaats);
	
}

$("#updateDocent").click(function(){
	//defineer de volgende url
	var uri = "http://localhost:4711/quizschoolapp/restservices/accounts/updateDocent/" + $("#insertInlognaam2").val();
	
	$.ajax(uri, {
		type: "PUT",
		//serialize het formulier
		data: $("#insertDocent").serialize(),
		//stuur  voor het formulier eerst de token om te kijken of de gebruiker wel mag updaten
		beforeSend: function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
			console.log(token);
		},
		//als het gelukt is doe dan het volgende
		success: function(respone) {
			$("#insertDocent").hide();
			window.alert("gelukt");
			window.location.href = 'accounts.html';
		},
		//als het niet lukt doe dan het volgende
		error: function(response) {
			window.alert("mislukt");
		}
	});
});

function sortDocentnummer() {
	  // maak de variabelen aan
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("filterDocentnummer");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("docentOverzichtTable");
	  tr = table.getElementsByTagName("tr");

	  // Loop door de tabel en verberg alle rijen die niet overeen komen met de waarde van de input
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
	
function sortNaam2() {
	// maak de variabelen aan
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("filterNaam2");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("docentOverzichtTable");
	  tr = table.getElementsByTagName("tr");

	// Loop door de tabel en verberg alle rijen die niet overeen komen met de waarde van de input
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
	
function sortNiveau2() {
	// maak de variabelen aan
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("filterNiveau2");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("docentOverzichtTable");
	  tr = table.getElementsByTagName("tr");

	// Loop door de tabel en verberg alle rijen die niet overeen komen met de waarde van de input
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
	
function sortWoonplaats() {
	// maak de variabelen aan
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("filterWoonplaats");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("docentOverzichtTable");
	  tr = table.getElementsByTagName("tr");

	// Loop door de tabel en verberg alle rijen die niet overeen komen met de waarde van de input
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
	
$("#okClose3").click(function() {
	window.location.href= 'home.html';
});
