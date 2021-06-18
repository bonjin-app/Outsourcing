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

})();

function isCanvasBlank(canvas) {
    return !canvas.getContext('2d')
        .getImageData(0, 0, canvas.width, canvas.height).data
        .some(channel => channel !== 0);
}

function applyValidate(canvas) {
    var $name = $("#name");
    var $residentId = $("#residentId");
    var $phone = $("#phone");
    var $address = $("#address");
    var $addressDetail = $("#addressDetail");
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

    } else if (!$address.val()) {
        alert("주소를 입력해주세요.");
        return false;

    } else if (!$addressDetail.val()) {
        alert("상세주소를 입력해주세요.");
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