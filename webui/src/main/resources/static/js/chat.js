var messageElem = $('#message-input');

$("#btn-chat").on('click', sendMessage);

messageElem.keypress(function (e) {
    var key = e.which;
    if (key == 13) {
        sendMessage();
    }
});

function sendMessage() {

    messageText = messageElem.val();
    if (!messageText) {
        return false;
    }
    chatId = 1; //>>>>>>>БРАТЬ ЧАТ АЙДИ ОТКУДА-ТО<<<<<<<<<<<<
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