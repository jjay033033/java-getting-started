$(document).ready(function() {

	$('.pagination').jqPagination({
		link_string	: '/?pageNo={page_number}',
		max_page	: $('#total'),
		paged		: function(page) {
			$('.log').prepend('<li>Requested page ' + page + '</li>');
		}
	});

});