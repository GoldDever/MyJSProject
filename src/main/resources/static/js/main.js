jQuery(document).ready(function() {
    $('#new-user-form-btn-save').on('click', function(event) { createUser(); });
    $('#modal-edit-btn-save').on('click', function(event) { updateUser(); });
    updateUsers();
});

function openEditTab(id) {
    $('#edit-user-form').trigger('reset');
    $('#modal-edit').modal();
    getUser(id, function(user) {
        $('#edit-user-form [name=id]').val(user.id);
        $('#edit-user-form [name=name]').val(user.name);
        $('#edit-user-form [name=lastName]').val(user.lastName)
        $('#edit-user-form [name=age]').val(user.age);
        $('#edit-user-form [name=login]').val(user.login);
        $('#edit-user-form [name=password]').val(user.password);
        $('#edit-user-form [name=roles]').val(user.roles?.map(function(a) { return a.authority; }));
    });
}

function redrawTable(userList) {
    var $table = $('.table-users tbody');
    $table.empty();
    userList.forEach((user, index) => {
        var authorities = user.role.map(function(a) { return a.authority ; }).join('<br/>');
        var btnEdit = '<a class="btn btn-primary btn-edit" data-id="' + user.id + '">Edit</a>';
        var btnDelete = '<a class="btn btn-danger btn-delete" data-id="' + user.id + '">Delete</a>';
        $table.append('<tr>' +
            '<td>' + user.id + '</td>' +
            '<td>' + user.name + '</td>' +
            '<td>' + user.lastName + '</td>' +
            '<td>' + user.age + '</td>' +
            '<td>' + user.login + '</td>' +
            '<td>' + authorities + '</td>' +
            '<td>' + btnEdit + '</td>' +
            '<td>' + btnDelete + '</td>' +
            '</tr>');
    });
    $('.btn-delete').on('click', function(event) { deleteUser( event.currentTarget.getAttribute('data-id')); });
    $('.btn-edit').on('click',   function(event) { openEditTab(event.currentTarget.getAttribute('data-id')); });
}





function redrawTableUser(userList) {
    var $table = $('.table-user tbody');
    $table.empty();
    userList((user, index) => {
        var authorities = user.roles.map(function(a) { return a.authority; }).join('<br/>');

        $table.append('<tr>' +
            '<td>' + user.id + '</td>' +
            '<td>' + user.name + '</td>' +
            '<td>' + user.lastName + '</td>' +
            '<td>' + user.age + '</td>' +
            '<td>' + authorities + '</td>' +
            '</tr>');
    });

}

function updateSoloUser() {
    $.ajax({
        url: '/admin/api/all',
        type: 'get',
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {
            redrawTableUser(data);
        },
        error: function () {
            showError('На сервере произошла ошибка');
        }
    });

}

function getCurrentUser(id) {
    $.ajax({
        url: '/admin/api/',
        type: 'get',
        dataType: 'json',
        contentType: 'application/json',
        success: function(data) { },
        error: function() { showError('На сервере произошла ошибка'); }
    });
}

function getUser(id, func) {
    $.ajax({
        url: '/api/' + id,
        type: 'get',
        dataType: 'json',
        contentType: 'application/json',
        success: function(data) { func(data); },
        error: function() { showError('На сервере произошла ошибка'); }
    });
}

// function

function createUser() {
    var userData = $('#new-user-form').jsonify();
    // if (!validateNewUser(userData)) {
    //     return;
    // }
    // if (typeof userData.roles === 'string') {
    //     userData.roles = [userData.roles];
    // }
    $.ajax({
        url: '/api/adding/addUser',
        type: 'post',
        data: JSON.stringify(userData),
        dataType: 'json',
        contentType: 'application/json',
        success: function(data) {
            redrawTable(data);
            $('#list-tab').click();
            $('#new-user-form').trigger('reset');
        },
        error: function() { showError('На сервере произошла ошибка'); }
    });
}

function updateUser() {
    var userData = $('#edit-user-form').jsonify();
    // if (!validateNewUser(userData)) {
    //     return;
    // }
    if (typeof userData.roles === 'string') {
        userData.roles = [userData.roles];
    }
    $.ajax({
        url: '/api/' + userData.id,
        type: 'put',
        data: JSON.stringify(userData),
        dataType: 'json',
        contentType: 'application/json',
        success: function(data) {
            redrawTable(data);
            $('#modal-edit').modal('toggle');
        },
        error: function() { showError('На сервере произошла ошибка'); }
    });
}

function deleteUser(id) {
    $.ajax({
        url: '/api/' + id,
        type: 'delete',
        dataType: 'json',
        contentType: 'application/json',
        success: function(data) { redrawTable(data); },
        error: function() { showError('На сервере произошла ошибка'); }
    });
}

function updateUsers() {
    $.ajax({
        url: '/api/admin',
        type: 'get',
        dataType: 'json',
        contentType: 'application/json',
        success: function(data) { redrawTable(data); },
        error: function() { showError('На сервере произошла ошибка'); }
    });
}


function validateNewUser(user) {
    if (!user.Name) {
        showError('Не заполнено поле Name');
        return false;
    }

    if (!user.email) {
        showError('Не заполнено поле email');
        return false;
    }
    if (!user.username) {
        showError('Не заполнено поле login');
        return false;
    }
    if (!user.password) {
        showError('Не заполнено поле password');
        return false;
    }
    if (!user.roles) {
        showError('Не заполнено поле Role');
        return false;
    }
    return true;
}

function showError(text) {
    $('#modal-error-text').text(text);
    $('#modal-error').modal();
}