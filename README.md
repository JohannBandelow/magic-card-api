# Magic! The Card API
This is a simple API to register your Magic! The game cards, you can also create different decks and compare your cards to other players cards.

## TODO LIST:
- Add userId verification on deleting a card from userId
- Add String translation on saving a foreign card
- Add route to get card, from card Id
- Add route to list all cards,
- Add route to list other user decks and order the search by parameter
- Add route to edit a deck
- Fazer deploy na nuvem

## To use this API, first you need to configure your MySQL database:
To create DB simply access your MySQL console, then:

`create database *your_db*;` -- Creates the new database

`create user '*yourUser*'@'%' identified by '*yourPassword*';` -- Creates the user

`grant all on your_db.* to '*yourUser*'@'%';` -- Gives all privileges to the new user on the newly created database

After the DB was created/configured, head on to application.properties and replace with the names & password

## With everything configured you can run the MtgCardApiApplication.java

## USERS
### With the server running you can now create a User:

URL: `POST: http://localhost:8080/api/user/create`

Body:
`{
"name": "userName",
"email": "userEmail"
}`

> Two users cannot have the same email, only the first one will be created

### Get user by userEmail

URL: `GET: http://localhost:8080/api/user/{email}`

## CARDS
### With the user created you can start adding cards to the User inventory

URL: `POST: http://localhost:8080/api/card/add?userId={userId}`

Body: `{
    "name": "cardName",
    "language": "ENGLISH | PORTUGUESE | JAPANESE",
    "isFoil": true,
    "price": cardPrice,
    "expansionPack": "expansionPackName"}`

> UserID must exist

### Get cards from user

URL: `GET: http://localhost:8080/api/card?userId={userId}`

> UserID must exist

### Remove card from user inventory

URL: `DELETE: http://localhost:8080/api/card/remove?cardId=6`
 
## DECKS
### Create a deck

URL: `POST: http://localhost:8080/api/deck/create`

Body: `{
    "userId": userId,
    "name": "deckName",
    "cards": [cardId, cardId, cardId]}`

> Will only add the card to the deck if it belongs to the user
