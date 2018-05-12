$.ajax({
    	url: "/err/getAjaxerror2",
    	type: "POST",
    	async: false,
    	success: function(data) {
            if(data.status == 200 && data.msg == "OK") {
            	alert("success");
            } else {
            	alert("error");
            }
    	},
        error: function (response, ajaxOptions, thrownError) {
        	alert("error");       
        }
    });