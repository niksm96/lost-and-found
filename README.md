
# Lost & Found Items

The Lost & Found Items is a java backend application designed to process a list of items that are lost in a pdf format.

The following software & IDE are required to build and run the project.

    - Java 21
    - IntelliJ IDE (or any IDE)
    - MySQL Developer
    - Postman (to test API's)


## API Reference

#### Upload list of lost items.

```http
  POST /lostAndFound/admin/uploadLostItems
  
  Request Body: Select Type File and upload the text file. 
```

| URL Path  | Method Type | Description          |
|:----------|:------------|:---------------------|
| `/lostAndFound/admin/uploadLostItems` | `POST`      | Uploading Lost Items |

#### Get all items

```http
  GET /lostAndFound/public/lostItems
```

| URL Path  | Method Type | Description        |
|:----------|:------------|:-------------------|
| `/lostAndFound/public/lostItems` | `GET`       | Get All Lost Items |

#### Claim Lost items

```http
  POST /lostAndFound/public/claimLostItems
  
  Request Body:
  {
    "userId":1,
    "item":
    {
        "itemName":"Laptop",
        "quantity":1,
        "place":"Airport"

    }
}
```

| URL PATH                              | Method Type | Description      |
|:--------------------------------------|:------------|:-----------------|
| `/lostAndFound/public/claimLostItems` | `POST`      | Claim Lost Items |

#### Retrieve claimed item list
```http
GET /lostAndFound/admin/getClaimedItems
```
| Parameter | Type  | Description                            |
| :-------- |:------|:---------------------------------------|
| `/lostAndFound/admin/getClaimedItems` | `GET` | Fetches all the items claimed by users |

#### Retrieve all user information
```http
GET /lostAndFound/public/users
```
| URL                          | Method Type | Description           |
|:-----------------------------|:------------|:----------------------|
| `/lostAndFound/public/users` | `GET`       | Fetches all the users |


## Installation

`Maven Build & Install
Run LostAndFoundApplication.java`

## Authors

- [@niksm96](https://www.github.com/niksm96)


## 🚀 About Me
Full Stack Java Developer | Ex - Lead Software Engineer at Fidelity Investments, Bangalore | Currently moved to Netherlands and looking for opportunities as a Software Developer.


## License

[MIT](https://choosealicense.com/licenses/mit/)

