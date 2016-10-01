var currentLink = location.href;
console.log("Link: " + currentLink)
console.log("Here");
var tabLinks = $('.tab-link');
tabLinks.each(function() {
	var tab = $(this);
	console.log(tab.prop('href'));
	if (tab.prop('href') == currentLink) {
		console.log('match!');
		tab.addClass('active');
		return false;
	}
});