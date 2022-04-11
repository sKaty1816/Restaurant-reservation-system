public interface Table_Interface {

    boolean isFree();

    int getId();

    void setId(int id);

    int getMaxCapacity();

    void setMaxCapacity(int maxCapacity);

    int getPeopleSeated();

    void setPeopleSeated(int peopleSeated);

    String getNameOfCurrentGuest();

    void setNameOfCurrentGuest(String name);

    boolean isSmoker();

    void setSmoker(boolean smoker);

    String getType();

    void setType(String type);

    void print();

}
