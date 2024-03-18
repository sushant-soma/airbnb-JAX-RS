const signedInDisplay = document.getElementById("signedIn");
const notSignedInDisplay = document.getElementById("notSignedIn");
const sellerDisplay = document.getElementById("isSeller");
const nameDisplay = document.getElementById("signedInName");
const name = document.getElementById("name");
const price = document.getElementById("price");
const cover = document.getElementById("cover");
const status = document.getElementById("status");
const description = document.getElementById("description");
const category = document.getElementById("category");
const submit = document.getElementById("submit");
const apiURL = "http://localhost:8080/store/api";
let categoriesAll = {};
let user = null;
let isSeller = false;

const checkLoggedIn = () => {
	const authURL = apiURL + "/auth";
	fetch(authURL).then(response => {
		if (response.status == 204) {
			signedInDisplay.style.display = "none";
			notSignedInDisplay.style.display = "flex";
			window.location = "./index.html"
			return null;
		}
		signedInDisplay.style.display = "flex";
		notSignedInDisplay.style.display = "none";
		return response.json();
	}).then(response => {
		if (response) {
			user = response.user;
			if (user.name) {
				nameDisplay.innerText = user.name;
			}
			if (response.seller) {
				sellerDisplay.style.display = "inline";
				isSeller = true;
			} else {
				sellerDisplay.style.display = "none";
				isSeller = false;
				window.location = "./index.html"
			}
		}
		console.log(response);
		console.log(user, isSeller);
	}).catch(e => console.error(e));
}

checkLoggedIn();

const signOutUser = () => {
	const signOutURL = apiURL + "/auth/signout";
	fetch(signOutURL).then(response => response.text())
		.then(response => {
			user = null;
			isSeller = false;
			signedInDisplay.style.display = "none";
			notSignedInDisplay.style.display = "flex";
			alert("Logged out successfully!");
		})
}


const getCategories = () => {
	const categoryURL = apiURL + "/category";
	fetch(categoryURL).then(response => {
		if (response.status != 200) {
			return null;
		}
		return response.json();
	}
	).then(response => {
		categoriesAll = response;
		console.log(categoriesAll);
		makeCategories();
	}).catch(e => console.error(e));
}
getCategories();

const makeCategories = () => {
	let innerOptions = "";
	for (let i = 0; i < categoriesAll.length; i++) {
		innerOptions += `<option value="${i}">${categoriesAll[i].name}</option>`
	}
	category.innerHTML = innerOptions;
}

const addProduct = () => {
	const addURL = apiURL + "/products";
	const data = {
		"name": name.value,
		"price": parseFloat(price.value),
		"cover": cover.value,
		"description": description.value,
		"category": categoriesAll[category.value],
		"available": status.value === "true",
	}
	console.log(data);
	fetch(addURL, {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(data)
	}).then(response => {
		if (response.status == 200) {
			alert("Product added successfully!");
			return;
		}
		alert("Couldn't add the product");
		return;
	}).catch(e => {
		alert("Couldn't add the product");
		console.error(e);
	})
}

submit.addEventListener("click", () => {
	addProduct();
})
