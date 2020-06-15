
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
	document.getElementById('mail-input').value = document.getElementById('invite-text').value;
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
	elem.innerHTML = "Dear ";
	elem.innerHTML += fname.textContent;
	elem.innerHTML += " ";
	elem.innerHTML += sname.textContent;
	elem.innerHTML += ",\nCongratulations, you have made a successful application. We have reviewed your application and would like to call you for an interview. You can find the interview date below:";
	elem.innerHTML += "\n	Advert Name: ";
	elem.innerHTML += aname.textContent;
	elem.innerHTML += "\n	Date of the interview: ";
	elem.innerHTML += idate.value;
	elem.innerHTML += "\n	Time of the  interview: ";
	elem.innerHTML += itime.value;
	elem.innerHTML += "\n	Place of the interview: ";
	elem.innerHTML += iplace.value;
	elem.innerHTML += "\n\nBest Regards";
	document.getElementById('mail-input').value = document.getElementById('invite-text').textContent;



};




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