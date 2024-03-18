const role = document.getElementById("role");
const email = document.getElementById("email");
const password = document.getElementById("password");
const submit = document.getElementById("submit");
const apiURL = "http://localhost:8080/store/api";

const register = (pathURL) => {
	const data = {
		"email": email.value,
		"password": password.value
	}
	const loginURL = apiURL + pathURL;
	fetch(loginURL, {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(data)
	}).then(response => {
		if (response.status == 200) {
			alert("Logged In successfully! You will be redirected to home page");
			window.location = "./index.html";
		} else {
			alert("Couldn't Login!");
		}
	}).catch(e => console.error(e));
}

submit.addEventListener("click", () => {
	let registerRole = role.value;
	if (registerRole == "seller") {
		register("/seller/signin");
	} else if (registerRole == "customer") {
		register("/customer/signin");
	}
})