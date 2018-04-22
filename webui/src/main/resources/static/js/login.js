var minPasswordLength = 8,
    passwordMessage = "Длина пароля должна быть не менее " + minPasswordLength + " символов",
    confirmPasswordMessage = "Пароли не совпадают",
    isValidForm = false;

var confirmPasswordDivNode =
    '<div class="form-group" id="confirm_password_group">' +
    '<label for="confirm_password">Повторите пароль</label>' +
    '<input type="password" class="form-control" id="confirm_password" placeholder="Повторите пароль" required=""/>' +
    '</div>';

$(document).ready(function () {
    let isSignup = document.location.pathname.endsWith("signup");
    let selected_radio = $('#signin_radio');
    if (isSignup) {
        selected_radio = $('#signup_radio');
    }
    selected_radio.prop('checked', true);
    selected_radio.parent().addClass('active');
    changeLoginRegistrationForm(isSignup);
});

$('input[name="sign_type"]').change(function () {
    let isSignup = $('#signup_radio').is(':checked');
    changeLoginRegistrationForm(isSignup);
});

$('#userform').on('submit', function (e) {
    let isSignup = $('#userform').attr('action').toString().endsWith('signup');
    if (isSignup && !isValidForm) {
        e.preventDefault();
    }
});

function changeLoginRegistrationForm(isSignup) {
    label = "";
    password = document.getElementById("password");
    pathname = document.location.pathname;
    pathToPage = pathname.substring(0, pathname.lastIndexOf('/') + 1);
    submitButton = $('#submit-form');
    if (isSignup) {
        submitButton.before(confirmPasswordDivNode);
        let confirm_password = document.getElementById("confirm_password");
        confirm_password.onkeyup = validatePassword;
        password.onchange = validatePassword;
        $('#userform').attr('action', pathToPage + 'signup');
        label = 'Зарегистрироваться';
    } else {
        let confirm_password = document.getElementById("confirm_password");
        if (confirm_password) {
            confirm_password.onkeyup = null;
        }
        $('#confirm_password_group').remove();
        password.onchange = null;
        $('#userform').attr('action', pathToPage + 'login');
        label = 'Войти';
    }
    submitButton.text(label);
}

function validatePassword() {
    let confirm_password = document.getElementById("confirm_password");
    let password = document.getElementById("password");

    let isValidPassword = password.value.length >= minPasswordLength;
    if (isValidPassword) {
        password.setCustomValidity('');
    } else {
        password.setCustomValidity(passwordMessage);
    }
    let isPasswordsEqual = confirm_password.value === password.value;
    if (isPasswordsEqual) {
        confirm_password.setCustomValidity('');
    } else {
        confirm_password.setCustomValidity(confirmPasswordMessage);
    }
    isValidForm = isValidPassword && isPasswordsEqual;
}