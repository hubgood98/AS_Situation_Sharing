var usernameChecked = false;
var emailChecked = false;

function checkUsername() {
    console.log('checkUsername called');  // 로그
    var username = document.getElementById('username').value;

    if (username === '') {
        alert('사용자 ID를 입력하세요.');
        return;
    }

    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/user/checkUsername?username=' + encodeURIComponent(username), true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var response = JSON.parse(xhr.responseText);
            if (response.exists) {
                alert('이미 존재하는 아이디입니다.');
                usernameChecked = false; // 중복 확인 실패
            } else {
                alert('사용 가능한 아이디입니다.');
                usernameChecked = true; // 중복 확인 성공
            }
        }
    };
    xhr.send();
}

function checkEmail() {
    console.log('checkEmail called');  // 로그
    var email = document.getElementById('email').value;

    if (email === '') {
        alert('이메일을 입력하세요.');
        return;
    }

    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/user/checkEmail?email=' + encodeURIComponent(email), true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var response = JSON.parse(xhr.responseText);
            if (response.exists) {
                alert('이미 존재하는 이메일입니다.');
                emailChecked = false; // 중복 확인 실패
            } else {
                alert('사용 가능한 이메일입니다.');
                emailChecked = true; // 중복 확인 성공
            }
        }
    };
    xhr.send();
}

function validateForm() {
    if (!usernameChecked) {
        alert('아이디 중복 확인을 해주세요.');
        return false; // 폼 제출 중지
    }
    if (!emailChecked) {
        alert('이메일 중복 확인을 해주세요.');
        return false; // 폼 제출 중지
    }
    return true; // 폼 제출 진행
}
