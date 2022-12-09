package Day9;

import java.util.Objects;

public class Position {

    private Integer x;
    private Integer y;

    public Position(final Integer x, final Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public void setX(final Integer x) {
        this.x = x;
    }

    public void setY(final Integer y) {
        this.y = y;
    }
}
