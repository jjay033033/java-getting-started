$(document).ready(function() {

	$('.pagination').jqPagination({
		link_string	: '/vipGet?pageNo={page_number}',
		max_page	: $('#total').val(),
		paged		: function(page) {
			alert(page);
			$('.log').prepend('<li>Requested page ' + page + '</li>');
		}
	});

});