var deleteChatButton = $('#deleteChatButton').parent();

$('nav.nav-sidebar#left-nav li a').click(function (e) {
    $('nav.nav-sidebar#left-nav ul').find('li.active').removeClass('active');
    $(this).parent().addClass('active');
    selectedChat = $($(this).attr("href"));
    selectedChat.parent().children().hide();
    selectedChat.show();//addClass('in active');
    if (selectedChat.attr('id').startsWith("chat")) {
        deleteChatButton.show();
        deleteChatButton.prev().show();
        chatId = selectedChat.attr('id').substring(4);
        loadChatMessages(chatId);
    } else {
        deleteChatButton.hide();
        deleteChatButton.prev().hide();
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
            messageUL.empty();
            for (i = 0; i < data.length; ++i) {
                message = data[i];
                addMessageLItoUL(
                    message.user.uuid,
                    message.user.firstName + ' ' + message.user.lastName,
                    message.content,
                    message.createdDateTime,
                    messageUL);
            }
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
            userName = $('#userFullName').text();
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

function addUserToGroup(groupId) {
    $.ajax({
        url: '/groups/' + groupId + '/addUser',
        method: 'post',
        success: function () {
            groupRow = $('#allgroup' + groupId);
            myGroupRow = groupRow.clone();
            groupDataCells = $('#allgroup' + groupId).children();
            groupDataCells.first().find('a').removeAttr('onclick');
            countCell = groupDataCells.first().next();
            countCell.text(parseInt(countCell.text()) + 1);

            myGroupRow = groupRow.clone();

            icon = groupDataCells.last().find('i');
            icon.attr('class', 'fa fa-check');
            icon.unwrap();

            myGroupRow.attr('id', 'mygroup' + groupId)
            iconInMyGroups = myGroupRow.find('i');
            iconInMyGroups.attr('class', 'fa fa-times');
            iconInMyGroups.parent().attr('href', 'javascript:removeUserFromGroup(' + groupId + ')');
            mygroups = $('#myGroups table tbody').prepend(myGroupRow);
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function removeUserFromGroup(groupId) {
    isConfirmed = confirm("Вы точно хотите покунить группу?");
    if (!isConfirmed) {
        return false;
    }
    $.ajax({
        url: '/groups/' + groupId + '/removeUser',
        method: 'delete',
        success: function () {
            groupRow = $('#mygroup' + groupId);
            groupRow.remove();
            allgroupRow = $('#allgroup' + groupId);
            allgroupRow.children().first().find('a').attr('onclick', 'return false;');
            counter = allgroupRow.children().eq(1);
            counter.text(parseInt(counter.text()) - 1);
            icon = allgroupRow.find('i');
            icon.attr('class', 'fa fa-plus');
            icon.wrap('<a/>');
            icon.parent().attr('href', 'javascript:addUserToGroup(' + groupId + ')');
        },
        error: function (e) {
            console.log(e);
        }
    });
    return true;
}

function removeGroup(groupId) {
    isConfirmed = confirm("Удалить группу?");
    if (!isConfirmed) {
        return false;
    }
    $.ajax({
        url: '/groups/' + groupId,
        method: 'delete',
        success: function () {
            $('#createdbyme' + groupId).remove();
            $('#allgroup' + groupId).remove();
            $('#mygroup' + groupId).remove();
        },
        error: function (e) {
            console.log(e);
        }
    });
    return true;
}

$('#deleteChatButton').click(function (e) {
    selected = $('nav.nav-sidebar#left-nav li.active a');
    selectedChatName = selected.text();
    isConfirmed = confirm('Вы точно хотите удалить чат "' + selectedChatName + '"?');
    if (!isConfirmed) {
        return false;
    }
    selectedChatId = selected.attr('href').substring(5);
    $.ajax({
        url: '/chat/' + selectedChatId,
        method: 'delete',
        success: function () {
            location.href = location.pathname;
        },
        error: function (e) {
            console.log(e);
        }
    });
});

$('#deleteGroupButton').click(function () {
    if (removeGroup(currentGroupId)) {
        location.href = '/groups';
    }
});

$('#deleteUserFromGroupButton').click(function () {
    if (removeUserFromGroup(currentGroupId)) {
        location.href = '/groups';
    }
})