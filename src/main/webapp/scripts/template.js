var currentLink = location.href;
var tabLinks = $('.tab-link');
tabLinks.each(function() {
	var tab = $(this);
	if (tab.prop('href') == currentLink) {
		tab.addClass('active')
		return false;
	}
});