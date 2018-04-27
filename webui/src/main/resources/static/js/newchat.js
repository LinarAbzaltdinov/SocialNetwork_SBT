var userlist = $('#userlist');

$('#openedChatCB').change(function (e) {
    if (e.target.checked) {
        userlist.hide();
    } else {
        userlist.show();
    }
})