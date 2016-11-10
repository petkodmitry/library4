function orderToHome(id) {
    $.ajax({
        url: 'controller?cmd=orderToHome&bookId=' + id,
        context: document.body,
        success: id = 2,

    }).done(function() {

        location.reload();
    });
}
