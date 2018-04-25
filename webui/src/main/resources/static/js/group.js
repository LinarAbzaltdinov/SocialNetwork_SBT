function addUserToGroup(groupId) {
    $.ajax({
        url: '/groups/'+groupId+'/addUser',
        method: 'get',
        success: function(){
            alert('Добавлен');
        },
        error: function(){
            alert('failure');
        }
    });
}

function removeUserFromGroup(groupId) {
    $.ajax({
        url: '/groups/'+groupId+'/removeUser',
        method: 'get',
        success: function(){
            alert('Удален');
        },
        error: function(){
            alert('failure');
        }
    });
}