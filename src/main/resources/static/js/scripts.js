$(document).ready(function() {
//	var currentPage = getParam("pageNo");
//	if(currentPage==null){
//		currentPage = 1;
//	}
	refreshTable(1);

});


function getParam(name){
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null){
    	 return unescape(r[2]); 
     }else{
    	 return null;
     }
}

function refreshTable(pageNo) {
	$('#vtable').html("Loading.....");
	$.ajax({
		type : "GET",
		url : "/vipGetJson",
		data : {
			pageNo : pageNo
		},
		dataType : "json",
		success : function(data) {
			
			var html = '<tr><th style="width: 210px;">TIME</th><th>IP</th></tr>';
			$.each(data.list, function(lIndex, vo) {
				html += '<tr><td>' + vo.ctime
						+ '</td><td>' + vo.remark
						+ '</td></tr>';
			});
			html += '</table>';
			$('#vtable').html(html);
			//移除页面旧翻页函数
			$('.pagination').unbind();
			//为页面添加新翻页函数
			$('.pagination').jqPagination({
				link_string : '/vipGet?pageNo={page_number}',
				max_page : data.totalPage,
				current_page : pageNo,
				paged : function(page) {			
					refreshTable(page);
				}
			});
		}
	});

}

Date.prototype.format = function (fmt) { //
    var o = {
        "M+": this.getMonth() + 1, //Month
        "d+": this.getDate(), //Day
        "h+": this.getHours(), //Hour
        "m+": this.getMinutes(), //Minute
        "s+": this.getSeconds(), //Second
        "q+": Math.floor((this.getMonth() + 3) / 3), //Season
        "S": this.getMilliseconds() //millesecond
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}