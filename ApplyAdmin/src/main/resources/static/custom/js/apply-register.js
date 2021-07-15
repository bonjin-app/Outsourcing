(function() {
    'use strict';
    window.addEventListener('load', function() {
        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        var forms = document.getElementsByClassName('needs-validation');
        // Loop over them and prevent submission
        var validation = Array.prototype.filter.call(forms, function(form) {
            form.addEventListener('submit', function(event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });

    }, false);

    $("#addressButton").click(function(event) {
        event.preventDefault();

        performAddress();
    });

    $("#smsButton").click(function() {
        var $phone = $("#phone");
        if(!$phone.val()) {
            alert("핸드폰번호를 입력해주세요.");
            return false;
        }

        $.ajax({
            url: "/api/apply/sms",
            data: {phone: $phone.val()},
            success: function (response) {
                var data = JSON.parse(response.data);
                console.log(data);

                if (data.result_code > 0) {
                    alert("인증번호를 발송하였습니다.");
                    var $messageId = $("#messageId");
                    $messageId.val(data.msg_id);

                    var $input_sms_confirm = $("#input_sms_confirm");
                    $input_sms_confirm.css('display', 'block');

                } else {
                    alert(data.message);
                }
            }
        });
    });

    $("#smsConfirmButton").click(function() {
        var $authCode = $("#authCode");
        if(!$authCode.val() || $authCode.val().length != 6) {
            alert("인증번호를 입력해주세요.");
            return false;
        }

        var $messageId = $("#messageId");

        $.ajax({
            url: "/api/apply/sms/auth",
            method: "POST",
            data: {
                messageId: $messageId.val(),
                authCode: $authCode.val()
            },
            success: function (response) {
                if (response.data.code == "1000") {
                    alert("인증번호가 일치하지 않습니다.");

                } else if (response.data.code == "1001") {
                    alert("인증요청을 다시 진행해주세요.");

                } else {
                    alert("인증에 성공하였습니다.");

                    var $isAuth = $("#isAuth");
                    $isAuth.val(true);

                    $("#phone").attr('disabled', 'disabled');
                    $("#authCode").attr('disabled', 'disabled');
                    $("#smsButton").attr('disabled', 'disabled');
                    $("#smsConfirmButton").attr('disabled', 'disabled');
                    $(".icon-check").css('display', 'block');
                }
            },
            error: function(request, status, error){
                alert("요청중 오류가 발생했습니다. 다시 시도해주세요.")
            }
        });
    });

})();

function isCanvasBlank(canvas) {
    return !canvas.getContext('2d')
        .getImageData(0, 0, canvas.width, canvas.height).data
        .some(channel => channel !== 0);
}

function applyValidate(canvas) {
    var $name = $("#name");
    var $residentId = $("#residentId");
    var $isAuth = $("#isAuth");
    var $phone = $("#phone");
    var $address = $("#address");
    var $gender = $("input[name=gender]:checked");
    var $terms = $("input[name=terms]");

    if(!$name.val()) {
        alert("이름을 입력해주세요.");
        return false;

    }else if(!$residentId.val()) {
        alert("주민번호를 입력해주세요.");
        return false;

    } else if (!$phone.val()) {
        alert("핸드폰번호를 입력해주세요.");
        return false;

    } else if (!$isAuth.val()) {
        alert("핸드폰인증을 진행해주세요.");
        return false;

    } else if (!$address.val()) {
        alert("주소를 입력해주세요.");
        return false;

    } else if (!$gender.val()) {
        alert("성별을 선택해주세요.");
        return false;

    } else if (isCanvasBlank(canvas)) {
        alert("사인을 진행해주세요.");
        return false;

    } else if (!$terms.prop("checked")) {
        alert("약관을 동의헤주세요.");
        return false;


    } else {
        return true;
    }
}