import java.util.Vector;

public class Controller {
    public static void main(String[] args) {
        //args[0] -> nb_floors
        //args[1] -> nb_elevators
        //args[3] -> name_of_input_file

        int nb_floors = 0;
        int nb_elevators = 0;
        String filename = "";
        try {
            nb_floors = Integer.parseInt(args[0]);
            nb_elevators = Integer.parseInt(args[1]);
            filename = args[2];
        } catch (Exception e) {
            System.out.println("Bad argument");
            System.exit(-1);
        }
        Elevator.floors = nb_floors;
        Vector<Elevator> elevators = new Vector<Elevator>();
        for (int i = 1; i <= nb_elevators; i++)
            elevators.add(new Elevator(i));
        Vector<Task> tasks = Task.csv_to_list(filename);
        int time = 0;
        int timeout = Elevator.floors * 2;
        Task task;
        Vector<Task> done = new Vector<Task>();
        while (timeout != 0) {
            done = new Vector<Task>();
            System.out.println("Time : " + time);
            for (int index = 0; index < tasks.size(); index++) {
                task = tasks.elementAt(index);
                if (task.current(time)) {
                    int min = 0;
                    for (int i = 1; i < elevators.size(); i++) {
                        if (elevators.elementAt(i).getcost(task.from, task.to) < elevators.elementAt(min).getcost(task.from, task.to))
                            min = i;
                    }
                    elevators.elementAt(min).go(task.from, task.to);
                    done.add(task);
                }
            }
            tasks.removeAll(done);
            if (tasks.size() == 0) {
                // All tasks are planed
                timeout--;
            }

            // Run each elevator
            for (Elevator el : elevators)
                el.run();
            time++;
        }
    }
}