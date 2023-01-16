# elevator
Sample Elevator Simulation

## Introduction
This api assists in simple simulation of elevator functionality it does the following tasks:
1) Calling elevator from any floor;
2) Getting information about the state of the elevator

## Requirements:
- Summarized request paths and description

| Method | Path           | Description                                                                 |
|--------|----------------|-----------------------------------------------------------------------------|
| GET    | /elevator      | Returns a paginated list of all the elevators,including the state,direction |
| POST   | /elevator      | Adds new elevator                                                           |
| GET    | /elevator/{id} | Retrieves the full details of a single elevator                             |
| POST   | /elevator/call | Calls an elevator from it's floor to the passangers floor                   |

- Note
1) Ensure you create a new elevator before making a call 
2) You can configure the number of floors in the properties  file





