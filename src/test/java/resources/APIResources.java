package resources;

public enum APIResources {
	
	AddPlaceAPI("/maps/api/place/add/json"),
	GetPlaceAPI("/maps/api/place/get/json"),
	DeletePlaceAPI("/maps/api/place/delete/json"),
	UpdatePlaceAPI("/maps/api/place/update/json"),
	LoginAPI("/api/ecom/auth/login"),
	CreateProductAPI("/api/ecom/product/add-product"),
	CreateOrderAPI("/api/ecom/order/create-order"),
	GetOrderAPI("/api/ecom/order/get-orders-details"),
	DeleteProductAPI("/api/ecom/product/delete-product/{prodId}"),
	DeleteOrderAPI("/api/ecom/order/delete-order/{orderId}");
	
	private String resource;

	APIResources(String resource){
		this.resource=resource;
	}
	
	public String getResource() {
		
		return resource;
	}

}
