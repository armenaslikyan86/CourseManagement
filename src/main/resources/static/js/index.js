$(window).on('resize', function(){
    if ($(window).width() <= 768) {

        $('.companies').addClass('collapse');
    } else {
        $('.companies').removeClass('collapse');
    }
});


var modal = document.getElementById('course');

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}