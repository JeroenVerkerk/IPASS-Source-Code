var role = window.sessionStorage.getItem("Role");
var questions = [];
var fouteVragen = [];
var count = 1;
var punten = 0;
var test = [];

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
	
	var id = window.sessionStorage.getItem("QuizId");
	//zet de titel hetzelfde als de quiznaam
	$("#title").text(window.sessionStorage.getItem("QuizNaam"));
	var uri = "/quizschoolapp/restservices/quizzes/question/" + id;
	var uri2 = "/quizschoolapp/restservices/quizzes/" + id;
	//sla alle vragen op in een arraylist
	$.get(uri, function(data) {
		questions = data;
	});
	start();
}

$("#startButton").click(function() {
	console.log(window.sessionStorage.getItem("QuizId"));
	$("#border").show();
	//maak voor de vraag een textarea aan
	$("#quiz").append("<textarea id='question' readonly>"+questions[0].Question+"</textarea><br>");
	//haal het vraagId uit de arraylist op plaat 0
	var id = questions[0].QuestionId;
	//gebruik het id om alle antwoorden te krijgen
	var uri = "/quizschoolapp/restservices/quizzes/answer/" + id;
	$.get(uri, function(data) {
		$.each(data, function(i, answer) {
			var a = answer.Answer
			var correct = answer.IsCorrect;
			//maak voor elk antwoord enn radio button aan
			var appended = "<input type='radio' class='answers' name="+questions[0].QuestionId+" value="+correct+">"+a+"<BR>";
			return $(appended).appendTo("#quiz");
		});
	});
	$("#quizNaam").hide();
	$("#timeText").hide();
	$("#startButton").hide();
	$("#nextQuestion").show();
});

$("#nextQuestion").click(function() {
	berekenPunten();
	//haal het id uit de arrayList op de plaats van count(count wordt elke keer met één verhoogd dus krijg je de volgende vraag te zien)
	var id = questions[count].QuestionId;
	//als count + 1 hetzelfde getal is als de grootte van de arrayList laat dan een ander button zien
	//(je wilt count + 1 weten want als je gewoon count neemt dan zal de button pas verschijnen als je op de knop klik
	//er zijn echter geen vragen meer dus loopt het systeem vast)
	if (questions.length == count + 1) {
		$("#nextQuestion").hide();
		$("#controleren").show();
		var uri = "/quizschoolapp/restservices/quizzes/answer/" + id;
		//leeg de div
		$("#quiz").empty();
		//voeg een nieuw textarea toe met de vraag erin
		$("#quiz").append("<textarea id='question' readonly>"+questions[count].Question+"</textarea><br>");
		$.get(uri, function(data) {
			$.each(data, function(i, answer) {
				var a = answer.Answer
				var correct = answer.IsCorrect;
				//maak voor elk antwoord een radio button aan
				var appended = "<input type='radio' class='answers' name="+questions[count].QuestionId+" value="+correct+">"+a+"<BR>";
				return $(appended).appendTo("#quiz");
			});
		});
	}
	//als count + 1 niet net zo groot is als de grootte van de array doe dan het volgende
	else {
		var uri = "/quizschoolapp/restservices/quizzes/answer/" + id;
		//leeg de div
		$("#quiz").empty();
		//maak een nieuw textarea aan met de vraag erin
		$("#quiz").append("<textarea id='question' readonly>"+questions[count].Question+"</textarea><br>");
		$.get(uri, function(data) {
			$.each(data, function(i, answer) {
				var a = answer.Answer
				var correct = answer.IsCorrect;
				//maak voor elk antwoord een radio button aan
				var appended2 = "<input type='radio' class='answers' name="+questions[count].QuestionId+" value="+correct+">"+a+"<BR>";
				return $(appended2).appendTo("#quiz");
			});
			//verhoog count met 1
			count ++;
		});
	}
});

$("#controleren").click(function() {
	berekenPunten();
	//sla de volgende gegevens op in de sessionStorage omdat dit nodig is op de resultaat pagina
	window.sessionStorage.setItem("Punten", punten);
	//maak van de arraylist een json Object
	window.sessionStorage.setItem("fouteVragen", JSON.stringify(fouteVragen));
	window.sessionStorage.setItem("aantalVragen", questions.length);
	window.sessionStorage.setItem("ScoreQuizId", window.sessionStorage.getItem("QuizId"));
	window.location.href = 'Resultaat.html';

});
function berekenPunten() {
	//als de waarde van de radio butten die aangevinkt is gelijk is aan true dan gaat punten omhoog met 1
	var answer = $(".answers:checked").val();
 	if (answer == "true") {
 		punten = punten + 1;
 	}
 	//als het niet gelijk is aan true dan wordt de vraag opgeslagen in een arrayList
 	//(je wilt count - 1 weten omdat zodra er op de knop geklikt wordt je eigenlijk al bij de volgende vraag bent 
 	//en count dus al verhoogd is met 1 en dan zal de verkeerde vraag opgeslagen worden
 	else {
 		fouteVragen.push(questions[count - 1].Question);
 	}
}

function start() {
	//laat de quiznaam zien in de input
	$("#quizNaam").val(window.sessionStorage.getItem("QuizNaam"));
	$("#timeText").val("Er is geen tijd bij deze quiz.");
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

window.onload = initPage()