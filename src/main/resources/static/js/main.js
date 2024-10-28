document.addEventListener("DOMContentLoaded", () => {
    const button = document.getElementById("button-login");
    const email = document.getElementById("email");
    const password = document.getElementById("password");
    const form = document.getElementById("form-submit-login");
    const message = document.getElementById("message");

    const defMessage = message.innerText;

    form.addEventListener("submit", (evt) => {
        evt.preventDefault();
        let textButton = button.innerText;
        let color = button.style.backgroundColor;
        button.innerText = "Waiting..."
        button.style.backgroundColor = "#727272FF"
        setTimeout(() => login(email, password, button, textButton, color), 2000)
    })

    email.addEventListener("keyup", () => {
        if (!email.value.trim().match(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/) && email.value.length !== 0) {
            email.classList.add("invalid");
            message.innerText = "Formato de correo incorrecto";
        } else {
            email.classList.remove("invalid");
            message.innerText = defMessage;
        }
    })

    password.addEventListener("input", () => {
        if (password.value.trim().length === 0 || password.value.trim().length < 8 || password.value.trim().length > 20) {
            password.classList.add("invalid");
            message.innerText = "Mínimo 8 y máximo 20 carácteres";
            if(password.value.length === 0) {
                password.classList.remove("invalid");
                message.value = defMessage;
            }
        } else {
            password.classList.remove("invalid");
            message.value = defMessage;
        }
    })

})

const login = (email, password, button, textButton, color) => {
    fetch("http://localhost:8088/api/auth/login", {
        method: "POST",
        body: {email, password}
    })
        .then((res) => {
            if (res.ok) alert("login success");
            if (!res.ok) alert("has produced an exception");
            console.log(res);
            res.json().then(r => console.log(r));
        })
        .catch(err => {
            console.log(err)
        })
        .finally(() => {
            button.innerText = textButton;
            button.style.backgroundColor = color;
        })
}
