package resources;

import java.util.ArrayList;
import java.util.List;


import pojo.addPlace.AddPlace;
import pojo.addPlace.DeletePlace;
import pojo.addPlace.Location;
import pojo.addPlace.UpdatePlace;
import pojo.ecommerce.Login;
import pojo.ecommerce.OrderDetails;
import pojo.ecommerce.Orders;

public class TestDataBuild {

	
	public AddPlace addPlacePayload(String name, String language, String address) {
		
		AddPlace a1 = new AddPlace();
		Location l =new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		a1.setLocation(l);
		a1.setName(name);
		a1.setAddress(address);
		a1.setPhone_number("(+91) 7305686048");
		a1.setAccuracy("50");
		List<String> mylist = new ArrayList<String>();
		mylist.add("shoe park");
		mylist.add("shop");
		a1.setTypes(mylist);
		a1.setLanguage(language);
		a1.setWebsite("http://google.com");
		
		return a1;
		
	}
	
	public DeletePlace deletePlacePayLoad(String place_id) {
		
		DeletePlace delete = new DeletePlace();
		delete.setPlace_id(place_id);
		return delete;	
	} 
	
	public UpdatePlace updatePlacePayLoad(String place_id, String address) {
		
		UpdatePlace update =new UpdatePlace();
		update.setPlace_id(place_id);
		update.setAddress(address);
		update.setKey("qaclick123");
		return update;
	}
	
	public Login ecomLoginPayLoad(String userName, String password) {
		
		Login l=new Login();
		l.setUserEmail(userName);
		l.setUserPassword(password);
		
		return l;
	}
	
	public Orders craeteOrderPayLoad(String productId) {
		
		OrderDetails orderDet = new OrderDetails();
		orderDet.setCountry("India");
		orderDet.setProductOrderedId(productId);
		
		List<OrderDetails> orderList = new ArrayList<OrderDetails>();
		
		orderList.add(orderDet);
		
		Orders order = new Orders();
		order.setOrders(orderList);
		
		return order;
		
	}
}
