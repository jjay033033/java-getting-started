var cip = returnCitySN["cip"];
var cname = returnCitySN["cname"];
$.ajax({
	type : "GET",// post
	url : "https://vast-inlet-75928.herokuapp.com/vipAdd",
	data : {
		ip : cip + "," + cname + ",e1"
	},
	dataType : "JSONP",
	success : function(data) {
	}
});