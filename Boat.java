
public class Boat {
    public int len;         //boat length
    public Cell[] parts;    //parts of boat
    boolean orient;         //boat orientation
    public int getLen() {   //gets the length of the boat
        return len;
    }
    public void setLen(int len1) {         //sets the length of the boat
        len = len1;
    }
    public Cell[] getParts() {
        return parts;
    }                   //getters and setters
    public void setParts(Cell[] parts1) {
        parts = parts1;
    }
    public boolean getOrient() {
        return orient;
    }
    public void setOrient(boolean orient1) {
        orient = orient1;
    }
}
