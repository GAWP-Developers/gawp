 function enableButton2() {
			if(document.getElementById("button2").disabled == false){
				document.getElementById("button2").disabled = true;
				document.getElementById("button2").style = "width: 100%;background: #fff;color:#D6D6D6 ;border-color: #D6D6D6;border-radius: 15px;";
				document.getElementById("button1").style ="width: 95%;background: #fff;color:#A0A0A0 ;border-color: #A0A0A0;border-radius: 15px;";
				document.getElementById("extra-1").style = "visibility: hidden";
				
			}
            else{
				document.getElementById("button2").disabled = false;
				document.getElementById("button2").style = "width: 100%;background: #fff;color:#B29B25 ;border-color: #B29B25;border-radius: 15px;";	
				document.getElementById("button1").style = "width: 95%;background: #7AAEE4; color:#fff ;border-color: #3F78B3; border-radius: 15px;";
				document.getElementById("button3").style ="width: 95%;background: #fff;color:#A0A0A0 ;border-color: #A0A0A0;border-radius: 15px;";
				document.getElementById("extra-1").style = "visibility: visable";
			}
        }
	
function disableButton2() {
			document.getElementById("button2").disabled = true;
			document.getElementById("button2").style = "width: 100%;background: #fff;color:#D6D6D6 ;border-color: #D6D6D6;border-radius: 15px;";
			
			
			if(document.getElementById("button3").style == "width: 95%;background: #7AAEE4; color:#fff ;border-color: #3F78B3; border-radius: 15px;"){
				document.getElementById("button3").style ="width: 95%;background: #fff;color:#A0A0A0 ;border-color: #A0A0A0;border-radius: 15px;";
				document.getElementById("button1").style ="width: 95%;background: #fff;color:#A0A0A0 ;border-color: #A0A0A0;border-radius: 15px;";
				document.getElementById("extra-1").style = "visibility: visable";
				
			}
            else{	
				document.getElementById("button3").style = "width: 95%;background: #7AAEE4; color:#fff ;border-color: #3F78B3; border-radius: 15px;";
				document.getElementById("button1").style ="width: 95%;background: #fff;color:#A0A0A0 ;border-color: #A0A0A0;border-radius: 15px;";
				document.getElementById("extra-1").style = "visibility: hidden";
			}
        }
		
function enableButton5() {
			if(document.getElementById("button5").disabled == false){
				document.getElementById("button5").disabled = true;
				document.getElementById("button5").style = "width: 100%;background: #fff;color:#D6D6D6 ;border-color: #D6D6D6;border-radius: 15px;";
				document.getElementById("button4").style ="width: 95%;background: #fff;color:#A0A0A0 ;border-color: #A0A0A0;border-radius: 15px;";
				
				
				document.getElementById("extra-2").style = "visibility: hidden";
				
			}
            else{
				document.getElementById("button5").disabled = false;
				document.getElementById("button5").style = "width: 100%;background: #fff;color:#B29B25 ;border-color: #B29B25;border-radius: 15px;";	
				document.getElementById("button4").style = "width: 95%;background: #7AAEE4; color:#fff ;border-color: #3F78B3; border-radius: 15px;";
				document.getElementById("button6").style ="width: 95%;background: #fff;color:#A0A0A0 ;border-color: #A0A0A0;border-radius: 15px;";
				document.getElementById("extra-2").style = "visibility: visable";
			}
        }
	
function disableButton5() {
			document.getElementById("button5").disabled = true;
			document.getElementById("button5").style = "width: 100%;background: #fff;color:#D6D6D6 ;border-color: #D6D6D6;border-radius: 15px;";
			
			
			if(document.getElementById("button6").style == "width: 95%;background: #7AAEE4; color:#fff ;border-color: #3F78B3; border-radius: 15px;"){
				document.getElementById("button6").style ="width: 95%;background: #fff;color:#A0A0A0 ;border-color: #A0A0A0;border-radius: 15px;";
				document.getElementById("button4").style ="width: 95%;background: #fff;color:#A0A0A0 ;border-color: #A0A0A0;border-radius: 15px;";
				document.getElementById("extra-2").style = "visibility: visable";
				
			}
            else{	
				document.getElementById("button6").style = "width: 95%;background: #7AAEE4; color:#fff ;border-color: #3F78B3; border-radius: 15px;";
				document.getElementById("button4").style ="width: 95%;background: #fff;color:#A0A0A0 ;border-color: #A0A0A0;border-radius: 15px;";
				document.getElementById("extra-2").style = "visibility: hidden";
			}
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