public class Bus {
    private String number;
    private String name;
    private String route;
    private int capacity;

    public Bus(String number, String name, String route, int capacity) {
        this.number = number;
        this.name = name;
        this.route = route;
        this.capacity = capacity;
    }

    public String getNumber() { return number; }
    public String getName() { return name; }
    public String getRoute() { return route; }
    public int getCapacity() { return capacity; }

    public void setNumber(String number) { this.number = number; }
    public void setName(String name) { this.name = name; }
    public void setRoute(String route) { this.route = route; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
}