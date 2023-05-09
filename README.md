# ShopSpiker Applicaton

Java open source e-commerce software

## Pre-requisites

* Java 11
* Intellij
* Docker and docker compose
* MySql
* Redis
* ElasticSearch

## How to Run

Run below command in the root folder, It will build the Jars for all the modules
mvn clean install

Go inside Docker folder
docker network create shopspiker-network
docker-compose up

## Modules

- Site
- Auth
- Customer
- WishList
- Content

- Catalog
- Pricing
- Inventory
- Search
- CatalogAgreegator

- Cart
- Order
- Checkout

- Notification
- ImportExport
- ScheduledJob

 
