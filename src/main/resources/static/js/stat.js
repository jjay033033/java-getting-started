var datas = window.btoa(encodeURI(returnCitySN["cip"] + "," + returnCitySN["cname"] + ","+window.location.pathname));
$.ajax({
	type : "GET",// post
	url : "https://vast-inlet-75928.herokuapp.com/vipAdd",
	data : {
		datas : datas
	},
	dataType : "JSONP",
	success : function(data) {
	}
});