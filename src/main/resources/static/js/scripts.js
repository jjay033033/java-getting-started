$(document).ready(function() {

	$('.pagination').jqPagination({
		link_string	: '/vipGet?pageNo={page_number}',
		max_page	: $('#total.text'),
		paged		: function(page) {
			$('.log').prepend('<li>Requested page ' + page + '</li>');
		}
	});

});