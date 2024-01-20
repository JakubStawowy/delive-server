# Delive-Server - Java Spring Boot Project

## Description

Delive-Server is a monolithic Java Spring Boot Rest Api project that allows user to post orders for the transport of various items from point A to point B.
Project uses such features as geocoding (forward and reverse) for address lookup and user authentication based on JWT.

## Technologies used

- Java 8
- MySql database
- Hibernate
- JWT authorization
- [Positionstack](https://www.positionstack.com/)
- [MapQuest](https://www.mapquest.com/)
  
## Licenses

- **Positionstack:** [Positionstack License](https://www.positionstack.com/terms)
- **MapQuest:** [MapQuest Terms of Use](https://www.mapquest.com/legal/terms)

## Configuration

1. Clone the repository:

```bash

git clone https://github.com/JakubStawowy/delive-server.git
```

2. Navigate to the project directory:

```bash

cd delive-server
```
3. Configure the file src/main/resources/application.properties with your database properties and geocoding services api keys:
```properties

# database
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
spring.jpa.properties.hibernate.hbm2ddl.auto=update
#spring.jpa.properties.hibernate.hbm2ddl.auto=create
spring.jpa.database=mysql
spring.datasource.url=jdbc:mysql://localhost:3306/delive
spring.datasource.username=root
spring.datasource.password=

# Show SQL
#spring.jpa.show-sql=true
#spring.jpa.properties.hibernate.format_sql=true

# Geocoding layer order number
# In case of first layer geocoding api failure, set geocoding layer to second, and so on
geocoding.layer=first

# Position stack
positionStack.api.key=<your_api_key>
positionStack.api.uri=http://api.positionstack.com/v1/

# MapQuest
mapquest.api.key=<your_api_key>
mapquest.api.uri=http://open.mapquestapi.com/geocoding/v1/
```
## Features
- **User registration**
- **Endpoint:** `/api/register`
- **Method:** `POST`
- **Body:**
  ```json
  {
    "name": "example_username",
    "phone": "example_phone",
    "email": "example@email.com",
    "password": "example_password"
  }
  ```
- **Login**
- **Endpoint:** `/api/login`
- **Method:** `POST`
- **Body:**
  ```json
  {
    "email": "example@email.com",
    "password": "example_password"
  }
  ```
- **Get user details**
- **Endpoint:** `/api/users/details`
- **Method:** `GET`
- **Body:**
  ```json
  {
    "userId": example_user_id
  }
  ```
- **Get logged user details**
- **Endpoint:** `/api/users/details/loggedUser`
- **Method:** `GET`

- **Edit User:**
  - **Endpoint:** `/api/edit-user`
  - **Method:** `PUT`
  - **Body:**
    ```json
    {
      "username": "new_username",
      "password": "new_password",
      "email": "new_email@example.com"
    }
    ```

- **Get User Feedback:**
  - **Endpoint:** `/api/get-user-feedback`
  - **Method:** `GET`
  - **Body:** *Brak*

- **Add Feedback:**
  - **Endpoint:** `/api/add-feedback`
  - **Method:** `POST`
  - **Body:**
    ```json
    {
      "userId": "user_id",
      "comment": "Feedback comment",
      "rating": 5
    }
    ```

- **Edit Feedback:**
  - **Endpoint:** `/api/edit-feedback`
  - **Method:** `PUT`
  - **Body:**
    ```json
    {
      "feedbackId": "feedback_id",
      "comment": "Updated feedback comment",
      "rating": 4
    }
    ```

- **Save Order:**
  - **Endpoint:** `/api/save-order`
  - **Method:** `POST`
  - **Body:**
    ```json
    {
      "clientId": "client_id",
      "destination": "Delivery destination",
      "packageDetails": "Package details"
    }
    ```

- **Get All Orders:**
  - **Endpoint:** `/api/get-all-orders`
  - **Method:** `GET`
  - **Body:** *Brak*

- **Filter Orders:**
  - **Endpoint:** `/api/filter-orders`
  - **Method:** `POST`
  - **Body:**
    ```json
    {
      "filterCriteria": "Filter criteria"
    }
    ```

- **Get Order Details:**
  - **Endpoint:** `/api/get-order-details`
  - **Method:** `GET`
  - **Body:**
    ```json
    {
      "orderId": "order_id"
    }
    ```

- **Delete Order:**
  - **Endpoint:** `/api/delete-order`
  - **Method:** `DELETE`
  - **Body:**
    ```json
    {
      "orderId": "order_id"
    }
    ```

- **Get Client Distance from Order Destination:**
  - **Endpoint:** `/api/get-client-distance`
  - **Method:** `GET`
  - **Body:** *Brak*

- **Is Client in Required Destination Area:**
  - **Endpoint:** `/api/is-client-in-destination`
  - **Method:** `POST`
  - **Body:**
    ```json
    {
      "clientId": "client_id",
      "destinationArea": "required_destination_area"
    }
    ```

- **Pick Package:**
  - **Endpoint:** `/api/pick-package`
  - **Method:** `PUT`
  - **Body:**
    ```json
    {
      "orderId": "order_id",
      "courierId": "courier_id"
    }
    ```

- **Start Delivery:**
  - **Endpoint:** `/api/start-delivery`
  - **Method:** `PUT`
  - **Body:**
    ```json
    {
      "orderId": "order_id"
    }
    ```

- **Finish Delivery:**
  - **Endpoint:** `/api/finish-delivery`
  - **Method:** `PUT`
  - **Body:**
    ```json
    {
      "orderId": "order_id"
    }
    ```

- **Accept Delivery:**
  - **Endpoint:** `/api/accept-delivery`
  - **Method:** `PUT`
  - **Body:**
    ```json
    {
      "orderId": "order_id"
    }
    ```

- **Discard Delivery:**
  - **Endpoint:** `/api/discard-delivery`
  - **Method:** `PUT`
  - **Body:**
    ```json
    {
      "orderId": "order_id"
    }
    ```

- **Close Delivery:**
  - **Endpoint:** `/api/close-delivery`
  - **Method:** `PUT`
  - **Body:**
    ```json
    {
      "orderId": "order_id"
    }
    ```

- **Get Sent Messages:**
  - **Endpoint:** `/api/get-sent-messages`
  - **Method:** `GET`
  - **Body:** *Brak*

- **Get Received Messages:**
  - **Endpoint:** `/api/get-received-messages`
  - **Method:** `GET`
  - **Body:** *Brak*

- **Send Message:**
  - **Endpoint:** `/api/send-message`
  - **Method:** `POST`
  - **Body:**
    ```json
    {
      "senderId": "sender_id",
      "receiverId": "receiver_id",
      "message": "Hello, how are you?"
    }
    ```

- **Reply on Message:**
  - **Endpoint:** `/api/reply-on-message`
  - **Method:** `POST`
  - **Body:**
    ```json
    {
      "senderId": "sender_id",
      "receiverId": "receiver_id",
      "message": "I'm good, thank you!"
    }
    ```

