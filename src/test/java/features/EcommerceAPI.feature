Feature: Validating Ecommerce Site API's

@EcommerceLogin
Scenario Outline: Verify login is successfully in ecommerce site

    Given Ecommerce Add Product Payload with "<userName>" "<password>"
    When user call "LoginAPI" with "Post" http request
    Then the API call got success with status code 200
    And verify access_token created using "LoginAPI"
    Then "message" in response body is "Login Successfully"
    Examples:
    | userName              | password    |
    |rahulshetty@gmail.com  | Iamking@000 |
    
Scenario: Verify to craete a product in ecommerce site

   Given Ecommerce Add Product Payload with form data
   When user call "CreateProductAPI" with "Post" http request
   Then the API call got success with status code 201
   And  verify "productId" created from response body using "CreateProductAPI"
   
Scenario: Verify to craete an order in ecommerce site

   Given Ecommerce create order Payload with request
   When user call "CreateOrderAPI" with "Post" http request
   Then the API call got success with status code 201
   And  verify "orderId" created from response body using "CreateOrderAPI"
   And  verify "orderview" created from response body using "GetOrderAPI"
   Then the API call got success with status code 200
   When deleted the "Product"
   When user call "DeleteProductAPI" with "DELETE" http request
   Then the API call got success with status code 200
   When deleted the "Order"
   When user call "DeleteOrderAPI" with "DELETE" http request
   Then the API call got success with status code 200