
## Smart Campus: Sensor \& Room Management API

### 

### Project Overview

## 

#### This project is a scalable RESTful API developed for the university’s Smart Campus system. It is implemented using Java JAX-RS (Jersey) with Maven as the build tool.

#### 

#### The system is designed to manage a large number of Rooms and Sensors (such as Temperature and CO₂ sensors). It follows REST best practices and includes:



* #### Sub-resource locators for handling sensor readings
* #### Centralized exception mapping for consistent error responses
* #### A clean and modular architecture suitable for large-scale systems

### 

### Build \& Run Instructions



#### To run the project locally, follow these steps:



1. #### Clone the project repository

##### &#x09;git clone https://github.com/dilni/Smart-Campus-api.git





#### 2\. Move into the project directory



##### &#x09;cd D:\\IIT Campus\\Git\_hub\\Smart-Campus-api



#### 3\. Build the project using Maven



##### &#x09;mvn clean install



#### 4.Start the application



##### &#x09;mvn exec:java



#### 5\. Once the server is running, the API will be available at:



##### &#x09;http://localhost:8080/api/v1









### API Testing (Postman / cURL Scenarios)



#### Step 1: API Entry Point (HATEOAS Check)



##### A JSON response is returned that includes API metadata (such as version "v1") along with links to key resources like rooms and sensors, illustrating the use of HATEOAS.

* ##### GET - http://localhost:8080/api/v1

#### 





#### Step 2: Room Management

#### Create a new room

* #### POST - http://localhost:8080/api/v1/rooms
* #### Body - {"id": "WBS-101","name": "Grand Lecture Theatre","capacity": 300}



* #### &#x20;GET - http://localhost:8080/api/v1/rooms/WBS-101
* #### The complete JSON representation of the newly created room.



#### 

#### Step 3: Sensor Operations

#### Add a Sensor

* #### POST - http://localhost:8080/api/v1/sensors
* #### Body - {"id": "TEMP-001", "type": "Temperature", "status": "ACTIVE", "roomId": "WBS-101"}



#### Add a sensor with a invalid roomId.

* #### POST - http://localhost:8080/api/v1/sensors
* #### Body - {"id": "TEMP-001", "type": "Temperature", "status": "ACTIVE", "roomId": "NON-EXISTENT"}

#### 

* #### GET - http://localhost:8080/api/v1/sensors?type=Temperature

#### Demonstrates that only sensors of the specified type are retrieved.



#### 

#### Step 4: Sensor Updates and Readings

#### Update the currentValue.

* #### POST - http://localhost:8080/api/v1/TEMP-001/read
* #### Body - {"id": "READ-001","timestamp": 1713870000000,"value": 24.5}
* #### GET - http://localhost:8080/api/v1/TEMP-001



#### Update the sensor status to "MAINTENANCE".

* #### PUT - http://localhost:8080/api/v1/sensors/TEMP-001
* #### Headers - Content-Type to application/json
* #### Body - {"id": "TEMP-001", "type": "Temperature", "status": "MAINTENANCE", "currentValue": 24.5, "roomId": "WBS-101"}

#### 

#### Add a reading to the "MAINTENANCE" sensor.

* #### POST - http://localhost:8080/api/v1/sensors/TEMP-001/read
* #### Body - {"id": "READ-001","timestamp": 1713870000000,"value": 24.5}







#### Step 5: Deletion and Dependency Handling

#### Return the sensor status to "ACTIVE".

* #### PUT - http://localhost:8080/api/v1/sensors/TEMP-001
* #### Headers - Content-Type to application/json
* #### Body - {"id": "TEMP-001", "type": "Temperature", "status": "ACTIVE", "currentValue": 24.5, "roomId": "WBS-101"}

#### 

#### Delete the room with the ACTIVE sensor.

* #### DELETE - http://localhost:8080/api/v1/rooms/WBS-101

#### 

#### Delete the sensor first and then delete the room.

* #### DELETE - http://localhost:8080/api/v1/sensors/TEMP-001
* #### DELETE - http://localhost:8080/api/v1/rooms/WBS-101



## 

## Conceptual Report



#### Part 1



##### Q1:

##### Resource classes in JAX-RS have a default scope of per request, which means that a new instance is generated for each HTTP request made. This strategy ensures that unintended sharing of variables does not occur between clients. In my application, the data to be managed, namely the rooms and sensors, is maintained in memory collections.

##### 

##### This approach raises concerns about race condition errors since many clients may try to modify the same data at the same time. Therefore, it will be necessary to handle the access to collections in a thread-safe manner.

