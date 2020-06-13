 function enableButton2() {
			if(document.getElementById("extra-1").style == "visibility: hidden;"){
				document.getElementById("button1").style ="width: 95%;background: #fff;color:#A0A0A0 ;border-color: #A0A0A0;border-radius: 15px;";
				document.getElementById("extra-1").style = "";
				document.getElementById("id-input").required = "";
				document.getElementById("extra-1").title = "e1-0";

				
			}
            else{
				document.getElementById("button1").style = "width: 95%;background: #7AAEE4; color:#fff ;border-color: #3F78B3; border-radius: 15px;";
				document.getElementById("button3").style ="width: 95%;background: #fff;color:#A0A0A0 ;border-color: #A0A0A0;border-radius: 15px;";
				document.getElementById("extra-1").style = "";
				document.getElementById("id-input").required = "true";
				document.getElementById("extra-1").title = "e1-1";
			}
        };
	
function disableButton2() {
						
			if(document.getElementById("extra-1").style == "visibility: hidden;"){
				document.getElementById("button3").style ="width: 95%;background: #fff;color:#A0A0A0 ;border-color: #A0A0A0;border-radius: 15px;";
				document.getElementById("button1").style ="width: 95%;background: #fff;color:#A0A0A0 ;border-color: #A0A0A0;border-radius: 15px;";
				document.getElementById("extra-1").style = "";
				document.getElementById("id-input").required = "";
				document.getElementById("extra-1").title = "e1-0";
				
			}
            else{	
				document.getElementById("button3").style = "width: 95%;background: #7AAEE4; color:#fff ;border-color: #3F78B3; border-radius: 15px;";
				document.getElementById("button1").style ="width: 95%;background: #fff;color:#A0A0A0 ;border-color: #A0A0A0;border-radius: 15px;";
				document.getElementById("extra-1").style = "visibility: hidden";
				document.getElementById("id-input").required = "";
				document.getElementById("extra-1").title = "e1-0";
				back_6();
			}
		};
		

function enableButton5() {
			if(document.getElementById("extra-2").style == "visibility: hidden;"){
				document.getElementById("button4").style ="width: 95%;background: #fff;color:#A0A0A0 ;border-color: #A0A0A0;border-radius: 15px;";
				document.getElementById("extra-2").style = "";
				document.getElementById("work-input").required = "";
				document.getElementById("extra-2").title = "e2-0";
				
			}
            else{
				document.getElementById("button4").style = "width: 95%;background: #7AAEE4; color:#fff ;border-color: #3F78B3; border-radius: 15px;";
				document.getElementById("button6").style ="width: 95%;background: #fff;color:#A0A0A0 ;border-color: #A0A0A0;border-radius: 15px;";
				document.getElementById("extra-2").style = "";
				document.getElementById("work-input").required = "true";
				document.getElementById("extra-2").title = "e2-1";
			}
        };
	
function disableButton5() {
						
			if(document.getElementById("extra-2").style == "visibility: hidden;"){
				document.getElementById("button6").style ="width: 95%;background: #fff;color:#A0A0A0 ;border-color: #A0A0A0;border-radius: 15px;";
				document.getElementById("button4").style ="width: 95%;background: #fff;color:#A0A0A0 ;border-color: #A0A0A0;border-radius: 15px;";
				document.getElementById("extra-2").style = "";
				document.getElementById("work-input").required = "";
				document.getElementById("extra-2").title = "e2-0";
				
			}
            else{	
				document.getElementById("button6").style = "width: 95%;background: #7AAEE4; color:#fff ;border-color: #3F78B3; border-radius: 15px;";
				document.getElementById("button4").style ="width: 95%;background: #fff;color:#A0A0A0 ;border-color: #A0A0A0;border-radius: 15px;";
				document.getElementById("extra-2").style = "visibility: hidden";
				document.getElementById("work-input").required = "";
				document.getElementById("extra-2").title = "e2-0";
				back_7();
			}
        };
		


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



function createAddress()
      {
		var elem = document.getElementById('address-full');
        var elem1 = document.getElementById('country');  
		var elem2 = document.getElementById("city");
		var elem3 = document.getElementById("town");
		var elem4 = document.getElementById('addr-details');

		elem.value += elem4.value;
		elem.value += " ";
		elem.value += elem3.value;
		elem.value += " ";
		elem.value += elem2.value;
		elem.value += " ";
		elem.value += elem1.value;

        
	  };
	  

function show_1(){
	var elem = document.getElementById('info1');
	elem.style = "color: #9C9C9C; text-align: center;";
	var file = document.getElementById("photo-input").value;
	var fileList = file.split('\\');
	var filename = fileList[fileList.length - 1];
	elem.innerHTML = "";
	elem.innerHTML = filename;
	var elem = document.getElementById('x1');
	elem.style = style="height:25px; width: 25px; border-color: rgb(148, 85, 98); border-radius: 20px; color: rgb(179, 82, 101); ";
	document.getElementById('upload1').style =  style="display:none";

};

