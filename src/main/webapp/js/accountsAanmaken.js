$("#leerlingAanmaken").click(function() {
	//haal alle klassen uit de database
	$.get("/restservices/klassen",function(data) {
		//voer de volgende functies uit voor elk resultaat dat terug gestuurd wordt
		$.each(data, function(i, klas) {
			var code = klas.KlasCode
			//voeg voor elk resultaat een optie toe aan de klassen
			$("#insertKlas").append("<option value=" + code + ">" + code + "</option>");
				});
			});
	//verberg de div en laat formulier zien
	$("#choose").hide();
	$("#insertLeerling").show();
	$("#leerlingButtons").show();
});

$("#leerlingOpslaan").click(function() {
	//defineer de url waar de post naar toe gestuurd moet worden
	var uri = "/restservices/accounts/createLeerling";
	$.ajax({
		url : uri,
		type : "POST",
		//serialize het formuleer
		data : $("#insertLeerling").serialize(),
		//voordat het formulier verstuurd wordt stuur eerst de token om te kijken
		//of de gebruiker de rechten heeft om dit uit te voeren
		beforeSend : function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader('Authorization', 'Bearer ' + token);
			console.log(token);
		},
		//als de post gelukt is voer dan het volgende uit
		success : function(response) {
			window.alert("Leerling aangemaakt!");
			window.location.href = 'accounts.html';
		},
		//als het mislukt is om een reden doe dan het volgende
		error : function() {
			modal2.plainModal('open');
		}
	});
});

$("#volgendeLeerling").click(function() {
	//defineer de url waar het formulier naar toe gestuurd moet worden
	var uri = "/restservices/accounts/createLeerling";
	$.ajax({
		url : uri,
		type : "POST",
		//serialize het formulier
		data : $("#insertLeerling").serialize(),
		//voordat het formulier verstuurd wordt stuur de token om te kijken of de gebruiker dit mag uitvoeren
		beforeSend : function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader('Authorization', 'Bearer ' + token);
			console.log(token);
		},
		//als het gelukt is voer dan het volgende uit
		success : function() {
			modal.plainModal('open');
			//leeg alle invoervelden zodat er opnieuw ingevuld kan worden
			$("#insertInlognaam").val("");
			$("#insertWachtwoord").val("");
			$("#insertLeerlingnummer").val("");
			$("#insertNaam").val("");
			$("#insertAchternaam").val("");
			$("#insertGeboortedatum").val("");
			$("#insertPostcode").val("");
			$("#insertWoonplaats").val("");
			$("#insertKlas").val("");
			$("#insertNiveau").val("");
			$("#insertProfiel").val("--");
		},
		//als het om een reden niet lukt doe dan het volgende
		error : function() {
			modal2.plainModal('open');
		}
	});
});

$("#okClose").click(function() {
	//defineer de modal
	var modal = $('#modalSuccess').plainModal();
	//sluit de modal
	modal.plainModal('close');
});

$("#okClose2").click(function() {
	//defineer de modal
	var modal = $('#modalError').plainModal();
	//sluit de modal
	modal.plainModal('close');
});

$("#docentAanmaken").click(function() {
	//verberg div
	$("#choose").hide();
	//formulier zien
	$("#insertDocent").show();
	$("#docentButtons").show();
});

$("#docentOpslaan").click(function() {
	//defineer de url waar het formulier naar toe gestuurd moet worden
	var uri = "/restservices/accounts/createDocent";
	$.ajax({
		url : uri,
		type : "POST",
		//serialize het formulier
		data : $("#insertDocent").serialize(),
		//stuur voordat het formulier verstuurd wordt eerst de token om te kijken of de gebruiker dit wel mag
		beforeSend : function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader('Authorization', 'Bearer ' + token);
			console.log(token);
		},
		//als het gelukt is doe dan het volgende
		success : function(response) {
			window.alert("Docent aangemaakt!");
			//terug naar het overzicht
			window.location.href = 'accounts.html';
		},
		//als het om een reden niet lukt doe dan het volgende
		error : function() {
			modal2.plainModal('open');
		}
	});
});

$("#volgendeDocent").click(function() {
	//defineer de url waar het formulier naar toe gestuurd moet worden
	var uri = "/restservices/accounts/createDocent";
	$.ajax({
		url : uri,
		type : "POST",
		//serialize het formulier
		data : $("#insertDocent").serialize(),
		//stuur voordat het formulier verstuurd wordt de token om te kijken of de  gebruiker dit wel mag
		beforeSend : function(xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader('Authorization', 'Bearer ' + token);
			console.log(token);
		},
		//als het gelukt is doe dan het volgende
		success : function() {
			modal.plainModal('open');
			//leeg alle velden zodat er opnieuw kan worden ingevoerd
			$("#insertInlognaam2").val("");
			$("#insertWachtwoord2").val("");
			$("#insertDocentnummer").val("");
			$("#insertNaam2").val("");
			$("#insertAchternaam2").val("");
			$("#insertGeboortedatum2").val("");
			$("#insertPostcode2").val("");
			$("#insertWoonplaats2").val("");
			$("#insertNiveau2").val("");
		},
		//als het om een reden niet lukt doe dan het volgende
		error : function() {
			modal2.plainModal('open');
		}
	});
});
