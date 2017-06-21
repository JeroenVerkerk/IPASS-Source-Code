//haal de rol uit de sessionStorage
var role = window.sessionStorage.getItem("Role");
var modal = $('#modalSuccess').plainModal();
var modal2 = $('#modalError').plainModal();
var modal3 = $("#modalSmallScreen").plainModal();
//verberg sommige menu opties gebaseerd op de rollen
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
	
	//als het scherm kleiner is dan 1024 doe dan het volgende
	if ($(window).width() < 1024) {
		$("#main").empty();
		modal3.plainModal('open');
	}
}

//zet de breedte van de navigatei balk op 250px
function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
    document.body.style.backgroundColor = "rgba(0,0,0,0.4)";
}

// zet de breedte van de navigatie balk op 0
function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
    document.body.style.backgroundColor = "white";
}

//zodra de pagina geladen is voer dan het volgende uit
window.onload = initPage();