var number = 0;


function initialLoad(){
	console.log(document.getElementById("is-phd"))
	if (document.getElementById("is-phd").value === "false"){
		console.log("GİRDİM")
		makeUnvisiable("phd-application");
	}

	if(document.getElementById("hasEnglishExam").value === "false")
		makeUnvisiable("englishExamPart");

	if(document.getElementById("isForeign").value === "true")
		enablePassport();
	else
		disablePassport();

	if(document.getElementById("isWorking").value === "true")
		enableWork();
	else
		disableWork();


}

function enablePassport() {
				document.getElementById("extra-1").style = "visibility: visable";
	 			document.getElementById("no1").style = "display: block";
        }
	
function disablePassport() {
				document.getElementById("extra-1").style = "visibility: hidden";
				document.getElementById("yes1").style = "display: block";

        }

function enableWork() {
				document.getElementById("extra-2").style = "visibility: visable";
	 			document.getElementById("yes2").style = "display: block";
        }
	
function disableWork() {
				document.getElementById("extra-2").style = "visibility: hidden";
				document.getElementById("no2").style = "display: block";

        }


$(document).ready(function () {
  $('textarea[data-limit-rows=true]')
    .on('keypress', function (event) {
        var textarea = $(this),
            text = textarea.val(),
            numberOfLines = (text.match(/\n/g) || []).length + 1,
            maxRows = parseInt(textarea.attr('rows'));

        if (event.which === 13 && numberOfLines === maxRows ) {
          return false;
        }
    });
});


function copyToInput(){
	var elem = document.getElementById('missing-info');
	var elem1 = document.getElementById('mail-message');
	elem1.value = elem.textContent;
}


function insertText(buttonID) {
		var elem = document.getElementById('missing-info');
        var elem1 = document.getElementById('sum-missing-info');  
		var elem2 = document.getElementById(buttonID);
		if(elem2.textContent ==="Added")
			{
				elem.innerHTML += buttonID;
				elem.innerHTML += ', ';
				elem1.innerHTML += '<br>';
				elem1.innerHTML += buttonID;
			}
        
}


function insertTexts()
{
	insertText('Name');
	insertText('Surname');
	insertText('Phone-Number');
	insertText('Birth-Date');
	insertText('Address');
	insertText('Photo');
	insertText('Transcript');
	insertText('ALES-Result');
	insertText('English-Exam-Result');
	insertText('Purpose-Letter');
	insertText('Reference-Letter');
	insertText('Foreign-Passport');
	insertText('Permission-Letter');
	insertText('PhD');
	copyToInput();

}

function reverseSituation(){
	var elem = document.getElementById("sum-missing-info");
	var elem2 = document.getElementById("missing-info");
	
	elem.innerHTML = "Summary of missing/corrupted info";
	elem2.innerHTML = "Dear Applicant,In your application some informations which you give when appling are not correct or has some mistakes. Please check your information/s which is in below:";
}


function increase(){
	// $("number").html(parseInt($('#number').html(), 10)+1);
	number = number + 1;
	document.getElementById("number").innerHTML = number;
}
	
	
function decrease(){
	// $("number").html(parseInt($('#number').html(), 10)-1);
	number = number - 1;
	document.getElementById("number").innerHTML = number;
}

function checkStatus(){
	// parseInt($('number').html(), 10) == 0
	if (number == 0){
		document.getElementById("continue").style = "width: 100%;background: #3F78B3;color:#fff ;border-color: #3F78B3;border-radius: 15px; display: none;"; 
		document.getElementById("confirm").style = "width: 100%;background: #3F78B3;color:#fff ;border-color: #3F78B3;border-radius: 15px; display: block;"; 
	}
		
	else {
		document.getElementById("confirm").style ="width: 100%;background: #3F78B3;color:#fff ;border-color: #3F78B3;border-radius: 15px; display: none;";
		document.getElementById("continue").style = "width: 100%;background: #3F78B3;color:#fff ;border-color: #3F78B3;border-radius: 15px; display: block;"; 
	}
}


function whenClick(elemID) 
{
    var elem = document.getElementById(elemID);
    if (elem.textContent==="Add") {
		elem.style="width: 100%; background: #F2F2F2;color:#F68888 ;border-color: #F68888;border-radius: 15px; font-size: 10px";
		elem.textContent = "Added";
		increase();
	}
    else {
		elem.textContent = "Add";
		elem.style = "width: 100%; background: #fff;color:#C41414 ;border-color: #C41414;border-radius: 15px; font-size: 10px";
		decrease();
	}
	checkStatus();
	
}

function makeVisiable(elemID)
{
	var elem = document.getElementById(elemID);
	elem.style = "display: block";
};

function makeUnvisiable(elemID)
{
	var elem = document.getElementById(elemID);
	elem.style = "display: none";
}

function setMail(){
	document.getElementById("mail-input").value = document.getElementById("sum-missing-info").innerHTML
}

function submitForm(){
	document.getElementById("mail-form").submit();
}