##### 

* ##### Each request generates a new resource class
* ##### Collections may still pose concurrency problems
* ##### Thread-safe access methods are necessary

##### 



##### Q2:

##### HATEOAS enables the inclusion of hyperlinks within responses, allowing clients to navigate through the available resources dynamically. This removes the need for relying solely on external documentation by discovering the endpoints directly from the responses.

##### 

##### In the case of the current project, there are possibilities for including hypermedia links leading to relevant resources.

##### 

* ##### Self-descriptive API
* ##### Reduces reliance on static documentation
* ##### Dynamic discovery of possible actions



#### 

#### Part 2



##### Q1:

##### There are two approaches to implementing an API: sending either the ID of a resource or the full object itself. While using only IDs is more effective in terms of saving bandwidth, it would entail sending more requests.

##### 

##### For my API design, I have chosen to use full objects in order to simplify its usage and make it more user-friendly.

##### 

* ##### ID Only → saves bandwidth but needs multiple requests
* ##### Full Objects → simpler usage but wastes more bandwidth
* ##### Performance vs. Convenience Trade-off

##### 

##### 

##### Q2:

##### DELETE requests are classified as idempotent, where repeating the same request will lead to the same system state. In my code, deleting the resource means it will be deleted from the list of resources. Sending the same DELETE request again will not affect the system state.

##### 

##### While the result may vary (like getting 404 status), the system state will remain the same, thus meeting idempotency.

##### 

* ##### First request deletes the resource
* ##### Further requests don’t affect the system anymore
* ##### System state remains the same after multiple requests







#### Part 3



##### Q1:

##### The @Consumes("application/json") indicates that this API can only process JSON data. Any other form of data sent by the client, whether it be XML or plaintext, cannot be processed by the server due to its format.

##### 

##### Here, the response given by the JAX-RS framework would be an HTTP 415 Unsupported Media Type response.

##### 

* ##### Guarantees proper format processing
* ##### Prevents improper parsing
* ##### Provides HTTP 415 response for unsupported formats

##### 

##### 

##### Q2:

##### The use of query parameters (for example, /sensors?type=temperature) is a more suitable method to filter resources, whereas path parameters (such as /sensors/type/CO2) should be used to identify resources.

##### 

##### The approach that I have adopted uses query parameters for filtering since they provide flexibility and are optional.

##### 

* ##### QueryParam → filtering/searching
* ##### PathParam → identifying resources
* ##### Query parameters enhance flexibility and readability



#### 

#### Part 4



##### Q1:

##### The sub-resource locator design pattern makes it possible to divide the functions of the API into smaller resource classes rather than concentrating everything in one class.

##### 

##### With this solution, it is possible to use different classes for various aspects of the API (such as sensors and sensor data).

##### 

* ##### Separates concerns
* ##### Easier to maintain and scale
* ##### Avoids monolithic resource classes







#### Part 5



##### Q1:

##### In the case of a valid syntactical request which points to an inexistent entity (for example, a sensor allocated to a room which does not exist), it would be better to use HTTP 422 Unprocessable Entity rather than 404 Not Found.

##### 

##### This is due to the fact that the endpoint in question exists and the request syntax is valid; however, the information provided is logically wrong.

##### 

* ##### 404: endpoint/resource not found
* ##### 422: request is valid but semantically wrong

##### 

##### 

##### Q2:

##### Exposing any of those raw messages will expose you to potential security threats since your application may expose any of the internal system details like class names or file directories.

##### 

##### In my case, exception mappers would be used in order to display meaningful messages rather than the raw details of the exceptions.

##### 

* ##### Internal system details are exposed
* ##### Help attackers spot vulnerabilities
* ##### Should be replaced with generic errors

##### Q3:

##### Using JAX-RS filters for logging is advantageous because they provide a centralized and reusable way to handle cross-cutting concerns like request and response logging. Instead of adding Logger.info() statements inside every resource method, a single filter can automatically intercept all incoming requests and outgoing responses. This keeps the resource classes clean and focused only on business logic, improving overall code quality and maintainability.

##### Filters also ensure consistency, since every request and response is logged in the same format without relying on developers to manually add logging in each method. This reduces the risk of missing logs or having inconsistent logging behavior across different endpoints. Additionally, if logging requirements change (e.g., adding headers or timestamps), updates only need to be made in one place.

##### 

* ##### Centralizes logging logic in one component
* ##### Keeps resource methods clean and focused
* ##### Ensures consistent logging across all endpoints
* ##### Reduces code duplication and human error
* ##### Makes future changes easier and more maintainable
