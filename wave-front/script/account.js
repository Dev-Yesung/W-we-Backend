const baseUrl = "http://localhost:8080";

// 회원가입 이벤트 함수
function checkDuplicateEmail() {
  const email = document.querySelector("#signup-email").value;
  const queryParams = {
    email: email,
  };
  const queryString = new URLSearchParams(queryParams).toString();

  fetch(`${baseUrl}/api/auth/duplicate?${queryString}`, {
    method: "GET",
  }).then((response) => {
    if (response.status === 200) {
      console.log("사용 가능한 이메일입니다.");
    } else {
      console.log("이미 등록된 이메일입니다.");
    }
  });
}

function requestSignupCertificationCode() {
  const email = document.querySelector("#signup-email").value;
  const certificationType = "signup";
  const certificationCodeRequest = {
    email: email,
    certificationType: certificationType,
  };

  fetch(`${baseUrl}/api/auth/certification`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(certificationCodeRequest),
  }).then((response) => {
    if (response.status === 200) {
      response.json().then((data) => {
        console.log(data);
      });
    } else {
      console.log("인증코드 전송에 실패했습니다.");
    }
  });
}

function submitSignupForm() {
  const email = document.querySelector("#signup-email").value;
  const nickname = document.querySelector("#signup-nickname").value;
  const certificationCode = document.querySelector(
    "#signup-certification"
  ).value;
  const signupRequest = {
    email: email,
    nickname: nickname,
    certificationCode: certificationCode,
  };

  fetch(`${baseUrl}/api/auth/signup`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(signupRequest),
  }).then((response) => {
    if (response.status === 201) {
      response.json().then((data) => {
        console.log(data);
      });
    } else {
      console.log("회원가입 실패");
    }
  });

  return false;
}

// 로그인 이벤트 함수
function submitLoginForm() {
  const email = document.querySelector("#login-email").value;
  const certificationCode = document.querySelector("#login-password").value;
  const loginRequest = {
    email: email,
    certificationCode: certificationCode,
  };

  fetch(`${baseUrl}/api/auth/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(loginRequest),
  }).then((response) => {
    if (response.status === 200) {
      response.json().then((data) => {
        console.log(data);
      });
    } else {
      console.log("로그인 실패");
    }
  });

  return false;
}
