const role = document.getElementById("role");
const email = document.getElementById("email");
const password = document.getElementById("password");
const name = document.getElementById("name");
const submit = document.getElementById("submit");
const apiURL = "http://localhost:8080/store/api";

const register = (pathURL) => {
	const data = {
		"email": email.value,
		"name": name.value,
		"password": password.value
	}
	const registerURL = apiURL + pathURL;
	fetch(registerURL, {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(data)
	}).then(response => {
		if (response.status == 200) {
			alert("Registered successfully! You will be redirected to login");
			window.location = "./login.html";
		} else {
			alert("Couldn't register!");
		}
	}).catch(e => console.error(e));
}

submit.addEventListener("click", () => {
	let registerRole = role.value;
	if (registerRole == "seller") {
		register("/seller/signup");
	} else if (registerRole == "customer") {
		register("/customer/signup");
	}
})