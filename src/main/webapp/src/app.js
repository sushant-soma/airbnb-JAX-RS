const signedInDisplay = document.getElementById("signedIn");
const notSignedInDisplay = document.getElementById("notSignedIn");
const sellerDisplay = document.getElementById("isSeller");
const nameDisplay = document.getElementById("signedInName");
const productDisplay = document.getElementById("productDisplay");
const apiURL = "http://localhost:8080/store/api";
const categoriesNav = document.getElementById("categoriesNav");
const jumbotronDisplay = document.getElementById("jumbotronDisplay");
const searchBar = document.getElementById("searchBar");
const searchButton = document.getElementById("searchButton");
let categoriesAll = [];
let productsAll = [];
let categoriesWithProducts = {};
let user = null;
let isSeller = false;
let searchTerm = "";

const url = new URL(window.location);
let targetCategory = url.searchParams.get('category');
let targetSearch = url.searchParams.get('search');
let activeCategory = 0;
if (targetCategory) {
	activeCategory = targetCategory;
}
if (targetSearch) {
	searchTerm = targetSearch;
}
console.log(targetCategory, targetSearch);

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
		for (let category of categoriesAll) {
			categoriesWithProducts[category.id] = [];
		}
		makeCategories();
		makeJumbotrons();
	}).catch(e => console.error(e));
}

const makeCategories = () => {
	let innerHtml = "";
	innerHtml += `<a href="./index.html"><div class='category-label ${activeCategory == 0 ? 'active-category-label' : ''}'>All</div></a>`;
	for (let category of categoriesAll) {
		innerHtml += `<a href="./index.html?category=${category.id}"><div class='category-label ${activeCategory == category.id ? 'active-category-label' : ''}'>${category.name}</div></a>`;
	}
	categoriesNav.innerHTML = innerHtml;
}

getCategories();

const getProductElement = (product) => {
	return `
			<div class="product-card">
			<div class="product-image-holder">
				<img src="${product.cover}"
					alt="${product.name}" class="product-image">
			</div>
			<div class="product-title-section">
				<div class="product-title">${product.name}</div>
				${(product.available) ? '<div class="product-status">Available</div>' : '<div class="product-status out-of-stock">Out of stock</div>'}
			</div>
			<div class="product-price-section">
				<div class="product-price">
					₹ <span> ${Intl.NumberFormat('en-IN').format(product.price)} </span>
				</div>
				<div class="product-seller">
					Sold by - <span> ${product.seller.name} </span>
				</div>
			</div>
		</div>
	`;
}

const getProducts = () => {
	const productURL = apiURL + "/products";
	fetch(productURL).then(response => {
		if (response.status != 200) {
			return null;
		}
		return response.json();
	}).then(response => {
		productsAll = response;
		makeProducts(productsAll, 0);
		seperateProducts();
		makeProductsWithCategories();
		console.log(categoriesWithProducts);
	}).catch(e => console.error(e));
}


const makeProducts = (productsArray, id) => {
	let productHTML = "";
	if (productsAll) {
		for (let product of productsArray) {
			productHTML += getProductElement(product);
		}
	}
	const allProductsElement = document.createElement("div");
	allProductsElement.className = `main-content-item ${activeCategory != id ? 'hidden' : ''}`;
	allProductsElement.innerHTML = productHTML;
	productDisplay.appendChild(allProductsElement);
}

const makeProductsWithCategories = () => {
	for (let categoryId in categoriesWithProducts) {
		makeProducts(categoriesWithProducts[categoryId], categoryId);
	}
}

const seperateProducts = () => {
	categoriesWithProducts["-1"] = [];
	for (let product of productsAll) {
		if (product.name.toLowerCase().includes(searchTerm.toLowerCase())) {
			categoriesWithProducts[-1].push(product);
		}
		if (!categoriesWithProducts[product.category.id]) {
			categoriesWithProducts[product.category.id] = [];
		}
		categoriesWithProducts[product.category.id].push(product);
	}
}


const makeJumbotrons = () => {
	let jumbotronAll = document.createElement("div");
	jumbotronAll.innerText = "Browse Products";
	if (activeCategory != 0) {
		jumbotronAll.className = 'hidden';
	}
	jumbotronDisplay.appendChild(jumbotronAll);

	jumbotronAll = document.createElement("div");
	jumbotronAll.innerText = "Search Results";
	if (searchTerm != "") {
		jumbotronAll.innerText += " - " + searchTerm;
	}
	if (activeCategory != -1) {
		jumbotronAll.className = 'hidden';
	}
	jumbotronDisplay.appendChild(jumbotronAll);

	for (let category of categoriesAll) {
		const jumbotronElement = document.createElement("div");
		jumbotronElement.innerText = category.name;
		if (activeCategory != category.id) {
			jumbotronElement.className = 'hidden';
		}
		jumbotronDisplay.appendChild(jumbotronElement);
	}
}

const searchProduct = () => {
	let term = searchBar.value;
	window.location = `./index.html?category=-1&search=${term}`;
}

searchBar.addEventListener("keypress", (event) => {
  if (event.key === "Enter") {
    event.preventDefault();
	searchProduct();
  }
});

searchButton.addEventListener("click",()=>{
	searchProduct();
})

getProducts();



