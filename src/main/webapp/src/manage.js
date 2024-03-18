const signedInDisplay = document.getElementById("signedIn");
const notSignedInDisplay = document.getElementById("notSignedIn");
const sellerDisplay = document.getElementById("isSeller");
const nameDisplay = document.getElementById("signedInName");
const apiURL = "http://localhost:8080/store/api";
const categoriesNav = document.getElementById("categoriesNav");
const manageDisplay = document.getElementById("manageDisplay");
let categoriesAll = {};
let productsAll = {};
let user = null;
let isSeller = false;

const checkLoggedIn = () => {
	const authURL = apiURL + "/auth";
	fetch(authURL).then(response => {
		if (response.status != 200) {
			signedInDisplay.style.display = "none";
			notSignedInDisplay.style.display = "flex";
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
			window.location = "./index.html"
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
                    <div class="manage-cta">
                        <a href="./edit.html?id=${product.id}">
                            <div class="button register-button">Edit</div>
                        </a>
                        <div class="button" onclick="deleteProduct(${product.id},'${product.name}')">Delete</div>
                    </div>
                </div>
            </div>`;
}


const getProducts = () => {
	const productURL = apiURL + "/products/manage";
	fetch(productURL).then(response => {
		if (response.status != 200) {
			return null;
		}
		return response.json();
	}).then(response => {
		productsAll = response;
		makeProducts();
	}).catch(e => console.error(e));
}

const deleteProduct = (id, name) => {
	let confirmation = confirm("Are you sure to delete " + name + " ?");
	if (confirmation) {
		let productURL = apiURL + "/products/" + id;
		fetch(productURL, {
			method: "DELETE"
		}).then(response => {
			if (response.status == 200) {
				alert(name + " was deleted successfully!")
				window.location = "./manage.html"
				return;
			} else {
				alert("Couldn't delete - " + name);
			}
			return response.json()
		}).catch(e => {
			alert("Couldn't delete - " + name);
		});
	}

}

const makeProducts = () => {
	let productHTML = "";
	if (productsAll) {
		for (let product of productsAll) {
			productHTML += getProductElement(product);
		}
	}
	manageDisplay.innerHTML = productHTML;
}

getProducts();



