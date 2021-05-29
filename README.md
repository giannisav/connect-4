# Connect 4

Implementation of **Connect 4** board game

### Game rules

The Rules Are Simple

Connect 4 rules are easy to understand. In fact, it is in the name. To win Connect 4, all you have to do is connect 4 of your pieces in a row, much the same as tic tac toe. This can be done horizontally, vertically or diagonally. Each player will drop in one piece at a time. This will give you a chance to either build your row, or stop your opponent from getting 4 in a row.

Try it at [https://app-connect-4.herokuapp.com/](https://app-connect-4.herokuapp.com/)

## Requirements

+ **Java - 1.11.x**
+ **Maven - 3.x.x**

## Steps to run locally

1. **Clone the application**
2. **Open** `src/main/resources/application.properties`
3. **Comment line number 5 to disable production profile**
4. **Run the app with:** `mvn spring-boot:run`
5. **The app will start running at http://localhost:8080**

# API Guide

## Player API

| ENDPOINT | METHOD | PARAM TYPE | DESCRIPTION | RETURNS | STATUS |
| --- | --- | --- | --- | --- | --- |
| `/api/players/{nickname}` | POST | nickname-String | Register the player if does not exist | PlayerDTO | 200(OK) |
| `/api/players/statistics` | GET | Request param: sortingType optional, enum (points, wins,loses, draws, gamesPlayed, winsVsAi).<br>Request param: sortingOrder optional, enum (asc, desc) |  Returns sorted statistics for all the players | List<PlayerDTO> | 200(ΟΚ) |


## PlayerDTO

```json
"PlayerDTO": {
            "type": "object",
            "properties": {
                "draws": {
                    "type": "integer"
                },
                "gamesPlayed": {
                    "type": "integer"
                },
                "loses": {
                    "type": "integer"
                },
                "nickname": {
                    "type": "string"
                },
                "points": {
                    "type": "integer"
                },
                "wins": {
                    "type": "integer"
                }
            },
            "title": "PlayerDTO"
        }
```

## Game API

| ENDPOINT | METHOD | PARAM TYPE | DESCRIPTION | RETURNS | STATUS |
| --- | --- | --- | --- | --- | --- |
| `/api/games/{nickname}` | POST | nickname-String | Create a game for the user with the specific nickname | GameResponseDTO | 200(OK), 400(BAD REQUEST) |
| `/api/games` | GET |  | Rerurns all available games to join | List<GameResponseDTO> | 200(OK) |
| `/api/games/join/{nickname}/{id}` | POST | nickname-String,<br>id-Intrger | Add player with the specific nickname to the game with the specific id | GameResponseDTO | 200(OK), 400(BAD REQUEST), 401(UNAUTHORIZED) |
| `/api/games/play/{nickname}/{uuid}/{id}/{column}` | POST | nickname-String,<br>uuid-String,<br>id-Integer,<br>column-Integer | Plays for the specific player with this uuid to the game with this id the specific move | GameResponseDTO | 200(OK), 400(BAD REQUEST), 401(UNAUTHORIZED) |
| `/api/games/{nickname}/{uuid}/{id}` | GET | nickname-String,<br>id-Integer | Returns all the needed information about the game | GameResponseDTO | 200(ΟΚ), 401(UNAUTHORIZED) |
| `/api/games/cheat/{nickname}/{uuid}/{id}` | POST | nickname-String,<br>uuid-String,<br>id-Integer | Plays the best move for the player | GameResponseDTO | 200(OK), 400(BAD REQUEST), 401(UNAUTHORIZED) |
| `/api/games/ai/{nickname}` | POST | nickname-String | Create a game for the user with the specific nickname versus AI-Robot | GameResponseDTO | 200(OK), 400(BAD REQUEST) |
| `/api/games/ai/play/{nickname}/{uuid}/{id}/{column}` | POST | nickname-String,<br>uuid-String,<br>id-Integer,<br>column-Integer | Plays for the specific player with this uuid to the game with this id the specific move and opponent the AI-Robot | GameResponseDTO | 200(OK), 400(BAD REQUEST), 401(UNAUTHORIZED) |
| `/api/games/ai/cheat/{nickname}/{uuid}/{id}` | POST | nickname-String,<br>uuid-String,<br>id-Integer | Plays the best move for the player in a game versus AI-Robot | GameResponseDTO | 200(OK), 400(BAD REQUEST), 401(UNAUTHORIZED) |
| `/api/games/needsUpdate/{id}/{numOfMoves}` | GET | id-Intrger,<br>numOfMoves-Integer | True if the game with this id and this numOfmoves is not up to date, False otherwise | Boolean | 200(OK) |            
            
## GameResponseDTO

```json
"GameResponseDTO": {
            "type": "object",
            "properties": {
                "board": {
                    "type": "array",
                    "items": {
                        "type": "array",
                        "items": {
                            "type": "string"
                        }
                    }
                },
                "gameState": {
                    "type": "string"
                },
                "id": {
                    "type": "integer"
                },
                "nextMoveNickname": {
                    "type": "string"
                },
                "redPlayerNickname": {
                    "type": "string"
                },
                "redUuid": {
                    "type": "string"
                },
                "yellowPlayerNickname": {
                    "type": "string"
                },
                "yellowUuid": {
                    "type": "string"
                }
            },
            "title": "GameResponseDTO"
        }
```

## Spring MVC Controllers

The following endpoints are used from the app to return view freemarker templates and mostly use form parameters and model attributes.

| ENDPOINT | METHOD | DESCRIPTION |
| --- | --- | --- |
| `/` | GET | Returns home page |
| `/register` | GET | Returns register page |
| `/register` | POST | Register the player if does not exist and returns home page |
| `/create` | GET | Returns creare game page |
| `/create` | POST | Creates a game and returns board page |
| `/join` | GET | Returns join page |
| `/games` | GET | Displays all the available games to join page |
| `/join` | POST | Join player to game and returns board page |
| `/board` | GET | Returns board's current state |
| `/play` | POST | Plays the selected move for the player and returns board page |
| `/cheat` | POST | Plays the best move for the player and returns board page |
| `/statistics` | GET |  Returns the page with players' statistics |


