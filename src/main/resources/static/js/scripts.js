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
			var html = '<tr><td>时间</td><td>IP</td></tr><tr>';
			$.each(data.list, function(lIndex, vo) {
				html += '<td>' + new Date(vo.ctime).format("yyyy-MM-dd hh:mm:ss")
						+ '</td><td>' + vo.remark
						+ '</td>';
			});
			html += '</tr></table>';
			$('#vtable').html(html);
		}
	});

}