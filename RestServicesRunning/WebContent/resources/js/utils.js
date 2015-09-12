function getFormData(form) {
	var formData = {};
	$(form).find(".dataField").each(function(i, e) {
		if($(e).val()!=""){
			switch (e.tagName) {
			case "INPUT":
				switch (e.type) {
				case "date":
					formData[e.name] = new Date($(e).val());
					break;
				case "time":
					formData[e.name] = $(e).val();
					break;
				case "checkbox":
					formData[e.name] = $(e).is(":checked");
					break;
				default:

					formData[e.name] = $(e).val();
					break;
				}
				break;
			case "TEXTAREA":
				formData[e.name] = $(e).val();
				break;
			case "IMG":
				break;
			case "SELECT":
				formData[e.name] = $(e).find("option:selected").text();
				break;
			default:
				break;
			}
		
		}
	});
	return formData;
}

function mapToForm(data, form) {

	form.find(".dataField").each(function(i, e) {

		switch (e.tagName) {
		case "INPUT":
			switch (e.type) {
			case "date":
				$(e).val(getDateFromTime(data[e.name]));
				break;
			case "checkbox":
				$(e).prop("checked", data[e.name]);
				break;
			default:

				$(e).val(data[e.name]);
				break;
			}
			break;
		case "TEXTAREA":
			$(e).val(data[e.name]);
			break;
		case "IMG":
			$(e).attr("src", data[e.name]);
			break;
		case "SELECT":
			$(e).find("option").filter(function() {
				return $(this).html() == data[e.name];
			}).prop("selected", true)
			break;
		default:
			break;
		}

	})
}

function getDateFromTime(time) {
	var d = new Date(time);
	var month = d.getMonth() + 1;
	return d.getFullYear() + '-' + (month < 10 ? '0' + month : month) + '-'
			+ (d.getDate() < 10 ? '0' + d.getDate() : d.getDate());

}
function getUrlParameter(sParam) {
	var sPageURL = decodeURIComponent(window.location.search.substring(1)), sURLVariables = sPageURL
			.split('&'), sParameterName, i;

	for (i = 0; i < sURLVariables.length; i++) {
		sParameterName = sURLVariables[i].split('=');

		if (sParameterName[0] === sParam) {
			return sParameterName[1] === undefined ? true : sParameterName[1];
		}
	}
};