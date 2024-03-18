const signedInDisplay = document.getElementById("signedIn");
const notSignedInDisplay = document.getElementById("notSignedIn");
const sellerDisplay = document.getElementById("isSeller");
const nameDisplay = document.getElementById("signedInName");
const previewContent = document.getElementById("previewContent");
const name = document.getElementById("name");
const price = document.getElementById("price");
const cover = document.getElementById("cover");
const status = document.getElementById("status");
const description = document.getElementById("description");
const category = document.getElementById("category");
const submit = document.getElementById("submit");
const apiURL = "http://localhost:8080/store/api";
let productSelected = {};
let categoriesAll = {};
let user = null;
let isSeller = false;


const url = new URL(window.location);
let targetId = url.searchParams.get('id');

if (!targetId) {
	window.location = "./index.html";
}

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

const updateProduct = () => {
	const addURL = apiURL + "/products";
	const data = {
		"id": parseInt(targetId),
		"name": name.value,
		"price": parseFloat(price.value),
		"cover": cover.value,
		"description": description.value,
		"category": categoriesAll[category.value],
		"available": status.value === "true",
	}
	console.log(data);
	fetch(addURL, {
		method: "PUT",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(data)
	}).then(response => {
		if (response.status == 200) {
			alert("Product updated successfully!");
			window.location = "./manage.html";
			return;
		}
		alert("Couldn't update the product");
		return;
	}).catch(e => {
		alert("Couldn't update the product");
		console.error(e);
	})
}


const getProductElement = (product) => {
	return `<div class="manage-item">
                <div class="manage-cover">
                    <img src="${product.cover}"
                        alt="${product.name}">
                </div>
                <div class="manage-item-details">
                    <div class="manage-details">
                        <div class="product-title-section">
                            <div class="product-title">${product.name}</div>
            				${(product.available) ? '<div class="product-status">Available</div>' : '<div class="product-status out-of-stock">Out of stock</div>'}
                        </div>
                        <div class="product-price-section">
                            <div class="product-price">
                                ₹ <span> ${Intl.NumberFormat('en-IN').format(product.price)} </span>
                            </div>
                        </div>
                        <div class="product-seller">
                            Category - <span> ${product.category.name} </span>
                        </div>
                    </div>
                </div>
            </div>`;
}


const getProductById = (id) => {
	const productURL = apiURL + "/products/" + id;
	fetch(productURL).then(response => {
		if (response.status != 200) {
			return null;
		}
		return response.json();
	}).then(response => {
		productSelected = response;
		console.log("selected: ", productSelected);
		if (response) {
			setProductFields();
		}
	}).catch(e => console.error(e));
}


const setProductFields = () => {
	previewContent.innerHTML = getProductElement(productSelected);
	name.value = productSelected.name;
	price.value = productSelected.price;
	cover.value = productSelected.cover;
	description.value = productSelected.description;
	category.value = productSelected.category.id - 1;
	if (productSelected.available) {
		status.value = "true";
	} else {
		status.value = "false";
	}
}

getProductById(targetId);

submit.addEventListener("click", () => {
	updateProduct();
})