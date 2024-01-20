# Delive-Server - Java Spring Boot Project

## Description

Delive-Server is a monolithic Java Spring Boot Rest Api project that allows user to post orders for the transport of various items from point A to point B.
Project uses such features as geocoding (forward and reverse) for address lookup and user authentication based on JWT.

## Technologies used

- Java 8
- Spring Boot
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
spring.jpa.database=mysql
spring.datasource.url=jdbc:mysql://localhost:3306/delive
spring.datasource.username=root
spring.datasource.password=

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

4. Run the project using the command:
```bash

mvn spring-boot:run
```

## Endpoints
- **User registration**
  - **Endpoint:** `/api/register`
  - **Method:** `POST`
  - **Body:**
    ```json
    {
      "name": "example_username",
      "phone": "example_phone_number",
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
      "userId": 1
    }
    ```
- **Get logged user details**
  - **Endpoint:** `/api/users/details/loggedUser`
  - **Method:** `GET`
  - **Request parameters:**
    - *empty*

- **Edit User:**
  - **Endpoint:** `/api/users/edit`
  - **Method:** `PUT`
  - **Body:**
    ```json
    {
      "name": "new_username",
      "phone": "new_phone_number",
      "password": "new_password",
      "email": "new_email@example.com"
    }
    ```

- **Get User Feedback:**
  - **Endpoint:** `/api/feedback/user`
  - **Method:** `GET`
  - **Request parameters:**
    - `userId` 

- **Add Feedback:**
  - **Endpoint:** `/api/feedback/add`
  - **Method:** `POST`
  - **Body:**
    ```json
    {
      "userId": "user_id",
      "content": "Feedback content",
      "rate": 5,
      "userId":  1,
      "authorId": 2
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
  - **Endpoint:** `/api/orders/save`
  - **Method:** `POST`
  - **Body:**
    ```json
    {
      "destinationFrom": {
        "latitude": 2.312431,
        "longitude": 2.3145132,
        "address": "address"
      },
      "destinationTo": {
        "latitude": 2.312431,
        "longitude": 2.3145132,
        "address": "address"
      },
      "packages": [
        {
          "length": 10,
          "width": 20,
          "height": 30,
          "lengthUnit": "cm",
          "widthUnit": "cm",
          "heightUnit" : "cm"
        }
      ],
      "authorId": 1,
      "salary": 340.00,
      "weightUnit": "weight_unit",
      "requireTransportWithClient": false
    }
    ```

- **Get All Orders:**
  - **Endpoint:** `/api/orders`
  - **Method:** `GET`
  - **Request parameters:**
    - *empty*

- **Filter Orders:**
  - **Endpoint:** `/api/orders/filter`
  - **Method:** `GET`
  - **Request parameters:**
    - `initialAddress`
    - `finalAddress`
    - `minimalSalary`
    - `maxWeight`
    - `requireTransportWithClient`
    - `sortBySalary`
    - `sortByWeight`

- **Get Order Details:**
  - **Endpoint:** `/api/orders/order`
  - **Method:** `GET`
  - **Request parameters:**
    - `orderId`

- **Delete Order:**
  - **Endpoint:** `/api/orders/delete`
  - **Method:** `DELETE`
  - **Request parameters:**
    - `orderId`

- **Get Client Distance from Order Destination:**
  - **Endpoint:** `/api/distance/get`
  - **Method:** `GET`
  - **Request parameters:**
    - `orderId`
    - `clientLongitude`
    - `clientLatitude`

- **Is Client in Required Destination Area:**
  - **Endpoint:** `/api/distance/isInArea`
  - **Method:** `POST`
  - **Request parameters:**
    - `orderId`
    - `clientLongitude`
    - `clientLatitude`

- **Pick Package:**
  - **Endpoint:** `/api/delivery/pick`
  - **Method:** `PUT`
  - **Request parameters:**
    - `deliveryId`

- **Start Delivery:**
  - **Endpoint:** `/api/delivery/start`
  - **Method:** `PUT`
  - **Request parameters:**
    - `deliveryId`

- **Finish Delivery:**
  - **Endpoint:** `/api/delivery/finish`
  - **Method:** `PUT`
  - **Request parameters:**
    - `deliveryId`
    - `clientLatitude`
    - `clientLongitude`

- **Accept Delivery:**
  - **Endpoint:** `/api/delivery/accept`
  - **Method:** `PUT`
  - **Request parameters:**
    - `deliveryId`

- **Discard Delivery:**
  - **Endpoint:** `/api/delivery/discard`
  - **Method:** `PUT`
  - **Request parameters:**
    - `deliveryId`

- **Close Delivery:**
  - **Endpoint:** `/api/delivery/close`
  - **Method:** `PUT`
  - **Request parameters:**
    - `deliveryId`

- **Get Sent Messages:**
  - **Endpoint:** `/api/messages/sent`
  - **Method:** `GET`
  - **Request parameters:**
    - *empty*

- **Get Received Messages:**
  - **Endpoint:** `/api/messages/received`
  - **Method:** `GET`
  - **Request parameters:**
    - *empty*

- **Send Message:**
  - **Endpoint:** `/api/message/send`
  - **Method:** `POST`
  - **Body:**
    ```json
    {
      "senderId": "sender_id",
      "receiverId": "receiver_id",
      "message": "message",
      "orderId": "order_id",
      "vehicleRegistrationNumber": "vehicleRegistrationNumber",
      "phoneNumber": "phoneNumber"
    }
    ```

## Authors
- **Jakub Stawowy** (stawowykuba@gmail.com)
