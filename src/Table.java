public class Table implements Table_Interface {
    private int id;
    private String type;
    private int maxCapacity;
    private int peopleSeated;
    private boolean smoker;
    private String nameOfCurrentGuest;

    Table() {
        this.id = 0;
        this.maxCapacity = 0;
        this.peopleSeated = 0;
        this.nameOfCurrentGuest = null;
        this.type = null;
    }

    Table(int id, int maxCapacity, String smoke) {
        this.id = id;
        this.maxCapacity = maxCapacity;
        this.peopleSeated = 0;
        if (smoke.equals("smoker"))
            this.smoker = true;
        else if (smoke.equals("non-smoker"))
            this.smoker = false;
    }

    public boolean isFree() {
        if (this.peopleSeated != 0)
            return false;
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getPeopleSeated() {
        return peopleSeated;
    }

    public void setPeopleSeated(int peopleSeated) {
        this.peopleSeated = peopleSeated;
    }

    public String getNameOfCurrentGuest() {
        return this.nameOfCurrentGuest;
    }

    public void setNameOfCurrentGuest(String name) {
        this.nameOfCurrentGuest = name;
    }

    public boolean isSmoker() {
        return smoker;
    }

    public void setSmoker(boolean smoker) {
        this.smoker = smoker;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void print() {
        System.out.print("ID:" + this.id + " Capacity: " + this.maxCapacity);
        if (isSmoker())
            System.out.print(" Smoker");
        else
            System.out.print(" Non-smoker");
    }
}
