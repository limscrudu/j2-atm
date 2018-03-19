# j2-atm

## Build and Execute
Assumes that maven 3.0 and java 8 are on the path.
The project has been tested with maven 3.3.9 and Java (openjdk version 1.8.0_151 and oracle version 1.8.0_162)
To build and execute the project in the j2-atm directory

```
mvn package && java -jar target/j2-atm-0.0.1-SNAPSHOT.jar
```
To stop the execution use Ctrl-C

# REST API

## Inquire Balance

Determine the customer's account balance. The balance is reported as both the balance and the overdraft. An error is returned if invalid credentials are provided. 
* **URL**

  /atm/services/inquireBalance/{accountNo}/{pin}

* **Method:**
 
  `GET`
  
*  **URL Params**

   **Required:**
 
   `accountNo=[Integer]`
   `pin=[Integer]`

* **Data Params**

  None

* **Success Response:**
    
  * **Code:** 200 <br />
    **Content:** `{"resultStatus":"Request Succeeded","balance":0,"overdraft":100}`
 
* **Error Response:**

    * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{"resultStatus":"Invald Customer Credentials","balance":null,"overdraft":null}`

* **Sample Call:**

  ```curl -i -H "Content-Type: application/json" -X GET http://localhost:8080/atm/services/inquireBalance/123456789/1234```

* **Notes:**



## Withdraw Cash

Withdraw Cash from the customers account. The money must be available in the account. The exact amount in terms of note denominations and quantities must be available and the amount must be available in the ATM. The Service responds with details of the note denominations and their quantities

* **URL**

  /atm/services/withdrawCash/{accountNo}/{pin}

* **Method:**

  `POST`
  
*  **URL Params**

   **Required:**
 
   `accountNo=[Integer]`
   `pin=[Integer]`


* **Data Params**

  {"amount": "<Integer>"}'

* **Success Response:**
  
  * **Code:** 200 <br />
    **Content:** `{"resultStatus":"Request Succeeded","money":[{"denomination":50,"quantity":18}]}`
 
* **Error Response:**

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{"resultStatus":"Invald Customer Credentials","money":null}`

  OR

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{"resultStatus":"Withdrawal amount exceeds available cash","money":[]}`
    
  OR

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{"resultStatus":"Withdrawal amount exceeds customer balance","money":[]}`
 OR

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `"resultStatus":"Exact withdrawal amount cannot be dispensed","money":[]}`


* **Sample Call:**

  ```curl -d '{"amount": "900"}' -H "Content-Type: application/json" -X POST  http://localhost:8080/atm/services/withdrawCash/123456789/1234```

* **Notes:**

   
