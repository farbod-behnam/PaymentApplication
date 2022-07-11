# Payment Application

Payment uses RabbitMQ to communicate with [OnlineShop](https://github.com/farbod-behnam/OnlineShop). It is responsible for holding user's wallet and transactions.
Each user has a wallet to see if it has enough credit for the order that requested on the OnlineShop and RabbitMQ
is responsible for sending back and forth the order request from OnlineShop to PaymentApplication.

Also, if a new user is created or updated the Payment application uses RabbitMQ to stay updated with OnlineShop, and
it uses PostgreSQL to store data.

### RabbitMQ

### Payment Application uses
- **payment_app_order_queue** to send updated order transaction status back to OnlineShop

### Payment Application listens to

- **online_shop_order_queue** to receive order request from OnlineShop
- **online_shop_user_queue** to receive newly created or updated user data to from OnlineShop



