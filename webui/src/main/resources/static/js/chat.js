






$('nav.nav-sidebar li a').click(function (e) {

    $(this).addClass("in active")
})

$('[id^=btn-chat]').on('click', function (e) {
    var chatId = e.target.id.split('-').pop();
    sendMessage(chatId);
});

$('[id^=message-input]').keypress(function (e) {
    var key = e.which;
    if (key == 13) {
        var chatId = e.target.id.split('-').pop();
        sendMessage(chatId);
    }
});

function sendMessage(chatId) {
    messageText = $('#message-input-'+chatId).val();
    if (!messageText) {
        return false;
    }
    $.ajax({
        url: '/chat/' + chatId + '/sendMessage',
        method: 'post',
        data: {"messageText": messageText},
        success: function () {
            userName = $('#userFirstName').text();
            messageListItem = '<li class="right clearfix"><span class="chat-img pull-right">' +
                '<img src="/images/avatar.jpg" alt="User Avatar" class="img-circle" height="50px"/>' +
                '</span><div class="chat-body clearfix"><div class="header">' +
                '<strong class="pull-right primary-font">' + userName + '</strong>' +
                '</div><p>' + messageText + '</p></div></li>';
            $('ul.chat').append(messageListItem);
            window.scrollTo(0, document.body.scrollHeight);
            messageElem.val('');
            messageElem.focus();
        },
        error: function (e) {
            alert(JSON.stringify(e));
        }
    });
};