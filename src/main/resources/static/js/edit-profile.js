

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
		var elem4 = document.getElementById('addr-details');
		elem.value = elem4.value;    
	  };

function submitForm()
{
    document.getElementById("onlyForm").submit();
}
	