$("#leerlingOverzicht").click(function() {
	//verberg div
	$("#choose").hide();
	//laat tabellen zien
	$("#leerlingFilters").show();
	$("#leerlingOverzichtTable").show();
	//roep de volgende url aan
	$.get("http://localhost:4711/quizschoolapp/restservices/leerlingen", function(data) {
		//doe het volgende voor elk resultaat dat terug gestuurd wordt
		$.each(data, function(i, leerling) {
			var naam = leerling.Naam+' '+leerling.Achternaam;
			//defineer de onclicks die aan de buttons meegegeven worden
			var deleteLeerling = "onclick='getLeerling("+leerling.Leerlingnummer+")'";
			var updateLeerling = "onclick='getUpdateLeerling("+leerling.Leerlingnummer+")'";
			//voeg voor elke leerling een rij in de tabel toe
			$("#leerlingOverzichtTable").append("<tr><td>" + leerling.Leerlingnummer + "</td><td>" + leerling.KlasCode + "</td><td>" + naam + "</td><td>"+leerling.Niveau+"</td><td><button "+updateLeerling+"</button>Update Leerling</td><td><button "+deleteLeerling+">Delete Leerling</td></tr>");
		});
	});
});

function getLeerling(leerlingnummer) {
	//roep de volgende url aan
	$.get("http://localhost:4711/quizschoolapp/restservices/leerlingen/leerling/"+leerlingnummer, function(data){
		//sla de inlognaam op en roep functie delete leerling aan
		var inlognaam = data.Inlognaam;
		deleteLeerling(leerlingnummer, inlognaam)
	});
}

function deleteLeerling(leerlingnummer, inlognaam) {
	//defineer de url waar de delete request naar toe gestuurd wordt
	var uri = "http://localhost:4711/quizschoolapp/restservices/accounts/"+inlognaam+"/"+leerlingnummer;
	$.ajax(uri, {
		type: "DELETE",
		//voordat de request gestuurd wordt stuur eerst de token om te kijken of de gebruiker wel mag deleten
		beforeSend: function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token)
			console.log(token);	
		},
		//als het gelukt is doe dan het volgende
		success : function() {
			window.location.href = 'accounts.html';
		},
		//als het om een reden niet lukt doe dan het volgende
		error: function() {
			window.alert('error');
		}
	});
}

function getUpdateLeerling(leerlingnummer) {
	//roep de volgende url aan
	$.get("http://localhost:4711/quizschoolapp/restservices/leerlingen/leerling/"+leerlingnummer, function(data) {
		//sla de gegevens die worden meegestuurd op in een variabele
		var klas = data.KlasCode;
		var profiel = data.Profiel;
		var niveau = data.Niveau;
		var naam = data.Naam;
		var achternaam = data.Achternaam;
		var postcode = data.Postcode;
		var woonplaats = data.Woonplaats;
		var inlognaam = data.Inlognaam;
		var geboortedatum = data.Geboortedatum;
		
		//roep de volgende url aan met de inlognaam ide hierboven verkregen is
		$.get("http://localhost:4711/quizschoolapp/restservices/accounts/"+inlognaam, function(account) {
			var role= account.Rol
			var wachtwoord = account.Wachtwoord;
			
			//roep de functie showUpdateLeerling aan en stuur de verkregen gegevens mee
			showUpdateLeerling(leerlingnummer, klas, profiel, niveau, naam, achternaam, postcode, woonplaats, inlognaam, role, wachtwoord, geboortedatum);
		});
	});
}

function showUpdateLeerling(leerlingnummer, klas, profiel, niveau, naam, achternaam, postcode, woonplaats, inlognaam, role, wachtwoord, geboortedatum) {
	//roep de volgende url aan
	$.get("http://localhost:4711/quizschoolapp/restservices/klassen", function(data) {
		$.each(data, function(i, klas) {
			//voeg voor elke klas een nieuwe optie toe
			var code = klas.KlasCode
			$("#insertKlas").append("<option value="+code+">"+code+"</option>");
		});
	});
		//verberg de tabellen en laat het formulier zien met de gegevens die zijn opgehaald
		$("#leerlingOverzichtTable").hide();
		$("#insertLeerling").show();
		$("#leerlingUpdateButton").show();
		$("#insertInlognaam").prop('readonly', true);
		$("#insertLeerlingnummer").prop('readonly', true);
		$("#insertInlognaam").val(inlognaam);
		$("#insertRole").val(role);
		$("#insertWachtwoord").val(wachtwoord);
		$("#insertLeerlingnummer").val(leerlingnummer);
		$("#insertKlas").val(klas);
		$("#insertProfiel").val(profiel);
		$("#insertNiveau").val(niveau);
		$("#insertNaam").val(naam);
		$("#insertAchternaam").val(achternaam);
		$("#insertGeboortedatum").val(geboortedatum);
		$("#insertPostcode").val(postcode);
		$("#insertWoonplaats").val(woonplaats);
	
}

$("#updateLeerling").click(function(){
	$("#insertLeerling").hide();
	//defineer de url waar het formulier naar toe gestuurd moet worden
	var uri = "http://localhost:4711/quizschoolapp/restservices/accounts/updateLeerling/" + $("#insertInlognaam").val();
	
	$.ajax(uri, {
		type: "PUT",
		//serialize het formulier
		data: $("#insertLeerling").serialize(),
		//stuur de token voordat het formulier verstuurd wordt om te kijken of de gebruiker dit wel mag doen
		beforeSend: function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
			console.log(token);
		},
		//als het lukt doe dan het volgende
		success: function(respone) {
			window.alert("gelukt");
			window.location.href = 'accounts.html';
		},
		//als het niet lukt doe dan het volgende
		error: function(response) {
			window.alert("mislukt");
		}
	});
});

function sortLeerlingnummer() {
	  // maak de variabelen aan
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("filterLeerlingnummer");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("leerlingOverzichtTable");
	  tr = table.getElementsByTagName("tr");

	  // Loop door de tabel en verberg alle rijen die niet overeen komen met de input die ingevoerd is
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
	
function sortKlas() {
	  // maak de variabelen aan
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("filterKlas");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("leerlingOverzichtTable");
	  tr = table.getElementsByTagName("tr");

	  // Loop door de tabel en verberg alle rijen die niet overeen komen met de input die ingevoerd is
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
	
function sortNaam() {
	// maak de variabelen aan
	  var input, filter, table, tr
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("filterNaam");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("leerlingOverzichtTable");
	  tr = table.getElementsByTagName("tr");

	  // Loop door de tabel en verberg alle rijen die niet overeen komen met de input die ingevoerd is
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
	// maak de variabelen aan
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("filterNiveau");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("leerlingOverzichtTable");
	  tr = table.getElementsByTagName("tr");

	// Loop door de tabel en verberg alle rijen die niet overeen komen met de input die ingevoerd is
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