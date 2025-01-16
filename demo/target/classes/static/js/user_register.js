const name = document.getElementById("name");
const password = document.getElementById("password");
const passwordConf = document.getElementById("passwordConfirmation");

const passwordValidation = document.getElementById("password_validation");
const minLengthValidation = document.getElementById("min_length_validation");

const registrationForm = document.getElementById("registration_form");
const inputMistake = document.getElementById("input_mistakes");

// イベントリスナー
password.addEventListener("blur", validatePasswords);
password.addEventListener("blur", validatePasswordLength);
passwordConf.addEventListener("input", validatePasswords);

registrationForm.addEventListener("submit", (e) => {
    // すべてのバリデーションを実行し、結果を取得

    const isValidPasswords = validatePasswords();
    const isValidPasswordLength = validatePasswordLength();

    // どれか一つでも無効なら、フォーム送信を中止
    if (!(isValidPasswords && isValidPasswordLength)) {
        e.preventDefault();
        inputMistake.style.display = "block";
        console.log("event_prevented");
    }
});

// バリデーション関数

function validatePasswords() {
    if (password.value !== passwordConf.value) {
        passwordValidation.style.display = "block";
        return false;
    } else {
        passwordValidation.style.display = "none";
        return true;
    }
}

function validatePasswordLength() {
    console.log("validatePasswordLengthは呼ばれています.");
    console.log("passwordLength:" + password.value.length);
    if (password.value.length < 8) {
        minLengthValidation.style.display = "block";
        return false;
    } else {
        minLengthValidation.style.display = "none";
        return true;
    }
}
