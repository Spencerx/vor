# JSON messaging schemas and examples

- [Location message](#location-message) - [example message](#location-example)
- [Room message](#room-message) - [example message](#room-example)
- [Toilet message](#toilet-message) - [example message](#toilet-example)
- [Button message](#button-message) - [example message](#button-example)
- [Pool message](#pool-message) - [example message](#pool-example)
- [Food message](#food-message) - [example message](#food-example)

<h2 id="location-message">Location message</h2>

####Schema:
```
{
    "$schema": "http://json-schema.org/draft-04/schema#",  
    "title": "Location message",  
    "description": "Server publishes client's indoor location",  
    "type": "object",  
    "properties": {  
        "email": {  
            "description": "A label for client", 
            "type": "string"  
        },
        "type": {  
            "description": "The type of the message",  
            "constant": "location"  
        },
        "x": {  
            "description": "The calculated x-axis position of the client",  
            "type": "float"  
        },
        "y": {  
            "description": "The calculated y-axis position of the client",  
            "type": "float"  
        },
        "floor":{  
            "description": "The floor where beacons locate",  
            "type": "integer"  
        },
        "timestamp":{
            "description": "Unix timestamp when the location is received",
            "type": "long"
        }
    },  
    "required": ["email", "type", "x", "y", "floor"]
}
```

<h4 name="location-example">Example:</h4>

```
{
    "email": "user@mail.com",
    "type": "location",
    "x": 2.453, 
    "y": 1.224,
    "floor": 8,
    "timestamp": 1453734586035
}
```


<h2 id="room-message">Room message</h2>

####Schema:
```
{
    "$schema": "http://json-schema.org/draft-04/schema#",  
    "title": "Room message",  
    "description": "Information of room's conditions",  
    "type": "object",  
    "properties": {  
         "id": {  
            "description": "An unique identifier for a room",  
            "type": "string"  
        },
        "type": {  
            "description": "The type of the message",  
            "type": "room"  
        },
        "reserved": {  
            "description": "Current reservation status of the room",  
            "type": "boolean"  
        },
        "temperature": {  
            "description": "Room temperature(C°)",  
            "type": "float"  
        },
        "light": {  
            "description": "Amount of light(lumen) in the room",  
            "type": "integer"  
        },
        "dioxide": {  
            "description": "Amount of dioxide((percentage of CO2²) in the room ",  
            "type": "float"  
        },
        "noise": {  
            "description": "Amount of noise(dB) in the room",  
            "type": "float"  
        }
    },  
    "required": ["id", "type", "reserved", "temperature", "light", "dioxide", "noise"]
}
```

<h4 name="room-example">Example:</h4>

```
{
    "id": "room-1a",
    "type": "room"
    "reserved": true, 
    "temperature": 20.1,
    "light": 1002,
    "dioxide": 20.2,
    "noise":45.2
}
```


<h2 id="toilet-message">Toilet message</h2>

####Schema:

```
{
    "$schema": "http://json-schema.org/draft-04/schema#",  
    "title": "Toilet message",  
    "description": "Information of toilet's conditions",  
    "type": "object",  
    "properties": {  
         "id": {  
            "description": "An unique identifier for a toilet",  
            "type": "string"  
        },
        "type": {  
            "description": "The type of the message",  
            "constant": "toilet"  
        },
        "reserved": {  
            "description": "Current reservation status of the toilet",  
            "type": "boolean"  
        },
        "methane": {  
            "description": "Amount of methane(%) in the room",  
            "type": "float"  
        }
    },  
    "required": ["id", "type", "reserved", "temperature", "light", "dioxide", "noise"]
}
```

<h4 name="toilet-example">Example:</h4>

```
{
    "id": "toilet-1a",
    "type": "toilet",
    "reserved": true, 
    "methane": 0.2
}
```


<h2 id="button-message">Button message</h2>

####Schema:

```
{
    "$schema": "http://json-schema.org/draft-04/schema#",  
    "title": "Button message",  
    "description": "Button publishes on button up event",  
    "type": "object",  
    "properties": {  
         "id": {  
            "description": "An unique identifier for a button",  
            "type": "string"  
        },
        "type": {  
            "description": "The type of the message",  
            "constant": "button"  
        }
    },  
    "required": ["id", "type"]
}
```

<h4 name="button-example">Example:</h4>

```
{
    "id": "button-1",
    "type": "button"
}
```


<h2 id="pool-message">Pool message</h2>

####Schema:

```
{
    "$schema": "http://json-schema.org/draft-04/schema#",  
    "title": "Pool message",  
    "description": "Image message sent by pool camera client",  
    "type": "object",  
    "properties": {  
        "id": {  
            "description": "An unique identifier for a pool camera",  
            "type": "string"  
        },
        "type": {  
            "description": "The type of the message",  
            "constant": "pool"  
        },
        "image": {  
            "description": "Image as base64",  
            "type": "string"  
        }
    },  
    "required": ["id", "type", "image"]
}
```

<h4 name="pool-example">Example:</h4>

```
{
    "id": "pool",
    "type": "pool",
    "image": "/9j/4AAQSkZJRgABAQEASABIAAD/4QCAR.."
}
```

<h2 id="food-message">Food message</h2>

####Schema:

```
{
    "$schema": "http://json-schema.org/draft-04/schema#",  
    "title": "Food message",  
    "description": "Image message sent by food camera client",   
    "type": "object",  
    "properties": {  
        "id": {  
            "description": "An unique identifier for a food camera client",  
            "type": "string"  
        },
        "type": {  
            "description": "The type of the message",  
            "constant": "food"  
        },
        "image": {  
            "description": "Image as base64",  
            "type": "string"  
        }
    },  
    "required": ["id", "type", "image"]
}
```

<h4 name="food-example">Example:</h4>

```
{
    "id": "food",
    "type": "food",
    "image": "/9j/4AAQSkZJRgABAQEASABIAAD/4QCAR.."
}
```

