$(document).ready(function() {
	var currentPage = getParam("pageNo");
	if(currentPage==null){
		currentPage = 1;
	}
	refreshTable(currentPage);

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

//<table id="vtable" border="1" width="100%">
//<tr>
//	<td>时间</td>
//	<td>IP</td>
//</tr>
//<tr id="vtr" th:each="vo : ${list}">
//	<td th:text="${vo.ctime}"></td>
//	<td th:text="${vo.remark}"></td>
//</tr>
//</table>
function refreshTable(pageNo) {

	$.ajax({
		type : "GET",
		url : "/vipGetJson",
		data : {
			pageNo : pageNo
		},
		dataType : "json",
		success : function(data) {
			
			var html = '<tr><td>时间</td><td>IP</td></tr>';
			$.each(data.list, function(lIndex, vo) {
				html += '<tr><td>' + vo.ctime
						+ '</td><td>' + vo.remark
						+ '</td></tr>';
			});
			html += '</table>';
			$('#vtable').html(html);
			//为页面添加翻页函数
			$('.pagination').jqPagination({
				link_string : '/vipGet?pageNo={page_number}',
				max_page : data.totalPage,
				paged : function(page) {
					//$('.log').prepend('<li>Requested page ' + page + '</li>');
					$('#vtable').empty(); // 清空resText里面的所有内容
					$('#vtable').html("Loading.....");
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