function initPage() {
	//haal de rol uit de sessionStorage
	var role = window.sessionStorage.getItem("Role")
	$("#leerlingnummerInsert").val(window.sessionStorage.getItem("Leerlingnummer"));
	
	//verberg een aantal menu opties gebaseeerd op de rol
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
}

$("#saveLijst").click(function() {
	//defineer de url waar het formulier heen gestuurd moet worden
	var uri = "/restservices/woordenlijsten";
	$.ajax({
		type: "POST",
		url: uri,
		//serialize het formulier
		data: $("#insertLijst").serialize(),
		//stuur voor het formulier eerst de token om te kijken of de gebruiker dit wel mag
		beforeSend: function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
			console.log(token);
		},
		//als het luk laat dan het volgende formulier zien
		success: function(response) {
			$("#insertLijst").hide();
			$("#saveLijst").hide();
			//geef de labels de waarde van de ingevoerde talen
			$("#taal1WoordInsert2Text").text($("#taal1Insert").val()+ ": ");
			$("#taal2WoordInsert2Text").text($("#taal2Insert").val()+ ": ");
			$("#insertWoord").show();
			$("#volgendeWoord").show();
			$("#opslaan").show();
			$(".response").text("Lijst en Woorden opgeslagen");
		},
		//als het niet lukt doe dan  het volgende
		error: function() {
			$(".responseBad").text("Error: Kan lijst niet opslaan! Mogelijk is er een fout op de server of u bent niet bevoegd om dit uit te voeren.")
		}
	});
});

$("#volgendeWoord").click(function() {
	//defineer de url waar het formulier naar toe gestuurd moet worden
	var uri = "/restservices/woordenlijsten/insertWoord";
	$.ajax({
		type: "POST",
		url: uri,
		//serialize het formulier
		data: $("#insertWoord").serialize(),
		//stuur voor het formulier de token om te kijken of de gebruiker dit wel mag
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
			$(".response").text("Woorden opgeslagen");
		},
		//als het niet lukt doe dan het volgende
		error: function() {
			$(".responseBad").text("Error: Kan lijst niet opslaan! Mogelijk is er een fout op de server of u bent niet bevoegd om dit uit te voeren.")
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
			window.location.href = 'home.html';
		},
		//als het niet lukt doe dan het volgende
		error: function() {
			$(".responseBad").text("Error: Kan lijst niet opslaan! Mogelijk is er een fout op de server of u bent niet bevoegd om dit uit te voeren.")
		}
	});
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

$(document).ready(function(){
	//als er op enter gedrukt wordt in het volgende veld is dit gelijk aan een
	//click op de knop volgendeWoord
    $('#taal1WoordInsert2').keypress(function(e){
      if(e.keyCode==13)
      $('#volgendeWoord').click();
    });
});

$(document).ready(function(){
	//als er op enter gedrukt wordt in het volgende veld is dit gelijk aan een
	//click op de knop volgendeWoord
    $('#taal2WoordInsert2').keypress(function(e){
      if(e.keyCode==13)
      $('#volgendeWoord').click();
    });
});

window.onload = initPage;
