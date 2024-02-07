public class random {
    private long seed;

    public random(long seed) {
        this.seed = seed;
    }

    public int nextInt() {
        seed = (seed * 16807) % 2147483647;
        return (int) seed;
    }
}
