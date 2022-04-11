public class VIP_Table extends Table implements VIP_Table_Interface {
    private boolean nice_view;

    VIP_Table(int id, int maxCapacity, String smoke, String view) {
        setId(id);
        setMaxCapacity(maxCapacity);
        setPeopleSeated(0);

        if (smoke.equals("smoker"))
            setSmoker(true);
        else if (smoke.equals("non-smoker"))
            setSmoker(false);

        if (view.equals("view"))
            this.nice_view = true;
        else if (view.equals("no-view"))
            this.nice_view = false;
    }

    public boolean getView() {
        return this.nice_view;
    }

    public void print() {
        super.print();
        if (this.nice_view)
            System.out.print(" Nice view");
        else
            System.out.print(" No-view");
    }
}
