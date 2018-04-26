$('nav.nav-sidebar li a').click(function (e) {
    $('nav.nav-sidebar ul').find('li.active').removeClass('active');
    $(this).parent().addClass('active');
    selectedChat = $($(this).attr("href"));
    selectedChat.parent().find('.in.active').removeClass('in active');
    selectedChat.addClass('in active');
    if (selectedChat.attr('id').startsWith("chat")) {
        chatId = selectedChat.attr('id').substring(4);
        loadChatMessages(chatId);
    }
});

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

function loadChatMessages(chatId) {
    $.ajax({
        url: '/chat/' + chatId + '/loadMessages',
        method: 'get',
        success: function (data) {
            messageUL = $('#chat' + chatId + ' ul.chat');
            for (i = 0; i < data.length; ++i) {
                message = data[i];
                addMessageLItoUL(
                    message.user.uuid,
                    message.user.firstName + ' ' + message.user.lastName,
                    message.content,
                    message.createdDateTime,
                    messageUL);
            }
            console.log(JSON.stringify(data));
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function sendMessage(chatId) {
    messageElem = $('#message-input-' + chatId);
    messageText = messageElem.val();
    if (!messageText) {
        return false;
    }
    $.ajax({
        url: '/chat/' + chatId + '/sendMessage',
        method: 'post',
        data: {"messageText": messageText},
        success: function () {
            userName = $('#userFirstName').text();
            addMessageLItoUL(currentUserId, userName, messageText,
                (new Date()).toLocaleString(), $('#chat' + chatId + ' ul.chat'));
            //window.scrollTo(0, document.body.scrollHeight);
            messageElem.val('');
            messageElem.focus();
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function addMessageLItoUL(userId, userName, messageText, dateTime, ulNode) {
    if (userId === currentUserId) {
        messageListItem = '<li class="right clearfix"><span class="chat-img pull-right">' +
            '<img src="/images/avatar.jpg" alt="User Avatar" class="img-circle" height="50px"/>' +
            '</span><div class="chat-body clearfix"><div class="header">' +
            '<small class=" text-muted"><span class="glyphicon glyphicon-time"></span>' + dateTime + '</small>' +
            '<strong class="pull-right primary-font">' + userName + '</strong>' +
            '</div><p>' + messageText + '</p></div></li>';
    } else {
        messageListItem = '<li class="left clearfix"><span class="chat-img pull-left">' +
            '<img src="/images/avatar.jpg" alt="User Avatar" class="img-circle" height="50px"/>' +
            '</span><div class="chat-body clearfix"><div class="header">' +
            '<strong class="primary-font">' + userName + '</strong>' +
            '<small class="pull-right text-muted"><span class="glyphicon glyphicon-time"></span>' + dateTime + '</small>' +
            '</div><p>' + messageText + '</p></div></li>';
    }
    ulNode.append(messageListItem);

}