function back_1(){
	document.getElementById("photo-input").value = "";
	var elem = document.getElementById('x1');
	elem.style = style="height:25px; width: 25px; border-color: rgb(148, 85, 98); border-radius: 20px; color: rgb(179, 82, 101); visibility: hidden;";
	var elem = document.getElementById('info1');
	elem.innerHTML = "";
	document.getElementById('upload1').style  =  "";
	

};

function show_2(){
	var elem = document.getElementById('info2');
	elem.style = "color: #9C9C9C; text-align: center;";
	var file = document.getElementById("trans-input").value;
	var fileList = file.split('\\');
	var filename = fileList[fileList.length - 1];
	elem.innerHTML = "";
	elem.innerHTML = filename;
	var elem = document.getElementById('x2');
	elem.style = style="height:25px; width: 25px; border-color: rgb(148, 85, 98); border-radius: 20px; color: rgb(179, 82, 101); ";
	document.getElementById('upload2').style =  style="display:none";

};

function back_2(){
	document.getElementById("trans-input").value = "";
	var elem = document.getElementById('x2');
	elem.style = style="height:25px; width: 25px; border-color: rgb(148, 85, 98); border-radius: 20px; color: rgb(179, 82, 101); visibility: hidden;";
	var elem = document.getElementById('info2');
	elem.innerHTML = "";
	document.getElementById('upload2').style  =  "";
	

};


function show_3(){
	var elem = document.getElementById('info3');
	elem.style = "color: #9C9C9C; text-align: center;";
	var file = document.getElementById("ales-input").value;
	var fileList = file.split('\\');
	var filename = fileList[fileList.length - 1];
	elem.innerHTML = "";
	elem.innerHTML = filename;
	var elem = document.getElementById('x3');
	elem.style = style="height:25px; width: 25px; border-color: rgb(148, 85, 98); border-radius: 20px; color: rgb(179, 82, 101); ";
	document.getElementById('upload3').style =  style="display:none";

};

function back_3(){
	document.getElementById("ales-input").value = "";
	var elem = document.getElementById('x3');
	elem.style = style="height:25px; width: 25px; border-color: rgb(148, 85, 98); border-radius: 20px; color: rgb(179, 82, 101); visibility: hidden;";
	var elem = document.getElementById('info3');
	elem.innerHTML = "";
	document.getElementById('upload3').style  =  "";
	

};


function show_4(){
	var elem = document.getElementById('info4');
	elem.style = "color: #9C9C9C; text-align: center;";
	var file = document.getElementById("ee-input").value;
	var fileList = file.split('\\');
	var filename = fileList[fileList.length - 1];
	elem.innerHTML = "";
	elem.innerHTML = filename;
	var elem = document.getElementById('x4');
	elem.style = style="height:25px; width: 25px; border-color: rgb(148, 85, 98); border-radius: 20px; color: rgb(179, 82, 101); ";
	document.getElementById('upload4').style =  style="display:none";

};

function back_4(){
	document.getElementById("ee-input").value = "";
	var elem = document.getElementById('x4');
	elem.style = style="height:25px; width: 25px; border-color: rgb(148, 85, 98); border-radius: 20px; color: rgb(179, 82, 101); visibility: hidden;";
	var elem = document.getElementById('info4');
	elem.innerHTML = "";
	document.getElementById('upload4').style  =  "";
	

};

function show_5(){
	var elem = document.getElementById('info5');
	elem.style = "color: #9C9C9C; text-align: center;";
	var file = document.getElementById("ref-input").value;
	var fileList = file.split('\\');
	var filename = fileList[fileList.length - 1];
	elem.innerHTML = "";
	elem.innerHTML = filename;
	var elem = document.getElementById('x5');
	elem.style = style="height:25px; width: 25px; border-color: rgb(148, 85, 98); border-radius: 20px; color: rgb(179, 82, 101); ";
	document.getElementById('upload5').style =  style="display:none; padding-top:5px;";

};

function back_5(){
	document.getElementById("ref-input").value = "";
	var elem = document.getElementById('x5');
	elem.style = style="visibility: hidden;";
	var elem = document.getElementById('info5');
	elem.innerHTML = "";
	document.getElementById('upload5').style  =  "padding-top:5px;";
	

};


