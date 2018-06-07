$(window).on('resize', function(){
    if ($(window).width() <= 768) {

        $('.companies').addClass('collapse');
    } else {
        $('.companies').removeClass('collapse');
    }
});