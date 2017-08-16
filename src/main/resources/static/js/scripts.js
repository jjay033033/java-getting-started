$(document).ready(function() {

	$('.pagination').jqPagination({
		link_string : '/vipGet?pageNo={page_number}',
		max_page : $('#totalPage').val(),
		paged : function(page) {
			// alert(page);
			$('.log').prepend('<li>Requested page ' + page + '</li>');
//			window.location.href = '/vipGet?pageNo=' + page;
			refreshTable('/vipGetJson',page);
		}
	});

});

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
function refreshTable(url,pageNo) {

	$.ajax({
		type : "GET",
		url : url,
		data : {
			pageNo : pageNo
		},
		dataType : "json",
		success : function(data) {
			$('#vtable').empty(); // 清空resText里面的所有内容
			var html = '<tr><td>时间</td><td>IP</td></tr>';
			$.each(data.list, function(lIndex, vo) {
				html += '<tr><td>' + vo.ctime
						+ '</td><td>' + vo.remark
						+ '</td></tr>';
			});
			html += '</table>';
			$('#vtable').html(html);
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