var cip = returnCitySN["cip"];
var cname = returnCitySN["cname"];
var capp = window.location.pathname;
$.ajax({
	type : "GET",// post
	url : "https://vast-inlet-75928.herokuapp.com/vipAdd",
	data : {
		ip : cip + "," + cname + ","+capp
	},
	dataType : "JSONP",
	success : function(data) {
	}
});