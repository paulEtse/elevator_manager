
# Elevators Monitor
This system can control the hardware of elevators. Each elevator's hardware is only supposed to have operations ```goUp()``` & ```goDown()```. During the execution it loads all tasks received and assign them to the best elevator that can run them.
Convention: the first floor is numbered 0.
### requirements
> jdk >= 8
## Compilation
```javac Task.java Elevator.java Controller.java ```
## Execution
```java Controller <nb_floors> <nb_elevators> <filename>```<br>
``` nb_floors ``` is the number of floors in the building
``` nb_elevator ```is the number of elevators in the building
``` filename ```is the name of the CSV file that contains all tasks assign to the elevator with their assignation time
For instance ```java Controller 6 2 task.csv```
> The system will display the workflow of each elevator. This will contain the execution time, different stops and the movements (call to functions **goUp()** & **goDown()**).
## Details in the code
The code contains three sources. They represent the different classes of our system which are:  **Elevator**, **Tasks**  and **Controller**(main class). 
* Elevator :  this class designs the elevator. It offers the operation ```go``` that allow a user to go from a floor to another floor. It has the operation getcost that evaluate the cost in terms of waiting time of a movement asked by a User. For more detail in the design of the elevator, look in annexes
* Tasks : the class is used to represent different tasks that the system must run  and their corresponding running time.
* Controller : It is the main class of the system. It reads different tasks and add them at their date to the best elevator that can run them. The evaluation is based on the optimization criteria presented in next section.
### Optimization Criteria
The optimization criteria that we consider here is the waiting time of users. We design our system to minimize the time of waiting of a user that want to from a floor to another.
Note: Others optimization can be to choose the limit to move of the elevators. 
Image of the matrix SD matrix.
### Possible improvements in the current system
These are the elements we would like to improve if we had much time : 
* Consider stop times in our calculations
* Add a UI (User Interface) to the app
* Adapt the optimization criteria the client's needs
* Add comment to code to make it more understandable

## Annexes
### Task's file format (CSV)
Note: no need to order by Date.
|Date|From                         |To    |
|----|-----------------------------|------|
|0   |1                            |3     |
|1   |4                            |1     |
|1   |3                            |4     |
|5   |1                            |3     |
|3   |0                            |5     |
|4   |1                            |3     |
|4   |5                            |2     |
### Elevator Design
The elevator is represented by a matrix 4 * (<nb_floors> - 1). The lines 0 & 2 of matrix are used for up movements and the lines 1 & 3 for down movements. We use two rows for each direction because we need to save all movements that the elevator can't run immediately. [for instance, let us the elevator is at floor 2 and it is going to floor 4. And the user ask to go from floor 1 to floor 3]. The columns represent a floor. The  routing in the matrix is shown in the figure below. By default, all cells are filled with ```Forbidden``` and the elevator is at floor 0 [(0, 0) in our matrix].
|   | 0 | 1 | 2 | .... | <nb_floors -1 > |
|---|---|---|---|------|-----------------|
| 0 |   |   -------------->            |
| 1 |   |   <--------------            |
| 2 |   |   -------------->            |
| 3 |   |   <--------------            |

Example: the elevator is at (0,0) and a user ask to go from floor 3 to floor 1. The matrix will be filled like that: 
|   | 0  | 1    | 2  | 3    | .... | <nb_floors -1 > |
|---|----|------|----|------|------|-----------------|
| 0 | Go | Go   | Go | Go   |      |                 |
| 1 |    | Stop | Go | Stop |      |                 |
| 2 |    |      |    |      |      |                 |
| 3 |    |      |    |      |      |                 |
Output will be (system with only one elevator)
```
Time : 0
Elevator1 goUp() : 0 -> 1
Time : 1
Elevator1 goUp() : 1 -> 2
Time : 2
Elevator1 goUp() : 2 -> 3
Time : 3
Elevator1 Stop at 3
Elevator1 goDown() : 2 <- 3
Time : 4
Elevator1 goDown() : 1 <- 2
Time : 5
Elevator1 Stop at 1
Time : 6
Time : 7
Time : 8
Time : 9
Time : 10
Time : 11

Process finished with exit code 0
```

## Contact 
For any question you can contact:
* E. Paul [etse@etud.insa-toulouse.fr](mailto:etse@etud.insa-toulouse.fr)