function show_6(){
	var elem = document.getElementById('info6');
	elem.style = "color: #9C9C9C; text-align: center;";
	var file = document.getElementById("id-input").value;
	var fileList = file.split('\\');
	var filename = fileList[fileList.length - 1];
	elem.innerHTML = "";
	elem.innerHTML = filename;
	var elem = document.getElementById('x6');
	elem.style = style="height:25px; width: 25px; border-color: rgb(148, 85, 98); border-radius: 20px; color: rgb(179, 82, 101); ";
	document.getElementById('upload6').style =  style="display:none";

};

function back_6(){
	document.getElementById("id-input").value = "";
	var elem = document.getElementById('x6');
	elem.style = style="height:25px; width: 25px; border-color: rgb(148, 85, 98); border-radius: 20px; color: rgb(179, 82, 101); visibility: hidden;";
	var elem = document.getElementById('info6');
	elem.innerHTML = "";
	document.getElementById('upload6').style  =  "";

};




function show_7(){
	var elem = document.getElementById('info7');
	elem.style = "color: #9C9C9C; text-align: center;";
	var file = document.getElementById("work-input").value;
	var fileList = file.split('\\');
	var filename = fileList[fileList.length - 1];
	elem.innerHTML = "";
	elem.innerHTML = filename;
	var elem = document.getElementById('x7');
	elem.style = style="height:25px; width: 25px; border-color: rgb(148, 85, 98); border-radius: 20px; color: rgb(179, 82, 101); ";
	document.getElementById('upload7').style =  style="display:none";

};

function back_7(){
	document.getElementById("work-input").value = "";
	var elem = document.getElementById('x7');
	elem.style = style="height:25px; width: 25px; border-color: rgb(148, 85, 98); border-radius: 20px; color: rgb(179, 82, 101); visibility: hidden;";
	var elem = document.getElementById('info7');
	elem.innerHTML = "";
	document.getElementById('upload7').style  =  "";

}

function show_8(){
	var elem = document.getElementById('info8');
	elem.style = "color: #9C9C9C; text-align: center;";
	var file = document.getElementById("phd-input").value;
	var fileList = file.split('\\');
	var filename = fileList[fileList.length - 1];
	elem.innerHTML = "";
	elem.innerHTML = filename;
	var elem = document.getElementById('x8');
	elem.style = style="height:25px; width: 25px; border-color: rgb(148, 85, 98); border-radius: 20px; color: rgb(179, 82, 101); ";
	document.getElementById('upload8').style =  style="display:none";

};

function back_8(){
	document.getElementById("phd-input").value = "";
	var elem = document.getElementById('x8');
	elem.style = style="height:25px; width: 25px; border-color: rgb(148, 85, 98); border-radius: 20px; color: rgb(179, 82, 101); visibility: hidden;";
	var elem = document.getElementById('info8');
	elem.innerHTML = "";
	document.getElementById('upload8').style  =  "";

};


function checkRequiredInputs(){
	var count = 0;

	 if(document.getElementById("photo-input").value == ""){
		var elem = document.getElementById('info1');
		elem.style = "color: red"
		elem.innerHTML = "Please upload a photo.";
		count ++;
	 }

	 if(document.getElementById("trans-input").value == ""){
		var elem = document.getElementById('info2');
		elem.style = "color: red"
		elem.innerHTML = "Please upload a file.";
		count ++;
	 }

	 if(document.getElementById("ales-input").value == ""){
		var elem = document.getElementById('info3');
		elem.style = "color: red"
		elem.innerHTML = "Please upload a file.";
		count ++;
	 }

	 if(document.getElementById("ref-input").value == ""){
		var elem = document.getElementById('info5');
		elem.style = "color: red"
		elem.innerHTML = "Please upload a file.";
		count ++;
	 }

	 if(document.getElementById("id-input").value == ""){
		if(document.getElementById("extra-1").title == "e1-1"){
			var elem = document.getElementById('info6');
			elem.style = "color: red"
			elem.innerHTML = "Please upload a file.";
			count ++;
		}
		
	 }

	 if(document.getElementById("work-input").value == ""){
		if(document.getElementById("extra-2").title == "e2-1"){
			var elem = document.getElementById('info7');
			elem.style = "color: red"
			elem.innerHTML = "Please upload a file.";
			count ++;
		}
		
	 }

	 if(document.getElementById("phd-input").value == ""){
		var elem = document.getElementById('info8');
		elem.style = "color: red"
		elem.innerHTML = "Please upload a file.";
		count ++;
	 }

	 if(count > 0){
		 document.getElementById("apply-error").style = "color: red; text-align: center;";
	 }
	 else{
		document.getElementById("apply-error").style = "color: red; text-align: center;  display:none";
	 }

	 if(count == 0){
	 	document.getElementById("applicationForm").submit();
	 }

		
};

 function addPropopose(){
	 var elem = document.getElementById('propose-input');
	 var elem1 = document.getElementById('propose');
	 elem.innerHTML = elem1.value;
 };