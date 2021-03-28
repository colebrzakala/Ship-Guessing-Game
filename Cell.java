
public class Cell {
    public int row;     //variables
    public int col;
    public char status;
    public char get_status() {      //getters and setters
        return status;
    }
    public void set_status(char c) {
        status = c;
    }
    public int get_row() {
        return row;
    }
    public void set_row(int row) {
        this.row = row;
    }
    public int get_col() {
        return col;
    }
    public void set_col(int col) {
        this.col = col;
    }
    public Cell(int row,int col, char status) {     //constructor
        this.row = row;
        this.col = col;
        this.status = status;
    }
}
