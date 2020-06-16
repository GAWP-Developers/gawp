
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

function addInvite(){
	// document.getElementById('mail-input').value = document.getElementById('invite-text').value;
	let text = document.getElementById('invite-text').value;
	text = text.replace(/\r\n/g, "\n");
	text = text.replace(/\n/g, "newLineBreak\n");
	console.log(text);
	document.getElementById('mail-input').value = text;
	return document.getElementById('invite-text').innerHTML;
};


function updateInvite(){
	var elem = document.getElementById('invite-text');
	var fname = document.getElementById('fname');
	var sname = document.getElementById('sname');
	var aname = document.getElementById('aname'); //Advert Name
	var idate = document.getElementById('idate');
	var itime = document.getElementById('itime');
	var iplace = document.getElementById('iplace');
	let text = "Dear ";
	text += fname.value;
	text += " ";
	text += sname.value;
	text += ",\nCongratulations, you have made a successful application. We have reviewed your application and would like to call you for an interview. You can find the interview date below:";
	text += "\n	Advert Name: ";
	text += aname.value;
	text += "\n	Date of the interview: ";
	text += idate.value;
	text += "\n	Time of the interview: ";
	text += itime.value;
	text += "\n	Place of the interview: ";
	text += iplace.value;
	text += "\n\nBest Regards";

	elem.innerHTML = text;
	text = text.replace(/\r\n/g, "\n");
	text = text.replace(/\n/g, "newLineBreak\n");
	console.log(text);
	document.getElementById('mail-input').value = text;

	// document.getElementById('mail-input').value = document.getElementById('invite-text').textContent;
	addInvite();

}


function replaceLineBreaks(){
	document.getElementById('invite-text').value.replace(/\r\n/g, "\n");
	document.getElementById('invite-text').value.replace(/\n/g, "%$\n");
}

function go2(){
	if (checkReq()){
		document.getElementById('step1').style = "display:none";
		document.getElementById('step2').style = "";
		document.getElementById('cancel').style = "display:none";
		document.getElementById('next').style = "display:none";
		document.getElementById('back').style = "width: 100%;background: #fff;color:#3F78B3 ;border-color: #3F78B3;border-radius: 15px;";
		document.getElementById('apply').style = "";
	}
	
};

function go1(){
	
	document.getElementById('step2').style = "display:none";
	document.getElementById('step1').style = "";
	document.getElementById('back').style = "display:none";
	document.getElementById('apply').style = "display:none";
	document.getElementById('cancel').style = "width: 100%;background: #fff;color:#3F78B3 ;border-color: #3F78B3;border-radius: 15px;";
	document.getElementById('next').style = "width: 100%;background: #3F78B3;color:#fff ;border-color: #3F78B3;border-radius: 15px;";

	
};


function checkReq(){

	if ((document.getElementById('idate').value == "") || (document.getElementById('itime').value == "") ||  (document.getElementById('iplace').value == "")){
		document.getElementById('apply-error').style = "color: red; text-align: center; ";
		return 0;
	}
	else{
		document.getElementById('apply-error').style = "display:none";
		return 1;
	}
	
};

function submitTheForm(){
	console.log(document.getElementById(''))
}