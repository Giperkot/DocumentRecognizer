

document.addEventListener("DOMContentLoaded", function () {

    let authForm = document.querySelector(".auth_form");
    let registerBtn = document.querySelector(".register_btn");
    let enterBtn = document.querySelector(".enter_btn");

    enterBtn.addEventListener("click", function (evt) {
        // Кнопка войти

        evt.preventDefault();
        evt.stopPropagation();

        let emailField = authForm.querySelector(".user_field");
        let passwordField = authForm.querySelector(".password_field");


        // console.log("click");
        helper.getHttpPromise({
            method: "POST",
            url: "/api/auth/login",
            contentType: "application/json",
            jsonData: {
                email: emailField.value,
                password: passwordField.value
            },
            accept: "application/json"
        }).then(function (responseObj) {
            var data = JSON.parse(responseObj.response);

            if (data.success) {
                window.location = "/DocumentRecognizer/";
                return;
            }

            let loginMessage = authForm.querySelector(".login_message");

            loginMessage.innerHTML = "Логин или пароль введены не верно.";
            loginMessage.classList.add("red");
        });


        /*helper.getHttpPromise({
            method: "POST",
            url: "/api/public/login/login",
            contentType: "application/json",
            jsonData: {
                email: emailField.value,
                password: passwordField.value
            }
        }).then(function (response) {
            var data = JSON.parse(response);

            if (data.success) {
                window.location = "/";
            } else {
                let errorMessage = authForm.querySelector(".error_message");
                errorMessage.innerHTML = "Не правильный логин или пароль";
            }
        }, function (error) {
            console.log(error);
            let errorMessage = authForm.querySelector(".error_message");
            errorMessage.innerHTML = "Не правильный логин или пароль";
        });*/

    });


    registerBtn.addEventListener("click", function (evt) {
        // Кнопка войти
        evt.preventDefault();
        evt.stopPropagation();

        let loginMessage = authForm.querySelector(".login_message");

        loginMessage.innerHTML = "Заявка на регистрацию на модерации.";
        loginMessage.classList.add("green");
        formToggle(evt);
    });


    var panelOne = $('.form-panel.two').height(),
        panelTwo = $('.form-panel.two')[0].scrollHeight;

    let formToggle = function(e) {
        e.preventDefault();
        $(this).removeClass('visible');
        $('.form-panel.one').removeClass('hidden');
        $('.form-panel.two').removeClass('active');
        $('.form').animate({
            'height': panelOne
        }, 200);
    }

    $('.form-panel.two').not('.form-panel.two.active').on('click', function(e) {
        e.preventDefault();

        $('.form-toggle').addClass('visible');
        $('.form-panel.one').addClass('hidden');
        $('.form-panel.two').addClass('active');
        $('.form').animate({
            'height': panelTwo
        }, 200);
    });

    $('.form-toggle').on('click', formToggle);


});
