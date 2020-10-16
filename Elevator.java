enum Operation {
    Stop, Go, Forbidden
}

class Coord {
    int x;
    int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return this.x == coord.x && this.y == coord.y;
    }
}

public class Elevator {
    public static int floors; /*0 to n-1; n number of floors of the buildings*/

    private int id;
    private Coord current;
    private Operation[][] buffer;

    public Elevator(int id) {
        this.id = id;
        buffer = new Operation[4][floors];
        for (int i = 0; i < buffer.length; i++) {
            for (int j = 0; j < buffer[i].length; j++)
                buffer[i][j] = Operation.Forbidden;
        }
        current = new Coord(0, 0);
    }

    public Coord aside(Coord coord) {
        if (coord.x != 3)
            return new Coord(coord.x + 1, coord.y);
        return new Coord(0, coord.y);
    }

    public Coord next(Coord coord) {
        Coord suiv;
        if (coord.x == 0 || coord.x == 2) {
            if (coord.y == floors - 1)
                suiv = new Coord(coord.x + 1, coord.y);
            else
                suiv = new Coord(coord.x, coord.y + 1);
        } else {
            if (coord.y == 0)
                suiv = new Coord(coord.x + 1, coord.y);
            else
                suiv = new Coord(coord.x, coord.y - 1);
        }
        if (equivalent(suiv) != null)
            suiv = equivalent(suiv);
        return suiv;
    }

    public Coord equivalent(Coord coord) {
        if (coord.equals(new Coord(0, floors - 1)))
            return new Coord(1, floors - 1);

        if (coord.equals(new Coord(2, floors - 1)))
            return new Coord(3, floors - 1);

        if (coord.equals(new Coord(1, 0)))
            return new Coord(2, 0);

        if (coord.equals(new Coord(3, 0)))
            return new Coord(0, 0);
        return null;
    }


    public int getcost(int from, int to) {
        Coord[] tab = int_to_coord(from, to);
        return cost(current, tab[0]);
    }

    public int cost(Coord from, Coord to) {
        Coord coord = from;
        int cost = 0;
        boolean aside;
        while (!coord.equals(to)) {
            aside = false;
            Coord next = next(coord);
            if (isForbidden(next)) {
                if (coord.x == 0 || coord.x == 2) {
                    if (coord.y >= to.y) {
                        next = aside(coord);
                        aside = true;
                    }
                } else {
                    if (coord.y <= to.y) {
                        next = aside(coord);
                        aside = true;
                    }
                }
            }
            if (!aside)
                cost++;
            coord = next;
        }
        return cost;
    }

    public void addSteps(Coord from, Coord to) {
        //current -> from
        Coord coord = from;
        while (!coord.equals(to)) {
            setGo(coord);
            Coord next = next(coord);
            if (isForbidden(next)) {
                if (coord.x == 0 || coord.x == 2) {
                    if (coord.y >= to.y)
                        next = aside(coord);
                } else {
                    if (coord.y <= to.y)
                        next = aside(coord);
                }
            }
            coord = next;
        }
        setStop(to);
    }

    public Coord[] int_to_coord(int from, int to) {
        Coord cFrom;
        Coord cTo;
        int abs;
        if (from < to) {
            // 0 or 2
            if (current.x == 0 && current.y <= from ||
                    current.x == 2 && current.y > from ||
                    current.x == 3)
                abs = 0;
            else
                abs = 2;
        } else {
            // 1 or 3
            if (current.x == 1 && current.y >= from ||
                    current.x == 3 && current.y < from ||
                    current.x == 0)
                abs = 1;
            else
                abs = 3;
        }
        cFrom = new Coord(abs, from);
        cTo = new Coord(abs, to);
        if (equivalent(cFrom) != null)
            cFrom = equivalent(cFrom);
        if (equivalent(cTo) != null)
            cTo = equivalent(cTo);
        Coord[] tab = new Coord[2];
        tab[0] = cFrom;
        tab[1] = cTo;
        return tab;
    }

    public void go(int from, int to) {
        if (from == to)
            return;
        Coord tab[] = int_to_coord(from, to);
        addSteps(current, tab[0]);
        addSteps(tab[0], tab[1]);
    }

    public boolean isStop(Coord coord) {
        return buffer[coord.x][coord.y] == Operation.Stop;
    }

    public void setStop(Coord coord) {
        buffer[coord.x][coord.y] = Operation.Stop;
    }

    public void setGo(Coord coord) {
        if (isForbidden(coord))
            buffer[coord.x][coord.y] = Operation.Go;
    }

    public void setForbidden(Coord coord) {
        buffer[coord.x][coord.y] = Operation.Forbidden;
    }

    public boolean isForbidden(Coord coord) {
        return buffer[coord.x][coord.y] == Operation.Forbidden;
    }

    public Coord move(Coord coord, boolean stopped) {
        Coord next = next(coord);
        Coord aside;
        boolean stop = false;
        if (isStop(coord) && !stopped) {
            stop = true;
            System.out.println("Elevator" + id + " Stop at " + coord.y);
        }
        setForbidden(coord);
        if (isForbidden(next)) {
            aside = aside(coord);
            if (!isForbidden(aside))
                return move(aside, stop);
            else
                return coord;
        }
        return next;
    }

    public void run() {
        if (!isForbidden(current)) {
            Coord old = current;
            current = move(current, false);
            //goUp()
            if (old.y < current.y)
                System.out.println("Elevator" + id + " goUp() : " + old.y + " -> " + current.y);
            //goDown()
            else if (old.y > current.y)
                System.out.println("Elevator" + id + " goDown() : " + current.y + " <- " + old.y);
        }
    }
}


