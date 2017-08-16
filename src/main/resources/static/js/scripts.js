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

// <tr id="vtr" th:each="vo : ${list}">
// <td th:text="${vo.ctime}"></td>
// <td th:text="${vo.remark}"></td>
// </tr>
function refreshTable(url,pageNo) {

	$.ajax({
		type : "GET",
		url : url,
		data : {
			pageNo : pageNo
		},
		dataType : "json",
		success : function(data) {
			$('#vtr').empty(); // 清空resText里面的所有内容
			var html = '';
			$.each(data.list, function(lIndex, vo) {
				html += '<td>' + vo['ctime']
						+ '</td><td>' + vo['remark']
						+ '</td>';
			});
			$('#vtr').html(html);
		}
	});

}