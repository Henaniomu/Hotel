package exam.hotel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Room {
    private IntegerProperty floor;
    private IntegerProperty num;
    private IntegerProperty bed;
    private BooleanProperty wc;
    private BooleanProperty shower;
    private BooleanProperty taken;

    public Room(int floor, int num, int bed, boolean wc, boolean shower) {
        this.floor = new SimpleIntegerProperty();
        this.num = new SimpleIntegerProperty();
        this.bed = new SimpleIntegerProperty();
        this.wc = new SimpleBooleanProperty();
        this.shower = new SimpleBooleanProperty();
        this.shower.set(shower);
        this.wc.set(wc);
        this.floor.set(floor);
        this.num.set(num);
        this.bed.set(bed);
    }



    public IntegerProperty floorProperty() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor.set(floor);
    }

    public boolean isTaken() {
        return taken.get();
    }

    public BooleanProperty takenProperty() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken.set(taken);
    }

    public boolean isShower() {
        return shower.get();
    }

    public BooleanProperty showerProperty() {
        return shower;
    }

    public void setShower(boolean shower) {
        this.shower.set(shower);
    }

    public void setNum(int num) {
        this.num.set(num);
    }

    public void setBed(int bed) {
        this.bed.set(bed);
    }

    public boolean isWc() {
        return wc.get();
    }

    public BooleanProperty wcProperty() {
        return wc;
    }

    public void setWc(boolean wc) {
        this.wc.set(wc);
    }

    public IntegerProperty numProperty() {
        return num;
    }

    public IntegerProperty bedProperty() {
        return bed;
    }

    @Override
    public String toString() {
        return floor.toString() + num.toString();
    }
}
