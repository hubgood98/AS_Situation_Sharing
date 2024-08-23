var useridChecked = false;
var emailChecked = false;

function checkUserid() {
    console.log('checkUserid called');  // 로그
    var userid = document.getElementById('userid').value;

    if (userid === '') {
        alert('로그인 ID를 입력하세요.');
        return;
    }

    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/api/user/checkUserid?userid=' + encodeURIComponent(userid), true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                if (response.exists) {
                    alert('이미 존재하는 아이디입니다.');
                    useridChecked = false; // 중복 확인 실패
                } else {
                    alert('사용 가능한 아이디입니다.');
                    useridChecked = true; // 중복 확인 성공
                }
            } else {
                alert('아이디 중복 확인에 실패했습니다. 다시 시도해주세요.');
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
    xhr.open('GET', '/api/user/checkEmail?email=' + encodeURIComponent(email), true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                if (response.exists) {
                    alert('이미 존재하는 이메일입니다.');
                    emailChecked = false; // 중복 확인 실패
                } else {
                    alert('사용 가능한 이메일입니다.');
                    emailChecked = true; // 중복 확인 성공
                }
            } else {
                alert('이메일 중복 확인에 실패했습니다. 다시 시도해주세요.');
            }
        }
    };
    xhr.send();
}

function validateForm() {
    if (!useridChecked) {
        alert('아이디 중복 확인을 해주세요.');
        return false; // 폼 제출 중지
    }
    if (!emailChecked) {
        alert('이메일 중복 확인을 해주세요.');
        return false; // 폼 제출 중지
    }
    return true; // 폼 제출 진행
}
