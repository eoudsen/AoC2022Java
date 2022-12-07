package Day7;

public class Result {

    private Integer size;

     public Result(final Integer size) {
        this.size = 0;
    }

    public Integer getSize() {
         return this.size;
    }

    public void addSize(final Integer size) {
         this.size += size;
    }
}
